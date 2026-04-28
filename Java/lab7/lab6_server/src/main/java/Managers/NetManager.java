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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NetManager {
    private final static NetManager INSTANCE = new NetManager();

    private byte[] arr;
    private InetAddress host;
    private int port, bufferSize;
    private DatagramSocket ds;
    private DatagramPacket dp;
    private Selector selector;
    private DatagramChannel channel;
    private InetAddress clientAddress;
    private int clientPort;
    private static final Logger logger = LoggerFactory.getLogger(NetManager.class);

    private NetManager() {

    }

    public void initServer(int port) throws IOException {
        logger.info("Initializing port 12345 on localhost...");
        ds = new DatagramSocket(new InetSocketAddress("localhost", 12345));
        //ds.setSoTimeout(30);
    }

    private class responseTask extends RecursiveAction {
      private List<NetCommand> responses = new ArrayList<>();

      public responseTask (List<NetCommand> responses) {
          this.responses = responses;
      }

      @Override
        protected void compute() {
            if(true) {
                logger.info("Sending response:");
                ServerResponse.getInstance().setRoutes(CollectionManager.getInstance().getCollection());
                ServerResponse.getInstance().setData(responses.get(0).getResponse());
                logger.info("Serializing...");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(baos);
                    oos.writeObject(ServerResponse.getInstance());
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteBuffer buff = ByteBuffer.wrap(baos.toByteArray());
                //byte[] sendData = new byte[buff.remaining()];
                //System.arraycopy(buff, 0, sendData, 0, buff.remaining());
                logger.info("Sending...");
                dp = new DatagramPacket(buff.array(), buff.remaining(), responses.get(0).getClientAddress(), responses.get(0).getPort());
                try {
                    ds.send(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("Ready!");
            }
      }

    };

    public synchronized responseTask getResponseTaskClass(List<NetCommand> responses){
        return new responseTask(responses);
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


    public void scanUdpTask() {
        while(true) {
            byte[] buff = new byte[65536];
            DatagramPacket newDp = new DatagramPacket(buff, buff.length);
            try {
                ds.receive(newDp);
            } catch (IOException e) {
                return;
            }
            try {
                byte[] bytes = new byte[newDp.getLength()];
                System.arraycopy(newDp.getData(), 0, bytes, 0, newDp.getLength());
                InetAddress clientAddress = newDp.getAddress();
                this.clientPort = newDp.getPort();
                logger.info("Got data from: IP: " + clientAddress.toString() + " port: " + clientPort);
                logger.info("Started deserialization...");
                ByteArrayInputStream ais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(ais);
                NetCommand newCommand = (NetCommand) ois.readObject();
                newCommand.setDp(dp);
                newCommand.setPort(clientPort);
                newCommand.setClientAddress(clientAddress);
                ois.close();
                ExecutionManager.getInstance().commandQueue.put(newCommand);
                //logger.debug(newCommand.toString());
//                logger.info("Invoking command " + newCommand.toString());
//                BufferedReader zaglushka = new BufferedReader(new InputStreamReader(System.in));
//                handleCommand(newCommand, zaglushka);
            }
            catch (IOException | ClassNotFoundException | InterruptedException e){
                e.printStackTrace();
            }
            if(Thread.currentThread().isInterrupted())
                return;
        }
    }

    public void closeAllTransactions(){
        this.ds.close();
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

    private void handleCommand(NetCommand command, BufferedReader console) throws IOException {
        String[] line = command.getLine();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();

        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        try {
            System.setOut(new PrintStream(outContent));
            System.setErr(new PrintStream(errContent));
            CommandType commandType = CommandType.valueOf(line[0]);
            Method method = command.getCommand().getClass().getMethod("apply", String[].class, BufferedReader.class, Route.class);
            String[] userLine = new String[line.length + 2];
            System.arraycopy(line, 0, userLine, 0, line.length);
            userLine[line.length] = command.getUser();
            userLine[line.length+1] = command.getPswd();
            Object[] args = {line, console, command.getRoute()};
            method.invoke(command.getCommand(), args);

            //TODO Если у команды не те типы ключей - просто System.print и return;
        } catch (IllegalArgumentException e) {
            System.out.println("No command found");
            System.out.println(line[0]);
        } catch (Throwable e) {
            System.out.println("NetManager error: \n" + e);
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
