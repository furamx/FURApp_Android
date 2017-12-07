package fura.com.furapp_android.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ramon on 07/12/17.
 */

public class StringFormater {
    public static String ToDate(String longFormatDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = dateFormat.parse(longFormatDate);
        dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        return dateFormat.format(date);
    }
    public static String ToHour(String longFormatDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = dateFormat.parse(longFormatDate);
        dateFormat = new SimpleDateFormat("hh:mm aa");
        return dateFormat.format(date);
    }
}
