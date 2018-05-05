package com.lunf.delilah;

import com.lunf.delilah.utilities.RandomString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

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


    }


}
