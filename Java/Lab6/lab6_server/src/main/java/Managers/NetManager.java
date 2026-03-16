package Managers;

import Commands.Command;
import Commands.CommandType;
import Commands.NetCommand;
import Models.Route;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

    private NetManager() {

    }

    public void initServer(int port) throws IOException {
        selector = Selector.open();
        channel = DatagramChannel.open();

        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(12345));
        channel.register(selector, SelectionKey.OP_READ);
    }

    public void scan(BufferedReader console) throws IOException, ClassNotFoundException {
        int readyChannels = selector.selectNow();

        if (readyChannels > 0) {
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isReadable()) {
                    handleCommand(handleRead(channel), console);
                }
            }
        }
    }

    private NetCommand handleRead(DatagramChannel channel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        InetSocketAddress clientAddr = (InetSocketAddress) channel.receive(buffer);

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

    private void handleCommand(NetCommand command, BufferedReader console){
        String[] line = command.getLine();
        try {

//            Class<? extends Command> newCommand = CommandType.valueOf(line[0]).getClazz();
//            Command instance = newCommand.getDeclaredConstructor().newInstance();
//            Method method = instance.getClass().getMethod("apply", String[].class, BufferedReader.class);
//            Object[] args = {line, console};
//            method.invoke(instance, args);
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

    }


        public static NetManager getInstance() {
        return INSTANCE;
    }
}
