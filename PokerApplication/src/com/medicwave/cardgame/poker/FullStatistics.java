package com.medicwave.cardgame.poker;

import java.util.Vector;

/**
 * Class contains statistic for all rounds
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class FullStatistics {
    // =========================================================================
    // Fields
    // =========================================================================

    private RoundStatistics[] statistics = new RoundStatistics[0];

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
    
    public Vector getFullStatisticsForTesting() {
        Vector attributes = new Vector();
        int checks = 0;
        int raises = 0;
        int calls = 0;
        int folds = 0;
        int allIns = 0;
        for (int i = 0; i < statistics.length; i++) {
            checks += statistics[i].getChecks();
            raises += statistics[i].getRaises();
            calls += statistics[i].getCalls();
            folds += statistics[i].isFold() ? 1 : 0;
            allIns += statistics[i].isAllIn() ? 1 : 0;
        }
        
        return attributes;
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
    
    public boolean isCallsMoreFolds() {
        int calls = 0;
        int folds = 0;
        for (int i = 0; i < statistics.length; i++) {
            calls += statistics[i].getCalls();
            folds += statistics[i].isFold() ? 1 : 0;
        }
        return calls > folds;
    }
    
    public boolean isRaisesMoreThanAllIns() {
        int calls = 0;
        int allIns = 0;
        for (int i = 0; i < statistics.length; i++) {
            calls += statistics[i].getCalls();
            allIns += statistics[i].isAllIn() ? 1 : 0;
        }
        return calls > allIns;
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
