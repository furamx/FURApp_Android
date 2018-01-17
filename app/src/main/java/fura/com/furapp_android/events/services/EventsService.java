package fura.com.furapp_android.events.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import fura.com.furapp_android.R;
import fura.com.furapp_android.events.model.helpers.EventRoot;
import fura.com.furapp_android.events.presenter.EventsPresenter;
import fura.com.furapp_android.generic_services.FacebookGenericService;
import fura.com.furapp_android.generic_services.FirebaseGenericService;
import fura.com.furapp_android.view.MainActivity;

/**
 * Created by jorge on 15/01/2018.
 */

public class EventsService {

    //Receives an EventPresenter object and set in it the list with event objects
    public static void GetEventsFromFacebook(final EventsPresenter eventsPresenter){
        //Set parameters
        Bundle bundle_parameters=new Bundle();
        bundle_parameters.putString("fields","cover,description,end_time,name,place,start_time,is_canceled,is_draft");
        //Get the generic token.
        AccessToken accessToken = FirebaseGenericService.getGenericFacebookToken();
        //Get the events id of FURA's Facebook page.
        String strEventsId = FacebookGenericService.getEventsPageId();
        //make the API call
        try {
            new GraphRequest(
                    accessToken,
                    "/" + strEventsId + "/events",
                    bundle_parameters,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            //Receives the json object
                            JSONObject object = response.getJSONObject();
                            //Uses the Gson library to easy convert json into class object
                            EventRoot eventRoot = new Gson().fromJson(object.toString(), EventRoot.class);
                            //Set events to EventPresenter object
                            eventsPresenter.SetEvents(eventRoot);
                        }
                    }
            ).executeAsync();
        }
        catch (Exception e){
        }
    }

    // Graph method to attend an event on Facebook.
    public static void GetAttendedPersonsFromEventFromFacebook(final EventsPresenter eventsPresenter, final Context context) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
        {
            //Context to get the Facebook token of the logged in user.
            Context mainContext = MainActivity.getContextMain();

            SharedPreferences shPref = mainContext.getSharedPreferences("pref",Context.MODE_PRIVATE);
            final String strIdUser = shPref.getString("UserIdFacebook", "There is no id");

            if (!strIdUser.equals("There is no id")) {

                //Get the generic token.
                AccessToken accessToken = FirebaseGenericService.getGenericFacebookToken();
                //make the API call
                new GraphRequest(
                        accessToken,
                        "/" + eventsPresenter.eventHolder.idEvent.getText().toString() + "/attending",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                //Code
                                JSONObject object = response.getJSONObject();

                                try {

                                    JSONArray objArray = object.getJSONArray("data");

                                    eventsPresenter.UpdateAssistButton(objArray, strIdUser, context);
                                }
                                catch (Exception ex) {

                                    eventsPresenter.NotifyUser(context.getString(R.string.attend_event_attended_error), context);
                                }


                            }
                        }
                ).executeAsync();
            }

        }
    }


    public static void PostAttendEventFromFacebook(final EventsPresenter eventsPresenter, final Context context) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
        {
            //Context to get the Facebook token of the logged in user.
            Context mainContext = MainActivity.getContextMain();

            SharedPreferences shPref = mainContext.getSharedPreferences("pref",Context.MODE_PRIVATE);

            String strTokenUsr = shPref.getString("TokenFacebook", "There is no token");
            String strIdUser = shPref.getString("UserIdFacebook", "There is no id");


            if (!strTokenUsr.equals("There is no token") && !strIdUser.equals("There is no id")) {

                AccessToken accessToken = FirebaseGenericService.getUserFacebookToken(strTokenUsr, strIdUser);
                //make the API call
                new GraphRequest(
                        accessToken,
                        "/" + eventsPresenter.eventHolder.idEvent.getText().toString() + "/attending",
                        null,
                        HttpMethod.POST,
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                //Code
                                JSONObject object = response.getJSONObject();

                                //Validating the response.
                                if (response.getError() != null) {

                                    String strError = response.getError().getErrorMessage();

                                    if (strError.equals("(#100) User must be able to RSVP to the event.")) {
                                        //Event not available
                                        eventsPresenter.NotifyUser(context.getString(R.string.attend_event_not_available), context);

                                    }
                                    else {
                                        eventsPresenter.NotifyUser(context.getString(R.string.attend_event_error), context);

                                    }
                                }

                            }
                        }
                ).executeAsync();

            }

        }

    }
}
