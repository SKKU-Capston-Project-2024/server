package site.mutopia.server.global.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StringUtil {
    public static String unixTimeToString(Long unixTime) {
        Date date = new Date(unixTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return sdf.format(date);
    }
}
