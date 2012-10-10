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
    private static final int RANGE_20 = 20;
    private static final int RANGE_43 = 43;
    private static final float RANGE_70 = (float) 70.5;
    private static final int RANGE_90 = 90;
    private static final int RANGE_95 = 95;
    private static final int RANGE_97 = 97;
    private static final int RANGE_98 = 98;
    private static final float RANGE_99 = (float) 99.38;
    // =========================================================================
    // Fields
    // =========================================================================

    public Decision() {
    }

    // =========================================================================
    // Methods
    // =========================================================================
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
        int pot = 3;
        int stake = chips / 10;
        boolean highPot = pot > 5 * stake;

        if (probability >= 0 && probability <= RANGE_20) {
            return 0;

        } else if (probability <= RANGE_43) {
            if (highPot) {
                return 2 * stake;
            } else {
                return stake;
            }
        } else if (probability <= RANGE_70) {
            if (highPot) {
                return 3 * stake;
            } else {
                return stake * 2;
            }
        } else if (probability <= RANGE_90) {
            if (highPot) {
                return 4 * stake;
            } else {
                return stake * 3;
            }
        } else if (probability <= RANGE_95) {
            if (highPot) {
                return 5 * stake;
            } else {
                return stake * 4;
            }
        } else if (probability <= RANGE_97) {
            if (highPot) {
                return 6 * stake;
            } else {
                return stake * 5;
            }
        } else if (probability <= RANGE_98) {
            if (highPot) {
                return 8 * stake;
            } else {
                return stake * 6;
            }
        } else if (probability <= RANGE_99) {
            if (highPot) {
                return chips;
            } else {
                return stake * 7;
            }
        } else {
            return chips;
        }
    }

    public static BettingAnswer makeOpenActionFirstRound(PokerClient pokerClient, int limit, float probOfWin,
            int minimumPotAfterOpen, int playersCurrentBet, int playersRemainingChips) {
        int stake = (playersCurrentBet + playersRemainingChips) / 10;

        if ((limit > playersCurrentBet + playersRemainingChips)
                || limit < minimumPotAfterOpen) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CHECK);
        } else if (playersRemainingChips < 2 * minimumPotAfterOpen && limit > 3 * stake) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_ALLIN);
        } else if (limit <= 5 * stake) {
            if (limit <= 2 * stake) {
                return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CHECK);
            } else {

                return pokerClient.new BettingAnswer(minimumPotAfterOpen + playersCurrentBet <= limit
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
        if ((limit > playersCurrentBet + playersRemainingChips)
                || limit < minimumPotAfterOpen) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CHECK);
        } else if (playersRemainingChips < 2 * minimumPotAfterOpen && limit > 3 * stake) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_ALLIN);
        } else if (limit <= 5 * stake) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CHECK);

        } else {
            return pokerClient.new BettingAnswer(
                    minimumPotAfterOpen + playersCurrentBet <= limit
                    ? BettingAnswer.ACTION_OPEN : BettingAnswer.ACTION_CHECK,
                    minimumPotAfterOpen);

        }
    }

    public static BettingAnswer makeRaiseAction(PokerClient pokerClient, int limit, float probOfWin,
            int maximumBet, int minimumAmountToRaiseTo, int playersCurrentBet, int playersRemainingChips) {
        int stake = (playersCurrentBet + playersRemainingChips) / 10;
        if (limit < minimumAmountToRaiseTo + playersCurrentBet) {
            return pokerClient.new BettingAnswer(BettingAnswer.ACTION_FOLD);
        } else {
            if (limit < 5 * stake) {
                return pokerClient.new BettingAnswer(BettingAnswer.ACTION_CALL);
            } else {
                return pokerClient.new BettingAnswer(BettingAnswer.ACTION_RAISE,
                        3 * minimumAmountToRaiseTo + playersCurrentBet < limit
                        ? 3 * minimumAmountToRaiseTo : limit - playersCurrentBet);
            }
        }

    }
}