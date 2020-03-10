/**
 * file: AESCipher.java
 * author: Brandon Litwin
 * course: MSCS 630
 * assignment: Lab 5
 * due date: 3/15/2020
 * version: 1.1
 * <p>
 * This file contains the AESCipher class which is called by Driver_lab5.
 */

/**
 * AESCipher
 *
 * This class contains a cipher that will encrypt a plaintext according to AES.
 * It contains the following methods: aesRoundKeys AES aesSBox aesRcon
 * AESStateXOR AESNibbleSub AESShiftRow AESMixColumn
 */
public class AESCipher {
  // Number of rows and columns in the square matrix.
  public static final int MATRIX_SIZE = 4;
  public static final int W_MATRIX_COLS = 44;
  // The sbox values
  public static final int[][] sbox = {
      { 0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76 },
      { 0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0 },
      { 0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15 },
      { 0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75 },
      { 0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84 },
      { 0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf },
      { 0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8 },
      { 0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2 },
      { 0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73 },
      { 0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb },
      { 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79 },
      { 0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08 },
      { 0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a },
      { 0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e },
      { 0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf },
      { 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16 } };
  // The Rcon values
  public static final int[] rcon = { 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab,
      0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72,
      0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d,
      0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
      0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
      0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
      0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
      0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
      0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
      0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
      0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb,
      0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc,
      0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2,
      0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb };

  /**
   * aesRoundKeys
   *
   * This method reads a system key hex and follows the algorithm to generate
   * round keys for 11 rounds.
   *
   *
   * Parameters: keyHex: The input system key, a 16 uppercase char String
   * keyHexMatrix: The keyHex converted into a 4x4 matrix wMatrix: The 4x44 matrix
   * for w, which is needed for encryption substringVal: The current placeholder
   * for the substring of the keyHex round: The round number roundKey: The key of
   * this round to add to roundKeys col: The current col of the matrix that is
   * being operated on row: The current row of the matrix that is being operated
   * on wNew: A String array containing the col to add to wMatrix, which is the
   * result of getting the s-box value on the left-shifted original value of that
   * position in wMatrix rCon: The round constant from the aesRcon method
   * wMatrixToHex: A String array containing the wMatrix vals converted to hex
   *
   * Return value: roundKeys, a String array containing the 11 round keys
   */
  public static String[] aesRoundKeys(String keyHex) {
    int[][] keyHexMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    int[][] wMatrix = new int[MATRIX_SIZE][W_MATRIX_COLS];
    String[] roundKeys = new String[11];
    int substringVal = 0;
    int round = 0;
    String roundKey = "";
    // Convert the keyHex into a matrix containing each hex value
    for (int row = 0; row < MATRIX_SIZE; row++) {
      for (int col = 0; col < MATRIX_SIZE; col++) {
        keyHexMatrix[col][row] = Integer.parseInt(keyHex.substring(substringVal, substringVal + 2), 16);
        substringVal += 2;
      }
    }
    // The main loop to construct w
    for (int col = 0; col < W_MATRIX_COLS; col++) {
      for (int row = 0; row < MATRIX_SIZE; row++) {
        // Make the first 4 cols of w the same as k
        if (col < 4) {
          wMatrix[row][col] = keyHexMatrix[row][col];
          if (Integer.toHexString(wMatrix[row][col]).length() == 1) {
            roundKey += "0" + Integer.toHexString(wMatrix[row][col]);
          } else {
            roundKey += Integer.toHexString(wMatrix[row][col]);
          }
        } else {
          if (col % 4 != 0) {
            // Do XOR
            wMatrix[row][col] = wMatrix[row][col - 4] ^ wMatrix[row][col - 1];
            if (Integer.toHexString(wMatrix[row][col]).length() == 1) {
              roundKey += "0" + Integer.toHexString(wMatrix[row][col]);
            } else {
              roundKey += Integer.toHexString(wMatrix[row][col]);
            }
          } else {
            if (row == 0) {
              // new round is starting so add the current roundKey to the final
              // list
              roundKeys[round] = roundKey.toUpperCase();
              roundKey = "";
              round++;
              // fill wNew with vals of prev w column, shift to left, and
              // transform using sbox
              int[] wNew = { aesSBox(wMatrix[1][col - 1]), aesSBox(wMatrix[2][col - 1]), aesSBox(wMatrix[3][col - 1]),
                  aesSBox(wMatrix[0][col - 1]) };
              int rCon = aesRcon(round);
              // XOR with the round constant
              wNew[0] = rCon ^ wNew[0];
              // final XOR
              wMatrix[0][col] = wMatrix[0][col - 4] ^ wNew[0];
              wMatrix[1][col] = wMatrix[1][col - 4] ^ wNew[1];
              wMatrix[2][col] = wMatrix[2][col - 4] ^ wNew[2];
              wMatrix[3][col] = wMatrix[3][col - 4] ^ wNew[3];
              String[] wMatrixToHex = new String[4];
              wMatrixToHex[0] = Integer.toHexString(wMatrix[0][col]);
              wMatrixToHex[1] = Integer.toHexString(wMatrix[1][col]);
              wMatrixToHex[2] = Integer.toHexString(wMatrix[2][col]);
              wMatrixToHex[3] = Integer.toHexString(wMatrix[3][col]);
              // pad the single digit hex values with a leading 0
              if (wMatrixToHex[0].length() == 1) {
                wMatrixToHex[0] = "0" + wMatrixToHex[0];
              }
              if (wMatrixToHex[1].length() == 1) {
                wMatrixToHex[1] = "0" + wMatrixToHex[1];
              }
              if (wMatrixToHex[2].length() == 1) {
                wMatrixToHex[2] = "0" + wMatrixToHex[2];
              }
              if (wMatrixToHex[3].length() == 1) {
                wMatrixToHex[3] = "0" + wMatrixToHex[3];
              }

              // add new column of vals to roundKey
              roundKey = roundKey + wMatrixToHex[0] + wMatrixToHex[1] + wMatrixToHex[2] + wMatrixToHex[3];

            }

          }

        }
      }
    }
    // Store the last round key
    roundKeys[round] = roundKey.toUpperCase();
    return roundKeys;

  }

