package Engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        // Check if the correct number of command line arguments is provided
        if (args.length < 2) {
            System.out.println("Insufficient command line arguments.");
            System.out.println("Usage: iengine method filename");
            System.exit(0);
        }

        // Read the input file
        BufferedReader inputFile = new BufferedReader(new FileReader(args[0]));

        // Create a knowledge base from the input file
        KnowledgeBase knowledgeBase = new KnowledgeBase(inputFile);

        // Get the method specified in the command line argument
        String method = args[1];

        // Create the logic algorithm based on the specified method
        LogicAlgorithm algorithm;

        switch (method.toLowerCase()) {
            case "tt":
                algorithm = new TTMethod(knowledgeBase, "");
                break;

            case "fc":
                algorithm = new FCMethod(knowledgeBase, "");
                break;

            case "bc":
                algorithm = new BCMethod(knowledgeBase, "");
                break;

            default:
                System.out.print("Wrong Search Method! Options: tt, fc & bc");
                return;
        }

        // Test the algorithm and print the result
        String result = algorithm.testQuestion();
        System.out.println(result);
    }
}
