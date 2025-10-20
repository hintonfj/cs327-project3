package project3;

import java.math.BigInteger;

/**
 * Utilize Hill Cipher to encrypt and decrypt.
 * 
 * @author: Olivia Andrew
 * @author: Faith Hinton
 * @date: 10/20/25
 * “This work complies with the JMU honor code. I did not give or receive unauthorized help on this assignment.”
 * 
 */
class AndrewOliviaHintonFaithHillCipher
{

	/**
	 * Calculate the decryption key from a given encryption key. 
	 * 
	 * @param encryptionKey -- 2x2 matrix-based encryption key
	 * @return int[][] -- resulting decryption key
	 */
    public static int[][] findDecryptionKey(int[][] encryptionKey)
    {
        // 2x2 matrix
        int a = encryptionKey[0][0];
        int b = encryptionKey[0][1];
        int c = encryptionKey[1][0];
        int d = encryptionKey[1][1];

        int det = (a * d - b * c) % 26;
        if (det < 0) det += 26;

        // using BigInt for modular inverse
        BigInteger detInv = BigInteger.valueOf(det).modInverse(BigInteger.valueOf(26));

        int[][] decryptionKey = new int[2][2];
        decryptionKey[0][0] = detInv.intValue() * d % 26;
        decryptionKey[0][1] = detInv.intValue() * (-b + 26) % 26;
        decryptionKey[1][0] = detInv.intValue() * (-c + 26) % 26;
        decryptionKey[1][1] = detInv.intValue() * a % 26;

        // making sure all values are positive
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (decryptionKey[i][j] < 0) decryptionKey[i][j] += 26;
            }
        }
        return decryptionKey;
    }

    /**
     * Print the contents of a 2x2 matrix to console.
     * 
     * @param matrix2x2 - int[][]
     */
    public static void print2x2Matrix(int[][] matrix) 
    {
    	for (int i = 0; i < matrix.length; i++) {
    		// print one row at a time
    		System.out.print("[");
    		for (int j = 0; j < matrix[i].length; j++) {
    			System.out.print(matrix[i][j]);
    			if (j != matrix[i].length - 1) {
    				System.out.print(", ");
    			}
    		}
    		System.out.println("]");
    	}
    }
    
    
    /**
     * Encrypt a given multiple-letter plaintext message using a given 2 × 2 encryption key.
     * 
     * @param plaintext
     * @param encryptionKey
     * @return int[] -- integer version of ciphertext
     */
    public static int[] encrypt(int[] plaintext, int[][] encryptionKey)
    {
        int n = plaintext.length;
        int[] encryptedText = new int[n];

        for(int i = 0; i < n; i += 2){
            int p1 = plaintext[i];
            int p2 = (i + 1 < n) ? plaintext[i + 1] : 0;

            encryptedText[i] = (encryptionKey[0][0] * p1 + encryptionKey[0][1] * p2) % 26;
            // if length is odd
            if (i + 1 < n) {
                encryptedText[i + 1] = (encryptionKey[1][0] * p1 + encryptionKey[1][1] * p2) % 26;
            }
        }

        return encryptedText;
    }

    /**
     * Convert string to int[].
     * 
     * @param text
     * @return integer array version of String text
     */
    public static int[] stringToIntArray(String text) 
    {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        int[] arr = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            arr[i] = text.charAt(i) - 'A';
        }
        return arr;
    }

    /**
     * Convert int[] to string[].
     * 
     * @param arr -- int[]
     * @return String version of integer array
     */
    public static String intArrayToString(int[] arr) 
    {
        StringBuilder sb = new StringBuilder();
        for (int i : arr) sb.append((char) (i + 'A'));
        return sb.toString();
    }

    /**
     * Decrypt a given multiple-letter ciphertext using the accquired 2x2 decryption key.
     * 
     * @param ciphertext
     * @param decryptionKey
     * @return int[] -- integer version of plaintext
     */
    public static int[] decrypt(int[] ciphertext, int[][] decryptionKey)
    {
    	int numChars = ciphertext.length;
    	int[] decryptedText = new int[numChars];
    	
    	// decrypt in pairs
    	for (int i = 0; i < numChars; i += 2) {
    		// get current pair (c1 and c2)
    		int c1 = ciphertext[i];
    		int c2 = ciphertext[i + 1];
    		
    		// compute each pair
    		decryptedText[i] = (decryptionKey[0][0] * c1 + decryptionKey[0][1] * c2) % 26;
    		decryptedText[i + 1] = (decryptionKey[1][0] * c1 + decryptionKey[1][1] * c2) % 26;
    	}
    	return decryptedText;
    }
    
    /**
     * Print results of required unit / integration tests here.
     * 
     * @param args
     */
    public static void main (String[] args) 
    {
    	System.out.println("Project 3: Hill Cipher");
    	
        int[][] encryptionKey = {{16, 7}, {9, 14}};

        // A) decryptionKey
        System.out.println("\n 3.3.1 A) Testing findDecryptionKey...");
        int[][] decryptionKey = findDecryptionKey(encryptionKey);
        System.out.println("Given encryption key: ");
        print2x2Matrix(encryptionKey);
        System.out.println("Decryption key: ");
        print2x2Matrix(decryptionKey);
        
        // B) encrypt
        System.out.println("\n 3.3.1 B) Testing encrypt...");
        System.out.println("Given plaintext = JMUCSISCOOL");
        String plaintextStr = "JMUCSISCOOL";
        int[] plaintext1 = stringToIntArray(plaintextStr);
        int[] ciphertext1 = encrypt(plaintext1, encryptionKey);
        System.out.println("Encrypted plaintext: " + intArrayToString(ciphertext1));


        // C) decrypt
        System.out.println("\n 3.3.1 C) Testing decrypt...");
        System.out.println("Given ciphertext = MQGVGQSMJI");
        String ciphertextStr = "MQGVGQSMJI";
        int[] ciphertext2 = stringToIntArray(ciphertextStr);
        int[] plaintext2 = decrypt(ciphertext2, decryptionKey);
        System.out.println("Decrypted text: " + intArrayToString(plaintext2));
    }

}