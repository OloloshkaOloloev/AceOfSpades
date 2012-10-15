package com.medicwave.cardgame.poker;

/**
 * Class contains statistic for all rounds
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class FullStatistics {
    // =========================================================================
    // Fields
    // =========================================================================

    private RoundStatistics[] statistics;

    // =========================================================================
    // Constructor
    // =========================================================================
    public FullStatistics() {
    }

    // =========================================================================
    // Methods
    // =========================================================================
    public void addRoundStatistics(RoundStatistics roundStatistics) {
        RoundStatistics[] temp = new RoundStatistics[statistics.length + 1];
        System.arraycopy(statistics, 0, temp, 0, statistics.length);
        temp[statistics.length] = roundStatistics;
        statistics = temp;
    }

    public boolean isChecksMoreThanRaises() {
        int checks = 0;
        int raises = 0;
        for (int i = 0; i < statistics.length; i++) {
            checks += statistics[i].getChecks();
            raises += statistics[i].getRaises();
        }
        return checks > raises;
    }

    public boolean isCallsMoreThanRaises() {
        int calls = 0;
        int raises = 0;
        for (int i = 0; i < statistics.length; i++) {
            calls += statistics[i].getCalls();
            raises += statistics[i].getRaises();
        }
        return calls > raises;
    }
    
    public int getAllInsCount() {
        int allIns = 0;
        for(int i = 0; i < statistics.length; i++) {
            if(statistics[i].isAllIn()) {
                allIns++;
            }
        }
        return allIns;
    }
    
    public int getFoldsCount() {
        int folds = 0;
        for(int i = 0; i < statistics.length; i++) {
            if(statistics[i].isFold()) {
                folds++;
            }
        }
        return folds;
    }
}
