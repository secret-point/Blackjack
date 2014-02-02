package ua.kiev.javacourses.casino;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 02.12.13
 * Time: 0:26
 * To change this template use File | Settings | File Templates.
 */
public class Dealer implements Serializable {
    public class CardsDeck implements Serializable {
        Card[] cards;
        int topCardIdx = 0;

        // Формирование колоды карт
        public CardsDeck() {
            cards = new Card[36];
            for (int i=0; i<4; i++) {
                for (int k=0; k<9; k++) {
                    cards[i*9+k] = new Card(Card.suitsBuf[i], Card.valuesBuf[k+4]);
                }
            }
        }

        public CardsDeck(int cardsNumber) {
            switch (cardsNumber) {
                case 36:
                    cards = new Card[36];
                    for (int i=0; i<4; i++) {
                        for (int k=0; k<9; k++) {
                            cards[i*9+k] = new Card(Card.suitsBuf[i], Card.valuesBuf[k+4]);
                        }
                    }
                    break;

                case 52:
                    cards = new Card[52];
                    for (int i=0; i<4; i++) {
                        for (int k=0; k<13; k++) {
                            cards[i*9+k] = new Card(Card.suitsBuf[i], Card.valuesBuf[k]);
                        }
                    }
                    break;

                default:
                    System.out.println("There is no deck of such type. Default deck with 36 cards was created.");
                    cards = new Card[36];
                    for (int i=0; i<4; i++) {
                        for (int k=0; k<9; k++) {
                            cards[i*9+k] = new Card(Card.suitsBuf[i], Card.valuesBuf[k+4]);
                        }
                    }
                    break;
            }
        }

        public CardsDeck(int cardsNumber, int numberOfDecks) {
            if (numberOfDecks>0) {
                switch (cardsNumber) {
                    case 36:
                        cards = new Card[36*numberOfDecks];
                        for (int n=0; n<numberOfDecks; n++) {
                            for (int i=0; i<4; i++) {
                                for (int k=0; k<9; k++) {
                                    cards[i*9+k+n*36] = new Card(Card.suitsBuf[i], Card.valuesBuf[k+4]);
                                }
                            }
                        }
                        break;

                    case 52:
                        cards = new Card[52*numberOfDecks];
                        for (int n=0; n<numberOfDecks; n++) {
                            for (int i=0; i<4; i++) {
                                for (int k=0; k<13; k++) {
                                    cards[i*13+k+n*52] = new Card(Card.suitsBuf[i], Card.valuesBuf[k]);
                                }
                            }
                        }
                        break;

                    default:
                        System.out.println("There is no deck of such type. Default deck with 36 cards was created.");
                        cards = new Card[36];
                        for (int i=0; i<4; i++) {
                            for (int k=0; k<9; k++) {
                                cards[i*9+k] = new Card(Card.suitsBuf[i], Card.valuesBuf[k+4]);
                            }
                        }
                        break;
                }
            } else {
                System.out.println("Error while creating cards deck! numberOfDecks <= 0. Default deck with " +
                        numberOfDecks + "cards was created.");
            }
        }

        // Добавление карты в колоду
        public void add(Card card) {
            int newLen = cards.length+1;
            Card[] cardsTmp = new Card[newLen];
            System.arraycopy(cards,0,cardsTmp,0,cards.length);
            cardsTmp[newLen-1] = card;
            cards = cardsTmp;
        }

        // Вывести список карт в колоде
        public void printCardsList() {
            int i=1;
            for (Card card : cards) {
                System.out.println(i + " " + card.toString());
                i++;
            }
        }

        // Перетасовать колоду
        public void shuffle() {
            int i,j;
            Random rnd = new Random();
            Card temp;

            for (i=0; i<cards.length; i++) {
                j = rnd.nextInt(cards.length-1);
                temp = cards[i];
                cards[i] = cards[j];
                cards[j] = temp;
            }
        }

        // Выдать верхнюю в колоде карту
        public Card getCard() {
            if (topCardIdx < cards.length) {
                topCardIdx++;
                return cards[topCardIdx];
            } else {
                System.out.println("Error. End of cards deck reached.");
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CardsDeck cardsDeck = (CardsDeck) o;

            if (topCardIdx != cardsDeck.topCardIdx) return false;
            if (!Arrays.equals(cards, cardsDeck.cards)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(cards);
            result = 31 * result + topCardIdx;
            return result;
        }
    }

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
