package Engine;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class App 
{
    private static JFrame resultWindow;
    private static JTextArea resultTextArea;
    private static LogicAlgorithms logic;
    private static JComboBox<String> methodComboBox;

    private static void createResultWindow() 
    {

        resultWindow = new JFrame();
        resultWindow.setTitle("Algorithm Results");
        resultWindow.setSize(500, 250);
        resultWindow.setLocationRelativeTo(null);
        resultWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();

        String[] methods = { "TT", "FC", "BC" };
        methodComboBox = new JComboBox<>(methods);

        // Create the result text area
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        // Create a scroll pane and add the result text area to it
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JButton resultButton = new JButton("Result");
        resultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                resultTextArea.append(logic.testInput());
                resultTextArea.append("\n");
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                resultTextArea.setText("");
                resultTextArea.repaint();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
        });

        // Add the buttons to the button panel
        buttonPanel.add(resultButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        // Add the scroll pane and button panel to the result window
        resultWindow.getContentPane().add(methodComboBox, BorderLayout.NORTH);
        resultWindow.getContentPane().add(scrollPane, BorderLayout.CENTER);
        resultWindow.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Make the result window visible
        resultWindow.setVisible(true);
    }

    public static void main(String[] args) 
    {
        if (args.length != 2) {
            System.err.println("Usage: iengine method filename");
            System.exit(1);
        }

        String methods = args[0];
        String filename = args[1];

        // used to read and store the input data from the file into a parameter
        try (BufferedReader inputFile = new BufferedReader(new FileReader(filename))) 
        {
            KnowledgeBase kb = new KnowledgeBase(inputFile);

            inputFile.readLine();
            String askStatement = inputFile.readLine();

            LogicAlgorithms algorithms = null;

            switch (methods.toLowerCase()) 
            {

                case "tt":
                    algorithms = new TruthTableMethod(kb, askStatement);
                    break;

                case "fc":
                    algorithms = new ForwardChainingMethod(kb, askStatement);
                    break;

                case "bc":
                    algorithms = new BackwardChainingMethod(kb, askStatement);
                    break;

                default:
                    System.out.print("Wrong Search Method! Options: tt, fc & bc");
                    System.exit(2);

            }

            // Print out the result of testing the askStatement using the algorithm
            if (algorithms != null) 
            {
                createResultWindow();
                logic = algorithms;

                methodComboBox.addActionListener(new ActionListener() 
                {
                    public void actionPerformed(ActionEvent e) 
                    {
                        JComboBox<?> combo = (JComboBox<?>) e.getSource();
                        String selectedMethod = (String) combo.getSelectedItem();
                        LogicAlgorithms selectedAlgorithm = null;

                        switch (selectedMethod.toLowerCase()) 
                        {
                            case "tt":
                                selectedAlgorithm = new TruthTableMethod(kb, askStatement);
                                break;
                            case "fc":
                                selectedAlgorithm = new ForwardChainingMethod(kb, askStatement);
                                break;
                            case "bc":
                                selectedAlgorithm = new BackwardChainingMethod(kb, askStatement);
                                break;

                        }

                        if (selectedAlgorithm != null) {
                            logic = selectedAlgorithm;
                        }
                    }
                });
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
