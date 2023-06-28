## Title

COS 30019 Assignment 2: Inference Engine for Propositional Logic

## Methods to run the program 

1. Command line method - In command prompt, navigate to the 'src' folder and type the command below. 

   - javac *.java
   - java iengine <method> <filename> Example: java iengine TT test_HornKB.txt
   - Available method: TT, FC, BC
   - Method is case sensitive, wrong method input will showed incorrect method message. 
   

2. GUI method - Open java compiler, in the project folder open a java file called 'iengineGUI'. 
   Run this file to get the GUI version of Inference Engine. You will be able to select which method to run the program, 
   make sure the input directory of file is using this format 'src/*****.txt.
   
   - Select one of the method
   - On the filename row, type this 'src/test_HornKB.txt' etc. 
   - Click run button to get the output. 
   - Clear button to erase all the output to prevent confusion, if running too many test cases. 