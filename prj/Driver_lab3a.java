/**
 * file: Driver_lab3a.java
 * author: Brandon Litwin
 * course: MSCS 630
 * assignment: Lab 3a
 * due date: 2/16/2020
 * version: 1.0
 *
 * This file contains the declaration of the driver 
 * for Lab 3a's matrix cofactor modular determinant algorithm.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Driver_lab3a
 *
 * This class reads two integers (m and n) followed by n lines of n rows to
 * form a square matrix A. It outputs an integer representing the cofactor 
 * modular determinant |a| of matrix A.
 *
 */
public class Driver_lab3a {
  /**
   * main
   *
   * This class reads an input consisting of two integers (m and n) followed
   * by n lines of n rows to form a square matrix A. Then, it calls the
   * cofModDet method to calculate the determinant.
   * Then, it outputs an integer representing the cofactor 
   * modular determinant |a| of matrix A.
   *
   * Parameters:
   *   inputLine: The String that will contain the current line of input.
   *   modulo: The integer value of the modulo for the matrix.
   *   matrixSize: The integer value of the size of the matrix.
   *   matrix: The 2D integer array representing the matrix.
   *   determinant: The determinant of the matrix.
   *   rowNumber: The current row of the matrix.
   *   colNumber: The current column of the matrix.
   *
   * Return value: A System output containing the determinant in modulo m.
   */
  public static void main(String[]args) {
    try (BufferedReader reader = new
        BufferedReader(new InputStreamReader(System.in))) {
      String inputLine = "";
      inputLine = reader.readLine();
      long modulo = Long.parseLong(inputLine.split(" ")[0]);
      long matrixSize = Long.parseLong(inputLine.split(" ")[1]);
      long[][] matrix = new long[(int) matrixSize][(int) matrixSize];
      int rowNumber = 0;
      while ((inputLine = reader.readLine()) != null) {
        // assign each row of input to the columns of the current matrix row
        for (int colNumber = 0; colNumber < matrixSize; colNumber++) {
          matrix[rowNumber][colNumber] = 
          Long.parseLong(inputLine.split(" ")[colNumber]);
        }
        rowNumber++;
      }
      System.out.println(cofModDet(modulo, matrix));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * cofModDet
   *
   * This class takes a modulo m and a matrix A and computes the value
   * of the cofactor modular determinant of A.
   *
   * Parameters:
   *   m: the integer value of the modulo.
   *   A: the matrix represented as a 2D integer array.
   *   determinant: the integer value of the determinant.
   *   sign: the integer value of the sign used in calculations.
   *   submatrix: a 2D integer array that holds the submatrix of the 
   *              current matrix.
   *   determinantInModulo: the determinant in modulo m.
   *
   * Return value: the integer value of the determinant in modulo m.
   */
  public static long cofModDet(long m, long[][] A) {
    long determinant = 0L;
    long sign = 1L;
    long determinantInModulo = 0L;
    if (A.length == 1 && A[0].length == 1) {
      determinant = A[0][0];
    }
    else if (A.length == 2 && A[0].length == 2) {
      determinant = A[0][0]*A[1][1] - A[1][0]*A[0][1];
    }
    else {
      for(int i = 0; i < A.length; i++) { 
        // values of A after removing A[i] and A[j]
        long[][] submatrix = new long[A.length-1][A.length-1]; 
        for(int a = 1; a < A.length; a++){
          for(int b = 0; b < A.length; b++){
            if(b < i) {
              submatrix[a-1][b] = A[a][b] % m;
            }
            else if(b > i){
              submatrix[a-1][b-1] = A[a][b] % m;
            }
          }
        }
        // odd numbered values of i in a[0][i] get a negative sign
        if(i % 2 != 0) { 
          sign = -1;
        }
        else {
          sign = 1;
        }
        determinant += (sign * (A[0][i])) * (cofModDet(m, submatrix));
      }
    }
    determinantInModulo = determinant % m;
    if (determinantInModulo < 0)
      determinantInModulo += m;
    return determinantInModulo;
  }

}