  /**
   * aesSBox
   *
   * This method will retrieve the S-box substitution value for the given hex
   * value.
   *
   * Parameters: inHex: The input hex code as a String inHexToString: The
   * HexString representation of the input integer sBoxRow: The row to lookup in
   * the s-box table sBoxCol: The col to lookup in the s-box table
   *
   * Return value: the output hex code from the s-box table
   */
  public static int aesSBox(int inHex) {
    // split inHex into its two hex values
    String inHexToString = Integer.toHexString(inHex);
    // put a 0 in front of 1 char hex to make it 2 chars
    if (inHexToString.length() == 1) {
      inHexToString = "0" + inHexToString;
    }
    int sBoxRow = Integer.parseInt(String.valueOf(inHexToString.charAt(0)), 16);
    int sBoxCol = Integer.parseInt(String.valueOf(inHexToString.charAt(1)), 16);
    return sbox[sBoxRow][sBoxCol];
  }

  /**
   * aesRcon
   *
   * This method will retrieve the round constant from the rcon table (which is an
   * int array) for the given round number.
   *
   * Parameters: round: The round number
   *
   * Return value: the constant from the Rcon table
   */
  public static int aesRcon(int round) {
    return rcon[round];
  }

  /**
   * AES
   *
   * This method produces a ciphertext from a given plaintext and system key using
   * the AES algorithm.
   *
   * Parameters: pTextHex: The plaintext String, which can be converted to hex
   * keyHex: The system key as a hex String
   *
   * Return value: a String containing the resulting ciphertext
   */
  public static String AES(String pTextHex, String keyHex) {
    String[] roundKeys = aesRoundKeys(keyHex);
    String cipherText = "";
    int[][] pTextMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    int[][] XORRoundKey = null;
    int[][] nibbleSubstitutionKey = null;
    int[][] shiftRows = null;
    int[][] mixColumns = null;
    int substringVal = 0;
    // Convert pTextHex to matrix
    for (int i = 0; i < MATRIX_SIZE; i++) {
      for (int j = 0; j < MATRIX_SIZE; j++) {
        pTextMatrix[j][i] = Integer.parseInt(pTextHex.substring(substringVal, substringVal + 2), 16);
        substringVal += 2;
      }
    }
    for (int i = 0; i < roundKeys.length; i++) {
      XORRoundKey = AESStateXOR(pTextMatrix, roundKeys[i]);
      nibbleSubstitutionKey = AESNibbleSub(XORRoundKey);
      shiftRows = AESShiftRow(nibbleSubstitutionKey);
      mixColumns = AESMixColumn(shiftRows);
    }
    // Convert final hex matrix to String
    for (int i = 0; i < XORRoundKey.length; i++) {
      for (int j = 0; j < XORRoundKey.length; j++) {
        if (Integer.toHexString(XORRoundKey[i][j]).length() == 1) {
          cipherText += "0" + Integer.toHexString(XORRoundKey[i][j]);
        } else {
          cipherText += Integer.toHexString(XORRoundKey[i][j]);
        }
      }
    }

    return cipherText.trim().toUpperCase();
  }

