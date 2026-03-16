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

public final class NetManager {
    private final static NetManager INSTANCE = new NetManager();
    private byte[] arr;
    InetAddress host;
    int port, bufferSize;
    DatagramSocket ds;
    DatagramPacket dp;
    Selector selector;
    DatagramChannel channel;
    InetSocketAddress clientAddress;

    private NetManager() {

    }

    public void initServer(int port) throws IOException {
        selector = Selector.open();
        channel = DatagramChannel.open();

        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(12345));
        channel.register(selector, SelectionKey.OP_READ);
    }

    private void sendResponse (String data, DatagramChannel channel) throws IOException {
        ServerResponse.getInstance().setRoutes(CollectionManager.getInstance().getCollection());
        ServerResponse.getInstance().setData(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(ServerResponse.getInstance());
        oos.close();
        ByteBuffer buff = ByteBuffer.wrap(baos.toByteArray());
        //byte[] arr = baos.toByteArray();
        channel.send(buff, clientAddress);

    }

    public void scan(BufferedReader console) throws IOException, ClassNotFoundException {
        int readyChannels = selector.selectNow();

        if (readyChannels > 0) {
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isReadable()) {
                    handleCommand(handleRead(channel), console, channel);
                }
            }
        }
    }

    private NetCommand handleRead(DatagramChannel channel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        InetSocketAddress clientAddr = (InetSocketAddress) channel.receive(buffer);
        this.clientAddress = clientAddr;

        if(clientAddr != null) {
            buffer.flip();

            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            ByteArrayInputStream ais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(ais);
            NetCommand newCommand = (NetCommand) ois.readObject();
            ois.close();
            System.out.println(newCommand);
            return newCommand;
        }
        return null;
    }

    private void handleCommand(NetCommand command, BufferedReader console, DatagramChannel channel) throws IOException {
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
        this.sendResponse(out, channel);
    }


        public static NetManager getInstance() {
        return INSTANCE;
    }
}
