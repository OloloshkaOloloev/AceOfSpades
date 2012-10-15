package com.medicwave.cardgame.poker;

/**
 * Class describes statistics for one round of playing of the particular player
 * 
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class RoundStatistics {
    // =========================================================================
    // Fields
    // =========================================================================

    private int checks = 0;
    private int calls = 0;
    private int raises = 0;
    private boolean fold = false;
    private boolean allIn = false;
    private int chipsBetted = 0;

    // =========================================================================
    // Constructor
    // =========================================================================
    public RoundStatistics() {
    }

    // =========================================================================
    // Methods
    // =========================================================================
    public boolean isChecksMoreThanRaises() {
        return checks > raises;
    }

    public boolean isCallsMoreThanRaises() {
        return calls > raises;
    }

    public void increaseChecks() {
        this.checks++;
    }

    public void increaseCalls() {
        this.calls++;
    }

    public void increaseRaises() {
        this.raises++;
    }

    public void resetStatisctics() {
        this.checks = 0;
        this.calls = 0;
        this.raises = 0;
        this.allIn = false;
        this.fold = false;
    }
    
    public void increaseChipsBetted(int chips) {
        this.chipsBetted += chips;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    /**
     * @return the checks
     */
    public int getChecks() {
        return checks;
    }

    /**
     * @param checks the checks to set
     */
    public void setChecks(int checks) {
        this.checks = checks;
    }

    /**
     * @return the calls
     */
    public int getCalls() {
        return calls;
    }

    /**
     * @param calls the calls to set
     */
    public void setCalls(int calls) {
        this.calls = calls;
    }

    /**
     * @return the rases
     */
    public int getRaises() {
        return raises;
    }

    /**
     * @param rases the rases to set
     */
    public void setRaises(int rases) {
        this.raises = rases;
    }

    /**
     * @return the fold
     */
    public boolean isFold() {
        return fold;
    }

    /**
     * @param fold the fold to set
     */
    public void setFold(boolean fold) {
        this.fold = fold;
    }

    /**
     * @return the allIn
     */
    public boolean isAllIn() {
        return allIn;
    }

    /**
     * @param allIn the allIn to set
     */
    public void setAllIn(boolean allIn) {
        this.allIn = allIn;
    }

    /**
     * @return the chipsBetted
     */
    public int getChipsBetted() {
        return chipsBetted;
    }

    /**
     * @param chipsBetted the chipsBetted to set
     */
    public void setChipsBetted(int chipsBetted) {
        this.chipsBetted = chipsBetted;
    }
}
