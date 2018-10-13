package com.lunf.lois;

import com.lunf.lois.utilities.PasswordHelper;
import com.lunf.lois.utilities.RandomTokenGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        System.out.println(alphaAndDigits.substring(alphaAndDigits.length() - 64,alphaAndDigits.length()));

        RandomTokenGenerator session = new RandomTokenGenerator();
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

        String computed_hash = PasswordHelper.hashPassword(test_passwd);
        System.out.println("Test computed hash: " + computed_hash);
        System.out.println();
        System.out.println("Verifying that hash and stored hash both match for the test password...");
        System.out.println();

//        String compare_test = checkPassword(test_passwd, test_hash)
//                ? "Passwords Match" : "Passwords do not match";
        String compare_computed = PasswordHelper.checkPassword(test_passwd, computed_hash)
                ? "Passwords Match" : "Passwords do not match";

        //System.out.println("Verify against stored hash:   " + compare_test);
        System.out.println("Verify against computed hash: " + compare_computed);

    }




}
