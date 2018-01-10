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
    private Cover cover;
    private boolean is_canceled;
    private boolean is_draft;

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

    public String getStart_time(){return start_time;}

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public boolean isIs_canceled() {
        return is_canceled;
    }

    public void setIs_canceled(boolean is_canceled) {
        this.is_canceled = is_canceled;
    }

    public boolean isIs_draft() {
        return is_draft;
    }

    public void setIs_draft(boolean is_draft) {
        this.is_draft = is_draft;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}
