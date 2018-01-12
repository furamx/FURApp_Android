package fura.com.furapp_android.events.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fura.com.furapp_android.R;
import fura.com.furapp_android.generic_services.FacebookRequest;
import fura.com.furapp_android.events.model.helpers.Event;
import fura.com.furapp_android.events.model.helpers.EventRoot;
import fura.com.furapp_android.events.view.EventsFragment;
import fura.com.furapp_android.events.view.helpers.EventsAdapter;
import fura.com.furapp_android.view.SignInActivity;

/**
 * Created by ramon on 27/11/17.
 */

public class EventsPresenter {

    //region GLOBAL FIELDS
    private EventsFragment eventsFragment;
    private List<Event> eventList;
    public EventsAdapter.MyViewHolder eventHolder;
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

    public void AttendEvent(final Context _context) {

        AlertDialog.Builder alertAttendBuilder = new AlertDialog.Builder(_context);
        alertAttendBuilder.setMessage(R.string.attend_event_warning);

        final EventsPresenter presenter = this;

        alertAttendBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Code
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() != null) {

                    FacebookRequest.PostAttendEventFromFacebook(presenter, _context);
                }
                else {


                    AlertDialog.Builder alertLoginBuilder = new AlertDialog.Builder(_context);
                    alertLoginBuilder.setMessage(R.string.attend_event_login);

                    alertLoginBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Code
                            Intent intent_sign_in = new Intent(_context, SignInActivity.class);
                            intent_sign_in.putExtra("caller-activity", "EventsAdapter");
                            _context.startActivity(intent_sign_in);

                        }
                    });

                    alertLoginBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Code
                        }
                    });


                    AlertDialog alertLogin = alertLoginBuilder.create();
                    alertLogin.show();

                }



            }
        });

        alertAttendBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Code
            }
        });

        AlertDialog alertAttendMessage = alertAttendBuilder.create();
        alertAttendMessage.show();
    }

    public void GetAttendedPersonsFromEvent(Context context) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            List<String> lstProviders = auth.getCurrentUser().getProviders();

            if (lstProviders.contains("facebook.com")) {

                FacebookRequest.GetAttendedPersonsFromEventFromFacebook(this, context);
            }

        }

    }

    //Method to disable the attend function for already selected events.
    public void UpdateAssistButton(JSONArray objArray, String strIdUser, Context context) {

        try {

            for (int i = 0; i < objArray.length(); i++) {

                JSONObject objJson = (JSONObject)objArray.get(i);

                if (objJson.getString("id").equals(strIdUser)) {

                    eventsFragment.UpdateAssistButton(eventHolder);

                    break;
                }
            }

        }
        catch (Exception ex) {

            eventsFragment.NotifyUser("Error al administrar eventos a asistir.", context);
        }

    }

    //Method to notify the user possible errors trying to do an operation.
    public void NotifyUser(String strMessage, Context context) {

        eventsFragment.NotifyUser(strMessage, context);

    }
    //endregion
}
