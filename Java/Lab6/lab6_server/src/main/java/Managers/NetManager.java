package Managers;

import Commands.Command;
import Commands.CommandType;
import Commands.NetCommand;
import Models.Route;
import Models.ServerResponse;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NetManager {
    private final static NetManager INSTANCE = new NetManager();
    private byte[] arr;
    InetAddress host;
    int port, bufferSize;
    DatagramSocket ds;
    DatagramPacket dp;
    Selector selector;
    DatagramChannel channel;
    InetAddress clientAddress;
    int clientPort;
    private static final Logger logger = LoggerFactory.getLogger(NetManager.class);

    private NetManager() {

    }

    public void initServer(int port) throws IOException {
//        selector = Selector.open();
//        channel = DatagramChannel.open();
//
//        channel.configureBlocking(false);
//        channel.bind(new InetSocketAddress("localhost", 12345));
//        channel.register(selector, SelectionKey.OP_READ);
        logger.info("Initializing port 12345 on localhost...");
        ds = new DatagramSocket(new InetSocketAddress("localhost", 12345));
        ds.setSoTimeout(30);
    }

    private void sendResponse (String data) throws IOException {
        logger.info("Sending response:");
        ServerResponse.getInstance().setRoutes(CollectionManager.getInstance().getCollection());
        ServerResponse.getInstance().setData(data);
        logger.info("Serializing...");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(ServerResponse.getInstance());
        oos.close();
        ByteBuffer buff = ByteBuffer.wrap(baos.toByteArray());
        //byte[] sendData = new byte[buff.remaining()];
        //System.arraycopy(buff, 0, sendData, 0, buff.remaining());
        logger.info("Sending...");
        dp = new DatagramPacket(buff.array(), buff.remaining(), this.clientAddress, this.clientPort);
        ds.send(dp);
        logger.info("Ready!");
        //byte[] arr = baos.toByteArray();
//        channel.send(buff, clientAddress);


    }

    public void scan(BufferedReader console) throws IOException, ClassNotFoundException {
        byte[] buff = new byte[65536];
        dp = new DatagramPacket(buff, buff.length);
        try {
            ds.receive(dp);
        }
        catch (SocketTimeoutException e) {
            return;
        }

        byte[] bytes = new byte[dp.getLength()];
        System.arraycopy(dp.getData(), 0, bytes, 0, dp.getLength());
        this.clientAddress = dp.getAddress();
        this.clientPort = dp.getPort();
        logger.info("Got data from: IP: " + clientAddress.toString() + " port: " + clientPort);
        logger.info("Started deserialization...");
        ByteArrayInputStream ais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(ais);
        NetCommand newCommand = (NetCommand) ois.readObject();
        ois.close();
        //logger.debug(newCommand.toString());
        logger.info("Invoking command " + newCommand.toString());
        handleCommand(newCommand, console);
    }

//    private NetCommand handleRead(DatagramChannel channel) throws IOException, ClassNotFoundException {
//
//        if(this.clientAddress != null) {
//            buffer.flip();
//
//            byte[] bytes = new byte[buffer.remaining()];
//            buffer.get(bytes);
//
//            ByteArrayInputStream ais = new ByteArrayInputStream(bytes);
//            ObjectInputStream ois = new ObjectInputStream(ais);
//            NetCommand newCommand = (NetCommand) ois.readObject();
//            ois.close();
//            System.out.println(newCommand);
//            return newCommand;
//        }
//        return null;
//    }

    private void handleCommand(NetCommand command, BufferedReader console) throws IOException {
        String[] line = command.getLine();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();

        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        try {
            System.setOut(new PrintStream(outContent));
            System.setErr(new PrintStream(errContent));

            Method method = command.getCommand().getClass().getMethod("apply", String[].class, BufferedReader.class, Route.class);
            Object[] args = {line, console, command.getRoute()};
            method.invoke(command.getCommand(), args);

            //TODO Если у команды не те типы ключей - просто System.print и return;
        } catch (IllegalArgumentException e) {
            System.out.println("No command found");
            System.out.println(line[0]);
        } catch (Throwable e) {
            System.out.println("Unexpected error occurred, try again");
            System.out.println(line[0]);
        }
        finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }

        String out = outContent.toString() + errContent.toString();
        this.sendResponse(out);
    }


        public static NetManager getInstance() {
        return INSTANCE;
    }
}
