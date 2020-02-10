/**
 * file: Driver_lab3a.java
 * author: Brandon Litwin
 * course: MSCS 630
 * assignment: Lab 3a
 * due date: 2/16/2020
 * version: 1.0
 *
 * This file contains the declaration of the driver 
 * for Lab 3a's matrix determinant algorithm.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Driver_lab3a
 *
 * This class reads two integers (m and n) followed by n lines of n rows to
 * form a square matrix A. It outputs an integer representing the determinant
 * |a| of matrix A.
 *
 */
public class Driver_lab3a {
  /**
   * main
   *
   * This class reads an input consisting of two integers (m and n) followed
   * by n lines of n rows to form a square matrix A. Then, it calls the
   * cofModDet method to calculate the determinant.
   * Then, it outputs an integer representing the determinant |a| of matrix A.
   *
   * Parameters:
   *   inputLine: The String that will contain the current line of input.
   *   modulo: The integer value of the modulo for the matrix.
   *   matrixSize: The integer value of the size of the matrix.
   *   matrix: The 2D integer array representing the matrix.
   *   determinant: The determinant of the matrix.
   *
   * Return value: A System output containing the determinant.
   */
  public static void main(String[]args) {
    try (BufferedReader reader = new
        BufferedReader(new InputStreamReader(System.in))) {
      String inputLine = "";
      inputLine = reader.readLine();
      int modulo = Integer.parseInt(inputLine.split(" ")[0]);
      int matrixSize = Integer.parseInt(inputLine.split(" ")[1]);
      int[][] matrix = new int[matrixSize][matrixSize];
      int rowNumber = 0;
      while ((inputLine = reader.readLine()) != null) {
        // assign each row of input to the columns of the current matrix row
        for (int colNumber = 0; colNumber < matrixSize; colNumber++) {
          matrix[rowNumber][colNumber] = Integer.parseInt(inputLine.split(" ")[colNumber]);
        }
        rowNumber++;
      }
      System.out.println(Arrays.deepToString(matrix));
      int determinant = cofModDet(modulo, matrix);
      System.out.println(determinant);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * cofModDet
   *
   * This class takes a modulo m and a matrix A and computes the value
   * of the determinant of A.
   *
   * Parameters:
   *   m: the modulo.
   *   A: the matrix represented as a 2D integer array.
   *
   * Return value: determinant, the integer value of the determinant of A.
   */
  public static int cofModDet(int m, int[][] A) {
    int determinant = 0;
    return determinant;
  }

}