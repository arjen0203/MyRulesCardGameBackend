package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;

import com.example.myrulescardgamebackend.sockets.domain.Card;
import com.example.myrulescardgamebackend.sockets.domain.MessageData;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.GameState;
import com.example.myrulescardgamebackend.sockets.games.Player;

public class SkipRule implements Rule {
    ArrayList<Card> cards;

    public SkipRule(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void doRule(Game game) {
        ArrayList<Player> turnOrder = game.getGameState().getTurnOrder();
        Player player = turnOrder.get(0);
        turnOrder.remove(0);
        turnOrder.add(player);
        for (Player plr: game.getGameState().getPlayers()) {
            plr.getSocket().sendEvent("message",
                    new MessageData("[SERVER]: The turn of " + turnOrder.get(0).getName() + " was skipped!",
                    true));
        }
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }
}
