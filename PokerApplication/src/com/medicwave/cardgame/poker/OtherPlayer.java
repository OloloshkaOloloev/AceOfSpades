package com.medicwave.cardgame.poker;

/**
 * Public for describing information about other player (interaction)
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class OtherPlayer {
    // =========================================================================
    // Constants
    // =========================================================================

    public final static int DRAW_0 = 99;
    public final static int DRAW_1 = 64;
    public final static int DRAW_2 = 53;
    public final static int DRAW_3 = 45;
    public final static int DRAW_4 = 30;
    public final static int DRAW_5 = 10;
    // =========================================================================
    // Fields
    // =========================================================================
    private String nameId = null;
    private int probabilitiesOfWin = 0;
    private int numberOfThrownCards = 0;
    private int chips = 0;
    private RoundStatistics roundStatistics = new RoundStatistics();
    private FullStatistics fullStatistics = new FullStatistics();
    private Boolean aggressive = null;

    // =========================================================================
    // Constructor
    // =========================================================================
    public OtherPlayer(String playerName) {
        this.nameId = playerName;
    }

    // =========================================================================
    // Methods
    // =========================================================================
    public void calculateProbabilityOfWin(int cardsDraw) {
        switch (cardsDraw) {
            case 0:
                probabilitiesOfWin = DRAW_0;
            case 1:
                probabilitiesOfWin = DRAW_1;
            case 2:
                probabilitiesOfWin = DRAW_2;
            case 3:
                probabilitiesOfWin = DRAW_3;
            case 4:
                probabilitiesOfWin = DRAW_4;
            case 5:
                probabilitiesOfWin = DRAW_5;
            default:
                probabilitiesOfWin = 0;
        }
    }

    public void reset() {
        this.numberOfThrownCards = 0;
        this.probabilitiesOfWin = 0;
        this.roundStatistics = new RoundStatistics();
    }

    public void saveRoundStatistics() {
        fullStatistics.addRoundStatistics(roundStatistics);
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
    public Boolean isAggressive() {
        return aggressive;
    }

    /**
     * @param isAggressive the isAggressive to set
     */
    public void setIsAggressive(Boolean aggressive) {
        this.aggressive = aggressive;
    }

    /**
     * @return the probabilitiesOfWin
     */
    public int getProbabilitiesOfWin() {
        return probabilitiesOfWin;
    }
}
