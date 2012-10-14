package com.medicwave.cardgame.poker;

import ca.ualberta.cs.poker.Hand;

/**
 * Public for describing information about other player (interaction)
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class OtherPlayer {
    // =========================================================================
    // Fields
    // =========================================================================

    private String nameId = null;
    private int numberOfThrownCards = 0;
    private int chips = 0;
    private RoundStatistics roundStatistics = new RoundStatistics();
    private FullStatistics fullStatistics = new FullStatistics();
    private boolean aggressive = false;
    private float aggressiveCoefficient = (float) -1.0;

    // =========================================================================
    // Constructor
    // =========================================================================
    public OtherPlayer(String playerName) {
        this.nameId = playerName;
    }

    // =========================================================================
    // Methods
    // =========================================================================
    public void reset() {
        this.numberOfThrownCards = 0;
        this.roundStatistics = new RoundStatistics();
    }

    public void saveRoundStatistics() {
        fullStatistics.addRoundStatistics(roundStatistics);
    }

    public void calculateAggressiveCoefficient(Hand hand) {
        Combination combination = new Combination(hand);
        float probOfWin = (float) Agent.calculateProbabilityOfWin(combination, true);
        int agentsLimit = Decision.calculateLimit(probOfWin, this.chips);
        int playersLimit = roundStatistics.getChipsBetted();
        if (playersLimit != 0) {
            this.aggressiveCoefficient = (float) agentsLimit / playersLimit;
            this.aggressive = this.aggressiveCoefficient < 1;
        }
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    /**
     * Setting number of the cards that current player throwed
     *
     * @param numberOfCards - number of cards that current player throwed
     */
    public void setNumberOfThrownCards(int numberOfCards) {
        this.numberOfThrownCards = numberOfCards;
    }

    /**
     * @return nubmer of thrown cards by current player
     */
    public int getNumberOfThrownCards() {
        return this.numberOfThrownCards;
    }

    /**
     * Gets player name
     *
     * @return plaer name (id)
     */
    public String getPlayerName() {
        return this.nameId;
    }

    /**
     * @return chips of the current player
     */
    public int getChips() {
        return chips;
    }

    /**
     * @param chips - chips of the current player
     */
    public void setChips(int chips) {
        this.chips = chips;
    }

    /**
     * @return the fullStatistics
     */
    public RoundStatistics getRoundStatistics() {
        return roundStatistics;
    }

    /**
     * @return the fullStatistics
     */
    public FullStatistics getFullStatistics() {
        return fullStatistics;
    }

    /**
     * @return the isAggressive
     */
    public boolean isAggressive() {
        return aggressive;
    }

    /**
     * @param isAggressive the isAggressive to set
     */
    public void setIsAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }

    /**
     * @return the aggressiveCoefficient
     */
    public float getAggressiveCoefficient() {
        return aggressiveCoefficient;
    }

    /**
     * @param aggressiveCoefficient the aggressiveCoefficient to set
     */
    public void setAggressiveCoefficient(float aggressiveCoefficient) {
        this.aggressiveCoefficient = aggressiveCoefficient;
    }
}
