package ua.kiev.javacourses.casino;

import java.io.Serializable;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 27.11.13
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public class Card implements Serializable {

    public static String[] suitsBuf = {"SPADES", "HEARTS", "DIAMONDS", "CLUBS"};
    public static String[] valuesBuf = {"TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT",
            "NINE", "TEN", "JACK", "QUEEN", "KING", "ACE"};
    public static int[] pointsBuf = {2,3,4,5,6,7,8,9,10,10,10,10,11};

    String suit; // масть
    String value; // достоинство
    int points; // количество очков

    public Card() {
        Random rnd = new Random();
        int idx = rnd.nextInt(3);
        this.suit = suitsBuf[idx];
        idx = rnd.nextInt(12);
        this.value = valuesBuf[idx];
        this.points = pointsBuf[idx];
    }

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        points = 0;
        for (int i=0; i<valuesBuf.length; i++) {
            if (value.equals(valuesBuf[i])) {
                points = pointsBuf[i];
                break;
            }
        }
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return (value + " of " + suit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (points != card.points) return false;
        if (!suit.equals(card.suit)) return false;
        if (!value.equals(card.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + points;
        return result;
    }
}

