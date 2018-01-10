package fura.com.furapp_android.presenter;

import java.util.ArrayList;
import java.util.List;

import fura.com.furapp_android.model.FacebookRequest;
import fura.com.furapp_android.model.dataModels.EventHelpers.Event;
import fura.com.furapp_android.model.dataModels.EventHelpers.EventRoot;
import fura.com.furapp_android.events.EventsFragment;

/**
 * Created by ramon on 27/11/17.
 */

public class EventsPresenter {

    //region GLOBAL FIELDS
    private EventsFragment eventsFragment;
    private List<Event> eventList;
    //endregion

    //region CLASS CONSTRUCTORS
    public EventsPresenter(EventsFragment _eventsFragment){
        this.eventsFragment=_eventsFragment;
    }
    //endregion

    //region EVENTS METHODS
    public void UpdateEvents(){
        FacebookRequest.GetEventsFromFacebook(this);
    }

    public void SetEvents(EventRoot _eventRoot){
        eventList=new ArrayList<>();
        eventList=_eventRoot.getData();
        eventsFragment.UpdateAdapter(eventList);
    }
    //endregion
}
