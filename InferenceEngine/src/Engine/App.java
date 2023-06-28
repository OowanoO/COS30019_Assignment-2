package Engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App 
{
    public static void main(String[] args) throws IOException 
    {
        //used to read and store the input data from the file into a parameter
        BufferedReader inputFile = new BufferedReader(new FileReader(args[0]));
        
        String method = args[1];  

        switch (method.toLowerCase())
        {
            case "tt":
            TTMethod.TTSearch();
            break;

            case "fc":
            FC.FCMethod();
            break; 

            case "bc": 
            BC.BCMethod();
            break; 

            default:
            {
                System.out.print("Wrong Search Method! Options: tt, fc & bc");
                System.exit(0);
            }
        }
    }
}
