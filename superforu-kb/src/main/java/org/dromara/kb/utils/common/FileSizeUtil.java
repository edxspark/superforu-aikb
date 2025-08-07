package org.dromara.kb.utils.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FileSizeUtil {

    /**
     * GB to B
     *
     * @param gb GB
     * */
    public static long gbToB(long gb) {
        return gb * 1024 * 1024 * 1024;
    }

    /**
     * 文件字节转B KB MB GB TB
     * @param b B
     */
    public static String bToString(long b) {
        if (b <= 0) return "0B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(b) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(b / Math.pow(1024, digitGroups)) + units[digitGroups];
    }

    /**
     * 文件字节转GB，小于0.001GB返回0.001
     *
     * @param bytes Bytes
     */
    public static BigDecimal bytesToGb(long bytes) {
        BigDecimal bigDecimal = new BigDecimal(bytes);
        BigDecimal divisorDecimal = new BigDecimal(1024*1024*1024);
        BigDecimal resultDecimal = bigDecimal.divide(divisorDecimal,3, RoundingMode.HALF_UP);
        BigDecimal miniDecimal = new BigDecimal("0.001");
        if(resultDecimal.compareTo(miniDecimal)<0){
            return miniDecimal;
        }else{
            return bigDecimal.divide(divisorDecimal,3, RoundingMode.HALF_UP);
        }
    }

    /**
     * 文件字节转GB，小于0.001GB返回0.001
     *
     * @param bytes Bytes
     */
    public static BigDecimal bytesToGb(long bytes,int scale) {
        BigDecimal bigDecimal = new BigDecimal(bytes);
        BigDecimal divisorDecimal = new BigDecimal(1024*1024*1024);
        BigDecimal resultDecimal = bigDecimal.divide(divisorDecimal,scale, RoundingMode.HALF_UP);
        BigDecimal miniDecimal = new BigDecimal("0.001");
        if(resultDecimal.compareTo(miniDecimal)<0){
            return miniDecimal;
        }else{
            return bigDecimal.divide(divisorDecimal,scale, RoundingMode.HALF_UP);
        }
    }

    /**
     * 文件字节转GB，小于0.001GB返回0.001
     *
     * @param current 当前GB
     * @param max 最大GB
     */
    public static BigDecimal percent(String current,String max){
        BigDecimal bigDecimal = new BigDecimal(current);
        BigDecimal divisorDecimal = new BigDecimal(max);
        BigDecimal resultDecimal = bigDecimal.divide(divisorDecimal,3, RoundingMode.HALF_UP);
        BigDecimal miniDecimal = new BigDecimal("0.01");
        if(resultDecimal.compareTo(miniDecimal)<0){
            return miniDecimal;
        }else{
            return bigDecimal.divide(divisorDecimal,2, RoundingMode.HALF_UP);
        }
    }

    /**
     * 文件内容计算
     * @param context 文件内容
     */
    public static long contextSize(String context) {
        return context.length();
    }

    public static void main(String[] args) {
        System.out.println(percent("1","5"));
    }

}
