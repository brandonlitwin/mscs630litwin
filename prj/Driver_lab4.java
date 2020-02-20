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
   * uppercase. 
   *
   * Parameters:
   *
   *
   * Return value: A System output containing the 11 round keys, 1 per line.
   */
  public static void main(String[]args) {
    try (BufferedReader reader = new
        BufferedReader(new InputStreamReader(System.in))) {
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * getHexMatP
   *
   * This class takes a substitution character s and a String p up to 16
   * characters and converts the string into matrices of hex values.
   * The substitution character pads the rest of the matrix in order to
   * fill it up to 4x4.
   *
   * Parameters:
   *   s: The substitution character used for padding the matrix.
   *   p: The full plaintext string to encrypt into the matrix.
   *   row: The current row of the matrix that is being built.
   *   col: The current column of the matrix that is being built.
   *   currentMatrixPosition: The current index of the matrix.
   *   currentChar: The current character at the current index of the matrix.
   *
   * Return value: The matrix of encrypted text as a 2D 4x4 array.
   */
  public static int[][] getHexMatP(char s, String p) {
    int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    for (int row = 0; row < MATRIX_SIZE; row++) {
      for (int col = 0; col < MATRIX_SIZE; col++) {
        int currentMatrixPosition = row + (MATRIX_SIZE * col);
        char currentChar = s;
        if (p != null) {
          if (currentMatrixPosition < p.length()) {
            currentChar = p.charAt(currentMatrixPosition);
          } 
        }
        
        matrix[row][col] = currentChar; 
      }
    }
    return matrix;
  }

}