  /**
   * AESStateXOR
   *
   * This method performs an XOR operation on two given Strings and returns a 4x4
   * matrix.
   *
   * Parameters: sHex: A 4x4 matrix containing the plaintext hex keyHex: A String
   * containing the current round key hex
   *
   * Return value: an array containing the resulting 4x4 matrix
   *
   */
  public static int[][] AESStateXOR(int[][] sHex, String keyHex) {
    int substringVal = 0;
    int[][] XORMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    for (int i = 0; i < MATRIX_SIZE; i++) {
      for (int j = 0; j < MATRIX_SIZE; j++) {
        XORMatrix[j][i] = sHex[j][i] ^ Integer.parseInt(keyHex.substring(substringVal, substringVal + 2), 16);
        substringVal += 2;
      }
    }
    return XORMatrix;
  }

  /**
   * AESNibbleSub
   *
   * This method calls the aesSBox method to perform substitution on the given hex
   * matrix.
   *
   * Parameters: inStateHex: A 4x4 matrix containing the current hex
   *
   * Return value: an array containing the resulting 4x4 matrix
   *
   */
  public static int[][] AESNibbleSub(int[][] inStateHex) {
    int[][] nibbleSubMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    for (int i = 0; i < inStateHex.length; i++) {
      for (int j = 0; j < inStateHex.length; j++) {
        nibbleSubMatrix[i][j] = aesSBox(inStateHex[i][j]);
      }
    }
    return nibbleSubMatrix;
  }

  /**
   * AESShiftRow
   *
   * This method performs the row shift step of the AES algorithm for the given
   * hex matrix
   *
   * Parameters: inStateHex: A 4x4 matrix containing the current hex
   *
   * Return value: an array containing the resulting 4x4 matrix
   */
  public static int[][] AESShiftRow(int[][] inStateHex) {
    int[][] shiftRowMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    for (int i = 0; i < inStateHex.length; i++) {
      for (int j = 0; j < inStateHex.length; j++) {
        if (i == 0) {
          shiftRowMatrix[i][j] = inStateHex[i][j];
        } else if (i == 1) {
          shiftRowMatrix[i][j] = inStateHex[i][(j + 1) % MATRIX_SIZE];
        } else if (i == 2) {
          shiftRowMatrix[i][j] = inStateHex[i][(j + 2) % MATRIX_SIZE];
        } else {
          shiftRowMatrix[i][j] = inStateHex[i][(j + 3) % MATRIX_SIZE];
        }
      }

    }
    return shiftRowMatrix;
  }

  /**
   * AESMixColumn
   *
   * This method performs the mix column step of the AES algorithm for the given
   * hex matrix
   *
   * Parameters: inStateHex: A 4x4 matrix containing the current hex
   *
   * Return value: an array containing the resulting 4x4 matrix
   */
  public static int[][] AESMixColumn(int[][] inStateHex) {
    return null;
  }

}