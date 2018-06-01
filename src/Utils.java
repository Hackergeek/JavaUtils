import java.io.IOException;

public class Utils {




    public static void executeCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("cmd /c" + command);
            process.waitFor();
            if (process.exitValue() != 0) {
                System.out.println("execute command failed : " + command);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.close(process.getErrorStream());
            CloseUtils.close(process.getInputStream());
            CloseUtils.close(process.getOutputStream());
            process.destroy();
        }
    }

    public static boolean isNotNull(Object object) {
        return object != null;
    }

    public static boolean isNull(Object object) {
        return object == null;
    }



    public static void executeBadCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("cmd /c" + command);
            process.waitFor();
            if (process.exitValue() != 0) {
                System.out.println("execute command failed : " + command);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }




    public static void main(String[] args) {
        while(true) {
            executeBadCommand("dir");
        }


    }
}
