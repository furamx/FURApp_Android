package fura.com.furapp_android.events.model.helpers;

import java.util.List;

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
