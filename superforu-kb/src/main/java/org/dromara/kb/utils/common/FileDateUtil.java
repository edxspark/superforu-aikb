package org.dromara.kb.utils.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FileDateUtil {

    /**
     * 时间处理 今天、昨天、其他
     * @author moks.mo
     * */
    public static String toEditDate(Date dateTime ){

        String targetDate = new SimpleDateFormat("yy-MM-dd").format(dateTime);

        // 今天
        String today = new SimpleDateFormat("yy-MM-dd").format(new Date());
        if(today.equals(targetDate)){
            return "今天 " +new SimpleDateFormat("HH时mm分").format(dateTime);
        }

        // 昨天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        String yesterday = new SimpleDateFormat("yy-MM-dd").format(calendar.getTime());
        if(targetDate.equals(yesterday)){
            return "昨天 " +new SimpleDateFormat("HH时mm分").format(dateTime);
        }

        // 其他
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH时mm分");
        return sdf.format(dateTime);
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        System.out.println(toEditDate(sdf.parse("2023-12-22 11:12:10")));
    }
}
