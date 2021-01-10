package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;
import java.util.Collections;

import com.example.myrulescardgamebackend.sockets.domain.Card;
import com.example.myrulescardgamebackend.sockets.domain.MessageData;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.Player;

public class ReverseRule implements Rule{
    ArrayList<Card> cards;

    public ReverseRule(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void doRule(Game game) {
        ArrayList<Player> turnOrder = game.getGameState().getTurnOrder();
        ArrayList<Player> turnOrderFlipped = new ArrayList<>();

        Collections.reverse(turnOrder);
        Player currentPlr = turnOrder.get(turnOrder.size() - 1);
        turnOrder.remove(turnOrder.size() - 1);
        turnOrder.add(0, currentPlr);

//        turnOrderFlipped.add(turnOrder.get(0));
//        for (int i = turnOrder.size(); 1 > turnOrder.size(); i--) {
//            turnOrderFlipped.add(turnOrder.get(i - 1));
//        }

        for (Player plr: game.getGameState().getPlayers()) {
            plr.getSocket().sendEvent("message",
                    new MessageData("[SERVER]: The turn order has been reversed!",
                            true));
        }
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }
}
