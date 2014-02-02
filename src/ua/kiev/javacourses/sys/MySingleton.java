package ua.kiev.javacourses.sys;

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 07.12.13
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 */
public class MySingleton {
    private static MySingleton instance = null;

    private MySingleton() {

    }

    public static MySingleton getInstance() {
        if (instance == null) {
            instance = new MySingleton();
        }
        return instance;
    }
}
