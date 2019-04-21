package SecureSocketChipV1.module;

import SecureSocketChipV1.SSCV1;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptionManager {

    private KeyPair chipKeyPair;
    private PublicKey serverPublic;
    private SSCV1 main;
    public EncryptionManager(SSCV1 main){
        this.main = main;
        try {
            chipKeyPair = generate(2048);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String encrypt(String a, PublicKey key) throws Exception {
        byte[] data = a.getBytes();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    static String decrypt(String a, PrivateKey key) throws Exception {
        byte[] data = Base64.getDecoder().decode(a);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(data), "utf-8");
    }

    public KeyPair generate(int bits) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(bits,
                RSAKeyGenParameterSpec.F4);
        keygen.initialize(spec);
        return keygen.generateKeyPair();
    }

    public String exportPublic() throws Exception {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(chipKeyPair.getPublic().getEncoded());
        return new String(Base64.getEncoder().encode(publicSpec.getEncoded()), "utf-8");
    }

    public String exportPrivate() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(chipKeyPair.getPrivate().getEncoded());
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        return new String(Base64.getEncoder().encode(privateKey.getEncoded()), "utf-8");
    }

    public PublicKey importPublic(String key) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
        return keyFactory.generatePublic(publicKeySpec);
    }

    public void setServerPublic(PublicKey serverPublic) {
        this.serverPublic = serverPublic;
    }

    public String encryptMessage(String raw){
        if(serverPublic == null) return null;
        try {
            return encrypt(raw, serverPublic);
        } catch (Exception e) {
            return null;
        }
    }

    public String decryptMessage(String raw){
        if(chipKeyPair == null) return null;
        try {
            return decrypt(raw, chipKeyPair.getPrivate());
        } catch (Exception e) {
            return null;
        }
    }

}
