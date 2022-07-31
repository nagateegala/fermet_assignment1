/* Title: Fermat's Near Miss Finder
 * File name: Fermat.jar
 * External files: No external files were used.
 * Generated files: small_output.txt is generated to record the list of nearest misses.
 * Programmers: Nikith Kethipally , Naga Raju Teegala 
 * Email addresses: NikithKethipally@lewisu.edu, NagaRajuTeegala@lewisu.edu
 * Software Engineering (Sections SU22-CPSC-60500-001 & 002)
 * Date completed: July 30, 2022
 * Date submitted: July 30, 2022
 * Explanation: Mathematician Pierre Fermat proved that no arbitrary natural numbers x, y, and z could
   * satisfy the equation x^n+y^n=z^n where n is a natural number above 2. This program searches for
   * values of z given integers x and y greater than 10 (and less than a specified user input value k)
   * that satisfy the above equation, noting misses, that is the distance from the true z value to the
   * nearest integer value. In running this program, providing a bound on the x and y values as well as
   * an n value, it will search for misses and output the conditions under which the miss was discovered
   * every time a smaller relative miss size percentage is found.
 * External sources: We did not use any external sources.
*/

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class main {
  public static void main(String[] args) {
    try {
      printDescription();
      writeFileCheck();
      long n = setValue(2, 12, "n"); //the exponent to be used
      long kBound = setUpperBound((int)n); //the maximum allowed value for k
      long k = setValue(10, kBound, "k"); //the maximum value to be used for x and y
    
      findMiss(k, n);
    }
    catch (IOException e) {
      System.out.println("An error occurred in creating or accessing small_output.txt.");
      e.printStackTrace();
    }
  }
  
  //check that it's possible to write to small_output.txt
  public static void writeFileCheck() throws IOException {
    File move_log = new File("small_output.txt"); //file to write to for recording current lowest
    if (move_log.createNewFile()) {
      System.out.println("small_output.txt has been created to record a list of current smallest misses.");
    }
    else {
      System.out.println("There was a problem generating small_output.txt.");
    }
  }
  
  //Function for finding the smallest miss
  static void findMiss(long k, long n) {
    try {
      //FileWriter for recording outputs in a file
      FileWriter myWriter = new FileWriter("small_output.txt");
      myWriter.write("For n = " + n + ":\n");
      double z; //z^n = x^n + y^n
      double zn; //z = nth root of (x^n + y^n)
      double z_up; //natural number above z
      double z_down; //natural number below z
      double test_up; //test_up = z_up^n - z^n
      double test_down; //test_down = z^n - z_down^n
      
      double smallestMiss = 100; //the smallest known miss, recorded as a decimal
      //a value of 1 would mean that you missed by 100%
      //100 is the starting value to guarantee that the first found value is also the starting smallest

      //test every possible numerical combination of x and y between 10 and k, inclusive, given n
      for (long x=10; x<=k; x++) {
        for (long y=x; y<=k; y++) {
          zn = Math.pow(x, n) + Math.pow(y, n);
          z = Math.pow(zn, 1.0 / n);
          z_up = Math.ceil(z);
          z_down = Math.floor(z);
          test_up = (Math.pow(z_up, n) - zn) / zn;
          test_down = (zn - Math.pow(z_down, n)) / zn;
          
          if (test_up >= test_down) { //if the upper bound is a bigger difference than the lower bound, or they're equal
            if (test_down <= smallestMiss) { //if the lower bound is a smaller difference than the smallest known miss
              smallestMiss = test_down;
              System.out.println(z_down + " is " + (smallestMiss*100) + "% away from " + z + " for x = " + x + " and y = " + y);
              myWriter.write(z_down + " is " + (smallestMiss*100) + "% away from " + z + " for x = " + x + " and y = " + y + "\n");
            }
          }
          else if (test_up < test_down) { //if the lower bound is a bigger difference than the higher bound
            if (test_up < smallestMiss) { //if the upper bound is a smaller difference than the smallest known miss
              smallestMiss = test_up;
              System.out.println(z_up + " is " + (smallestMiss*100) + "% away from " + z + " for x = " + x + " and y = " + y);
              myWriter.write(z_up + " is " + (smallestMiss*100) + "% away from " + z + " for x = " + x + " and y = " + y + "\n");
            }
          }
          else {
            System.out.println("error");
            myWriter.write("An error has occurred.\n");
            return;
          }
        }
      }
      myWriter.close();
      
      //let the user press "enter" when they're finished
      System.out.println("Press \"Enter\" once you're done.");
      Scanner scan = new Scanner(System.in);
      String throwaway = scan.nextLine();
      scan.close();
    }
    catch (IOException e) { //if there's an error with FileWriter
      System.out.println("An error occurred in writing to small_output.txt.");
      e.printStackTrace();
    }
  }
  
  //function for printing the description for the convenience of the user in a readable fashion
  public static void printDescription(){
    System.out.println("Mathematician Pierre Fermat proved that no arbitrary natural numbers x, y, and z could");
    System.out.println("satisfy the equation x^n+y^n=z^n where n is a natural number above 2. This program searches");
    System.out.println("for values of z, noting misses: the distance from the true z value to the nearest integer value.\n");
    System.out.println("It will search for misses given combinations of values x and y starting at 10 and going to an");
    System.out.println("upper bound k that you will provide. You will also designate the power value of n.\n");
    System.out.println("Upon finding a smaller relative miss percentage, the conditions under which the miss was found");
    System.out.println("will be ouput to you, thus the last miss output will be the smallest relative miss given your");
    System.out.println("initial conditions.\n");

}
  
  //function for determining values for k and n
  public static long setValue(long low, long high, String name) {
    //for scanning in values for n and k
    Scanner sc = new Scanner(System.in);
    System.out.println("Please enter an integer value in the range (" + low + "," + high + ") for " + name + ".");
    long retVal=0;
    do{     
      if(retVal!=0)
       retVal =0;
      try {
        retVal = sc.nextInt();
        if(retVal < (low+1) || retVal > (high-1))
          System.out.println("That is an invalid value for " + name + ". Please enter an integer between " + low + " and " + high + ".");
      }  
      catch(InputMismatchException e) {
        System.out.println("That is an invalid value for " + name + ". Please enter an integer between " + low + " and " + high + ".");
        sc.next();
      }
    }
      while(retVal < (low+1) || retVal > (high-1));
    //sc.close(); //removed because it caused a "no such element" error in JDE, despite working in DrJava
    return retVal;
  }
  
  //set the upper bound for k, given n, to avoid overflow
  public static long setUpperBound(int power) {
    int bound;
    switch (power) {
      case 3:
        bound = 12000;
        break;
      case 4:
        bound = 10500;
        break;
      case 5:
        bound = 9000;
        break;
      case 6:
        bound = 5000;
        break;
      case 7:
        bound = 2800;
        break;
      case 8:
        bound = 2300;
        break;
      case 9:
        bound = 2000;
        break;
      case 10:
        bound = 1700;
        break;
      case 11:
        bound = 1500;
        break;
      default:
        bound = 1500;
        break;
    }
    return bound;
  }
}
