package Managers;

import Commands.CommandType;
import Commands.NetCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.file.attribute.FileTime;
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

    public void sendCommand(NetCommand netCommand) throws IOException {
        if(Objects.nonNull(netCommand)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(netCommand);
            oos.close();
            arr = baos.toByteArray();
            dp = new DatagramPacket(arr, arr.length, host, port);
            ds.send(dp);
        }
    }

    public DatagramPacket sendRequest(CommandType command,byte[] arr, int port, InetAddress addr) {
        dp = new DatagramPacket(arr, arr.length, addr, port);
        return dp;
    }


    public void getInitTime(){

    }
    //public void sendCommandToServer

    public static NetManager getInstance() {
        return INSTANCE;
    }
}
