package commands;

import models.Route;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;

public final class NetCommand implements Serializable {
    private Command command;
    private String[] line;
    private String response;
    private Route myRoute;
    private DatagramPacket dp;
    private InetAddress clientAddress;
    private int clientPort;
    private String user = "", pswd = "";

    public NetCommand (Command command, String[] line, Route myRoute) {
        this.command = command;
        this.line = line;
        this.myRoute = myRoute;
    }

    public synchronized void setUser (String user) {
        this.user = user;
    }

    public synchronized void setPswd (String pswd) {
        this.pswd = pswd;
    }

    public synchronized String getUser() {
        return user;
    }

    public synchronized String getPswd() {
        return pswd;
    }

    public synchronized Command getCommand() {
        return this.command;
    }

    public synchronized String[] getLine() {
        return line;
    }

    public synchronized Route getRoute() {
        return myRoute;
    }

    public synchronized DatagramPacket getDp(){
        return dp;
    }

    public synchronized void setDp(DatagramPacket dp) {
        this.dp = dp;
    }

    public synchronized InetAddress getClientAddress(){
        return clientAddress;
    }

    public synchronized void setClientAddress(InetAddress address) {
        this.clientAddress = address;
    }

    public synchronized void setResponse(String response){
        this.response = response;
    }

    public synchronized String getResponse(){
        return response;
    }

    public synchronized void setPort(int port){
        this.clientPort = port;
    }

    public synchronized int getPort(){
        return clientPort;
    }

    @Override
    public synchronized String toString(){
        return Arrays.toString(line);
    }
}
