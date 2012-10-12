package com.medicwave.cardgame.poker;

import ca.ualberta.cs.poker.Card;
import ca.ualberta.cs.poker.Hand;
import com.medicwave.cardgame.poker.PokerClientBase.BettingAnswer;

/**
 * Class describes agent's of five-cards draw poker behaviour
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class Agent {

    /**
     * Inner class for Agent to get the factor for more precission calculating
     * the probabilities
     */
    private class Factor {

        // =========================================================================
        // Constants
        // =========================================================================
        public static final float FULL_HOUSE = (float) 0.018;
        public static final float FLUSH = (float) 0.025;
        public static final float STRAIGHT = (float) 0.077;
        public static final float THREE_OF_A_KIND = (float) 0.27;
        public static final float TWO_PAIR = (float) 0.62;
        public static final float ONE_PAIR = (float) 5.5;
        public static final float HIGH_CARD = (float) 1.2;
        public static final float IMPROVE = (float) 4.28;
    }

    /**
     * Inner class for Agent to get the odds of each hand
     */
    private class Odds {

        // =========================================================================
        // Constants
        // =========================================================================
        public static final float ROYAL_FLUSH = 100;
        public static final float STRAIGHT_FLUSH = (float) 99.99;
        public static final float FOUR_OF_A_KIND = (float) 99.95;
        public static final float FULL_HOUSE = (float) 99.71;
        public static final float FLUSH = (float) 99.38;
        public static final float STRAIGHT = (float) 98.71;
        public static final float THREE_OF_A_KIND = (float) 95.13;
        public static final float TWO_PAIR = (float) 87.08;
        public static final float ONE_PAIR = (float) 15.48;
    }

    /**
     * Inner class for Agent to get the odds of each hand
     */
    private class Improvment {

        // =========================================================================
        // Constants
        // =========================================================================
        public static final float PAIR_WITH_KICKER = (float) 25;
        public static final float PROJECT_STRAIGHT_INSIDE = (float) 8.2;
        public static final float PROJECT_STRAIGHT_OPEN = (float) 16.38;
        public static final float PROJECT_FLUSH_THREE = (float) 4.13;
        public static final float PROJECT_FLUSH_FOUR = 18;
        public static final float THREE_OF_A_KIND = (float) 10.52;
        public static final float TWO_PAIR = (float) 8.31;
        public static final float ONE_PAIR = (float) 28.5;
    }
    
    // =========================================================================
    // Agent Fields
    // =========================================================================
    private Combination combination;
    private boolean round;
    private float probabilityOfWin = 0;

    // =========================================================================
    // Agent Constructors
    // =========================================================================
    public Agent() {
    }

    public Agent(Hand hand) {
        this.combination = new Combination(hand);
        this.probabilityOfWin = calculateProbabilityOfWin(combination, round);
    }
    // =========================================================================
    // Agent Methods
    // =========================================================================

    /**
     * Called when server calls for "PokerClient.queryCardsToThrow"
     *
     * @return An array of cards that should be thrown
     */
    public Card[] getCardsToThrow() {
        return combination.getCardsToThrow();
    }

    /**
     * Resets agent's fields when act of playing finished
     */
    public void reset() {
        round = false;
        probabilityOfWin = 0;
    }

    public void setToRoundTwo() {
        round = true;
    }

    /**
     * Refreshing hand after draw
     *
     * @param hand - new hand
     */
    public void refreshHand(Hand hand) {
        this.combination = new Combination(hand);
        this.probabilityOfWin = calculateProbabilityOfWin(combination, round);
    }

    public void refreshHand(int[] cards) {
        combination = new Combination(cards);
        this.probabilityOfWin = calculateProbabilityOfWin(combination, round);
    }

    /**
     * Called when we computed combination and probability
     *
     * @param combination - combination that was previously computed
     * @param probability - probability of win
     * @return BettingAnswer object according to the hand and chips
     */
    public BettingAnswer makeOpenAction(PokerClient pokerClient, int minimumPotAfterOpen, int playersCurrentBet, int playersRemainingChips) {
        int limit = Decision.calculateLimit(probabilityOfWin, playersRemainingChips);
        if (!round) {
            return Decision.makeOpenActionFirstRound(pokerClient, limit, probabilityOfWin, minimumPotAfterOpen, playersCurrentBet, playersRemainingChips);
        } else {
            return Decision.makeOpenActionSecondRound(pokerClient, limit, probabilityOfWin, minimumPotAfterOpen, playersCurrentBet, playersRemainingChips);
        }
    }

    public BettingAnswer makeRaiseAction(PokerClient pokerClient, int maximumBet, int minimumAmountToRaiseTo, int playersCurrentBet, int playersRemainingChips) {
        int limit = Decision.calculateLimit(probabilityOfWin, playersRemainingChips);
        return Decision.makeRaiseAction(pokerClient, limit, probabilityOfWin, maximumBet, minimumAmountToRaiseTo, playersCurrentBet, playersRemainingChips);
    }

    /**
     * Calculates probability of win with particular combination of cards
     *
     * @param combination - object is a result of findBestCombination method
     * @param round - false = 1st round ; true = 2 round
     * @return Float value of probable win with given combination
     */
    private float calculateProbabilityOfWin(Combination combination, boolean round) {
        float odd = 0;
        int firstCard = Card.getRank(combination.getFirstCombinationValue());
        int highestCard = Card.getRank(combination.getTheHighestCardValue());
        switch (combination.getCombinationName()) {
            case Combination.ROYAL_FLUSH:
                odd = Odds.ROYAL_FLUSH;
                break;
            case Combination.STRAIGHT_FLUSH:
                odd = (float) Odds.STRAIGHT_FLUSH;
                break;
            case Combination.FOUR_OF_A_KIND:
                odd = (float) Odds.FOUR_OF_A_KIND;
                break;
            case Combination.FULL_HOUSE:
                odd = (float) (Odds.FULL_HOUSE + firstCard * Factor.FULL_HOUSE);
                break;
            case Combination.FLUSH:
                odd = (float) (Odds.FLUSH + firstCard * Factor.FLUSH);
                break;
            case Combination.STRAIGHT:
                odd = (float) (Odds.STRAIGHT + highestCard * Factor.STRAIGHT);
                break;
            case Combination.THREE_OF_A_KIND:
                odd = (float) (Odds.THREE_OF_A_KIND + firstCard * Factor.THREE_OF_A_KIND);
                break;
            case Combination.TWO_PAIR:
                odd = (float) (Odds.TWO_PAIR + firstCard * Factor.TWO_PAIR);
                break;
            case Combination.ONE_PAIR:
                if (round) {
                    odd = (float) (Odds.ONE_PAIR + firstCard * Factor.ONE_PAIR);
                } else {
                    odd = (float) ((Odds.ONE_PAIR + firstCard * Factor.ONE_PAIR)
                            * (1 - Improvment.ONE_PAIR)
                            + Odds.TWO_PAIR * Improvment.TWO_PAIR);
                }

                break;
            case Combination.HIGH_CARD:
                odd = (float) (highestCard * Factor.HIGH_CARD);
                break;
            case Combination.PAIR_WITH_KICKER:
                if (round) {
                    odd = (float) (highestCard * Factor.HIGH_CARD);
                } else {
                    odd = ((Odds.TWO_PAIR + highestCard * Factor.TWO_PAIR)
                            * Improvment.PAIR_WITH_KICKER)
                            + ((Odds.ONE_PAIR + firstCard * Factor.ONE_PAIR)
                            * (1 - Improvment.PAIR_WITH_KICKER));
                }
                break;
            case Combination.PROJECT_STRAIGHT_INSIDE:
                if (round) {
                    odd = (float) (highestCard * Factor.HIGH_CARD);
                } else {
                    odd = ((Odds.STRAIGHT + highestCard * Factor.STRAIGHT)
                            * Improvment.PROJECT_STRAIGHT_INSIDE)
                            + ((highestCard * Factor.HIGH_CARD)
                            * (1 - Improvment.PROJECT_STRAIGHT_INSIDE));
                }
                break;
            case Combination.PROJECT_STRAIGHT_OPEN:
                if (round) {
                    odd = (float) (highestCard * Factor.HIGH_CARD);
                } else {
                    odd = ((Odds.STRAIGHT + highestCard * Factor.STRAIGHT)
                            * Improvment.PROJECT_STRAIGHT_OPEN)
                            + ((highestCard * Factor.HIGH_CARD)
                            * (1 - Improvment.PROJECT_STRAIGHT_OPEN));
                }
                break;
            case Combination.PROJECT_FLUSH_THREE:
                if (round) {
                    odd = (float) (highestCard * Factor.HIGH_CARD);
                } else {
                    odd = ((Odds.FLUSH + highestCard * Factor.FLUSH)
                            * Improvment.PROJECT_FLUSH_THREE)
                            + ((highestCard * Factor.HIGH_CARD)
                            * (1 - Improvment.PROJECT_FLUSH_THREE));
                }
                break;
            case Combination.PROJECT_FLUSH_FOUR:
                if (round) {
                    odd = (float) (highestCard * Factor.HIGH_CARD);
                } else {
                    odd = ((Odds.FLUSH + highestCard * Factor.FLUSH)
                            * Improvment.PROJECT_FLUSH_FOUR)
                            + ((highestCard * Factor.HIGH_CARD)
                            * (1 - Improvment.PROJECT_FLUSH_FOUR));
                }
                break;
        }
        return (float) odd;
    }
}
