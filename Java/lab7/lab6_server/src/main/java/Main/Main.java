package main;

import managers.SystemManager;

import java.io.*;

/**
 * This is the main class. Program's entry point
 * @author Clown
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        while(true) {
//            NetManager.getInstance().scan();
//        }
        SystemManager.getInstance().init(args);
    }
}