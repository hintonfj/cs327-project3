import java.math.BigInteger;


class AndrewOliviaHintonFaithHillCipher{

    /**
     * Calculate the decryption key from a given encryption key. 
     * EncryptedKey is a given 2 x 2 matrix-based encryption key. 
     */
    public int[][] findDecryptionKey(int[][] encryptionKey){
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
     * Encrypt a given multiple-letter plaintext message with a given 2 × 2 encryption key.
     */
    public static int[] encrypt(int[] plaintext, int[][] encryptionKey){
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
     * Convert string [] to int[].
     */
    public static int[] stringToIntArray(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        int[] arr = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            arr[i] = text.charAt(i) - 'A';
        }
        return arr;
    }

    /**
     * Convert int[] to string[].
     */
    public static String intArrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i : arr) sb.append((char) (i + 'A'));
        return sb.toString();
    }

    /**
     * Decrypt a given multiple-letter ciphertext with a given 2 × 2 decryption key. 
     */
    public static int[] decrypt(int[] ciphertext, int[][] decryptionKey){
        // TODO
        return null;
    }
    public static void main (String[] args) {
        /**
         * You must test your above functionalities through the following example:
         * Encryption key: 16, 7, 9, 14 
         * You must print out the corresponding decryption key to the console (3 points).
         *  Plaintext JMUCSISCOOL. Encrypt it with your encrypt() method. Letters are mapped to integers as A → 0, B1,..., Z→ 25.
         * You must print out the ciphertext in letters, not in integers.
         *  For ciphertext MQGVGQSMJI, decrypt it with your decrypt() method.
         * Please note that this is a new ciphertext and it is not the one that you have generated earlier. You must print out the corresponding cleartext in letters, not in integers.
         */
        int[][] encryptionKey = {{16, 7}, {9, 14}};

        //encrypt
        String plaintextStr = "JMUCSISCOOL";
        int[] plaintext = stringToIntArray(plaintextStr);
        int[] ciphertext = encrypt(plaintext, encryptionKey);
        System.out.println("Encrypted plaintext: " + intArrayToString(ciphertext));


        //decrypt
        // TODO
        String ciphertextStr = "MQGVGQSMJI";
        System.out.println("Decrypted text: ");
    }

}