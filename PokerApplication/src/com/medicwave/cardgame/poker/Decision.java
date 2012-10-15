package com.medicwave.cardgame.poker;

import com.medicwave.cardgame.poker.PokerClientBase.BettingAnswer;

/**
 * Class describes the decission(Raise, check , fold ,all-in) that agent has to
 * make in five-cards draw poker
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class Decision {

    // =========================================================================
    // Constants
    // =========================================================================
    private static final float RANGE_20 = (float) 0.20;
    private static final float RANGE_43 = (float) 0.43;
    private static final float RANGE_70 = (float) 0.705;
    private static final float RANGE_90 = (float) 0.90;
    private static final float RANGE_95 = (float) 0.95;
    private static final float RANGE_97 = (float) 0.97;
    private static final float RANGE_98 = (float) 0.98;
    private static final float RANGE_99 = (float) 0.938;
    // =========================================================================
    // Constructor
    // =========================================================================

    public Decision() {
    }

    // =========================================================================
    // Methods
    // =========================================================================
    public static BettingAnswer makeOpenActionFirstRound(PokerClient pokerClient, int limit, float probOfWin,
            int minimumPotAfterOpen, int playersCurrentBet, int playersRemainingChips) {
        int stake = (playersCurrentBet + playersRemainingChips) / 10;

        if ((limit < playersCurrentBet)
                || limit < minimumPotAfterOpen) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CHECK);
        } else if (playersRemainingChips < 2 * minimumPotAfterOpen && limit > 3 * stake) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_ALLIN);
        } else if (limit <= 5 * stake) {
            if (limit < 2 * stake) {
                return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CHECK);
            } else {

                return pokerClient.new BettingAnswer(playersCurrentBet <= limit
                        ? BettingAnswer.ACTION_OPEN : BettingAnswer.ACTION_CHECK,
                        3 * minimumPotAfterOpen + playersCurrentBet <= limit
                        ? 3 * minimumPotAfterOpen : limit - playersCurrentBet);
            }

        } else {
            return pokerClient.new BettingAnswer(
                    minimumPotAfterOpen + playersCurrentBet <= limit
                    ? BettingAnswer.ACTION_OPEN : BettingAnswer.ACTION_CHECK,
                    minimumPotAfterOpen);

        }
    }

    public static BettingAnswer makeOpenActionSecondRound(PokerClient pokerClient, int limit, float probOfWin,
            int minimumPotAfterOpen, int playersCurrentBet, int playersRemainingChips) {
        int stake = (playersCurrentBet + playersRemainingChips) / 10;
        if ((limit < playersCurrentBet)
                || limit < minimumPotAfterOpen) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CHECK);
        } else if (playersRemainingChips < 2 * minimumPotAfterOpen && limit > 3 * stake) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_ALLIN);
        } else if (limit < stake) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CHECK);
        } else if (limit < 3 * stake) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_OPEN, minimumPotAfterOpen);
        } else {
            return pokerClient.new BettingAnswer(
                    playersCurrentBet <= limit
                    ? BettingAnswer.ACTION_OPEN : BettingAnswer.ACTION_CHECK,
                    3 * minimumPotAfterOpen);

        }
    }

    public static BettingAnswer makeRaiseAction(PokerClient pokerClient, int limit, float probOfWin,
            int maximumBet, int minimumAmountToRaiseTo, int playersCurrentBet, int playersRemainingChips) {
        int stake = (playersCurrentBet + playersRemainingChips) / 10;
        if (limit < minimumAmountToRaiseTo + playersCurrentBet) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_FOLD);
        } else {
            if (limit <= 2 * stake) {
                return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CALL);
            } else {
                return pokerClient.new BettingAnswer(BettingAnswer.ACTION_RAISE,
                        3 * minimumAmountToRaiseTo + playersCurrentBet < limit
                        ? 3 * minimumAmountToRaiseTo : limit - playersCurrentBet);
            }
        }

    }

    /**
     * Calculate the limit of our nexts bet having consideration chips and pot.
     * Stake 1/10 - 2/10 => Low trust Stake 3/10 - 5/10 => Medium trust Stake
     * 6/10 - 10/10 => High trust
     *
     * @param probability - probability of win
     * @param chips - amount of money we have
     * @param pot - amount of money in play
     * @return int with the maximum value that our Agent must bet
     */
    public static int calculateLimit(float probability, int chips) {
        int stake = chips / 10;
        if (probability >= 0 && probability <= RANGE_20) {
            return 0;
        } else if (probability <= RANGE_43) {
            return stake;
        } else if (probability <= RANGE_70) {
            return stake * 2;
        } else if (probability <= RANGE_90) {
            return stake * 3;
        } else if (probability <= RANGE_95) {
            return stake * 4;
        } else if (probability <= RANGE_97) {
            return stake * 5;
        } else if (probability <= RANGE_98) {
            return stake * 6;
        } else if (probability <= RANGE_99) {
            return stake * 7;
        } else {
            return chips;
        }
    }

    /**
     * Recalculate limit in each turn considering pot and other players
     * variables such as probability and chips
     *
     * @param limit previous limit
     * @param chips amount of money of player
     * @param pot amount of money bet for all players
     * @param otherPlayerProbability odds of win other player
     * @param otherPlayerBet amount of money otherPlayerBet
     * @return new maximum that a player must bet
     */
    public static int recalculateLimit(int limit, int chips, int pot,
            int otherPlayerProbability, int otherPlayerBet) {
        int stake = chips / 10;
        boolean highPot = pot > 7 * stake;
        boolean highBet = otherPlayerBet > 3 * stake;
        if (highPot) {
            if (limit < 6 * stake) {
                return limit + stake;
            } else if (limit < chips) {
                return chips;
            }
        }
        switch (otherPlayerProbability) {
            case OtherPlayer.DRAW_0: {
                if (limit < 6 * stake) {
                    return 0;
                } else {
                    return limit - 2 * stake;
                }
            }
            case OtherPlayer.DRAW_1: {
                if (limit < 3 * stake) {
                    return 0;
                } else if (limit < 5 * stake) {
                    if (highBet) {
                        return limit - 3 * stake;
                    } else {
                        return limit - 2 * stake;
                    }
                } else if (limit < 7 * stake) {
                    if (highBet) {
                        return limit - 4 * stake;
                    } else {
                        return limit + stake;
                    }
                } else {
                    return chips;
                }
            }
            case OtherPlayer.DRAW_2: {
                if (limit < 3 * stake) {
                    if (highBet) {
                        return 0;
                    } else {
                        return limit - stake;
                    }
                } else if (limit < 5 * stake) {
                    if (highBet) {
                        return 0;
                    } else {
                        return limit - 2 * stake;
                    }
                } else if (limit < 7 * stake) {
                    if (highBet) {
                        return limit;
                    } else {
                        return limit + stake;
                    }
                } else {
                    return chips;
                }
            }
            case OtherPlayer.DRAW_3: {
                if (limit < 3 * stake) {
                    if (highBet) {
                        return 0;
                    } else {
                        return limit - stake;
                    }
                } else if (limit < 5 * stake) {
                    if (highBet) {
                        return limit - stake;
                    } else {
                        return limit + 2 * stake;
                    }
                } else {
                    if (highBet) {
                        return limit;
                    } else {
                        return chips;
                    }
                }
            }
            case OtherPlayer.DRAW_4: {
                if (limit < 3 * stake) {
                    if (highBet) {
                        return limit;
                    } else {
                        return limit + stake;
                    }
                } else if (limit < 5 * stake) {
                    if (highBet) {
                        return limit;
                    } else {
                        return limit + 2 * stake;
                    }
                } else {
                    return chips;
                }
            }
            case OtherPlayer.DRAW_5: {
                if (limit < 3 * stake) {
                    if (highBet) {
                        return limit;
                    } else {
                        return limit + 2 * stake;
                    }
                } else if (limit < 5 * stake) {
                    if (highBet) {
                        return limit;
                    } else {
                        return limit + 3 * stake;
                    }
                } else {
                    return chips;
                }
            }
        }
        return limit;
    }
}