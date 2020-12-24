package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.example.myrulescardgamebackend.CardEnums;
import com.example.myrulescardgamebackend.sockets.domain.Card;
import com.example.myrulescardgamebackend.sockets.domain.RuleSetData;
import com.example.myrulescardgamebackend.sockets.games.rules.Rule;
import com.example.myrulescardgamebackend.sockets.games.rules.RuleSet;
import com.example.myrulescardgamebackend.sockets.games.rules.SkipRule;

public class GameManager {
    Card[][] cardsLookupTable;

    public GameManager() {
        cardsLookupTable = new Card[][] {
                {new Card(CardEnums.Suit.CLUBS, CardEnums.Value.ACE),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.TWO),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.THREE),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.FOUR),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.FIVE),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.SIX),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.SEVEN),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.EIGHT),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.NINE),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.TEN),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.JACK),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.QUEEN),
                    new Card(CardEnums.Suit.CLUBS, CardEnums.Value.KING)},
                {new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.ACE),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.TWO),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.THREE),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.FOUR),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.FIVE),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.SIX),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.SEVEN),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.EIGHT),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.NINE),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.TEN),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.JACK),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.QUEEN),
                    new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.KING)},
                {new Card(CardEnums.Suit.SPADES, CardEnums.Value.ACE),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.TWO),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.THREE),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.FOUR),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.FIVE),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.SIX),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.SEVEN),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.EIGHT),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.NINE),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.TEN),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.JACK),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.QUEEN),
                    new Card(CardEnums.Suit.SPADES, CardEnums.Value.KING)},
                {new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.ACE),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.TWO),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.THREE),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.FOUR),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.FIVE),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.SIX),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.SEVEN),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.EIGHT),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.NINE),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.TEN),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.JACK),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.QUEEN),
                    new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.KING)},
                {new Card(CardEnums.Suit.JOKER, CardEnums.Value.JOKER1),
                    new Card(CardEnums.Suit.JOKER, CardEnums.Value.JOKER2)}
        };
    }

    public Game createGame(RuleSet ruleSet) {
        Game game = new Game();
        game.setPickPile(createGameDeckWithRules(ruleSet.getRules()));

        return game;
    }

    public void addPlayersToGame(Game game, ArrayList<Player> players) {
        game.setPlayers(players);

        for (Player player: players) {
            player.setGame(game);
        }
    }

    public ArrayList<Card> createGameDeckWithRules(ArrayList<Rule> rules) {
        Card[][] cardWithRules = cardsLookupTable.clone();
        for (Rule rule: rules) {
            for (Card card: rule.getCards()) {
                cardWithRules[card.getSuit()][card.getValue()].addRule(rule);
            }
        }

        ArrayList<Card> deckWithRules = new ArrayList<Card>();
        for (Card[] array : cardWithRules) {
            deckWithRules.addAll(Arrays.asList(array));
            deckWithRules.addAll(Arrays.asList(array));
        }

        Collections.shuffle(deckWithRules);

        return deckWithRules;
    }

    public RuleSet createRuleSet(RuleSetData data) {
        //todo ad actual rules creating function
        ArrayList<Rule> rules = new ArrayList<>();
        RuleSet ruleSet = new RuleSet(rules);

        return ruleSet;
    }
}
