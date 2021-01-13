package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;
import java.util.List;

import com.example.myrulescardgamebackend.rest.domain.Card;
import com.example.myrulescardgamebackend.sockets.domain.SocketCard;
import com.example.myrulescardgamebackend.sockets.domain.MessageData;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.Player;

public class PickRule implements Rule{
    ArrayList<SocketCard> socketCards;
    int pickAmount;

    public PickRule(List<Card> cards, int pickAmount) {
        socketCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            socketCards.add(new SocketCard(cards.get(i)));
        }
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
    public ArrayList<SocketCard> getCards() {
        return socketCards;
    }
}
