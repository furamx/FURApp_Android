package fura.com.furapp_android.events.model;

import android.content.Context;

import fura.com.furapp_android.R;
import fura.com.furapp_android.events.model.helpers.Event;
import fura.com.furapp_android.generic_helpers.StringFormatter;

/**
 * Created by jorge on 15/01/2018.
 */

public class EventsDataView {

    private String id;
    private String coverSource;
    private String name;
    private String city;
    private String street;
    private String placeName;
    private String startTime;
    private String endTime;
    private String date;
    private String description;

    public EventsDataView(Event event, Context context) throws Exception
    {
        id = event.getId();

        if (event.getCover() != null)
            coverSource = event.getCover().getSource();
        else
            coverSource = "";

        if (event.getName() != null)
            name = event.getName();
        else
            name = "";

        if (event.getPlace() != null) {

            if (event.getPlace().getName() != null)
                placeName = event.getPlace().getName();
            else
                placeName = "";


            if (event.getPlace().getLocation() != null) {

                if (event.getPlace().getLocation().getCity() != null)
                    city = event.getPlace().getLocation().getCity();
                else
                    city = "";

                if (event.getPlace().getLocation().getStreet() != null)
                    street = event.getPlace().getLocation().getStreet();
                else
                    street = "";

            }
            else {
                city = "";
                street = "";
            }
        }
        else {
            placeName = "";
            city = "";
            street = "";
        }



        if (event.getStart_time() != null) {
            startTime = context.getString(R.string.start_label) + "  " + StringFormatter.ToHour(event.getStart_time());
            date = StringFormatter.ToDate(event.getStart_time());
        }
        else {
            startTime = "";
            date = "";
        }

        if (event.getEnd_time() != null)
            endTime = context.getString(R.string.end_label) + "  " + StringFormatter.ToHour(event.getEnd_time());
        else
            endTime = "";

        if (event.getDescription() != null)
            description = event.getDescription();
        else
            description = "";

    }

    public String getEventId() {
        return id;
    }

    public String getCoverSource() {
        return coverSource;
    }

    public String getEventName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}

