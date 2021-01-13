package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;
import java.util.List;

import com.example.myrulescardgamebackend.rest.domain.Card;
import com.example.myrulescardgamebackend.sockets.domain.SocketCard;
import com.example.myrulescardgamebackend.sockets.domain.MessageData;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.Player;

public class SkipRule implements Rule {
    ArrayList<SocketCard> socketCards;

    public SkipRule(List<Card> cards) {
        socketCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            socketCards.add(new SocketCard(cards.get(i)));
        }
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
    public ArrayList<SocketCard> getCards() {
        return socketCards;
    }
}
