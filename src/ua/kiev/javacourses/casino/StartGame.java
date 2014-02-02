package ua.kiev.javacourses.casino;

import ua.kiev.javacourses.sys.MyException;
import ua.kiev.javacourses.sys.MyOsUtils;
import ua.kiev.javacourses.visitors.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 27.11.13
 * Time: 22:29
 * To change this template use File | Settings | File Templates.
 */
public class StartGame {
    public static void main(String[] args) throws MyException {
        Scanner stdIn = new Scanner(System.in);
        String input;

        System.out.println("Hello! To see the commands list type 'help'.");
        input = stdIn.nextLine();
        while(!input.toLowerCase().equals("exit")) {
            if (input.equals("play")) {
                // Игра с игроком
                int numberOfPlayers = 7;
                Properties appProp = new Properties();
                FileInputStream in = null;

                // Чтение папаметров игры из файла
                try {
                    String dirDiv= MyOsUtils.getDirDiv();
                    System.out.println(System.getProperty("user.dir")+dirDiv+"game.properties");
                    in = new FileInputStream(System.getProperty("user.dir")+dirDiv+"game.properties");
                    appProp.load(in);
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Config file Cannot be found!");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Config file cannot be read!");
                }

                int cardsNum = Integer.valueOf(appProp.getProperty("cardsNum"));
                numberOfPlayers = Integer.valueOf(appProp.getProperty("playersNum"));
                Game newGame = new Game(cardsNum);
                System.out.println("Number of players = "+numberOfPlayers+"\nNUmber of cards = "+cardsNum);

                // Инициализация игрока (не бота)
                System.out.println("Please, enter your name:");
                input = stdIn.nextLine();
                Player player1 = new Player(input, Player.PlayerType.human);
                player1.play(newGame);
                for (int i=0; i<numberOfPlayers-1; i++) {
                    Player player = new Player();
                    player.play(newGame);
                }

                // Сохранение и начало игры
                newGame.saveGame();
                newGame.printPlayerList();
                newGame.startGame();
            }
            else if (input.equals("load")) {
                // Загрузка последней сохраненной игры
                Game newGame=Game.loadGame();
                newGame.printPlayerList();
                newGame.startGame();
            }
            else if (input.equals("demo")) {
                // Игра без игрока (только боты)
                int numberOfPlayers = 7;
                Game newGame = new Game();

                Player player1 = new Player("Player 1");
                player1.play(newGame);
                for (int i=0; i<numberOfPlayers-1; i++) {
                    Player player = new Player();
                    player.play(newGame);
                }

                newGame.saveGame();
                newGame.printPlayerList();
                newGame.startGame();
            }
            else if (input.equals("help")) {
                String s="play\t-\tplay new game\r\n"+
                        "load\t-\tload last saved game\r\n"+
                        "demo\t-\tplay demo game with no real player\r\n"+
                        "exit\t-\texit from the game\r\n";
                System.out.println(s);
            } else {
                System.out.println("'"+input+"' no such command!");
            }
            input = stdIn.nextLine();
        }
    }
}
