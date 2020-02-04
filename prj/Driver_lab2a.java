/** 
 * file: Driver_lab2a.java
 * author: Brandon Litwin
 * course: MSCS 630
 * assignment: Lab 2a
 * due date: 2/9/2020
 * version: 1.0
 *
 * This file contains the declaration of the driver 
 * for the Lab 2a Euclidean algorithm.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/** 
 * Driver_lab2a
 *
 * This class reads two integers (a and b) and calls the euclidAlg method to
 * compute their greatest common divisor using the Euclidean algorithm.
 */
public class Driver_lab2a {
  /**
   * main
   *
   * This class reads an input consisting of one or more lines of two integers,
   * and calls the euclidAlg method to calculate the greatest common divisor.
   * Then, it outputs this GCD as an integer.
   *
   * Parameters:
   *   inputLine: The String that will contain the current line of input.
   *   currentFirstInt: The first integer of the current line of input.
   *   currentSecondInt: The second integer of the current line of input.
   *   currentGCD: The result of the euclidAlg method on 
   *               the current line of input.
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
        long currentGCD = euclidAlg(currentFirstInt, currentSecondInt);
        System.out.println(currentGCD);
      }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  /** 
   * euclidAlg
   * 
   * This class takes two integers and computes their greatest common
   * divisor using the Euclidean algorithm which states:
   * Assuming both integers a and b >= 0,
   *   k is the remainder of some integer that divides a and b.
   *   while the result of a % b > 0,
   *     find k using a % b
   *     store b into a
   *     store k into b
   *     so that k becomes the new modulus to divide the previous modulus.
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
  public static long euclidAlg(long a, long b) {
    long gcd,k = 0;
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
    return gcd;
  }

}
