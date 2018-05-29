import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class VerifyUtils {

    public static String fileChecksumWithMD5(String path) {
        if (path == null) {
            return null;
        }
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
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

    public static void main(String[] args) throws IOException {
        System.out.println(fileChecksumWithMD5("src/VerifyUtils.java"));
    }
}
