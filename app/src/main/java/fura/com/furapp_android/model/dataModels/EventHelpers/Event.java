package fura.com.furapp_android.model.dataModels.EventHelpers;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ramon on 23/11/17.
 */

public class Event {
    private String description;
    private String end_time;
    private String name;
    private Place place;
    private String start_time;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getStart_time() throws ParseException {
        /*
        * String date = "2014-11-25 14:30";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
        String newFormat = formatter.format(testDate);
        System.out.println(".....Date..."+newFormat);
        * */
        /*SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date;
        try{
            dateFormat.parse(start_time);
        } catch (Exception e){}

        SimpleDateFormat formatter=new SimpleDateFormat("dd MMMMM, yyyy");
        String newFormat=formatter.format(date);
        return newFormat;*/
        return null;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    //
    public String getStart_date() throws ParseException {
        String date= DateFormat.getDateInstance(DateFormat.MEDIUM).format(start_time);
        return date;
    }

}
