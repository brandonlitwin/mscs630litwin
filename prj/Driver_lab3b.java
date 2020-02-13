/**
 * file: Driver_lab3b.java
 * author: Brandon Litwin
 * course: MSCS 630
 * assignment: Lab 3b
 * due date: 2/16/2020
 * version: 1.0
 *
 * This file contains the declaration of the driver
 * for Lab 3b's plaintext hex matrix algorithm.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Driver_lab3b
 *
 * This class reads 2 lines of input: the first containing the substitution
 * character, and the second containing the full plaintext to encrypt. It
 * outputs multiple 4x4 matrices separated by an empty line to represent
 * the fully encrypted text with the necessary padding.
 *
 */
public class Driver_lab3b {
  // Number of characters per matrix.
  static final int MATRIX_LENGTH = 16; 
  // Number of rows and columns in the square matrix.
  static final int MATRIX_SIZE = (int) Math.sqrt(MATRIX_LENGTH);                           
  /**
   * main
   *
   * This class reads 2 lines of input: the first containing the substitution
   * character, and the second containing the full plaintext to encrypt.
   * Then, it breaks the plaintext into multiple substrings of no more than 16
   * characters. For each substring, it calls the getHexMatP method to produce
   * its encrypted text.
   * Finally, it outputs multiple 4x4 matrices separated by an empty line to
   * represent the fully encrypted text with the necessary padding.
   *
   * Parameters:
   *   substitutionChar: The first line of input containing the substitution 
   *                     character used for padding the matrix.
   *   plaintext: The second line of input containing the text to encrypt.
   *
   *
   * Return value: A System output containing the fully encrypted text
   * represented in at least 1 4x4 matrix.
   */
  public static void main(String[]args) {
    try (BufferedReader reader = new
        BufferedReader(new InputStreamReader(System.in))) {
      char substitutionChar = reader.readLine().charAt(0);
      String plaintext = reader.readLine();
      int plaintextLength = plaintext.length();
      int numberOfMatrices = (int) Math.ceil(((double) plaintextLength / 
                                              (double) MATRIX_LENGTH));
      int currentStringBeginIndex = 0;
      int currentStringEndIndex = currentStringBeginIndex + MATRIX_LENGTH;
      int[][] currentResultMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];

      while (currentStringBeginIndex < (numberOfMatrices * MATRIX_LENGTH)) {
        if (currentStringBeginIndex > 0) {
          System.out.println(" ");
        }
        String currentString = "";
        if (plaintextLength - currentStringBeginIndex > MATRIX_LENGTH) {
          currentString = plaintext.substring(currentStringBeginIndex,
                                              currentStringEndIndex);
        } else {
          currentString = plaintext.substring(currentStringBeginIndex);
        }
        currentResultMatrix = getHexMatP(substitutionChar, currentString);
        // print the current matrix
        for (int i = 0; i < currentResultMatrix.length; i++) {
          for (int j = 0; j < MATRIX_SIZE; j++) {
            if (j == 0) {
              System.out.print(String.format("%H", currentResultMatrix[i][j]));
            } else {
              System.out.print(" " + 
                               String.format("%H", currentResultMatrix[i][j]));
            }
          }
          System.out.println(" ");
        }
        currentStringBeginIndex = currentStringEndIndex;
        currentStringEndIndex = currentStringBeginIndex + MATRIX_LENGTH;
      }
      
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
   *   s: the substitution character used for padding the matrix.
   *   p: the full plaintext string to encrypt into the matrix.
   *
   * Return value: the matrix of encrypted text as a 2D 4x4 array.
   */
  public static int[][] getHexMatP(char s, String p) {
    int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    for (int row = 0; row < MATRIX_SIZE; row++) {
      for (int col = 0; col < MATRIX_SIZE; col++) {
        int currentMatrixPosition = row + (MATRIX_SIZE * col);
        char currentChar = s;
        if (currentMatrixPosition < p.length()) {
          currentChar = p.charAt(currentMatrixPosition);
        } 
        matrix[row][col] = currentChar; 
      }
    }
    return matrix;
  }

}