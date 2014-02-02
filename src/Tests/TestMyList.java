package Tests;

import org.junit.Before;
import org.junit.Test;
import ua.kiev.javacourses.casino.Card;
import ua.kiev.javacourses.sys.MyList;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Created by Oleg on 14.01.14.
 */
public class TestMyList {
    private final int listSize = 15;
    MyList<Card> myList;

    @Before
    public void testSetup() {
        myList = new MyList<Card>();
        for (int i=0;i<listSize;i++) {
            myList.add(new Card());
        }

    }

    @Test
    public void testAdd() {
        int startSize=myList.size();
        myList.add(new Card());
        int newSize=myList.size();
        assertEquals(startSize+1,newSize);
    }

    @Test
    public void testAddWithIndex() {
        int idx=listSize-5;
        Card c=new Card();
        myList.add(c,idx);
        assertEquals(c,myList.get(idx));
    }

    @Test
    public void testRemoveByIndex() {
        int idx=listSize-5;
        Card c=myList.get(idx);
        Card cRemoved=myList.remove(idx);
        assertEquals(c,cRemoved);
    }

    @Test
    public void testRemoveByElement() {
        int idx=listSize-5;
        Card c=myList.get(idx);
        myList.remove(c);
        assertNotSame(c,myList.get(idx));
    }
}
