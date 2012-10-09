// =========================================================================
// =========================================================================
// NOT TESTED NOT TESTED NOT TESTED NOT TESTED NOT TESTED NOT TESTED
// =========================================================================
// =========================================================================
// =========================================================================
package com.medicwave.cardgame.poker;

import ca.ualberta.cs.poker.Card;
import ca.ualberta.cs.poker.Hand;

/**
 * Class finds the combination or the project of the combination
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class Combination {

    // =========================================================================
    // Constants
    // =========================================================================
    public static final int ROYAL_FLUSH = 0;
    public static final int STRAIGHT_FLUSH = 1;
    public static final int FOUR_OF_A_KIND = 2;
    public static final int FULL_HOUSE = 3;
    public static final int FLUSH = 4;
    public static final int STRAIGHT = 5;
    public static final int THREE_OF_A_KIND = 6;
    public static final int TWO_PAIR = 7;
    public static final int ONE_PAIR = 8;
    public static final int HIGH_CARD = 9;
    public static final int PROJECT_STRAIGHT_OPEN = 10;
    public static final int PROJECT_STRAIGHT_INSIDE = 11;
    public static final int PROJECT_FLUSH_THREE = 12;
    public static final int PROJECT_FLUSH_FOUR = 13;
    public static final int PAIR_WITH_KICKER = 14;
    public static final int FIVE_CARDS_POKER = 5;
    public static final int VALUE = 0;
    public static final int POSITION = 1;
    // =========================================================================
    // Fields
    // =========================================================================
    private int combinationName;
    private boolean[] mask = {false, false, false, false, false};
    private int theHighestCardValue;
    private int theHighestCardValuePos;
    private int firstCombinationValue;
    private int secondCombinationValue;
    private int[] sortedByRank = new int[5];
    private int[] sortedByRankAndSuit = new int[5];
    private int[] cards = new int[5];
    private String strRank = "";
    private String strRankSuit = "";

    // =========================================================================
    // Constructors
    // =========================================================================
    /**
     * Constructor takes hand as parameter and calculates inner values
     *
     * @param hand
     */
    public Combination(Hand hand) {
        // Getting array of cards from the hand
        cards = hand.getCardArray();

        // Ordering cards in the right way
        for (int i = 0; i < FIVE_CARDS_POKER; i++) {
            cards[i] = cards[i + 1];
        }

        // Initializing highest value
        theHighestCardValue = -1;
        theHighestCardValuePos = 0;

        // Start finding combinations
        calculateValues(cards);
    }

    /**
     * Constructor for testing combinations
     *
     * @param cards - 5-items array with indexes of cards
     */
    public Combination(int[] cards) {
        this.cards = cards;
        String cardsStr = new String();
        for (int i = 0; i < FIVE_CARDS_POKER; i++) {
            cardsStr += Card.getRankChar(cards[i]) + Card.getSuit(cards[i]) + " ";
        }
        System.out.println(cardsStr);
        if (cards.length == 5) {
            calculateValues(cards);
        }
    }

    // =========================================================================
    // Methods
    // =========================================================================
    /**
     * Calculate and set fields of the Combination object
     *
     * @param cards - 5-items array with indexes of cards
     */
    private void calculateValues(int[] cards) {
        // Setting the worst combination name
        combinationName = HIGH_CARD;

        // Sorting cards for further finding combinations
        sortByRank(cards);
        sortByRankAndSuit(cards);

        // Start checking combinations from the lowest chance of win
        checkProjectStraight();
        checkProjectFlush();
        checkOnePair();
        checkPairWithKicker();
        checkTwoPair();
        checkThreeOfAKind();
        checkStraight();
        checkFlush();
        checkFullHouse();
        checkFourOfAKind();
        checkStraightRoyalFlush();

        System.out.print(strRank);
    }

    /**
     * Setting all items of mask field to false
     */
    private void resetMask() {
        mask = new boolean[]{false, false, false, false, false};
    }

    /**
     * Finds the highest rank card in the card array
     *
     * @param cards - 5-items array with indexes of cards
     * @return array[VALUE] is rank of the highest card, array[POSITION] is it's
     * position
     */
    private int[] findHighestRank(int[] cards) {
        int highestRank = Integer.MIN_VALUE;
        int highestRankPos = 0;
        for (int i = 0; i < FIVE_CARDS_POKER; i++) {
            int rank = Card.getRank(cards[i]);
            if (rank > theHighestCardValue) {
                highestRank = rank;
                highestRankPos = i;
            }
        }
        return new int[]{highestRank, highestRankPos};
    }

    private int[] cloneCardsArray(int[] from, int[] to) {
        for (int i = 0; i < FIVE_CARDS_POKER; i++) {
            to[i] = from[i];
        }
        return to;
    }

    /**
     * Sorting cards by rank and setting the field sortByRank
     *
     * @param cards - 5-items array with indexes of cards
     */
    private void sortByRank(int[] cards) {
        cloneCardsArray(cards, sortedByRank);
        // Bubble sort
        for (int i = 0; i < FIVE_CARDS_POKER; i++) {
            for (int j = 0; j < FIVE_CARDS_POKER - 1 - i; j++) {
                if (Card.getRank(sortedByRank[j]) > Card.getRank(sortedByRank[j + 1])) {
                    int temp = sortedByRank[j];
                    sortedByRank[j] = sortedByRank[j + 1];
                    sortedByRank[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < FIVE_CARDS_POKER; i++) {
            strRank += Card.getRank(sortedByRank[i]) + "; ";
        }
    }

    /**
     * Sorting cards by rank and suit and setting the field sortByRankAndSuit
     *
     * @param cards - 5-items array with indexes of cards
     */
    private void sortByRankAndSuit(int[] cards) {
        cloneCardsArray(cards, sortedByRankAndSuit);
        // Bubble sort
        for (int i = 0; i < FIVE_CARDS_POKER; i++) {
            for (int j = 0; j < FIVE_CARDS_POKER - 1; j++) {
                if (Card.getSuit(sortedByRankAndSuit[j]) > Card.getSuit(sortedByRankAndSuit[j + 1])) {
                    int temp = sortedByRankAndSuit[j];
                    sortedByRankAndSuit[j] = sortedByRankAndSuit[j + 1];
                    sortedByRankAndSuit[j + 1] = temp;
                }
            }
        }

        // Bubble sort
        for (int i = 0; i < FIVE_CARDS_POKER; i++) {
            for (int j = 0; j < FIVE_CARDS_POKER - 1; j++) {
                if (Card.getRank(sortedByRankAndSuit[j]) > Card.getRank(sortedByRankAndSuit[j + 1])) {
                    if (Card.getSuit(sortedByRankAndSuit[j]) == Card.getSuit(sortedByRankAndSuit[j + 1])) {
                        int temp = sortedByRankAndSuit[j];
                        sortedByRankAndSuit[j] = sortedByRankAndSuit[j + 1];
                        sortedByRankAndSuit[j + 1] = temp;
                    }
                }
            }
        }

        for (int i = 0; i < FIVE_CARDS_POKER; i++) {
            strRankSuit += Card.getRank(sortedByRankAndSuit[i]) + " " + Card.getSuit(sortedByRankAndSuit[i]) + "; ";
        }
    }

    /**
     * Check hand for straight project and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkProjectStraight() {
        boolean isFirstHalf = false;
        boolean isOpen = true;
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 1] - 1) && i > 0) {
                if (isOpen) {
                    if (Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 2] - 2) && i > 0) {
                        isOpen = false;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                isFirstHalf = i < 1 ? true : false;
            }
        }
        secondCombinationValue = -1;
        // Get the first value according to half in what straight project have been detected
        firstCombinationValue = isFirstHalf ? Card.getRank(sortedByRank[4]) : Card.getRank(sortedByRank[4]);
        // Setting mask accodring to in what half the project of straight
        mask = isFirstHalf ? new boolean[]{true, true, true, true, false} : new boolean[]{false, true, true, true, true};
        combinationName = isOpen ? PROJECT_STRAIGHT_OPEN : PROJECT_STRAIGHT_INSIDE;
    }

    /**
     * Check hand for flush project and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkProjectFlush() {
        int cardsOfSameSuit = 0;
        int startSameSuitPos = 0;
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getSuit(sortedByRankAndSuit[i]) == Card.getSuit(sortedByRankAndSuit[i + 1])) {
                cardsOfSameSuit++;
            } else {
                if (cardsOfSameSuit < 3) {
                    cardsOfSameSuit = 1;
                    startSameSuitPos = i;
                }
            }
        }
        if (cardsOfSameSuit > 2) {
            // Setting mask accodring to in what half the project of flush
            resetMask();
            for (int i = startSameSuitPos; i < cardsOfSameSuit; i++) {
                mask[i] = true;
            }
            combinationName = cardsOfSameSuit == 3 ? PROJECT_FLUSH_THREE : PROJECT_FLUSH_FOUR;
        }
    }

    /**
     * Check hand for straight flush project and set fields: combinationName,
     * mask, firstCombinationValue, secondCombinationValue
     */
    private void checkProjectStraightFlush() {
        boolean isFirstHalf = false;
        boolean isOpen = true;
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getRank(cards[i]) != Card.getRank(cards[i + 1]) + 1
                    && i > 0 && Card.getSuit(cards[i]) != Card.getSuit(cards[i + 1])) {
                if (isOpen) {
                    if (i + 2 < FIVE_CARDS_POKER) {
                        if (Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 2])
                                && Card.getSuit(cards[i]) != Card.getSuit(cards[i + 1])) {
                            return;
                        } else {
                            isOpen = false;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                isFirstHalf = i < 1 ? true : false;
            }
        }
        // Check in which half of five cards is straight flush project
        secondCombinationValue = isFirstHalf ? Card.getRank(sortedByRank[FIVE_CARDS_POKER - 2]) : Card.getRank(sortedByRank[FIVE_CARDS_POKER - 1]);
        // If highest card in project not Ace then potentialy think that we can get when draw a higher card
        firstCombinationValue = secondCombinationValue == Card.ACE ? secondCombinationValue : secondCombinationValue + 1;
        // Setting mask accodring to in what half the project of straight flush
        mask = isFirstHalf ? new boolean[]{true, true, true, true, false} : new boolean[]{false, true, true, true, true};
        combinationName = PROJECT_STRAIGHT_OPEN;
    }

    /**
     * Check hand for royal flush project and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkProjectRoyalFlush() {
        boolean isFirstHalf = false;
        boolean isOpen = true;
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 1]) + 1 && i > 0
                    && Card.getSuit(sortedByRank[i]) != Card.getSuit(sortedByRank[i + 1])) {
                if (isOpen) {
                    if (i + 2 < FIVE_CARDS_POKER) {
                        if (Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 2])
                                && Card.getSuit(cards[i]) != Card.getSuit(cards[i + 1])) {
                            return;
                        } else {
                            isOpen = false;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                isFirstHalf = i < 1 ? true : false;
            }
        }
        if (theHighestCardValue == Card.KING || theHighestCardValue == Card.ACE) {
            // Check in which half of five cards is straight flush project
            secondCombinationValue = isFirstHalf ? Card.getRank(sortedByRank[FIVE_CARDS_POKER - 2]) : Card.getRank(sortedByRank[FIVE_CARDS_POKER - 1]);
            // The highest is always an Ace
            firstCombinationValue = Card.ACE;
            // Setting mask accodring to in what half the project of straight flush
            mask = isFirstHalf ? new boolean[]{true, true, true, true, false} : new boolean[]{false, true, true, true, true};
            combinationName = PROJECT_STRAIGHT_OPEN;
        }
    }

    /**
     * Check hand for one pair and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkOnePair() {
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getRank(sortedByRank[i]) == Card.getRank(sortedByRank[i + 1])) {
                resetMask();
                firstCombinationValue = Card.getRank(cards[i]);
                secondCombinationValue = -1;
                mask[i] = true;
                mask[i + 1] = true;
                combinationName = ONE_PAIR;
                return;
            }
        }
    }

    /**
     * Check hand for one pair with kicker and set fields: combinationName,
     * mask, firstCombinationValue, secondCombinationValue
     */
    private void checkPairWithKicker() {
        int[] temp = findHighestRank(sortedByRank);
        theHighestCardValue = temp[VALUE];
        theHighestCardValuePos = temp[POSITION];
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getRank(sortedByRank[i]) == Card.getRank(sortedByRank[i + 1])
                    && (theHighestCardValue == Card.KING || theHighestCardValue == Card.ACE)) {
                resetMask();
                mask[i] = true;
                mask[i + 1] = true;
                mask[theHighestCardValuePos] = true;
                combinationName = PAIR_WITH_KICKER;
                return;
            }
        }
    }

    /**
     * Check hand for two pair and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkTwoPair() {
        boolean isFirstPairFound = false;
        boolean[] tempMask = new boolean[]{false, false, false, false, false};
        int tempHighestValue = -1;
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getRank(sortedByRank[i]) == Card.getRank(sortedByRank[i + 1])) {
                if (!isFirstPairFound) {
                    isFirstPairFound = true;
                    tempMask[i] = true;
                    tempMask[i + 1] = true;
                    tempHighestValue = Card.getRank(sortedByRank[i]);
                    i++;
                } else {
                    resetMask();
                    if (tempHighestValue > sortedByRank[i]) {
                        firstCombinationValue = tempHighestValue;
                        secondCombinationValue = sortedByRank[i];
                    } else {
                        firstCombinationValue = sortedByRank[i];
                        secondCombinationValue = tempHighestValue;
                    }
                    mask[i] = true;
                    mask[i + 1] = true;
                    for (int j = 0; j < FIVE_CARDS_POKER; j++) {
                        mask[j] = tempMask[j] ? true : mask[i];
                    }
                    combinationName = TWO_PAIR;
                }
            }
        }
    }

    /**
     * Check hand for three of a kind and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkThreeOfAKind() {
        for (int i = 0; i < FIVE_CARDS_POKER - 2; i++) {
            if (Card.getRank(sortedByRank[i]) == Card.getRank(sortedByRank[i + 1])
                    && Card.getRank(sortedByRank[i]) == Card.getRank(sortedByRank[i + 2])) {
                resetMask();
                firstCombinationValue = Card.getRank(cards[i]);
                secondCombinationValue = -1;
                mask[i] = true;
                mask[i + 1] = true;
                mask[i + 2] = true;
                combinationName = THREE_OF_A_KIND;
                return;
            }
        }
    }

    /**
     * Check hand for straight and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkStraight() {
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 1] - 1)) {
                return;
            }
        }
        firstCombinationValue = theHighestCardValue;
        secondCombinationValue = -1;
        mask = new boolean[]{true, true, true, true, true};
        combinationName = STRAIGHT;
    }

    /**
     * Check hand for flush and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkFlush() {
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getSuit(sortedByRank[i]) != Card.getSuit(sortedByRank[i + 1])) {
                return;
            }
        }
        firstCombinationValue = theHighestCardValue;
        secondCombinationValue = -1;
        mask = new boolean[]{true, true, true, true, true};
        combinationName = FLUSH;
    }

    /**
     * Check hand for full house and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkFullHouse() {
        int i = 0;
        if (Card.getRank(sortedByRank[i]) == Card.getRank(sortedByRank[i + 1])
                && Card.getRank(sortedByRank[i + 2]) == Card.getRank(sortedByRank[i + 3])
                && Card.getRank(sortedByRank[i + 2]) == Card.getRank(sortedByRank[i + 4])) {
            firstCombinationValue = Card.getRank(sortedByRank[i]) > Card.getRank(sortedByRank[i + 4]) ? Card.getRank(sortedByRank[i]) : Card.getRank(sortedByRank[i + 4]);
            secondCombinationValue = Card.getRank(sortedByRank[i]) > Card.getRank(sortedByRank[i + 4]) ? Card.getRank(sortedByRank[i + 4]) : Card.getRank(sortedByRank[i]);
            mask = new boolean[]{true, true, true, true, true};
            combinationName = FULL_HOUSE;
        }

        if (Card.getRank(sortedByRank[i]) == Card.getRank(sortedByRank[i + 1])
                && Card.getRank(sortedByRank[i]) == Card.getRank(sortedByRank[i + 2])
                && Card.getRank(sortedByRank[i + 3]) == Card.getRank(sortedByRank[i + 4])) {
            if (Card.getRank(sortedByRank[i]) > Card.getRank(sortedByRank[i + 3])) {
                firstCombinationValue = Card.getRank(sortedByRank[i]) > Card.getRank(sortedByRank[i + 4]) ? Card.getRank(sortedByRank[i]) : Card.getRank(sortedByRank[i + 4]);
                secondCombinationValue = Card.getRank(sortedByRank[i]) > Card.getRank(sortedByRank[i + 4]) ? Card.getRank(sortedByRank[i + 4]) : Card.getRank(sortedByRank[i]);
                mask = new boolean[]{true, true, true, true, true};
                combinationName = FULL_HOUSE;
            }
        }
    }

    /**
     * Check hand for four of a kind and set fields: combinationName, mask,
     * firstCombinationValue, secondCombinationValue
     */
    private void checkFourOfAKind() {
        boolean isFirstHalf = true;
        for (int i = 0; i < FIVE_CARDS_POKER - 3; i++) {
            if ((Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 1])
                    || Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 2])
                    || Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 3]))
                    && i > 0) {
                return;
            } else {
                isFirstHalf = false;
            }
        }
        firstCombinationValue = Card.getRank(sortedByRank[2]);
        secondCombinationValue = -1;
        if (theHighestCardValue < Card.NINE) {
            mask = new boolean[]{isFirstHalf ? true : false, true, true, isFirstHalf ? false : true};
        } else {
            mask = new boolean[]{true, true, true, true, true};
        }
        combinationName = FOUR_OF_A_KIND;
    }

    /**
     * Check hand for straight flush or royal flush and set fields:
     * combinationName, mask, firstCombinationValue, secondCombinationValue
     */
    private void checkStraightRoyalFlush() {
        for (int i = 0; i < FIVE_CARDS_POKER - 1; i++) {
            if (Card.getRank(sortedByRank[i]) != Card.getRank(sortedByRank[i + 1] - 1)
                    || Card.getSuit(sortedByRank[i]) != Card.getSuit(sortedByRank[i + 1])) {
                return;
            }
        }
        firstCombinationValue = Card.getRank(sortedByRank[FIVE_CARDS_POKER - 1]);
        secondCombinationValue = -1;
        mask = new boolean[]{true, true, true, true, true};
        int[] highestValue = findHighestRank(sortedByRank);
        theHighestCardValue = highestValue[VALUE];
        theHighestCardValuePos = highestValue[POSITION];
        combinationName = theHighestCardValue == Card.ACE ? ROYAL_FLUSH : STRAIGHT_FLUSH;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    /**
     * @return the combinationName
     */
    public int getCombinationName() {
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
