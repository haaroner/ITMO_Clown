package Managers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public final class NetManager {
    private final static NetManager INSTANCE = new NetManager();
    private byte[] arr;
    InetAddress host;
    int port, bufferSize;
    DatagramSocket ds;
    DatagramPacket dp;

    private NetManager() {

    }

    public void initServer(int port, int bufferSize) throws SocketException {
        this.port = port;
        this.arr = new byte[bufferSize];
        this.bufferSize = bufferSize;
        ds = new DatagramSocket(port);
    }

    public void startScan() throws IOException {
        while(true) {
            dp = new DatagramPacket(arr, bufferSize);
            ds.receive(dp);
            //TODO PACKET HANDLER
            host = dp.getAddress();
            port = dp.getPort();
            dp = new DatagramPacket(arr, bufferSize, host, port);
            ds.send(dp);
        }
    }

    public static NetManager getInstance() {
        return INSTANCE;
    }
}
