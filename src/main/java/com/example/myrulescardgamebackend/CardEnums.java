package com.example.myrulescardgamebackend;

public class CardEnums {
    public enum Suit {
        CLUBS(0),
        HEARTHS(1),
        SPADES(2),
        DIAMONDS(3),
        JOKER(4);

        private int suit;

        public int getSuit() {
            return this.suit;
        }

        Suit(int suit) {
            this.suit = suit;
        }
    }

    public enum Value {
        ACE(0),
        TWO(1),
        THREE(2),
        FOUR(3),
        FIVE(4),
        SIX(5),
        SEVEN(6),
        EIGHT(7),
        NINE(8),
        TEN(9),
        JACK(10),
        QUEEN(11),
        KING(12),
        JOKER1(0),
        JOKER2(1);

        private int value;

        public int getValue() {
            return this.value;
        }

        Value(int value) {
            this.value = value;
        }
    }
}
