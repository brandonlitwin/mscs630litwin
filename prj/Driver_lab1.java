/** 
 * file: Driver_lab1.java
 * author: Brandon Litwin
 * course: MSCS 630
 * assignment: Lab 1
 * due date: 2/2/2020
 * version: 1.0
 *
 * This file contains the declaration of the driver 
 * for the Lab 1 cryptography algorithm.
 */
import java.util.stream.Stream;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/** 
 * Driver_lab1
 *
 * This class reads an input String from 'a' to 'z' or 'A' to 'Z' and
 * calls the str2int method to transform it into integers from 0 to 25, 
 * with a space becoming 26.
 */
public class Driver_lab1 {
  static final int ASCII_VALUE_OF_A = 65;
  /**
   * main
   *
   * This class reads an input String, calls the str2int method to
   * transform it into an integer array, then uses a StringBuilder
   * to convert the array into a string with a space between each 
   * integer, and trims off trailing whitespace. 
   *
   * Parameters:
   *   inputLine: The String that will contain the current line of input.
   *   encryptedIntArray: The return value from str2int
   *   encryptedString: An instance of StringBuilder which is used to 
   *                    convert the integer array to a string for output.
   * 
   * Return value: A System output containing the encrypted string.
   */
  public static void main(String[]args) {
    try (BufferedReader reader = new 
         BufferedReader(new InputStreamReader(System.in))) {
      String inputLine = "";
      while ((inputLine = reader.readLine()) != null) {
        int[] encryptedIntArray = str2int(inputLine);
        StringBuilder encryptedString = new StringBuilder();
        for (int i = 0; i < encryptedIntArray.length; i++) {
          encryptedString.append(encryptedIntArray[i] + " ");
        }
        System.out.println(encryptedString.toString().trim());
      }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  /** 
   * str2int
   * This method takes an input String and reads each character one at 
   * a time, and then converts it into an array of integers to be returned.
   * The algorithm consists of converting the character to uppercase, then
   * converting that to an integer, then subtracting the ASCII value of 'A'.
   * If the integer value is less than 0, it must be a space so it must be
   * encrypted to 26.
   *
   * Parameters: 
   *   plaintext: the String taken from a line of input.
   *   encryptedArray: the integer array that will contain the encrypted text.
   *   charToEncrypt: the current character in the string to be encrypted.
   *   charToInt: the current character casted to int.
   *
   * Return value: an integer array containing the encrypted plaintext.
   */
  public static int[] str2int(String plaintext) {
    int[] encryptedArray = new int[plaintext.length()];
    for (int i = 0; i < encryptedArray.length; i++) {
      char charToEncrypt = plaintext.charAt(i);
      int charToInt = (int) (Character.toUpperCase(charToEncrypt));
      // Any value below the ASCII value of A must be a space. 
      // In that case, the encrypted value is 26.
      // All other cases must be between 'A' and 'Z'.
      if (charToInt < ASCII_VALUE_OF_A) {
        encryptedArray[i] = 26;
      } else {
        encryptedArray[i] = charToInt - ASCII_VALUE_OF_A;
      }
    }

    return encryptedArray;
  }

}
