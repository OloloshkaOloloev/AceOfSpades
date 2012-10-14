package com.medicwave.cardgame.poker;

/**
 * Class contains methods for getting in convinient way to other players
 * information. Uses a collection of players objects.
 *
 * @author Evgeny Kubrakov, Edu Tarascon
 */
public class Players {
    // =========================================================================
    // Constants
    // =========================================================================

    public static final int FIVE_PLAYERS = 5;
    // =========================================================================
    // Fields
    // =========================================================================
    private OtherPlayer[] players = new OtherPlayer[FIVE_PLAYERS - 1];

    // =========================================================================
    // Constructors
    // =========================================================================
    public Players() {
    }

    public Players(int playersNumber) {
        this.players = new OtherPlayer[playersNumber];
    }

    public Players(String[] playersNames) {
        this.players = new OtherPlayer[playersNames.length];
        for (int i = 0; i < playersNames.length; i++) {
            this.players[i] = new OtherPlayer(playersNames[i]);
        }
    }

    // =========================================================================
    // Methods
    // =========================================================================
    public OtherPlayer getPlayer(String name) {
        for(int i = 0; i < players.length; i++) {
            if(players[i] != null && players[i].getPlayerName().equals(name)) {
                return players[i];
            }
        }
        return null;
    }
    
    public void saveRoundStatistics() {
        for(int i = 0; i < players.length; i++) {
            if(players[i] != null) {
                players[i].saveRoundStatistics();
            }
        }
    }
    
    private void initIfNeeded(String player) {
        // Finding if player was previously initialized
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                if (player.equals(players[i].getPlayerName())) {
                    return; // player was initialized before
                }
            }
        }

        // Player wasn't initialized, creating new object in empty place in array
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = new OtherPlayer(player);
            }
        }
    }
    
    public void resetPlayersInfo() {
        for(int i = 0; i < players.length; i++) {
            if(players[i] != null) {
                players[i].reset();
            }
        }
    }

    public void setCardsCountPlayerThrown(String player, int numberOfCards) {
        initIfNeeded(player);
        for (int i = 0; i < players.length; i++) {
            if (player.equals(players[i].getPlayerName())) {
                players[i].setNumberOfThrownCards(numberOfCards);
                return;
            }
        }
    }

    public int getCardsCountPlayerThrown(String player) {
        initIfNeeded(player);
        for (int i = 0; i < players.length; i++) {
            if (player.equals(players[i].getPlayerName())) {
                return players[i].getNumberOfThrownCards();
            }
        }
        return 0;
    }

    public void setPlayersChips(String player, int chips) {
        initIfNeeded(player);
        for (int i = 0; i < players.length; i++) {
            if (player.equals(players[i].getPlayerName())) {
                players[i].setChips(chips);
                return;
            }
        }
    }

    public int getPlayersChips(String player) {
        initIfNeeded(player);
        for (int i = 0; i < players.length; i++) {
            if (player.equals(players[i].getPlayerName())) {
                return players[i].getChips();
            }
        }
        return 0;
    }
}
