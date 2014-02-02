package ua.kiev.javacourses.sys;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

/**
 * Created by Oleg on 12.01.14.
 */
public class MyFileUtils {
    final static boolean debugPrint = false;

    public static void copyRecursively(String pathFrom, String pathTo) {
        File pFrom = new File(pathFrom);
        File pTo = new File(pathTo);
        File[] fList=pFrom.listFiles();
        String dirDiv= MyOsUtils.getDirDiv();

        if (fList!=null) {
            if (!pTo.exists()) {
                pTo.mkdir();
                if (debugPrint)
                    System.out.println("Creating mkdir() "+pTo.toString());
            }
            for(File f : fList) {
                if (f.isDirectory()) {
                    copyRecursively(f.toString(),pathTo+dirDiv+f.getName());
                } else {
                    File fTo = new File(pTo.toString()+dirDiv+f.getName());
                    try {
                        if (!fTo.exists()) {
                            Files.copy(f.toPath(), fTo.toPath());
                        }
                        else {
                            if (debugPrint)
                                System.out.println("File \""+fTo.toString()+"\" already exists!");
                        }
                    }
                    catch (FileAlreadyExistsException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void deleteDirectory(String path) {
        File p=new File(path);
        boolean res;
        if (!p.exists())
            return;

        File[] fList=p.listFiles();
        for(File f:fList) {
            if (f.isDirectory()) {
                deleteDirectory(f.toString());
            } else {
                res = f.delete();
                if (debugPrint)
                    System.out.println("File del: "+f.toString()+", "+res);
            }
        }
        res = p.delete();
        if (debugPrint)
            System.out.println("Dir del: "+p.toString()+", "+res);
    }
}
