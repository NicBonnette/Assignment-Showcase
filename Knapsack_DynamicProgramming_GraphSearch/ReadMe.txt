Knapsack Many Ways

The jar file includes the following classes:
Knapsack - the main class that deals with command line input and contains the main class
AutomatedTester - the class that runs a variety of automated tests
InputGetter - the class that reads files input at the command line
Thing - the class of items for consideration wrt the knapsack
The remaining classes correspond to specific solvers: Brute Force, DynamicProgramming, or (Heuristically guided) Graph Search
Each type of searching has 2 options: 01 or Many - 01 allows only 0 or 1 of each item to be added to the knapsack, Many allows multiples of each item)

I recommend running the jar file from the command line with the following command, which will run through a fairly exhaustive set of tests that matches the results presented in the assignment write up:

java -jar Knapsack.jar test full

If you try to run the program without any options, suggestions will be displayed.
If you want to try out reading in a file, for example you could enter: 
java -jar Knapsack.jar gsm 40 exampleInput.txt

