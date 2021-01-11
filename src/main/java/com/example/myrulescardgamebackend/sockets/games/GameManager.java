package com.example.myrulescardgamebackend.sockets.games;

import static com.example.myrulescardgamebackend.RuleEnum.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.example.myrulescardgamebackend.CardEnums;
import com.example.myrulescardgamebackend.rest.domain.CardRule;
import com.example.myrulescardgamebackend.rest.domain.RuleSet;
import com.example.myrulescardgamebackend.sockets.domain.SocketCard;
import com.example.myrulescardgamebackend.sockets.games.rules.PickRule;
import com.example.myrulescardgamebackend.sockets.games.rules.ReverseRule;
import com.example.myrulescardgamebackend.sockets.games.rules.Rule;
import com.example.myrulescardgamebackend.sockets.games.rules.RuleSetSockets;
import com.example.myrulescardgamebackend.sockets.games.rules.SkipRule;

public class GameManager {
    SocketCard[][] cardsLookupTable;

    public GameManager() {
        cardsLookupTable = new SocketCard[][] {
                {new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.ACE),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.TWO),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.THREE),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.FOUR),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.FIVE),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.SIX),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.SEVEN),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.EIGHT),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.NINE),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.TEN),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.JACK),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.QUEEN),
                    new SocketCard(CardEnums.Suit.CLUBS, CardEnums.Value.KING)},
                {new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.ACE),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.TWO),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.THREE),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.FOUR),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.FIVE),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.SIX),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.SEVEN),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.EIGHT),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.NINE),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.TEN),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.JACK),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.QUEEN),
                    new SocketCard(CardEnums.Suit.HEARTHS, CardEnums.Value.KING)},
                {new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.ACE),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.TWO),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.THREE),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.FOUR),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.FIVE),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.SIX),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.SEVEN),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.EIGHT),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.NINE),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.TEN),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.JACK),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.QUEEN),
                    new SocketCard(CardEnums.Suit.SPADES, CardEnums.Value.KING)},
                {new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.ACE),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.TWO),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.THREE),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.FOUR),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.FIVE),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.SIX),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.SEVEN),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.EIGHT),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.NINE),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.TEN),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.JACK),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.QUEEN),
                    new SocketCard(CardEnums.Suit.DIAMONDS, CardEnums.Value.KING)},
                {new SocketCard(CardEnums.Suit.JOKER, CardEnums.Value.JOKER1),
                    new SocketCard(CardEnums.Suit.JOKER, CardEnums.Value.JOKER2)}
        };
    }

    public Game createGame(RuleSetSockets ruleSetSockets) {
        Game game = new Game();
        game.setPickPile(createGameDeckWithRules(ruleSetSockets.getRules()));

        return game;
    }

    public void addPlayersToGame(Game game, ArrayList<Player> players) {
        game.setPlayers(players);

        for (Player player: players) {
            player.setGame(game);
        }
    }

    public ArrayList<SocketCard> createGameDeckWithRules(ArrayList<Rule> rules) {
        SocketCard[][] socketCardWithRules = new SocketCard[cardsLookupTable.length][];
        for (int i = 0; i < cardsLookupTable.length; i++) {
            socketCardWithRules[i] = new SocketCard[cardsLookupTable[i].length];
            for (int j = 0; j < cardsLookupTable[i].length; j++) {
                socketCardWithRules[i][j] = new SocketCard(cardsLookupTable[i][j]);
            }
        }

        for (Rule rule: rules) {
            for (SocketCard socketCard : rule.getCards()) {
                socketCardWithRules[socketCard.getSuit()][socketCard.getValue()].addRule(rule);
            }
        }

        ArrayList<SocketCard> deckWithRules = new ArrayList<SocketCard>();
        for (SocketCard[] array : socketCardWithRules) {
            deckWithRules.addAll(Arrays.asList(array));
            deckWithRules.addAll(Arrays.asList(array));
        }

        Collections.shuffle(deckWithRules);

        return deckWithRules;
    }

    public RuleSetSockets createRuleSet(RuleSet data) {
        //todo ad actual rules creating function
        ArrayList<Rule> rules = new ArrayList<>();


        for (CardRule cardRule : data.getCardRules()) {
            switch (values()[cardRule.getRuleEnum()]) {
            case SKIP:
                rules.add(new SkipRule(cardRule.getCards()));
                break;
            case PICK1:
                rules.add(new PickRule(cardRule.getCards(), 1));
                break;
            case PICK2:
                rules.add(new PickRule(cardRule.getCards(), 2));
                break;
            case PICK4:
                rules.add(new PickRule(cardRule.getCards(), 4));
                break;
            case REVERSE:
                rules.add(new ReverseRule(cardRule.getCards()));
                break;
            default:
                break;
            }
        }

        RuleSetSockets ruleSetSockets = new RuleSetSockets(rules);

        return ruleSetSockets;
    }
}
