package org.example;

import controller.Controller;
import views.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();

        Controller controller = new Controller(view);


//        Thread t = new Thread(controller);
//        t.start();

    }
}
