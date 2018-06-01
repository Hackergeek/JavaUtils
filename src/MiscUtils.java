import java.io.*;

public class MiscUtils {
    private static void generateSoftwareInfo(String path) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        BufferedWriter bw = null;
        BufferedReader in = null;
        try {
            process = runtime
                    .exec("cmd /c reg query HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\");
            in = new BufferedReader(new InputStreamReader(
                    process.getInputStream(), "GBK"));
            String line = null;
            bw = new BufferedWriter(new FileWriter(path, true));
            while ((line = in.readLine()) != null) {
                String[] message = queryValue(line);
                if (message != null) {
                    for (int i = 0; i < message.length; i++) {
                        if (message[i] != null) {
                            bw.write(message[i]);
                        }
                    }
                    bw.write("\n");
                    bw.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.close(in);
            CloseUtils.close(bw);
            process.destroy();
        }
    }

    // 具体查询每一个软件的详细信息
    private static String[] queryValue(String string) throws IOException {
        String nameString = "";
        String versionString = "";

        String publisherString = "";
        String uninstallPathString = "";

        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        BufferedReader br = null;

        process = runtime
                .exec("cmd /c reg query " + string + " /v DisplayName");
        br = new BufferedReader(new InputStreamReader(process.getInputStream(),
                "GBK"));
        br.readLine();
        br.readLine();// 去掉前两行无用信息
        if ((nameString = br.readLine()) != null) {
            nameString = nameString.replaceAll("DisplayName    REG_SZ    ", ""); // 去掉无用信息
        }

        process = runtime.exec("cmd /c reg query " + string
                + " /v DisplayVersion");
        br = new BufferedReader(new InputStreamReader(process.getInputStream(),
                "GBK"));
        br.readLine();
        br.readLine();// 去掉前两行无用信息
        if ((versionString = br.readLine()) != null) {
            versionString = versionString.replaceAll(
                    "DisplayVersion    REG_SZ    ", ""); // 去掉无用信息
        }

        process = runtime.exec("cmd /c reg query " + string + " /v Publisher");
        br = new BufferedReader(new InputStreamReader(process.getInputStream(),
                "GBK"));
        br.readLine();
        br.readLine();// 去掉前两行无用信息
        if ((publisherString = br.readLine()) != null) {
            publisherString = publisherString.replaceAll(
                    "Publisher    REG_SZ    ", ""); // 去掉无用信息
        }

        process = runtime.exec("cmd /c reg query " + string
                + " /v UninstallString");
        br = new BufferedReader(new InputStreamReader(process.getInputStream(),
                "GBK"));
        br.readLine();
        br.readLine();// 去掉前两行无用信息
        if ((uninstallPathString = br.readLine()) != null) {
            uninstallPathString = uninstallPathString.replaceAll(
                    "UninstallString    REG_SZ    ", ""); // 去掉无用信息
        }

        String[] resultString = new String[4];
        resultString[0] = nameString;
        resultString[1] = versionString;
        resultString[2] = publisherString;
        resultString[3] = uninstallPathString;
        if (resultString[0] == null)
            resultString = null;
        return resultString;
    }

    public static void main(String[] args) throws Exception {
        generateSoftwareInfo("本系统已经安装的软件列表.txt");
    }
}
