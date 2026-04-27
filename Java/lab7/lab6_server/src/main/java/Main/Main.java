package Main;

import Commands.NetCommand;
import Managers.NetManager;
import Managers.SystemManager;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

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