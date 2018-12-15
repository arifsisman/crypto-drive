package Crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class Provider {
    public static void addProvider(){
        Security.addProvider(new BouncyCastleProvider());
    }
}
