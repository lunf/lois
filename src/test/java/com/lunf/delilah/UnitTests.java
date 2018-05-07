package com.lunf.delilah;

import com.lunf.delilah.utilities.RandomString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.security.*;
import java.util.Base64;

@RunWith(SpringRunner.class)
public class UnitTests {

    @Test
    public void randomNumberTest() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();

        Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");

        dsa.initSign(priv);
        dsa.update("doe@domain.com".getBytes(Charset.defaultCharset()));

        byte[] realSig = dsa.sign();
        String realSigString = Base64.getEncoder().encodeToString(realSig);

        String randomString = Base64.getEncoder().encodeToString(pub.getEncoded());

        String alphaAndDigits = randomString.replaceAll("[^a-zA-Z0-9]+","");
        System.out.println("-----------------------");
        System.out.println(randomString);
        System.out.println("-----------------------");
        System.out.println(alphaAndDigits);
        System.out.println("-----------------------");
        System.out.println(alphaAndDigits.substring(alphaAndDigits.length() - 32,alphaAndDigits.length()));

        RandomString session = new RandomString();
        System.out.println("-----------------------");
        System.out.println(session.nextString());
        System.out.println("-----------------------");
        System.out.println(realSigString);

    }

    @Test
    public void bcryptPassTest() throws Exception {
        String test_passwd = "abcdefghijklmnopqrstuvwxyz";
        //String test_hash = "$2a$06$.rCVZVOThsIa97pEDOxvGuRRgzG64bvtJ0938xuqzv18d3ZpQhstC";

        System.out.println("Testing BCrypt Password hashing and verification");
        System.out.println("Test password: " + test_passwd);
        //System.out.println("Test stored hash: " + test_hash);
        System.out.println("Hashing test password...");
        System.out.println();

        String computed_hash = hashPassword(test_passwd);
        System.out.println("Test computed hash: " + computed_hash);
        System.out.println();
        System.out.println("Verifying that hash and stored hash both match for the test password...");
        System.out.println();

//        String compare_test = checkPassword(test_passwd, test_hash)
//                ? "Passwords Match" : "Passwords do not match";
        String compare_computed = checkPassword(test_passwd, computed_hash)
                ? "Passwords Match" : "Passwords do not match";

        //System.out.println("Verify against stored hash:   " + compare_test);
        System.out.println("Verify against computed hash: " + compare_computed);

    }

    // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
    private static int workload = 10;

    /**
     * This method can be used to generate a string representing an account password
     * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
     * hash string of length=60
     * The bcrypt workload is specified in the above static variable, a value from 10 to 31.
     * A workload of 12 is a very reasonable safe default as of 2013.
     * This automatically handles secure 128-bit salt generation and storage within the hash.
     * @param password_plaintext The account's plaintext password as provided during account creation,
     *			     or when changing an account's password.
     * @return String - a string of length 60 that is the bcrypt hashed password in crypt(3) format.
     */
    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }

    /**
     * This method can be used to verify a computed hash from a plaintext (e.g. during a login
     * request) with that of a stored hash from a database. The password hash from the database
     * must be passed as the second variable.
     * @param password_plaintext The account's plaintext password, as provided during a login request
     * @param stored_hash The account's stored password hash, retrieved from the authorization database
     * @return boolean - true if the password matches the password of the stored hash, false otherwise
     */
    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }


}
