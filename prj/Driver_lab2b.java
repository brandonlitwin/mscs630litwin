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
 * Driver_lab2a
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
   * d = ax+by where d = gcd(a,b).
   * The Extended Euclidean algorithm adds the following procedures to the 
   * standard Euclidean algorithm:
   *
   *
   * Parameters: 
   *   a: the first input integer.
   *   b: the second input integer.
   *   tempHolder: the temporary holder of b in case a and b need to 
   *               be switched.
   *   k: the integer that divides a and b.
   *   gcd: the integer value of the greatest common divisor of a and b.
   *
   * Return value: gcd, the integer value of the greatest common divisor 
   *               of a and b.
   */
  public static long[] euclidAlgExt(long a, long b) {
    long[] results = new long[3];
    long gcd = 0;
    long x = 0;
    long y = 0;
    long k = 0;
    // switch values of a and b if b > a
    if (b > a) {
      long tempHolder = b;
      b = a;
      a = tempHolder;
    }
    while (b > 0) {
      k = a % b;
      a = b;
      b = k;
    }
    gcd = a;
    // calculate x and y
    // x = y1 - b/a * x1
    //   =    - 75/148
    // y = x1
    x = 
    results[0] = gcd;
    results[1] = x;
    results[2] = y;
    return results;
  }

}
