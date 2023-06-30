## Getting Started

Instructions 

To execute the Java-based program in Windows CMD shell, follow these steps: 

1. Open the Command Prompt (CMD) with normal user privilege. 

2. Use the cd command to navigate to the folder that contains the .exe file of the program. For example, if the .exe file is located in "C:\Users\Kang\Desktop\InferenceEngine"

3. Once you are in the correct folder, you can execute the program using the following command format: 
iengine <method> <filename> 

Replace <file> with the name or path of the file containing the test case, and <method> with the keyword of the search method you want to use. For example, tt for Truth Table Search, fc for Forward Chaining Search, bc for Backward Chaining Search. 

4. For example, if you want to test a case in a text file called "test_HornKB.txt" using the Truth Table Search, the command would be: 
iengine.exe tt test_HornKB.txt 

5. Press Enter to execute the command. The program will run and provide the response or output based on the specified test case and search method. 