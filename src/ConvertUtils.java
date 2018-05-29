public class ConvertUtils {

    public static String bytes2HexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & 0xff) | 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String bytesToHexString(byte[] data){
        StringBuilder stringBuilder = new StringBuilder();
        if (data == null || data.length <= 0) {
            return null;
        }
        for (int i = 0; i < data.length; i++) {
            int v = data[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
