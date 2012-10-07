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
    private boolean round;
    // For open action
    private int minimumPotAfterOpen = -1;
    // For raise action
    private int maximumBet = -1;
    private int minimumAmountToRaiseTo = -1;
    // For both actions
    private int playersCurrentBet = -1;
    private int playersRemainingChips = -1;

    // =========================================================================
    // Constructor
    // =========================================================================
    /**
     * Constructor for Agent
     */
    public Agent() {
        // TODO
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
        // TODO
    }

    /**
     * Refreshing hand after draw
     *
     * @param hand - new hand
     */
    public void refreshHand(Hand hand) {
        // TODO
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
        return new Combination(hand);
    }

    /**
     * Calculates probability of win with particular combination of cards
     *
     * @param combination - object is a result of findBestCombination method
     * @return Float value of probable win with given combination
     */
    public float calculateProbabilityOfWin(Combination combination) {
        // TODO
        return Float.MIN_VALUE;
    }
}
