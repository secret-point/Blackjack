package ua.kiev.javacourses.sys;

/**
 * Created by oleg on 12.01.14.
 */
public final class MyOsUtils
{
    private static String OS = null;
    private static String dirDiv;

    // Определение типа ОС
    public static String getOsName()
    {
        if(OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isUnix() {
        return getOsName().startsWith("Linux");
    }

    // Определение разделителя адресов в зависимости от типа ОС
    private static void setDirDiv() {
        if (dirDiv==null) {
            if (MyOsUtils.isUnix()) {
                dirDiv="/";
            } else if (MyOsUtils.isWindows()) {
                dirDiv="\\";
            }
        }
    }

    public static String getDirDiv() {
        setDirDiv();
        return dirDiv;
    }
}
