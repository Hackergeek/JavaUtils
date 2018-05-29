import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class VerifyUtils {
    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA1";

    public static String fileChecksum(String path, String algorithm) {
        if (path == null) {
            return null;
        }
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            fis = new FileInputStream(path);

            byte[] dataBytes = new byte[1024];
            int nRead = -1;
            while ((nRead = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nRead);
            }
            byte[] mdBytes = md.digest();
            return ConvertUtils.bytes2HexString(mdBytes);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.close(fis);
        }
        return null;
    }

    public static String stringChecksum(String string, String algorithm) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(string.getBytes());

        byte mdBytes[] = md.digest();
        return ConvertUtils.bytes2HexString(mdBytes);
    }

    public static String fileChecksumWithSHA1(String path) {
        return fileChecksum(path, SHA1);
    }

    public static String stringChecksumWithSHA1(String string) {
        return stringChecksum(string, SHA1);
    }

    public static String fileChecksumWithMD5(String path) {
        return fileChecksum(path, MD5);
    }

    public static String stringChecksumWithMD5(String string) {
        return stringChecksum(string, MD5);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(fileChecksumWithMD5("src/VerifyUtils.java"));
        System.out.println(fileChecksumWithSHA1("src/VerifyUtils.java"));
    }
}
