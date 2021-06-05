package ro.eu.passwallet.service.crypt;

public final class CryptographyService {
    private final AESHelper aesHelper = new AESHelper();
    private final String password;

    public CryptographyService(String password) {
        this.password = password;
    }

    public byte[] encrypt(byte[] stringToEncrypt) {
        synchronized (aesHelper) {
            return aesHelper.encrypt(stringToEncrypt, password);
        }
    }

    public byte[] decrypt(byte[] stringToDecrypt) {
        synchronized (aesHelper) {
            return aesHelper.decrypt(stringToDecrypt, password);
        }
    }
}
