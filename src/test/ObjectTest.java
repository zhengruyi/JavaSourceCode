package test;

import java.util.Date;

/**
 * @author Ruyi ZHENG
 * @version 1.00
 * @time 08/02/2021 14:04
 **/

public class ObjectTest {
    public static void main(String[] args) throws CloneNotSupportedException {
       Test test = new Test();
       test.clone();
    }
}
class Test extends Object{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return  super.clone();
    }
}