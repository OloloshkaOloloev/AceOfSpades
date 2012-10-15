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
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Inner class for Agent to get the factor for more precission calculating
     * the probabilities
     */
    private class Factor {

        // =========================================================================
        // Constants
        // =========================================================================
        public static final float FULL_HOUSE = (float) 0.00018;
        public static final float FLUSH = (float) 0.00025;
        public static final float STRAIGHT = (float) 0.00077;
        public static final float THREE_OF_A_KIND = (float) 0.0027;
        public static final float TWO_PAIR = (float) 0.0062;
        public static final float ONE_PAIR = (float) 0.055;
        public static final float HIGH_CARD = (float) 0.012;
        public static final float IMPROVE = (float) 0.0428;
    }

    /**
     * Inner class for Agent to get the odds of each hand
     */
    private class Odds {

        // =========================================================================
        // Constants
        // =========================================================================
        public static final float ROYAL_FLUSH = 1;
        public static final float STRAIGHT_FLUSH = (float) 0.9999;
        public static final float FOUR_OF_A_KIND = (float) 0.9995;
        public static final float FULL_HOUSE = (float) 0.9971;
        public static final float FLUSH = (float) 0.9938;
        public static final float STRAIGHT = (float) 0.9871;
        public static final float THREE_OF_A_KIND = (float) 0.9513;
        public static final float TWO_PAIR = (float) 0.8708;
        public static final float ONE_PAIR = (float) 0.1548;
    }

    /**
     * Inner class for Agent to get the odds of each hand
     */
    private class Improvment {

        // =========================================================================
        // Constants
        // =========================================================================
        public static final float PAIR_WITH_KICKER = (float) 0.25;
        public static final float PROJECT_STRAIGHT_INSIDE = (float) 0.082;
        public static final float PROJECT_STRAIGHT_OPEN = (float) 0.1638;
        public static final float PROJECT_FLUSH_THREE = (float) 0.0413;
        public static final float PROJECT_FLUSH_FOUR = (float) 0.18;
        public static final float THREE_OF_A_KIND = (float) 0.1052;
        public static final float TWO_PAIR = (float) 0.0831;
        public static final float ONE_PAIR = (float) 0.285;
    }
    // =========================================================================
    // Agent Fields
    // =========================================================================
    private Combination combination;
    private boolean round;
    private float probabilityOfWin = 0;
    private int limit = 0;

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
        setLimit(Decision.calculateLimit(probabilityOfWin, playersRemainingChips + playersCurrentBet));
        if (!round) {
            return Decision.makeOpenActionFirstRound(pokerClient, getLimit(), probabilityOfWin, minimumPotAfterOpen, playersCurrentBet, playersRemainingChips);
        } else {
            return Decision.makeOpenActionSecondRound(pokerClient, getLimit(), probabilityOfWin, minimumPotAfterOpen, playersCurrentBet, playersRemainingChips);
        }
    }

    public BettingAnswer makeRaiseAction(PokerClient pokerClient, int maximumBet, int minimumAmountToRaiseTo, int playersCurrentBet, int playersRemainingChips) {
        setLimit(Decision.calculateLimit(probabilityOfWin, playersRemainingChips));
        return Decision.makeRaiseAction(pokerClient, getLimit(), probabilityOfWin, maximumBet, minimumAmountToRaiseTo, playersCurrentBet, playersRemainingChips);
    }

    /**
     * Calculates probability of win with particular combination of cards
     *
     * @param combination - object is a result of findBestCombination method
     * @param round - false = 1st round ; true = 2 round
     * @return Float value of probable win with given combination
     */
    public static float calculateProbabilityOfWin(Combination combination, boolean round) {
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
                odd = (float) (Odds.FULL_HOUSE + firstCard* Factor.FULL_HOUSE);
                break;
            case Combination.FLUSH:
                odd = (float) (Odds.FLUSH + firstCard * Factor.FLUSH);
                break;
            case Combination.STRAIGHT:
                odd = (float) (Odds.STRAIGHT + highestCard * Factor.STRAIGHT);
                break;
            case Combination.THREE_OF_A_KIND:
                odd = (float) (Odds.THREE_OF_A_KIND + firstCard* Factor.THREE_OF_A_KIND);
                break;
            case Combination.TWO_PAIR:
                odd = (float) (Odds.TWO_PAIR + firstCard  * Factor.TWO_PAIR);
                break;
            case Combination.ONE_PAIR:
                if (round) {
                    odd = (float) (Odds.ONE_PAIR + firstCard  * Factor.ONE_PAIR);
                } else {
                    odd = (float) ((Odds.ONE_PAIR + firstCard  * Factor.ONE_PAIR)
                            * (1 - Improvment.ONE_PAIR)
                            + Odds.TWO_PAIR * Improvment.ONE_PAIR);
                }

                break;
            case Combination.HIGH_CARD:
                odd = (float) ((highestCard) * Factor.HIGH_CARD);
                break;
            case Combination.PAIR_WITH_KICKER:
                if (round) {
                    odd = (float) (highestCard * Factor.HIGH_CARD);
                } else {
                    odd = (float) ((Odds.TWO_PAIR + highestCard  * Factor.TWO_PAIR)
                            * Improvment.PAIR_WITH_KICKER)
                            + ((Odds.ONE_PAIR + firstCard  * Factor.ONE_PAIR)
                            * (1 - Improvment.PAIR_WITH_KICKER));
                }
                break;
            case Combination.PROJECT_STRAIGHT_INSIDE:
                if (round) {
                    odd = (float) (highestCard * Factor.HIGH_CARD);
                } else {
                    odd = (float) ((Odds.STRAIGHT + highestCard * Factor.STRAIGHT)
                            * Improvment.PROJECT_STRAIGHT_INSIDE)
                            + ((highestCard * Factor.HIGH_CARD)
                            * (1 - Improvment.PROJECT_STRAIGHT_INSIDE));
                }
                break;
            case Combination.PROJECT_STRAIGHT_OPEN:
                if (round) {
                    odd = (float) (highestCard  * Factor.HIGH_CARD);
                } else {
                    odd = (float) ((Odds.STRAIGHT + highestCard  * Factor.STRAIGHT)
                            * Improvment.PROJECT_STRAIGHT_OPEN)
                            + ((highestCard  * Factor.HIGH_CARD)
                            * (1 - Improvment.PROJECT_STRAIGHT_OPEN));
                }
                break;
            case Combination.PROJECT_FLUSH_THREE:
                if (round) {
                    odd = (float) (highestCard  * Factor.HIGH_CARD);
                } else {
                    odd = (float) ((Odds.FLUSH + highestCard  * Factor.FLUSH)
                            * Improvment.PROJECT_FLUSH_THREE)
                            + ((highestCard  * Factor.HIGH_CARD)
                            * (1 - Improvment.PROJECT_FLUSH_THREE));
                }
                break;
            case Combination.PROJECT_FLUSH_FOUR:
                if (round) {
                    odd = (float) (highestCard  * Factor.HIGH_CARD);
                } else {
                    odd = (float) ((Odds.FLUSH + highestCard  * Factor.FLUSH)
                            * Improvment.PROJECT_FLUSH_FOUR)
                            + ((highestCard * Factor.HIGH_CARD)
                            * (1 - Improvment.PROJECT_FLUSH_FOUR));
                }
                break;
        }
        return (float) odd;
    }
}
