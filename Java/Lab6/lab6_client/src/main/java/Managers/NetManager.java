package Managers;

import Commands.CommandType;
import Commands.NetCommand;
import Models.Route;
import Models.ServerResponse;

import java.io.*;
import java.net.*;
import java.nio.file.attribute.FileTime;
import java.util.Map;
import java.util.Objects;

public final class NetManager {
    private final static NetManager INSTANCE = new NetManager();
    private byte[] arr;
    InetAddress host;
    int port, bufferSize;
    DatagramSocket ds;
    DatagramPacket dp;

    private NetManager() {

    }

    public void initServer(int port, int bufferSize) throws SocketException, UnknownHostException {
        this.port = port;
        this.arr = new byte[bufferSize];
        this.bufferSize = bufferSize;
        ds = new DatagramSocket();
        host = InetAddress.getByName("localhost");
    }

    public void sendCommand(NetCommand netCommand) throws IOException, ClassNotFoundException {
        if(Objects.nonNull(netCommand)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(netCommand);
            oos.close();
            arr = baos.toByteArray();
            dp = new DatagramPacket(arr, arr.length, host, port);
            ds.send(dp);
            byte[] recArr = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(recArr, recArr.length);
            ds.receive(receivePacket);
            ByteArrayInputStream ais = new ByteArrayInputStream(receivePacket.getData(), 0, receivePacket.getLength());

            ObjectInputStream ois = new ObjectInputStream(ais);
            ServerResponse newResponse = (ServerResponse) ois.readObject();
            ois.close();
            System.out.println(newResponse.getData());
//            for(Map.Entry<Integer, Route> entry: newResponse.getRoutes().entrySet()) {
//                System.out.println(entry.getKey() + " " + entry.getValue());
//            }
//            if(newResponse.getRoutes().isEmpty()) {
//                System.out.println("Empty collection");
//            }
        }
    }

    public static NetManager getInstance() {
        return INSTANCE;
    }
}
