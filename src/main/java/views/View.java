package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JButton btnNewButton;

    private JTextField minArrivalTimeTextField;
    private JTextField maxArrivalTimeTextField;
    private JTextField minServiceTimeTextField;
    private JTextField maxServiceTimeTextField;
    private JTextField numberOfQueuesTextField;
    private JTextField numberOfClientsTextField;
    private JTextField simulationPeriodTextField;

    private JScrollPane logsScrollPane;
    private JScrollPane requiredStatisticalOutputScrollPane;

    private JTextArea logsTextArea;
    private JTextArea requiredStatisticalOutputTextArea;


    public View() {
        this.setTitle("Queue Simulation Manager");
        this.getContentPane().setBackground(new Color(178, 34, 34));
        this.getContentPane().setForeground(new Color(128, 0, 0));
        this.setBounds(100, 100, 1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        JLabel queueManagementSimulationLabel = new JLabel("Queue Management Simulation");
        queueManagementSimulationLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        queueManagementSimulationLabel.setBounds(417, 10, 461, 74);
        this.getContentPane().add(queueManagementSimulationLabel);

        JLabel arrivalTimeLabel = new JLabel("Arrival Time:");
        arrivalTimeLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        arrivalTimeLabel.setBounds(33, 104, 176, 41);
        this.getContentPane().add(arrivalTimeLabel);

        JLabel minArrivalTimeLabel = new JLabel("Min:");
        minArrivalTimeLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        minArrivalTimeLabel.setBounds(212, 104, 76, 41);
        this.getContentPane().add(minArrivalTimeLabel);

        minArrivalTimeTextField = new JTextField();
        minArrivalTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        minArrivalTimeTextField.setColumns(10);
        minArrivalTimeTextField.setBounds(286, 102, 76, 43);
        this.getContentPane().add(minArrivalTimeTextField);

        JLabel maxArrivalTimeLabel = new JLabel("Max:");
        maxArrivalTimeLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        maxArrivalTimeLabel.setBounds(372, 104, 76, 41);
        this.getContentPane().add(maxArrivalTimeLabel);

        maxArrivalTimeTextField = new JTextField();
        maxArrivalTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        maxArrivalTimeTextField.setColumns(10);
        maxArrivalTimeTextField.setBounds(445, 104, 76, 43);
        this.getContentPane().add(maxArrivalTimeTextField);

        JLabel serviceTimeLabel = new JLabel("Service Time:");
        serviceTimeLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        serviceTimeLabel.setBounds(33, 155, 200, 41);
        this.getContentPane().add(serviceTimeLabel);

        JLabel minServiceTimeLabel = new JLabel("Min:");
        minServiceTimeLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        minServiceTimeLabel.setBounds(222, 155, 76, 41);
        this.getContentPane().add(minServiceTimeLabel);

        minServiceTimeTextField = new JTextField();
        minServiceTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        minServiceTimeTextField.setColumns(10);
        minServiceTimeTextField.setBounds(296, 155, 76, 43);
        this.getContentPane().add(minServiceTimeTextField);

        JLabel maxServiceTimeLabel = new JLabel("Max:");
        maxServiceTimeLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        maxServiceTimeLabel.setBounds(382, 155, 76, 41);
        this.getContentPane().add(maxServiceTimeLabel);

        maxServiceTimeTextField = new JTextField();
        maxServiceTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        maxServiceTimeTextField.setColumns(10);
        maxServiceTimeTextField.setBounds(455, 157, 76, 43);
        this.getContentPane().add(maxServiceTimeTextField);

        JLabel numberOfQueuesLabel = new JLabel("Number of Queues:");
        numberOfQueuesLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        numberOfQueuesLabel.setBounds(33, 207, 296, 41);
        this.getContentPane().add(numberOfQueuesLabel);

        numberOfQueuesTextField = new JTextField();
        numberOfQueuesTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        numberOfQueuesTextField.setColumns(10);
        numberOfQueuesTextField.setBounds(321, 208, 76, 43);
        this.getContentPane().add(numberOfQueuesTextField);

        JLabel numberOfClientsLabel = new JLabel("Number of Clients:");
        numberOfClientsLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        numberOfClientsLabel.setBounds(33, 258, 296, 41);
        this.getContentPane().add(numberOfClientsLabel);

        numberOfClientsTextField = new JTextField();
        numberOfClientsTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        numberOfClientsTextField.setColumns(10);
        numberOfClientsTextField.setBounds(307, 258, 76, 43);
        this.getContentPane().add(numberOfClientsTextField);

        JLabel simulationTimeLabel = new JLabel("Simulation Period:           (s)");
        simulationTimeLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        simulationTimeLabel.setBounds(33, 309, 523, 41);
        this.getContentPane().add(simulationTimeLabel);

        simulationPeriodTextField = new JTextField();
        simulationPeriodTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        simulationPeriodTextField.setColumns(10);
        simulationPeriodTextField.setBounds(296, 309, 76, 43);
        this.getContentPane().add(simulationPeriodTextField);

        btnNewButton = new JButton("Begin Simulation");
        btnNewButton.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 30));
        btnNewButton.setBounds(43, 362, 284, 54);
        this.getContentPane().add(btnNewButton);

        requiredStatisticalOutputScrollPane = new JScrollPane();
        requiredStatisticalOutputScrollPane.setBounds(43, 466, 618, 207);
        this.getContentPane().add(requiredStatisticalOutputScrollPane);

        requiredStatisticalOutputTextArea = new JTextArea();
        requiredStatisticalOutputTextArea.setEditable(false);
        requiredStatisticalOutputScrollPane.setViewportView(requiredStatisticalOutputTextArea);

        JLabel requiredStatisticalOutputLabel = new JLabel("Required Statistical Output:");
        requiredStatisticalOutputLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
        requiredStatisticalOutputLabel.setBounds(423, 414, 238, 60);
        this.getContentPane().add(requiredStatisticalOutputLabel);

        JLabel logsLabel = new JLabel("Logs:");
        logsLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
        logsLabel.setBounds(690, 67, 68, 60);
        this.getContentPane().add(logsLabel);

        logsScrollPane = new JScrollPane();
        logsScrollPane.setBounds(690, 117, 566, 556);
        this.getContentPane().add(logsScrollPane);

        logsTextArea = new JTextArea();
        logsTextArea.setEditable(false);
        logsScrollPane.setViewportView(logsTextArea);

        this.setVisible(true);
    }

    public int getMinArrivalTimeTextField() {
        return Integer.parseInt(minArrivalTimeTextField.getText());
    }

    public void setMinArrivalTimeTextField(JTextField minArrivalTimeTextField) {
        this.minArrivalTimeTextField = minArrivalTimeTextField;
    }

    public int getMaxArrivalTimeTextField() {
        return Integer.parseInt(maxArrivalTimeTextField.getText());
    }

    public void setMaxArrivalTimeTextField(JTextField maxArrivalTimeTextField) {
        this.maxArrivalTimeTextField = maxArrivalTimeTextField;
    }

    public int getMinServiceTimeTextField() {
        return Integer.parseInt(minServiceTimeTextField.getText());
    }

    public void setMinServiceTimeTextField(JTextField minServiceTimeTextField) {
        this.minServiceTimeTextField = minServiceTimeTextField;
    }

    public int getMaxServiceTimeTextField() {
        return Integer.parseInt(maxServiceTimeTextField.getText());
    }

    public void setMaxServiceTimeTextField(JTextField maxServiceTimeTextField) {
        this.maxServiceTimeTextField = maxServiceTimeTextField;
    }

    public int getNumberOfQueuesTextField() {
        return Integer.parseInt(numberOfQueuesTextField.getText());
    }

    public void setNumberOfQueuesTextField(JTextField numberOfQueuesTextField) {
        this.numberOfQueuesTextField = numberOfQueuesTextField;
    }

    public int getNumberOfClientsTextField() {
        return Integer.parseInt(numberOfClientsTextField.getText());
    }

    public void setNumberOfClientsTextField(JTextField numberOfClientsTextField) {
        this.numberOfClientsTextField = numberOfClientsTextField;
    }

    public int getSimulationPeriodTextField() {
        return Integer.parseInt(simulationPeriodTextField.getText());
    }

    public void setSimulationPeriodTextField(JTextField simulationPeriodTextField) {
        this.simulationPeriodTextField = simulationPeriodTextField;
    }

    public JScrollPane getLogsScrollPane() {
        return logsScrollPane;
    }

    public void setLogsScrollPane(JScrollPane logsScrollPane) {
        this.logsScrollPane = logsScrollPane;
    }

    public JScrollPane getRequiredStatisticalOutputScrollPane() {
        return requiredStatisticalOutputScrollPane;
    }

    public void setRequiredStatisticalOutputScrollPane(JScrollPane requiredStatisticalOutputScrollPane) {
        this.requiredStatisticalOutputScrollPane = requiredStatisticalOutputScrollPane;
    }

    public JTextArea getLogsTextArea() {
        return logsTextArea;
    }

    public void setLogsTextArea(String logsTextArea) {
        this.logsTextArea.setText(logsTextArea);
    }

    public JTextArea getRequiredStatisticalOutputTextArea() {
        return requiredStatisticalOutputTextArea;
    }

    public void setRequiredStatisticalOutputTextArea(String requiredStatisticalOutputTextArea) {
        this.requiredStatisticalOutputTextArea.setText(requiredStatisticalOutputTextArea);
    }

    public void startSimulationCreateActionListener(ActionListener actionListener){
        btnNewButton.addActionListener(actionListener);
    }
}
