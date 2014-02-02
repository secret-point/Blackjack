package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import ua.kiev.javacourses.casino.Game;
import ua.kiev.javacourses.sys.MyOsUtils;
import ua.kiev.javacourses.visitors.Player;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 16.01.14
 * Time: 0:07
 * To change this template use File | Settings | File Templates.
 */
public class TestGame {
    @Test
    public void testSaveLoad() {
        Game g=new Game();
        (new Player()).play(g);
        (new Player()).play(g);

        String dirDiv= MyOsUtils.getDirDiv();
        String homeDir=System.getProperty("user.home")+dirDiv;

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(homeDir+"testSave.dat"));
            out.writeObject(g);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream in = null;
        Game ng=null;
        try {
            in = new ObjectInputStream(new FileInputStream(homeDir+"testSave.dat"));
            ng=(Game)in.readObject();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assertTrue(g.equals(ng));
    }
}
