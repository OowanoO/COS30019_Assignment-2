package Engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
               System.out.println("Result of " + algorithms.getFullName() + " Logic Algorithm:");
               System.out.println(algorithms.testInput());
            }


        }   
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }      
}
