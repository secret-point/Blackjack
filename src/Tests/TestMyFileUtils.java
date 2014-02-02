package Tests;

import org.junit.Before;
import org.junit.Test;
import ua.kiev.javacourses.sys.MyFileUtils;
import ua.kiev.javacourses.sys.MyOsUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Oleg on 12.01.14.
 */
public class TestMyFileUtils {
    String homeDir;
    String dirDiv;
    String pFrom;
    String pTo;

    /**
    * Method for preparing data needed for tests.
    */
    @Before
    public void testSetup() {
        dirDiv= MyOsUtils.getDirDiv();
        homeDir=System.getProperty("user.home")+dirDiv;

        File f=new File(homeDir+"Test1"+dirDiv);
        if (!f.exists()) {
            try {
                f.mkdir();
                new File(f.toString()+dirDiv+"Test1.txt").createNewFile();
                new File(f.toString()+dirDiv+"Test2.txt").createNewFile();
                new File(f.toString()+dirDiv+"Test1.1"+dirDiv).mkdir();
                new File(f.toString()+dirDiv+"Test1.1"+dirDiv+"Test1.1.1"+dirDiv).mkdir();
                new File(f.toString()+dirDiv+"Test1.1"+dirDiv+"Test1.1.1"+dirDiv+"TestDone.txt").createNewFile();
                new File(f.toString()+dirDiv+"Test1.2"+dirDiv).mkdir();
                new File(f.toString()+dirDiv+"Test1.2"+dirDiv+"Test123"+dirDiv).mkdir();
                new File(f.toString()+dirDiv+"Test1.2"+dirDiv+"TestDone.txt").createNewFile();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }

        pFrom=homeDir+"Test1"+dirDiv;
        pTo=homeDir+"Test2"+dirDiv;
    }


    @Test
    public void testCopyRecursively() {
        //
        MyFileUtils.copyRecursively(pFrom, pTo);
        assertTrue(new File(pTo.toString()).exists());
        assertTrue(new File(pTo.toString()+dirDiv+"Test1.txt").exists());
        assertTrue(new File(pTo.toString()+dirDiv+"Test2.txt").exists());
        assertTrue(new File(pTo.toString()+dirDiv+"Test1.1"+dirDiv).exists());
        assertTrue(new File(pTo.toString()+dirDiv+"Test1.1"+dirDiv+"Test1.1.1"+dirDiv).exists());
        assertTrue(new File(pTo.toString()+dirDiv+"Test1.1"+dirDiv+"Test1.1.1"+dirDiv+"TestDone.txt").exists());
        assertTrue(new File(pTo.toString()+dirDiv+"Test1.2"+dirDiv).exists());
        assertTrue(new File(pTo.toString()+dirDiv+"Test1.2"+dirDiv+"Test123"+dirDiv).exists());
        assertTrue(new File(pTo.toString()+dirDiv+"Test1.2"+dirDiv+"TestDone.txt").exists());

        MyFileUtils.deleteDirectory(pFrom);
        MyFileUtils.deleteDirectory(pTo);
    }

    @Test
    public void testDeleteDirectory() {
        MyFileUtils.copyRecursively(pFrom, pTo);
        MyFileUtils.deleteDirectory(pFrom);
        MyFileUtils.deleteDirectory(pTo);

        assertFalse(new File(pFrom).exists());
        assertFalse(new File(pTo).exists());
    }
}
