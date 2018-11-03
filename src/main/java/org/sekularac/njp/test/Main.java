package org.sekularac.njp.test;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class aClass = Class.forName("org.sekularac.njp.test.Entitet");
        Object obj = aClass.newInstance();

    }
}