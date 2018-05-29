import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {


    /**
     *
     * @param path  path must be not null
     */
    public static void deleteSameFile(String path) throws IOException {
        File root = new File(path);
        Set<String> md5Set = new HashSet<>();
        if (root.exists() && root.isDirectory()) {
            File[] files = root.listFiles();
            if (files == null || files.length <= 0) {
                return;
            }
            for (File f :
                    files) {
                if (f.isFile()) {
                    System.out.println("generate md5 : " + f.getCanonicalPath());
                    if (!md5Set.add(VerifyUtils.fileChecksumWithMD5(f.getAbsolutePath()))) {
                        System.out.println("delete the same file " + f.getCanonicalPath());
                        f.delete();
                    }
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        deleteSameFile("./src");
    }
}
