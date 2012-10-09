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

    // =========================================================================
    // Fields
    // =========================================================================
    private Hand agentHand;
    private int[] cards;
    private boolean round;
    // For open action
    private int minimumPotAfterOpen = -1;
    // For raise action
    private int maximumBet = -1;
    private int minimumAmountToRaiseTo = -1;
    // For both actions
    private int playersCurrentBet = -1;
    private int playersRemainingChips = -1;
    
    private float probabilityOfImprove = 0;
    private float probabilityOfWin = 0;

    // =========================================================================
    // Constructor
    // =========================================================================
    /**
     * Constructor for Agent
     */
    public Agent(int[] cards) {
        this.cards = cards;
        refreshHand(cards);
    }

    // =========================================================================
    // Methods
    // =========================================================================
    /**
     * Called when server calls for "PokerClient.queryCardsToThrow"
     *
     * @return An array of cards that should be thrown
     */
    public Card[] getCardsToThrow() {
        // TODO
        return null;
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
        refreshHand(this.cards);
    }

    /**
     * Refreshing hand after draw
     *
     * @param hand - new hand
     */
    public void refreshHand(Hand hand) {
        Combination combination = new Combination(hand);
        if (!round) {
            calculateProbabilityToImprove(combination);
        } else {
            calculateProbabilityOfWin(combination);
        }
    }

    public void refreshHand(int[] cards) {
        Combination combination = new Combination(cards);
        if (!round) {
            probabilityOfImprove = (float) calculateProbabilityToImprove(combination);
            probabilityOfWin = (float) calculateProbabilityOfWin(combination);
        } else {
            probabilityOfWin = (float) calculateProbabilityOfWin(combination);
        }

        System.out.print("");
    }

    /**
     * Called from outside when agent need to make a decision
     *
     * @param isOpenAction
     * @return BettingAnswer object according to if action is open or not(raise)
     * @see #queryOpenAction(com.medicwave.cardgame.poker.PokerClient)
     * @see #queryCallRaiseAction(com.medicwave.cardgame.poker.PokerClient)
     */
    public BettingAnswer makeDecision(boolean isOpenAction) {
        // TODO
        return null;
    }

    /**
     * Called when we computed combination and probability
     *
     * @param combination - combination that was previously computed
     * @param probability - probability of win
     * @return BettingAnswer object according to the hand and chips
     */
    private BettingAnswer makeDecision(Combination combination, float probability) {
        // TODO 
        return null;
    }

    /**
     * Finds the best combination or the project of the combination
     *
     * @param hand - 5 cards that were taken
     * @return Combination object
     */
    public Combination findBestCombination(Hand hand) {
        Combination combination = new Combination(hand);
        return combination;
    }

    /**
     * Calculates probability of win with particular combination of cards
     *
     * @param combination - object is a result of findBestCombination method
     * @return Float value of probable win with given combination
     */
    public float calculateProbabilityOfWin(Combination combination) {
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
                odd = (float) (Odds.ONE_PAIR + firstCard * Factor.ONE_PAIR);
                break;
            case Combination.HIGH_CARD:
                odd = (float) (highestCard * Factor.HIGH_CARD);
                break;
        }
        return (float) odd;
    }

    /**
     * Calculates probability of improve a particular combination of cards
     *
     * @param combination - object is a result of findBestCombination method
     * @return Float value of probable improvment with given combination
     *
     */
    private float calculateProbabilityToImprove(Combination combination) {
        float odd = 0;
        switch (combination.getCombinationName()) {
            case Combination.THREE_OF_A_KIND:
                odd = 10;
                break;
            case Combination.TWO_PAIR:
                odd = Improvment.TWO_PAIR;
                break;
            case Combination.ONE_PAIR:
                odd = Improvment.ONE_PAIR;
                break;
            case Combination.PAIR_WITH_KICKER:
                odd = Improvment.PAIR_WITH_KICKER;
                break;
            case Combination.PROJECT_STRAIGHT_INSIDE:
                odd = Improvment.PROJECT_STRAIGHT_INSIDE;
                break;
            case Combination.PROJECT_STRAIGHT_OPEN:
                odd = Improvment.PROJECT_STRAIGHT_OPEN;
                break;
            case Combination.PROJECT_FLUSH_THREE:
                odd = Improvment.PROJECT_FLUSH_THREE;
                break;
            case Combination.PROJECT_FLUSH_FOUR:
                odd = Improvment.PROJECT_FLUSH_FOUR;
                break;
            case Combination.HIGH_CARD:
                odd = (float) (Improvment.HIGH_CARD
                        + Factor.IMPROVE * combination.getTheHighestCardValue());
                break;
        }
        return (float) odd;
    }

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
        public static final float PAIR_WITH_KICKER = (float) 23.22;
        public static final float PROJECT_STRAIGHT_INSIDE = (float) 8.2;
        public static final float PROJECT_STRAIGHT_OPEN = (float) 16.38;
        public static final float PROJECT_FLUSH_THREE = (float) 4.13;
        public static final float PROJECT_FLUSH_FOUR = 18;
        public static final float HIGH_CARD = 70;
        public static final float THREE_OF_A_KIND = 10;
        public static final float TWO_PAIR = (float) 8.31;
        public static final float ONE_PAIR = (float) 26.26;
    }
}
