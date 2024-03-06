package models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {
    //private int index;
    private int id;
    private AtomicInteger waitingTimeInQueue;
    private BlockingQueue<Client> clients;
    //private Clock clock;
    private boolean isRunning;

//    public Queue(Clock clock) {
//        this.clients = new LinkedBlockingQueue<>();
//        this.waitingTimeInQueue = new AtomicInteger();
//        //this.clock = clock;
//        this.isRunning = true;
//    }

    public Queue(int id){
        this.id = id;
        this.clients = new LinkedBlockingQueue<>();
        this.waitingTimeInQueue = new AtomicInteger();
        this.isRunning = true;
    }

    public boolean isEmpty() {
        return clients.isEmpty();
    }

    public void addClient(Client client) {
        //int waitingTime = (int) Math.max(0, clock.getTime() - client.getArrivalTime());
        //int waitingTime = waitingTimeInQueue + waitingTimeOfClient;
        //client.setWaitingTime(waitingTimeInQueue + waitingTimeOfClient);
        clients.add(client);
        waitingTimeInQueue.getAndAdd(client.getServiceTime());
    }

    public void removeClient() {
        Client client = clients.remove();
        //waitingTimeInQueue.getAndAdd(-(client.getWaitingTime()));
    }

    public int getAverageWaitingTime() {
        if (clients.isEmpty()) {
            return 0;
        }
        return waitingTimeInQueue.get();
    }

    @Override
    public void run() {
        while (isRunning) {
            //Client client = removeClient();
            if(!isEmpty()) {
                Client client = (Client) clients.toArray()[0];
                if (client != null) {
                    try {
                        while (client.getServiceTime() > 0) {
                            Thread.sleep(1000);
                            waitingTimeInQueue.getAndAdd(-1);
                            client.setServiceTime(client.getServiceTime() - 1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                removeClient();
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Queue ").append(id).append(": ");
        if (isEmpty()) {
            sb.append("closed");
        } else {

            Iterator iterator = clients.iterator();

            while(iterator.hasNext()){
                Client client = (Client) iterator.next();
                sb.append(client.toString());
                sb.append(", ");
            }
//            for (int i = 0; i < clients.size(); i++) {
//                sb.append(clients.toArray()[i].toString());
//                if (i < clients.size() - 1) {
//                    sb.append(", ");
//                }
//            }
        }
        return sb.toString();
    }

    public AtomicInteger getWaitingTimeInQueue() {
        return waitingTimeInQueue;
    }

    public void setWaitingTimeInQueue(AtomicInteger waitingTimeInQueue) {
        this.waitingTimeInQueue = waitingTimeInQueue;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public void setClients(BlockingQueue<Client> clients) {
        this.clients = clients;
    }
}
