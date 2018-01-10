package fura.com.furapp_android.model.dataModels.EventHelpers;

import java.util.List;

import fura.com.furapp_android.model.dataModels.EventHelpers.Event;

/**
 * Created by ramon on 22/11/17.
 */

public class EventRoot {
    private List<Event> data;

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }
}
