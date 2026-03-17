package Managers;

import Commands.CommandType;
import Commands.NetCommand;
import Models.Route;
import Models.ServerResponse;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.file.attribute.FileTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public final class NetManager {
    private final static NetManager INSTANCE = new NetManager();
    private byte[] arr;
    InetAddress host;
    int port, bufferSize;
    DatagramSocket ds;
    DatagramPacket dp;
    DatagramChannel channel;
    Selector selector;

    private NetManager() {

    }

    public void initServer(int port, int bufferSize) throws SocketException, UnknownHostException {
        this.port = port;
        this.arr = new byte[bufferSize];
        this.bufferSize = bufferSize;
        ds = new DatagramSocket();
        host = InetAddress.getByName("localhost");
    }

    public void initClient() {
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("localhost", 12345));

            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
        }
        catch (IOException e) {
            System.out.println("Unable to open port, may be server is not reachable now");
            System.out.println("Exiting...");
            System.exit(69);
        }


    }

    public ServerResponse sendCommand(NetCommand netCommand) throws IOException, ClassNotFoundException {
        try {
            if (Objects.nonNull(netCommand)) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(netCommand);
                oos.close();
                arr = baos.toByteArray();
                ByteBuffer buff = ByteBuffer.wrap(arr);
                channel.send(buff, new InetSocketAddress("localhost", 12345));

                selector.select(1052);
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        ByteBuffer recBuff = ByteBuffer.allocate(65536);
                        SocketAddress server = channel.receive(recBuff);
                        if (server != null) {
                            recBuff.flip();
                            byte[] data = new byte[recBuff.remaining()];
                            recBuff.get(data);
                            ByteArrayInputStream ais = new ByteArrayInputStream(data);
                            ObjectInputStream ois = new ObjectInputStream(ais);
                            ServerResponse newResponce = (ServerResponse) ois.readObject();
                            ois.close();
                            System.out.println(newResponce.getData());
                            return newResponce;
                        }
                    }
                }

//            ByteBuffer recBuff = ByteBuffer.allocate(65536);
//            channel.receive(recBuff);
//            recBuff.flip();
//            ByteArrayInputStream ais = new ByteArrayInputStream(recBuff.array(), recBuff.position(), recBuff.remaining());
//            ObjectInputStream ois = new ObjectInputStream(ais);
//            ServerResponse newResponse = (ServerResponse) ois.readObject();
//            ois.close();
//            byte[] recArr = new byte[65535];
//            DatagramPacket receivePacket = new DatagramPacket(recArr, recArr.length);
//            ds.receive(receivePacket);
//            ByteArrayInputStream ais = new ByteArrayInputStream(receivePacket.getData(), 0, receivePacket.getLength());
//
//            ObjectInputStream ois = new ObjectInputStream(ais);
//            ServerResponse newResponse = (ServerResponse) ois.readObject();
//            ois.close();

            }
        }
        catch (PortUnreachableException e) {
            System.out.println("Unable to connect to server, may be it is not reachable now");
        }
        return null;
    }

    public void closeChannel() {
        try {
            channel.close();
        } catch (IOException e) {
            System.err.println("Unable to close channel");
        }

    }

    public static NetManager getInstance() {
        return INSTANCE;
    }
}
