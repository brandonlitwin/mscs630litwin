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
  public static void main(String[]args) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String inputLine;
        while ((inputLine = reader.readLine()) != null) {
          System.out.println(str2int(inputLine));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

}
/** 
 * str2int
 * This method takes an input String and reads each character one at 
 * a time, and then converts it into an array of integers to be returned.
 *
 * Parameters: 
 *   plaintext: the String taken from a line of input.

 * Return value: an integer array containing the encrypted plaintext.
 */
public class int[] str2int(String plaintext) {
    
}