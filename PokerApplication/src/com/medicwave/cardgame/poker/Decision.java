package com.medicwave.cardgame.poker;

/**
 * Class describes the decission(Raise, check , fold ,all-in) that agent has to
 * make in five-cards draw poker
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class Decision {

    // =========================================================================
    // Constants
    // =========================================================================
    private static final int RANGE_20 = 20;
    private static final int RANGE_43 = 43;
    private static final float RANGE_70 = (float) 70.5;
    private static final int RANGE_90 = 90;
    private static final int RANGE_95 = 95;
    private static final int RANGE_97 = 97;
    private static final int RANGE_98 = 98;
    private static final float RANGE_99 = (float) 99.38;
    // =========================================================================
    // Fields
    // =========================================================================
    float probability;
    int chips;
    int pot;

    public Decision(float prob, int chips, int pot) {
        this.probability = prob;
        this.chips = chips;
        this.pot = pot;
    }

    // =========================================================================
    // Methods
    // =========================================================================
    /**
     * @param float probability - probability of win
     * @return int with the maximum value that our Agent must bet
     */
    public int calculateLimit(float probability, int chips, int pot) {
        int stake = chips / 10;
        if (probability >= 0 && probability <= RANGE_20) {
            return 0;

        } else if (probability <= RANGE_43) {
            if (pot > 5 * stake) {
                return 2 * stake;
            } else {
                return stake;
            }
        } else if (probability <= RANGE_70) {
            if (pot > 5 * stake) {
                return 3 * stake;
            } else {
                return stake * 2;
            }
        } else if (probability <= RANGE_90) {
            if (pot > 5 * stake) {
                return 4 * stake;
            } else {
                return stake * 3;
            }
        } else if (probability <= RANGE_95) {
            if (pot > 5 * stake) {
                return 5 * stake;
            } else {
                return stake * 4;
            }
        } else if (probability <= RANGE_97) {
            if (pot > 5 * stake) {
                return 6 * stake;
            } else {
                return stake * 5;
            }
        } else if (probability <= RANGE_98) {
            if (pot > 5 * stake) {
                return 7 * stake;
            } else {
                return stake * 6;
            }
        } else if (probability <= RANGE_99) {
            if (pot > 5 * stake) {
                return 8 * stake;
            } else {
                return stake * 7;
            }
        } else {
            return chips;
        }
    }
}
