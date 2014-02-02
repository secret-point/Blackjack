package ua.kiev.javacourses.visitors;

import ua.kiev.javacourses.casino.Card;
import ua.kiev.javacourses.casino.Game;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 02.12.13
 * Time: 1:26
 * To change this template use File | Settings | File Templates.
 */
public class Player implements Serializable {
    String name;
    private Card[] cards = new Card[11];
    int cardsNumber = 0;
    int points = 0;
    public enum PlayerType {
        human,
        bot
    }
    PlayerType playerType;
    static String[] names = {"John","Paul","George","Ringo"};
    Game game;

    public Player() {
        Random rnd = new Random();
        int rndNumber = rnd.nextInt(8999) + 1000;
        name = "Player " + rndNumber;
        playerType = PlayerType.bot;
    }

    public Player(String name) {
        if (name.equals("")) {
            Random rnd = new Random();
            int rndNumber = rnd.nextInt(3);
            this.name = names[rndNumber];
        } else {
            this.name = name;
        }
        playerType = PlayerType.bot;
    }

    public Player(String name, PlayerType playerType) {
        if (name.equals("")) {
            if (playerType==PlayerType.bot) {
                Random rnd = new Random();
                int rndNumber = rnd.nextInt(3);
                this.name = names[rndNumber];
            } else {
                this.name = System.getProperty("user.name");
            }
        } else {
            this.name = name;
        }
        this.playerType = playerType;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    // Присоединиться к игре
    public void play(Game game) {
        this.game = game;
        cardsNumber = 0;
        points = 0;
        game.addPlayer(this);
    }

    // Попросить карту
    public void giveMeCard() {
        if (game != null) {
            cards[cardsNumber] = game.dealer.giveCard();
            points += cards[cardsNumber].getPoints();
            cardsNumber++;
        } else {
            System.out.println("Wait until the game will begin!");
        }
    }

    // Набрать карты
    public void giveMeCards() {
        if (playerType==PlayerType.bot) {
            if (game != null) {
                while (points < 16) {
                    cards[cardsNumber] = game.dealer.giveCard();
                    points += cards[cardsNumber].getPoints();
                    cardsNumber++;
                }
            } else {
                System.out.println("Wait until the game will begin!");
            }
        } else {
            if (game != null) {
                Scanner stdIn = new Scanner(System.in);
                String input = "";
                System.out.println("Your cards:");
                for (int i=0; i< cardsNumber; i++) {
                    System.out.println(cards[i].toString());
                }
                while (!input.equals("n") && points<21) {
                    System.out.println("More? (y/n)");
                    input = stdIn.nextLine();
                    if (input.equals("y")) {
                        cards[cardsNumber] = game.dealer.giveCard();
                        points += cards[cardsNumber].getPoints();
                        System.out.println(cards[cardsNumber].toString());
                        cardsNumber++;
                    }
                }
                System.out.println();
            } else {
                System.out.println("Wait until the game will begin!");
            }
        }
    }

    // Отображение результатов
    public void showResults() {
        if (cardsNumber>0) {
            System.out.print("*** Results for " + name + ": " + points + " (");
            System.out.print(cards[0].toString());
            for (int i=1; i< cardsNumber; i++) {
                System.out.print(", "+cards[i].toString());
            }
            System.out.print(")\r\n");
        } else {
            System.out.println("Player "+name+" has no cards!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (cardsNumber != player.cardsNumber) return false;
        if (points != player.points) return false;
        if (!Arrays.equals(cards, player.cards)) return false;
        //if (!game.equals(player.game)) return false;
        if (!name.equals(player.name)) return false;
        if (playerType != player.playerType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + Arrays.hashCode(cards);
        result = 31 * result + cardsNumber;
        result = 31 * result + points;
        result = 31 * result + playerType.hashCode();
        result = 31 * result + game.hashCode();
        return result;
    }
}
