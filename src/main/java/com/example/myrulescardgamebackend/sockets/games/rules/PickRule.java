package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;

import com.example.myrulescardgamebackend.sockets.domain.Card;
import com.example.myrulescardgamebackend.sockets.domain.MessageData;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.Player;

public class PickRule implements Rule{
    ArrayList<Card> cards;
    int pickAmount;

    public PickRule(ArrayList<Card> cards, int pickAmount) {
        this.cards = cards;
        this.pickAmount = pickAmount;
    }

    @Override
    public void doRule(Game game) {
        ArrayList<Player> turnOrder = game.getGameState().getTurnOrder();
        Player pickingPlayer = turnOrder.get(1);
        for (int i = 0; i < pickAmount; i++) {
            pickingPlayer.getCards().add(game.pickCard());
        }

        String sAfterCard = "";
        if (pickAmount > 1) sAfterCard = "s";
        for (Player plr: game.getGameState().getPlayers()) {
            plr.getSocket().sendEvent("message",
                    new MessageData("[SERVER]: The player " + turnOrder.get(1).getName() + " had to pick " + pickAmount + " card"+ sAfterCard + "!",
                            true));
        }
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }
}
