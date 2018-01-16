package fura.com.furapp_android.events.view;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fura.com.furapp_android.events.model.EventsDataView;
import fura.com.furapp_android.events.model.helpers.Event;
import fura.com.furapp_android.events.view.helpers.EventsAdapter;

/**
 * Created by jorge on 15/01/2018.
 */

public interface EventsInterface {

    void updateAdapter(List<Event> _events);
    void updateAssistButton(EventsAdapter.MyViewHolder eventHolder);
    void notifyUser(String strMessage, Context context);
    void displayDataTextView(String strData, TextView view);
    void displayCoverImageView(String strSource, ImageView view, Context context);

}
