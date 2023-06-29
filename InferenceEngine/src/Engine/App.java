package Engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class App 
{
    public static void main(String[] args)  
    {
        if (args.length != 2) 
        {
          System.err.println("Usage: iengine method filename");
          System.exit(1);
        }

        String method = args[0];
        String filename = args[1];

        //used to read and store the input data from the file into a parameter
        try(BufferedReader inputFile = new BufferedReader(new FileReader(filename)))
        {
            KnowledgeBase kb = new KnowledgeBase(inputFile);

            inputFile.readLine();
            String askStatement = inputFile.readLine();

            LogicAlgorithms algorithms = null;

            switch (method.toLowerCase())
            {

            case "tt":
            algorithms = new TruthTableMethod(kb, askStatement);
            break;

            /*case "fc":
            FC.FCMethod();
            break; 

            case "bc": 
            BC.BCMethod();
            break; 
*/ 
            default:
                System.out.print("Wrong Search Method! Options: tt, fc & bc");
                System.exit(2);
                
            }

            // Print out the result of testing the askStatement using the algorithm
            if (algorithms != null) 
            {
                 // Create a new JFrame for displaying the result
    JFrame resultWindow = new JFrame();
    resultWindow.setTitle("Algorithm Results");
    resultWindow.setSize(500, 250);
    resultWindow.setLocationRelativeTo(null);
    resultWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create a JTextArea to display the result
    JTextArea resultTextArea = new JTextArea();
    resultTextArea.setEditable(false);

    // Append the test output to the result text area
    resultTextArea.append(algorithms.testInput());

    // Create a JScrollPane and add the result text area to it
    JScrollPane scrollPane = new JScrollPane(resultTextArea);

    // Add the scroll pane to the result window
    resultWindow.getContentPane().add(scrollPane);

    // Make the result window visible
    resultWindow.setVisible(true);

            }


        }   
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }      
}
