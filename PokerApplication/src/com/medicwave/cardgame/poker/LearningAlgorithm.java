package com.medicwave.cardgame.poker;

/**
 * Implementation of the id3 algorithm. Algorithm is recalculating every 5
 * rounds according to collected statistics.
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class LearningAlgorithm {
    // =========================================================================
    // Constants
    // =========================================================================

    public static final int NUMBER_OF_EXAMPLES = 6;
    public static final int NUMBER_OF_ATTRIBUTES = 4;
    public static final int RESULT_COLUMN = 5;
    
    // =========================================================================
    // Fields
    // =========================================================================
    private boolean[][] predefinedExamples = new boolean[NUMBER_OF_EXAMPLES][NUMBER_OF_ATTRIBUTES + 1];

    // =========================================================================
    // Constructor
    // =========================================================================
    public LearningAlgorithm() {
        
    }

    // =========================================================================
    // Methods
    // =========================================================================
    public Boolean isPlayerAggresive(OtherPlayer player, FullStatistics statistics, int round) {
        if (round % 5 == 0 && player != null && statistics != null) {
            statistics.getFullStatisticsForTesting();
        }
        return null;
    }
;
}
