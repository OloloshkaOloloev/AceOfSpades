package com.medicwave.cardgame.poker;

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
    private int chipsBetted = 0;
    
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
        this.setChipsBetted(0);
        this.numberOfThrownCards = 0;
    }
    
    /**
     * Setting number of the cards that current player throwed
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
     * @return the chips that player betted
     */
    public int getChipsBetted() {
        return chipsBetted;
    }

    /**
     * Setting chips that current player contributed to pot
     * 
     * @param chipsBetted - chips that current player put in the pot
     */
    public void setChipsBetted(int chipsBetted) {
        this.chipsBetted = chipsBetted;
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
}
