
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileUtils {

    /**
     * 删除指定文件夹下的指定文件
     * @param dir	指定文件夹
     * @param filterName 要保留文件的扩展名
     */
    public static void deleteNoNeedFile(String dir, String filterName) {
        File file = new File(dir);
        if(file.isDirectory()) {
            File[] allFiles = file.listFiles();
            if(allFiles.length <= 0) {
                file.delete();
                return;
            }
            for(File f : allFiles) {
                if(!f.isDirectory()) {
                    if(!f.getName().endsWith(filterName)) {
                        f.delete();
                    }
                } else {
                    deleteNoNeedFile(f.getAbsolutePath(), filterName);
                }
            }
        } else {
            System.out.println("请选择一个目录");
            return;
        }
    }

    /**
     * 判断指定路径下是否存在指定文件
     * @param fileName
     * @param path
     * @return
     */
    public static boolean contain(String fileName, String path) {
        if (Utils.isNull(fileName) || Utils.isNull(path)) {
            throw new IllegalArgumentException("Argument can not be null!");
        }
        File pathFile = new File(path);
        File file = new File(fileName);
        File[] files = pathFile.listFiles();
        if (Utils.isNotNull(files)) {
            for (File f :
                    files) {
                if (f.getName().equals(file.getName()) && f.length() == file.length()) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 删除指定目录下的相同文件
     * 如果两个文件的MD5值相同，则认定这两个文件相同
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
