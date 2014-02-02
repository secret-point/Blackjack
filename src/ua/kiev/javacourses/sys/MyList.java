package ua.kiev.javacourses.sys;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 14.12.13
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public class MyList<T> {
    private Object[] arr = {};
    private int length = 0;
    private static final int DEFAULT_CAPACITY = 10;
    public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE-8;

    public MyList() {
        this(0);
    }

    public MyList(int length) {
        if (length<0) {
            throw new MyRuntimeException("MyList length cannot be less than 0.");
        } else if (length>MAX_ARRAY_LENGTH) {
            throw new MyRuntimeException("MyList length cannot be higher than "+MAX_ARRAY_LENGTH+".");
        } else {
            int arrLen;
            if (length>0)
                arrLen = (int)Math.ceil((double)length/10.0)*10;
            else
                arrLen = DEFAULT_CAPACITY;
            if (arrLen>MAX_ARRAY_LENGTH)
                arrLen=MAX_ARRAY_LENGTH;
            arr = new Object[arrLen];
            this.length=length;
        }
    }

    public boolean add(T element) {
        if (length<MAX_ARRAY_LENGTH) {
            if (arr.length==length) {
                Object[] tempArr = new Object[arr.length+DEFAULT_CAPACITY];
                System.arraycopy(arr,0,tempArr,0,length);
                tempArr[length]=element;
                arr=tempArr;
                length++;
                return true;
            } else {
                arr[length]=element;
                length++;
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean add(T element, int index) {
        if (length<MAX_ARRAY_LENGTH) {
            if (arr.length==length) {
                Object[] tempArr = new Object[arr.length+DEFAULT_CAPACITY];
                System.arraycopy(arr,0,tempArr,0,index);
                System.arraycopy(arr,index,tempArr,index+1,length-index);
                tempArr[index]=element;
                arr=tempArr;
                length++;
                return true;
            } else {
                if (index<length) {
                    System.arraycopy(arr,index,arr,index+1,length-index);
                } else {
                    index=length;
                }
                arr[index]=element;
                length++;
                return true;
            }
        } else {
            return false;
        }
    }

    public T get(int index) {
        if (index>=length || index<0) {
            throw new IndexOutOfBoundsException();
        } else {
            return (T)arr[index];
        }
    }

    public T remove(int index) {
        Object result=null;
        if (length>0) {
            if (index>=length || index<0) {
                throw new IndexOutOfBoundsException();
            } else {
                result = arr[index];
                if ((arr.length-10)==length) {
                    Object[] tempArr = new Object[arr.length-DEFAULT_CAPACITY];
                    System.arraycopy(arr,0,tempArr,0,index);
                    System.arraycopy(arr,index+1,tempArr,index,length-index-1);
                    arr=tempArr;
                    length--;
                } else {
                    System.arraycopy(arr,index+1,arr,index,length-index-1);
                    length--;
                }
            }
        } else {
            System.out.println("There is no data to remove anymore.");
        }
        return (T)result;
    }

    public boolean remove(T element) {
        boolean result=false;
        if (length>0){
            int index;
            if (element==null) {
                for (index=0; index<length; index++) {
                    if (arr[index]==null) {
                        remove(index);
                    }
                }
            } else {
                for (index=0; index<length; index++) {
                    if (element.equals(arr[index])) {
                        remove(index);
                    }
                }
            }
        } else {
            System.out.println("There is no data to remove anymore.");
        }
        return result;
    }

    public boolean isEmpty() {
        if (length==0){
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return length;
    }

    public int realSize() {
        return arr.length;
    }
}
