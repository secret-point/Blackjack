package ua.kiev.javacourses.casino;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 02.12.13
 * Time: 0:26
 * To change this template use File | Settings | File Templates.
 */
public class Dealer implements Serializable {
    private CardsDeck cardsDeck;
    private Card[] cards = new Card[11];
    int lastCardIdx = 0;
    int points = 0;

    public Dealer(int cardsNumber, int numberOfDecks) {
        cardsDeck = new CardsDeck(cardsNumber, numberOfDecks);
        cardsDeck.shuffle();
    }

    // Выдать карту
    public Card giveCard() {
        return cardsDeck.getCard();
    }

    // Взять себе карту
    public void takeCard() {
        cards[lastCardIdx] = cardsDeck.getCard();
        points += cards[lastCardIdx].points;
        lastCardIdx++;
    }

    // Набрать карты
    public void takeCards() {
        while (points<17) {
            takeCard();
        }
    }

    // Отображение результатов
    public void showResults() {
        if (lastCardIdx>0) {
            System.out.print("*** Results for Dealer: " + points + " (");
            System.out.print(cards[0].toString());
            for (int i=0; i<lastCardIdx; i++) {
                System.out.print(", " + cards[i].toString());
            }
            System.out.print(")\r\n");
       } 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dealer dealer = (Dealer) o;

        if (lastCardIdx != dealer.lastCardIdx) return false;
        if (points != dealer.points) return false;
        if (!Arrays.equals(cards, dealer.cards)) return false;
        if (!cardsDeck.equals(dealer.cardsDeck)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardsDeck.hashCode();
        result = 31 * result + Arrays.hashCode(cards);
        result = 31 * result + lastCardIdx;
        result = 31 * result + points;
        return result;
    }
}
