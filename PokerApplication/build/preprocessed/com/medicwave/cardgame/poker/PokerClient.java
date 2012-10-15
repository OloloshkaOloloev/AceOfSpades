/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medicwave.cardgame.poker;

import ca.ualberta.cs.poker.*;

/**
 * A class acting as a poker client. It extends the base class
 * {@link PokerClientBase} and is intended to be reviewed and/or changed by
 * students.
 *
 * @author Tobias Persson
 */
public class PokerClient extends PokerClientBase {

    private String name = null;
    private Agent agent = new Agent();
    private Players players = new Players();
    // For test
    public static final int NUMBER_OF_CASES = 50;
    public static final int SEED = 51;
    //private static int[][] testCases = new int[NUMBER_OF_CASES][Combination.FIVE_CARDS_POKER];

    /**
     * Creates a new instance of
     * <code>PokerClient</code>.
     *
     * @param server the address of the poker server.
     * @param port the port used by the poker server.
     */
    public PokerClient(String server, int port) {
        super(server, port);

//
////    private void test() {
////        random = new Random();
////
////        // Creating test array
//        for (int i = 0; i < NUMBER_OF_CASES; i++) {
//            for (int j = 0; j < Combination.FIVE_CARDS_POKER; j++) {
//                testCases[i][j] = random.nextInt(SEED);
//            }
//        }
////
////        // Starting testing
//////        testCases = new int[][]{{19, 32, 12, 22, 36}, {0, 37, 50, 24, 1}, {27, 14, 20, 33, 46}, {43, 31, 19, 7, 47}, {39, 44, 47, 50, 49}, {29, 42, 16, 3, 0}, {24, 23, 22, 21, 20}, {25, 24, 23, 22, 21}};
//////        for (int i = 0; i < 8; i++) {
//////            Agent agent = new Agent(testCases[i]);
//////        }
//        //testCases = new int[][]{{34, 21, 41, 28, 1}, {44, 32, 47, 35, 13}, {30, 18, 45, 33, 1}, {40, 47, 51, 10, 11}, {41, 26, 46, 47, 48}, {12, 14, 30, 17, 48}};
//        //testCases = new int[][]{{20, 44, 23, 0, 8}};
//        //Start testing
//        for (int i = 0; i < NUMBER_OF_CASES; i++) {
//            Agent agent = new Agent();
//            agent.refreshHand(testCases[i]);
//            Card[] cards = agent.getCardsToThrow();
//            System.out.print("");
//        }
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player as a single word without * * * * *      * space. <code>null</code> is not a valid answer.
     */
    protected String queryPlayerName() {
        // NOTE
        // You typically return a constant (final) string here, not something
        // generated dynamically and/or randomly as is done now. This is only
        // to give different clients different names during testing.
        if (name == null) {
            name = "Client#" + (System.currentTimeMillis() & 0xFFF);
        }
        return name;
    }

    /**
     * Called when a new round begins.
     *
     * @param round the round number (increased for each new round).
     */
    protected void infoNewRound(int round) {
        players.saveRoundStatistics();
        players.resetPlayersInfo();
        agent.reset();
        notifyTextReceivers("Starting round #" + round);
    }

    /**
     * Called when the poker server informs that the game is completed.
     */
    protected void infoGameOver() {
        notifyTextReceivers("The game is over.");
    }

    /**
     * Called when the server informs the players how many chips a player has.
     *
     * @param playerName the name of a player.
     * @param chips the amount of chips the player has.
     */
    protected void infoPlayerChips(String playerName, int chips) {
        notifyTextReceivers("The player '" + playerName + "' has " + chips + " chips.");
        if (!playerName.equals(this.name)) {
            players.setPlayersChips(playerName, chips);
        }
    }

    /**
     * Called when the ante has changed.
     *
     * @param ante the new value of the ante.
     */
    protected void infoAnteChanged(int ante) {
        notifyTextReceivers("The ante is " + ante);
    }

    /**
     * Called when a player had to do a forced bet (putting the ante in the
     * pot).
     *
     * @param playerName the name of the player forced to do the bet.
     * @param forcedBet the number of chips forced to bet.
     */
    protected void infoForcedBet(String playerName, int forcedBet) {
        notifyTextReceivers("Player " + playerName + " made a forced bet of " + forcedBet + " chips.");
        OtherPlayer player = players.getPlayer(playerName);
        if(player != null) {
            player.getRoundStatistics().increaseChipsBetted(forcedBet);
        }
    }

    /**
     * Called when a player opens a betting round.
     *
     * @param playerName the name of the player that opens.
     * @param openBet the amount of chips the player has put into the pot.
     */
    protected void infoPlayerOpen(String playerName, int openBet) {
        // Increasing raises count in statistics
        OtherPlayer player = players.getPlayer(name);
        if (player != null) {
            player.getRoundStatistics().increaseRaises();
            player.getRoundStatistics().increaseChipsBetted(openBet);
        }
        notifyTextReceivers("Player " + playerName + " opened, has put " + openBet + " chips into the pot.");
    }

    /**
     * Called when a player checks.
     *
     * @param playerName the name of the player that checks.
     */
    protected void infoPlayerCheck(String playerName) {
        // Increase checks in statistics
        OtherPlayer player = players.getPlayer(name);
        if (player != null) {
            player.getRoundStatistics().increaseChecks();
        }
        notifyTextReceivers("Player " + playerName + " checked.");
    }

    /**
     * Called when a player raises.
     *
     * @param playerName the name of the player that raises.
     * @param amountRaisedTo the amount of chips the player raised to.
     */
    protected void infoPlayerRaise(String playerName, int amountRaisedTo) {
        // Increasing raises count in statistics
        OtherPlayer player = players.getPlayer(name);
        if(player != null) {
            player.getRoundStatistics().increaseRaises();
            player.getRoundStatistics().increaseChipsBetted(amountRaisedTo);
        }
        notifyTextReceivers("Player " + playerName + " raised to " + amountRaisedTo + " chips.");
    }

    /**
     * Called when a player calls.
     *
     * @param playerName the name of the player that calls.
     */
    protected void infoPlayerCall(String playerName) {
        // Increasing calls count in statistics
        OtherPlayer player = players.getPlayer(name);
        if(player != null) {
            player.getRoundStatistics().increaseCalls();
        }
        notifyTextReceivers("Player " + playerName + " called.");
    }

    /**
     * Called when a player folds.
     *
     * @param playerName the name of the player that folds.
     */
    protected void infoPlayerFold(String playerName) {
        // Setting fold in statistics for particular round
        OtherPlayer player = players.getPlayer(name);
        if(player != null) {
            player.getRoundStatistics().setFold(true);
        }
        notifyTextReceivers("Player " + playerName + " folded.");
    }

    /**
     * Called when a player goes all-in.
     *
     * @param playerName the name of the player that goes all-in.
     * @param allInChipCount the amount of chips the player has in the pot and
     * goes all-in with.
     */
    protected void infoPlayerAllIn(String playerName, int allInChipCount) {
        // Setting all-in in statistics for particular round
        OtherPlayer player = players.getPlayer(name);
        if(player != null) {
            player.getRoundStatistics().setAllIn(true);
            player.getRoundStatistics().increaseChipsBetted(allInChipCount);
        }
        notifyTextReceivers("Player " + playerName + " goes all-in with a pot of " + allInChipCount + " chips.");
    }

    /**
     * Called when a player has exchanged (thrown away and drawn new) cards.
     *
     * @param playerName the name of the player that has exchanged cards.
     * @param cardCount the number of cards exchanged.
     */
    protected void infoPlayerDraw(String playerName, int cardCount) {
        notifyTextReceivers("Player " + playerName + " exchanged " + cardCount + " cards.");
        OtherPlayer player = players.getPlayer(playerName);
        if (playerName != null && !playerName.equals(name)) {
            player.calculateProbabilityOfWin(cardCount);
        }
    }

    /**
     * Called during the showdown when a player shows his hand.
     *
     * @param playerName the name of the player whose hand is shown.
     * @param hand the players hand.
     */
    protected void infoPlayerHand(String playerName, Hand hand) {
        // The hands toString() methods prepends the returned string with space.
        notifyTextReceivers("Player " + playerName + " has this hand:" + hand + " (" + getHandName(hand) + ", category #" + getHandCategory(hand) + ")");
        OtherPlayer player = players.getPlayer(playerName);
        if(player != null) {
            player.calculateAggressiveCoefficient(hand);
        }
    }

    /**
     * Called during the showdown when a players undisputed win is reported.
     *
     * @param playerName the name of the player whose undisputed win is
     * anounced.
     * @param winAmount the amount of chips the player won.
     */
    protected void infoRoundUndisputedWin(String playerName, int winAmount) {
        notifyTextReceivers("Player " + playerName + " won " + winAmount + " chips undisputed.");
    }

    /**
     * Called during the showdown when a players win is reported. If a player
     * does not win anything, this method is not called.
     *
     * @param playerName the name of the player whose win is anounced.
     * @param winAmount the amount of chips the player won.
     */
    protected void infoRoundResult(String playerName, int winAmount) {
        notifyTextReceivers("Player " + playerName + " won " + winAmount + " chips.");
    }

    /**
     * Called during the betting phases of the game when the player needs to
     * decide what open action to choose.
     *
     * @param minimumPotAfterOpen the total minimum amount of chips to put into
     * the pot if the answer action is {@link BettingAnswer#ACTION_OPEN}.
     * @param playersCurrentBet the amount of chips the player has already put
     * into the pot (dure to the forced bet).
     * @param playersRemainingChips the number of chips the player has not yet
     * put into the pot.
     * @return An answer to the open query. The answer action must be one of
     * {@link BettingAnswer#ACTION_OPEN}, {@link BettingAnswer#ACTION_ALLIN} or
     *                              {@link BettingAnswer#ACTION_CHECK }. If the action is open, the answers
     * amount of chips in the anser must be * * * * *      * between <code>minimumPotAfterOpen</code> and the players total
     * amount of chips (the amount of chips alrady put into pot plus the
     * remaining amount of chips).
     */
    protected BettingAnswer queryOpenAction(int minimumPotAfterOpen, int playersCurrentBet, int playersRemainingChips) {
        notifyTextReceivers("Player requested to choose an opening action.");
        return agent.makeOpenAction(this, minimumPotAfterOpen, playersCurrentBet, playersRemainingChips);
    }

    /**
     * Called when the player receives cards. The hand contains all cards the
     * player currently holds.
     *
     * @param hand the hand of cards the player currently holds.
     */
    protected void infoCardsInHand(Hand hand) {
        notifyTextReceivers("The cards in hand is" + hand + ".");
        agent.refreshHand(hand);
    }

    /**
     * Called during the betting phases of the game when the player needs to
     * decide what call/raise action to choose.
     *
     * @param maximumBet the maximum number of chips one player has already put
     * into the pot.
     * @param minimumAmountToRaiseTo the minimum amount of chips to bet if the
     * returned answer is {@link BettingAnswer#ACTION_RAISE}.
     * @param playersCurrentBet the number of chips the player has already put
     * into the pot.
     * @param playersRemainingChips the number of chips the player has not yet
     * put into the pot.
     * @return An answer to the call or raise query. The answer action must be
     * one of null null null null null null null null null null null     {@link BettingAnswer#ACTION_FOLD}, {@link BettingAnswer#ACTION_CALL},
     *                                  {@link BettingAnswer#ACTION_RAISE} or {@link BettingAnswer#ACTION_ALLIN
     * }. If the players number of remaining chips is less than the maximum bet
     * and the players current bet, the call action is not available. If the
     * players number of remaining chips plus the players current bet is less
     * than the minimum amount of chips to raise to, the raise action is not
     * available. If the action is raise, the answers amount of chips is the
     * total amount of chips the player puts into the pot and must be * *
     * between <code>minimumAmountToRaiseTo</code> and
     * <code>playersCurrentBet+playersRemainingChips</code>.
     */
    protected BettingAnswer queryCallRaiseAction(int maximumBet, int minimumAmountToRaiseTo, int playersCurrentBet, int playersRemainingChips) {
        notifyTextReceivers("Player requested to choose a call/raise action.");
        return agent.makeRaiseAction(this, maximumBet, minimumAmountToRaiseTo, playersCurrentBet, playersRemainingChips);
    }

    /**
     * Called during the draw phase of the game when the player is offered to
     * throw away some (possibly all) of the cards on hand in exchange for new.
     *
     * @return An array of the cards on hand that should be thrown away in
     * exchange for new, or <code>null</code> or an empty array to keep all
     * cards.
     * @see #infoCardsInHand(ca.ualberta.cs.poker.Hand)
     */
    protected Card[] queryCardsToThrow() {
        notifyTextReceivers("Requested information about what cards to throw");
        agent.setToRoundTwo();
        return agent.getCardsToThrow();
    }
}
