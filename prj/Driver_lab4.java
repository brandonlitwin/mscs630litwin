/**
 * file: Driver_lab4.java
 * author: Brandon Litwin
 * course: MSCS 630
 * assignment: Lab 4
 * due date: 3/1/2020
 * version: 1.0
 *
 * This file contains the declaration of the driver
 * for Lab 4's AES Key Generation algorithm.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Driver_lab4
 *
 * This class reads 1 line of input, the system key.
 *
 */
public class Driver_lab4 {                       
  /**
   * main
   *
   * This class reads 1 line of input, the system key, and converts it to all
   * uppercase. It calls the aesRoundKeys method of the AESCipher class. Then,
   * it outputs the resulting keys.
   *
   * Parameters:
   *   keyHex: The input system key, a 16 char String
   *   roundKeysHex: A String array containing all of the result keys
   *
   * Return value: A System output containing the 11 round keys, 1 per line.
   */
  public static void main(String[]args) {
    try (BufferedReader reader = new
        BufferedReader(new InputStreamReader(System.in))) {
          String keyHex = reader.readLine().toUpperCase();
          String[] roundKeysHex = AESCipher.aesRoundKeys(keyHex);
          for (int i = 0; i < roundKeysHex.length; i++) {
            System.out.println(roundKeysHex[i]);
          }
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}