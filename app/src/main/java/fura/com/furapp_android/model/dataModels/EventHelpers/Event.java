package fura.com.furapp_android.model.dataModels.EventHelpers;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ramon on 23/11/17.
 */

public class Event {
    // Id parameter for the attending function.
    private String id;
    private String description;
    private String end_time;
    private String name;
    private Place place;
    private String start_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

        return null;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }


    public String getStart_dateFormatted() {

        try {

            //Convert the String object to Date object.
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date tempDate = sdf.parse(this.start_time);

            //Convert the Date object to String object, but formatted.
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            String finalDate = sdf.format(tempDate);

            return finalDate;

        }
        catch (Exception ex) {
            return "(Fecha no disponible)";
        }

    }


    public String getStart_timeFormatted() {

        try {

            //Convert the String object to Date object.
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date tempDate = sdf.parse(this.start_time);

            //Convert the Date object to String object, but formatted.
            sdf = new SimpleDateFormat("HH:mm");
            String finalTime = sdf.format(tempDate);

            return finalTime + " hrs";

        }
        catch (Exception ex) {
            return "(Hora no disponible)";
        }

    }

}
