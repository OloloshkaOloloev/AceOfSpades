package com.medicwave.cardgame.poker;

import ca.ualberta.cs.poker.Card;
import ca.ualberta.cs.poker.Hand;

/**
 * Class finds the combination or 
 * the project of the combination
 * 
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class Combination {
    
    // =========================================================================
    // Constants
    // =========================================================================
    
    public static final String ROYAL_FLUSH = "RoyalFlush";
    public static final String STRAIGHT_FLUSH = "StraightFlush";
    public static final String FOUR_OF_A_KIND = "FourOfAKind";
    public static final String FULL_HOUSE = "FullHouse";
    public static final String FLUSH = "Flush";
    public static final String STRAIGHT = "Straight";
    public static final String THREE_OF_A_KIND = "ThreeOfAKind";
    public static final String TWO_PAIR = "TwoPair";
    public static final String ONE_PAIR = "OnePair";
    public static final String HIGH_CARD = "HighCard/Nothing";
    public static final String PROJECT_ROYAL_FLUSH  = "ProjRoaylFlush";
    public static final String PROJECT_STRAIGHT_FLUSH = "ProjStrFlush";
    public static final String PROJECT_FLUSH = "ProjFlush";
    public static final String PROJECT_STRAIGHT = "ProjStraight";
    public static final String PAIR_WITH_KICKER = "PairWithKicker";
    
    // =========================================================================
    // Fields
    // =========================================================================
    
    private String combinationName;
    private boolean[] mask = {false, false, false, false, false};
    private int theHighestCardValue;
    private int firstCombinationValue;
    private int secondCombinationValue;
    
    /**
     * 
     * @param hand 
     */
    public Combination(Hand hand) {
        // TODO
    }
    
    private void calculateValues() {
        // TODO
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    
    /**
     * @return the combinationName
     */
    public String getCombinationName() {
        return combinationName;
    }

    /**
     * @return the mask
     */
    public boolean[] getMask() {
        return mask;
    }

    /**
     * @param mask the mask to set
     */
    public void setMask(boolean[] mask) {
        this.mask = mask;
    }

    /**
     * @return the theHighestCardValue
     */
    public int getTheHighestCardValue() {
        return theHighestCardValue;
    }

    /**
     * @return the firstCombinationValue
     */
    public int getFirstCombinationValue() {
        return firstCombinationValue;
    }

    /**
     * @return the secondCombinationValue
     */
    public int getSecondCombinationValue() {
        return secondCombinationValue;
    }
}
