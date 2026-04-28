package Managers;

import Commands.NetCommand;
import Models.Route;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public final class ExecutionManager {
    private final int numberOfPoolThreads = 5;
    private ExecutorService scanningExecutor = Executors.newFixedThreadPool(numberOfPoolThreads);
    public LinkedBlockingQueue<NetCommand> commandQueue = new LinkedBlockingQueue<>();
    public LinkedBlockingQueue<NetCommand> responseQueue = new LinkedBlockingQueue<>();
    private final ReentrantLock invokeLock = new ReentrantLock();

    private ExecutionManager () {

    }

    private static class ExecutionHolder {
        private static final ExecutionManager INSTANCE = new ExecutionManager();
    }

    public static ExecutionManager getInstance() {
        return  ExecutionManager.ExecutionHolder.INSTANCE;
    }

    public void startUdpScan() {
        for(int i = 0; i < numberOfPoolThreads-1; i++) {
            scanningExecutor.submit(NetManager.getInstance()::scanUdpTask);
        }
        scanningExecutor.submit(ExecutionManager.getInstance()::startCommandProcessing);

    }

    public void startCommandProcessing(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                NetCommand command = commandQueue.take();

                Thread worker = new Thread( () -> {
                    Method method = null;
                    try {
                        method = command.getCommand().getClass().getMethod("apply", String[].class, BufferedReader.class, Route.class);
                    } catch (NoSuchMethodException e) {
                        System.out.println("No command found\n");
                        e.printStackTrace();
                    }
                    BufferedReader zaglushka = new BufferedReader(new InputStreamReader(System.in));
                    Object[] args = {command.getLine(), zaglushka, command.getRoute()};
                    try {
                        invokeLock.lock();
                        PrintStream originalOut = System.out;
                        PrintStream originalErr = System.err;
                        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
                        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
                        try {
                            System.setOut(new PrintStream(outContent));
                            System.setErr(new PrintStream(errContent));
                            method.invoke(command.getCommand(), args);
                        }  catch (IllegalAccessException | InvocationTargetException e) {
                            System.out.println("No command found");
                            System.out.println(command.getLine()[0]);
                        }
                        finally {
                            System.setOut(originalOut);
                            System.setErr(originalErr);
                            invokeLock.unlock();
                        }
                        String out = outContent.toString() + errContent.toString();
                        command.setResponse(out);
                        List<NetCommand> responses = new ArrayList<>();
                        responses.add(command);
                        ForkJoinPool fjp = new ForkJoinPool();

                        fjp.invoke(NetManager.getInstance().getResponseTaskClass(responses));
                        responseQueue.put(command);
                    } catch (InterruptedException e) {
                        return;
                    }
                });
                worker.start();
            } catch (InterruptedException e) {
                return;
            }
        }

    }

    public void stopUdpScan(){
        NetManager.getInstance().closeAllTransactions();
        scanningExecutor.shutdownNow();
    }
}
