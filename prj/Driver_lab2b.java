/**
 * file: Driver_lab2b.java
 * author: Brandon Litwin
 * course: MSCS 630
 * assignment: Lab 2b
 * due date: 2/9/2020
 * version: 1.0
 *
 * This file contains the declaration of the driver 
 * for the Lab 2b Extended Euclidean algorithm.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/**
 * Driver_lab2b
 *
 * This class reads two integers (a and b) and calls the euclidAlgExt method to
 * compute the resulting values of the Extended Euclidean algorithm.
 */
public class Driver_lab2b {
  /**
   * main
   *
   * This class reads an input consisting of one or more lines of two integers,
   * and calls the euclidAlgExt method to calculate d, x, and y to satify the
   * equation d = ax+by where d = gcd(a,b).
   * Then, it outputs these values as an array of long integers.
   *
   * Parameters:
   *   inputLine: The String that will contain the current line of input.
   *   currentFirstInt: The first integer of the current line of input.
   *   currentSecondInt: The second integer of the current line of input.
   *   currentEuclidResultArray: The result array of the euclidAlgExt method on
   *                             the current line of input.
   *   resultOutput: An instance of StringBuilder which is used to
   *                    convert the long array to a string for output.
   *
   * Return value: A System output containing the GCD of each input line.
   */
  public static void main(String[]args) {
    try (BufferedReader reader = new 
         BufferedReader(new InputStreamReader(System.in))) {
      String inputLine = "";
      while ((inputLine = reader.readLine()) != null) {
        long currentFirstInt = Long.parseLong(inputLine.split(" ")[0]);
        long currentSecondInt = Long.parseLong(inputLine.split(" ")[1]);
        long[] currentEuclidResultArray = 
               euclidAlgExt(currentFirstInt, currentSecondInt);
        StringBuilder resultOutput = new StringBuilder();
        for (int i = 0; i < currentEuclidResultArray.length; i++) {
          resultOutput.append(currentEuclidResultArray[i] + " ");
        }
        System.out.println(resultOutput.toString().trim());
      }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  /**
   * euclidAlg
   *
   * This class takes two integers and computes the values of the
   * Extended Euclidean algorithm d, x, and y, where
   * d = ax+by where d = gcd(a,b)
   *
   * Parameters:
   *   a: the first input integer.
   *   b: the second input integer.
   *   temp: a temporary storage for certain variable calculations.
   *   q: the floor of the quotient of b and a.
   *   x: the integer that holds the current value of x,
   *      the first Bezout number
   *   y: the integer that holds the current value of y,
   *      the second Bezout number
   *   prevX: the previous integer value of x
   *   prevY: the previous integer value of y
   *   results: the long integer array to hold the results
   *
   * Return value: results array containing the variables d, x, and y
   *               of the equation d = ax+by.
   */
  public static long[] euclidAlgExt(long a, long b) {
    long[] results = new long[3];
    long prevX = 0;
    long x = 1;
    long prevY = 1;
    long y = 0;
    if (a == b) {
      results[0] = b;
      results[1] = 0;
      results[2] = 1;
      return results;
    }
    while (a != 0) {
      long q = Math.floorDiv(b, a);
      long temp = a;
      a = b % a;
      b = temp;
      temp = prevY;
      prevY = y;
      y = temp - q * y;
      temp = prevX;
      prevX = x;
      x = temp - q * x;
    }
    results[0] = b;
    results[1] = prevX;
    results[2] = prevY;

    return results;
  }

}