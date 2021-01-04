package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;
import java.util.Collections;

import com.example.myrulescardgamebackend.CardEnums;
import com.example.myrulescardgamebackend.sockets.domain.Card;
import com.example.myrulescardgamebackend.sockets.domain.CardData;
import com.example.myrulescardgamebackend.sockets.games.rules.Rule;

public class Game {
    static final int CARDSPERPLAYER = 7;
    GameState gameState = new GameState();

    public void shufflePickPile() {
        Collections.shuffle(gameState.getPickPile());
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setPickPile(ArrayList<Card> pickPile) {
        this.gameState.setPickPile(pickPile);
    }

    public void setPlayers(ArrayList<Player> players) {
        this.gameState.setPlayers(players);
    }

    public void initGame() {
        //gives each player cards
        for (Player player: gameState.getPlayers()) {
            ArrayList<Card> cards = new ArrayList<>();
            for (int i = 0; i < CARDSPERPLAYER; i++) {
                cards.add(pickCard());
            }
            player.setCards(cards);
        }

        //sets turn order and current player
        ArrayList<Player> turnOrder = (ArrayList<Player>) gameState.getPlayers().clone();
        Collections.shuffle(turnOrder);
        gameState.setCurrentPlayer(turnOrder.get(0));
        gameState.setTurnOrder(turnOrder);
        gameState.playedCard(pickCard());
    }


    public ArrayList<Player> getWinners() {
        ArrayList<Player> winners = new ArrayList<Player>();
        for (Player player: this.gameState.getPlayers()) {
            if (player.getCards().size() < 1) winners.add(player);
        }
        return winners;
    }

    public Card getPlayersCard(CardData cardData, Player player) {
        for (Card card: player.getCards()) {
            if (card.getValue() == cardData.getValue() && card.getSuit() == cardData.getSuit()) {
                return card;
            }
        }
        return null;
    }

    public boolean isCardPlayable(Card card) {
        //currently only standard rules which means and will be declared on the home page:
        //joker can always be played and anything can be put on top
        //card played has to be of the same suit or value
        //possibly implement later picking the rules for a playable card
        if (card.getSuit() == CardEnums.Suit.JOKER.getSuit()) {
            return true;
        }
        if (gameState.getTopCard().getSuit() == CardEnums.Suit.JOKER.getSuit()) {
            return true;
        }
        if (card.getSuit() == gameState.getTopCard().getSuit()) {
            return true;
        }
        if (card.getValue() == gameState.getTopCard().getValue()) {
            return true;
        }
        return false;
    }

    public Card pickCard() {
        ArrayList<Card> pickPile = gameState.getPickPile();
        ArrayList<Card> discardPile = gameState.getDiscardPile();

        if (pickPile.size() < 1) {
            pickPile = (ArrayList<Card>) discardPile.clone();
            discardPile.clear();
            Collections.shuffle(pickPile);
        }

        if (pickPile.size() < 1) {
            return null;
        }

        Card card = pickPile.get(pickPile.size() - 1);
        pickPile.remove(pickPile.size() - 1);
        return card;
    }

    public void playCard(Card card, Player player) {
        player.getCards().remove(card);
        if (card.getRules() != null) {
            for (Rule rule: card.getRules()) {
                rule.doRule(this);
            }
        }
        gameState.getDiscardPile().add(gameState.getTopCard());
        gameState.setTopCard(card);
    }

    public void nextPlayer() {
        ArrayList<Player> turnOrder = gameState.getTurnOrder();
        Player player = turnOrder.get(0);
        turnOrder.remove(0);
        turnOrder.add(player);
        gameState.setCurrentPlayer(turnOrder.get(0));
    }
}
