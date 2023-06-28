// Inference Engine of GUI Version - Select Algorithm and input FileName to get the output 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class iengineGUI extends JFrame implements ActionListener {

    // Declare instance variables for the different components of the GUI
    private JComboBox<String> methodComboBox;
    private JTextField filenameField;
    private JTextArea outputArea;

    public iengineGUI() {

        // Set the title, default close operation, and layout of the window
        setTitle("Inference Engine GUI Version");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel to hold the input components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Method:"));
        String[] methods = { "TT", "FC", "BC" };
        methodComboBox = new JComboBox<>(methods);
        inputPanel.add(methodComboBox);
        inputPanel.add(new JLabel("Filename:"));
        filenameField = new JTextField();
        inputPanel.add(filenameField);
        add(inputPanel, BorderLayout.NORTH);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton runButton = new JButton("Run");
        runButton.addActionListener(this);
        buttonPanel.add(runButton);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
            }
        });
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Create a text area to display the output
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(400, 130));
        add(scrollPane, BorderLayout.SOUTH);

        // Set some visual properties to make the GUI look better
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            pack();
            setSize(500, 250); // Set the size of the window after calling pack()
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        String method = (String) methodComboBox.getSelectedItem();
        String filename = filenameField.getText();

        try (BufferedReader inputFile = new BufferedReader(new FileReader(filename))) {
            KnowledgeBase kb = new KnowledgeBase(inputFile);
            inputFile.readLine();
            String askStatement = inputFile.readLine();

            LogicAlgorithm algorithm;
            switch (method) {
                case "TT":
                    algorithm = new TruthTable(kb, askStatement);
                    break;
                case "FC":
                    algorithm = new ForwardChaining(kb, askStatement);
                    break;
                case "BC":
                    algorithm = new BackwardChaining(kb, askStatement);
                    break;
                default:
                    return;
            }

            outputArea.append("Result of " + algorithm.getFullName() + " Logic Algorithm:\n");
            outputArea.append(algorithm.testQuestion() + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new iengineGUI();
    }
}