// Inference Engine default running method - which launched the Command Prompt and using the following command ("java iengine <method> <filename>") 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class iengine {

  public static void main(String[] args) {

    // Check that the correct number of command line arguments were provided
    if (args.length != 2) {
      System.err.println("Usage: iengine method filename");
      System.exit(1);
    }

    // Get the method and filename from the command line arguments
    String method = args[0];
    String filename = args[1];

    // Use a try-with-resources statement to automatically close the BufferedReader
    try (BufferedReader inputFile = new BufferedReader(new FileReader(filename))) {

      // Create a KnowledgeBase object with the contents of the file
      KnowledgeBase kb = new KnowledgeBase(inputFile);

      // Read in the ASK line and then the askStatement from the file
      inputFile.readLine();
      String askStatement = inputFile.readLine();

      // Create an Algorithm object based on the specified method
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
          System.err.println("\nInvalid method: " + method);
          System.err.println("Available Algorithm -> ' TT | FC | BC ' (Case Sensitive)");
          System.exit(2);
          return;
      }

      // Print out the result of testing the askStatement using the algorithm
      System.out.println("Result of " + algorithm.getFullName() + " Logic Algorithm:");
      System.out.println(algorithm.testQuestion());
    } catch (IOException e) {

      // Catch and print any IOExceptions that occur while reading from the file
      e.printStackTrace();
    }
  }
}