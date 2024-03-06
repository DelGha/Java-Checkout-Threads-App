package controller;

import models.Client;
import models.Queue;
import models.Scheduler;
import views.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Runnable{
    private int currentTime;
    private int arrivalTimeMin;
    private int arrivalTimeMax;

    private int serviceTimeMin;
    private int serviceTimeMax;

    private int numberOfQueues;
    private int numberOfClients;

    private int timeLimit;
    private boolean isRunning;

    private Scheduler scheduler;

    private View view;

    private List <Client> generatedClients;
    private FileWriter fileWriter;

    private int averageWaitingTimeResult;
    private int averageServiceTimeResult;
    private int peakHourResult;

    public Controller(View view){
        //initialize the scheduler
        // => create and start numberOfQueues threads;
        // => initialize the selection strategy
        // initialize frame to display simulation
        // generate numberOfClients clients using generateNRandomClients()
        // and store them to generatedTasks

        this.averageWaitingTimeResult = 0;
        this.averageServiceTimeResult = 0;
        this.peakHourResult = 0;

        this.numberOfClients = 0;
        this.numberOfQueues = 0;

        this.timeLimit = 0;

        this.arrivalTimeMin = 0;
        this.arrivalTimeMax = 0;

        this.serviceTimeMin = 0;
        this.serviceTimeMax = 0;

        this.isRunning = false;
        this.view = view;
        this.view.startSimulationCreateActionListener(new startSimulationListener());


        // initialize the scheduler
        //this.scheduler = new Scheduler(numberOfQueues, numberOfClients);

//        try {
//            this.fileWriter = new FileWriter("logs.txt");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        this.generatedClients = Collections.synchronizedList(new ArrayList<>());

    }

    private void generateNRandomClients(){
        // generate N random clients
        // - random processing time
        // minProcessingTime < processingTime < maxProcessingTime
        // - random arrival time
        // minArrivalTime < arrivalTime < maxArrivalTime
        //sort list with respect to arrival time
        //synchronized (generatedClients) {
            for (int i = 0; i < numberOfClients; i++) {
                int arrivalTime = (int) (Math.random() * (arrivalTimeMax - arrivalTimeMin)) + arrivalTimeMin;
                int serviceTime = (int) (Math.random() * (serviceTimeMax - serviceTimeMin)) + serviceTimeMin;
                generatedClients.add(new Client(i, arrivalTime, serviceTime));
            }
            generatedClients.sort(Comparator.comparing(Client::getArrivalTime));
        //}
    }


    @Override
    public void run() {

        this.currentTime = 0;
        int maxClientsHourPeakAux = 0;
        int maxClientsHourPeak = 0;
        int waitingTimeAux = 0;
        boolean stopEarly1 = false;
        boolean stopEarly2 = false;

        Iterator iterator3 = generatedClients.iterator();

        while (iterator3.hasNext()) {
            Client client = (Client) iterator3.next();
            this.averageServiceTimeResult = this.averageServiceTimeResult + client.getServiceTime();
        }
        this.averageServiceTimeResult = this.averageServiceTimeResult / numberOfClients;

            while (this.currentTime < this.timeLimit) {
                //iterate generatedTasks list and pick clients that have
                //arrivalTime equal with currentTime
                // - send client to queue by calling the dispatchClientMethod
                // from Scheduler
                // - delete client from list
                // update UI frame

                if(stopEarly2 && stopEarly1){
                    break;
                }

                stopEarly1 = false;
                stopEarly2 = false;
                maxClientsHourPeakAux = 0;
                waitingTimeAux = 0;

                synchronized (generatedClients) {

                    Iterator iterator = generatedClients.iterator();

                    while (iterator.hasNext()) {
                        Client client = (Client) iterator.next();
                        if (client.getArrivalTime() == currentTime) {
                            scheduler.dispatchTask(client);
                            iterator.remove(); // !!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                }

                Iterator iterator2 = scheduler.getQueues().iterator();

                while(iterator2.hasNext()){
                    Queue queue = (Queue) iterator2.next();
                    maxClientsHourPeakAux = maxClientsHourPeakAux + queue.getClients().size();
                    waitingTimeAux = waitingTimeAux + queue.getAverageWaitingTime();
                }
                waitingTimeAux = waitingTimeAux / numberOfQueues;
                this.averageWaitingTimeResult = this.averageWaitingTimeResult + waitingTimeAux;

                if(maxClientsHourPeakAux > maxClientsHourPeak){
                    peakHourResult = currentTime;
                    maxClientsHourPeak = maxClientsHourPeakAux;
                }



                //view.update(currentTime, scheduler.getQueues()); // TO DO
                System.out.println(showAll());
                view.setLogsTextArea(showAll());
                System.out.println(averageServiceTimeResult);
                System.out.println(averageWaitingTimeResult);

                try {
                    fileWriter.write(showAll());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                currentTime++;

                //wait an interval of 1 second
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Iterator iterator1 = scheduler.getQueues().iterator();

                while(iterator1.hasNext()){
                    Queue queue = (Queue) iterator1.next();
                    if (!queue.isEmpty()) {
                        stopEarly1 = false;
                        break;
                    } else
                        stopEarly1 = true;
                }

                if (generatedClients.isEmpty()) {
                    stopEarly2 = true;
                }

            }

        try {
            fileWriter.write(ShowAllAdditionalResults());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            scheduler.stopQueues();
            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            averageWaitingTimeResult = averageWaitingTimeResult / timeLimit;

            // view.DisplayStatistics(); //TO DO
            view.setRequiredStatisticalOutputTextArea(ShowAllAdditionalResults());
            this.isRunning = false;


    }

    public String showAll(){
        StringBuilder builder = new StringBuilder();
        builder.append("Time ").append(currentTime).append("\n");
        builder.append("Waiting clients: ");
        for (int i = 0; i < generatedClients.size(); i++) {
            builder.append(generatedClients.toArray()[i].toString());
            if (i < generatedClients.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append("\n");
        for (Queue q : scheduler.getQueues()) {
            builder.append(q).append("\n");
        }
        return builder.toString();
    }

    public String ShowAllAdditionalResults(){
        StringBuilder builder = new StringBuilder();
        builder.append("Average Waiting Time ").append(this.averageWaitingTimeResult).append("\n");
        builder.append("Average Service Time ").append(this.averageServiceTimeResult).append("\n");
        builder.append("Peak Hour ").append(this.peakHourResult).append("\n");

        return builder.toString();
    }

    class startSimulationListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            Controller.this.numberOfClients = view.getNumberOfClientsTextField();
            Controller.this.numberOfQueues = view.getNumberOfQueuesTextField();

            Controller.this.timeLimit = view.getSimulationPeriodTextField();

            Controller.this.arrivalTimeMin = view.getMinArrivalTimeTextField();
            Controller.this.arrivalTimeMax = view.getMaxArrivalTimeTextField();

            Controller.this.serviceTimeMin = view.getMinServiceTimeTextField();
            Controller.this.serviceTimeMax = view.getMaxServiceTimeTextField();

            Controller.this.scheduler = new Scheduler(numberOfQueues, numberOfClients);

            generateNRandomClients();



            Controller.this.isRunning = true;

            try {
                Controller.this.fileWriter = new FileWriter("logs.txt");
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            Thread t = new Thread(Controller.this);
            t.start();

            if(!Controller.this.isRunning()){
                t.stop();
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
