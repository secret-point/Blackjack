package ua.kiev.javacourses.casino;

import ua.kiev.javacourses.sys.MyException;
import ua.kiev.javacourses.visitors.Player;

import java.io.*;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 02.12.13
 * Time: 2:52
 * To change this template use File | Settings | File Templates.
 */
public class Game implements Serializable {
    // Настройки игры
    int cardsNumber = 52;
    int numberOfDecks = 4;
    public Dealer dealer;
    static int maxPlayerNumber = 7;
    Player[] players = new Player[maxPlayerNumber];
    int playersNum = 0;
    private static String saveName = "blackjack_save.dat";

    public Game() {
        dealer = new Dealer(cardsNumber, numberOfDecks);
    }

    public Game(int cardsNumber) {
        dealer = new Dealer(cardsNumber, numberOfDecks);
    }

    // Добавить игрока в игру
    public void addPlayer(Player player) {
        if (playersNum < maxPlayerNumber) {
            players[playersNum] = player;
            playersNum++;
        } else {
            System.out.println("Players limit reached!");
        }
    }

    // Печать в консоль списка игроков
    public void printPlayerList() {
        System.out.println("Players list:");
        if (playersNum > 0) {
            for (int i=0; i< playersNum; i++) {
                System.out.println((i+1) + ". " + players[i].getName());
            }
            System.out.println();
        } else {
            System.out.println("Players list is empty.\n");
        }
    }

    // Запуск игры, выдача карт и определение победителей
    public void startGame() {
        if (playersNum > 0) {
            for (int i=0; i< playersNum; i++) {
                players[i].giveMeCard();
                players[i].giveMeCard();
                players[i].giveMeCards();
                players[i].showResults();
            }

            dealer.takeCards();
            dealer.showResults();
        } else {
            System.out.println("There are no players to play the game =(");
        }

        try {
            printWinners();
        }
        catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    // Метод, определяющий победителей
    private void printWinners() throws MyException{
        StringBuilder winList = new StringBuilder();
        StringBuilder winPtsList = new StringBuilder();
        int maxPoints = 0;
        if (dealer.points < 22) {
            maxPoints = dealer.points;
        }
        for(int i=0; i<playersNum; i++) {
            if (players[i].getPoints() < 22 && players[i].getPoints() > maxPoints) {
                maxPoints = players[i].getPoints();
            }
        }

        if( maxPoints > 0 ) {
            System.out.println("\r\nWinners with " + maxPoints + " points:");
            if (dealer.points == maxPoints) {
                winList.append("Dealer");
                winPtsList.append(dealer.points);
                System.out.println("Dealer");
            }
            for(int i=0; i<playersNum; i++) {
                if (players[i].getPoints() == maxPoints) {
                    winList.append(players[i].getName());
                    winPtsList.append(players[i].getPoints());
                    System.out.println(players[i].getName());
                }
                /*
                if (players[i].getPoints()==22) {
                    throw new MyException(players[i].getName() + " is cheater!");
                }*/
            }
        } else {
            System.out.println("\r\nThere are no winners.");
        }
        System.out.println();
    }

    // Saving game
    public boolean saveGame() {
        boolean result=false;
        long time=System.nanoTime();

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveName));
            out.writeObject(this);
            out.close();
            result=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Loading game
    public static Game loadGame() {
        Game game=null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveName));
            Object g=in.readObject();
            System.out.println(g.getClass().toString());
            game=(Game)g;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (cardsNumber != game.cardsNumber) return false;
        if (numberOfDecks != game.numberOfDecks) return false;
        if (playersNum != game.playersNum) return false;
        if (!dealer.equals(game.dealer)) return false;
        if (!Arrays.equals(players, game.players)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardsNumber;
        result = 31 * result + numberOfDecks;
        result = 31 * result + dealer.hashCode();
        result = 31 * result + Arrays.hashCode(players);
        result = 31 * result + playersNum;
        return result;
    }
}
