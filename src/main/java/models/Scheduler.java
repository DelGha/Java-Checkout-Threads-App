package models;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    //private List<Queue> queues;
    private List<Queue> queues;
    private int maxNumberOfQueues;
    private int maxClientsPerQueue;

    public Scheduler(int maxNumberOfQueues, int maxClientsPerQueue){
        //this.queues = new ArrayList<>();
        this.queues = Collections.synchronizedList(new ArrayList<>());
        this.maxNumberOfQueues = maxNumberOfQueues;
        this.maxClientsPerQueue = maxClientsPerQueue;

        synchronized (queues) {
            for (int i = 0; i < maxNumberOfQueues; i++) {
                Queue queue = new Queue(i+1);
                Thread thread = new Thread(queue);
                this.queues.add(queue);
                thread.start();
            }
        }
    }

    public void dispatchTask(Client client){
        synchronized (queues) {
            Queue chosenQueue = queues.get(0);
            AtomicInteger minWaitingTime = chosenQueue.getWaitingTimeInQueue();
            AtomicInteger waitingTime;

            Iterator iterator = queues.iterator();

            while(iterator.hasNext()){
                Queue queue = (Queue) iterator.next();
                waitingTime = queue.getWaitingTimeInQueue();
                if (waitingTime.get() < minWaitingTime.get()) {
                    chosenQueue = queue;
                    //minWaitingTime.set(waitingTime.get());
                    minWaitingTime = waitingTime;
                }
            }
            chosenQueue.addClient(client);
        }
    }

    public List<Queue> getQueues(){
        return queues;
    }

    public void stopQueues(){
        for(int i = 0; i<maxNumberOfQueues ; i++){
            queues.get(i).setRunning(false);
        }
    }

    public int getMaxNumberOfQueues() {
        return maxNumberOfQueues;
    }

    public void setMaxNumberOfQueues(int maxNumberOfQueues) {
        this.maxNumberOfQueues = maxNumberOfQueues;
    }

    public int getMaxClientsPerQueue() {
        return maxClientsPerQueue;
    }

    public void setMaxClientsPerQueue(int maxClientsPerQueue) {
        this.maxClientsPerQueue = maxClientsPerQueue;
    }
}
