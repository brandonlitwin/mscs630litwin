/**
 * file: Driver_lab5.java
 * author: Brandon Litwin
 * course: MSCS 630
 * assignment: Lab 5
 * due date: 3/15/2020
 * version: 1.0
 *
 * This file contains the declaration of the driver
 * for Lab 5's AES algorithm implementation.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.IOException;

/**
 * Driver_lab5
 *
 * This class reads 2 line of input, the system key and plaintext.
 *
 */
public class Driver_lab5 {                       
  /**
   * main
   *
   * This class reads 2 line of input, the system key and plaintext, and calls 
   * the AES() method of the AESCipher class. Then, it outputs the resulting
   * ciphertext.
   *
   * Parameters:
   *   keyHex: The input system key, a 128 bit String of 32 chars
   *   plaintext: The input plaintext
   *
   * Return value: A System output containing the result ciphertext.
   */
  public static void main(String[]args) {
    try (BufferedReader reader = new
        BufferedReader(new InputStreamReader(System.in))) {
          String keyHex = reader.readLine().toUpperCase();
          String plaintext = reader.readLine().toUpperCase();
          System.out.println(AESCipher.AES(keyHex, plaintext));
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}