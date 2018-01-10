package fura.com.furapp_android.events.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Button;
import android.widget.Toast;
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
import fura.com.furapp_android.view.MainActivity;

/**
 * Created by ramon on 22/11/17.
 */

public class FacebookRequest {
    //Receives an EventPresenter object and set in it the list with event objects
    public static void GetEventsFromFacebook(final EventsPresenter eventsPresenter){
        //Set parameters
        Bundle bundle_parameters=new Bundle();
        bundle_parameters.putString("fields","cover,description,end_time,name,place,start_time,is_canceled,is_draft");
        AccessToken accessToken=new AccessToken("EAAFZCyLWsVDYBAJYlmthkYIQmqZBeuNu6J1TkK79ulZBBZCYMfqIhEeQuPsV0cOe3ovWACMxZC2cjfVGRqQFtoMNdRDB1iDWl8ERap02WNcfHZAzylYErxx0ZCDvTuDB7vSD8nSU8auopFwUxZCZBUBgW5YB3orRKZAVoZD","421974994867254","10214004472684462",null,null,null,null,null);
        //make the API call
        try {
            new GraphRequest(
                    accessToken,
                    "/681255215248937/events",
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
    public static void GetAttendedPersonsFromEventFromFacebook(final EventsPresenter eventsPresenter) {

        Context mainContext = MainActivity.getContextMain();
        SharedPreferences shPref = mainContext.getSharedPreferences("pref",Context.MODE_PRIVATE);
        final String strIdUser = shPref.getString("UserIdFacebook", "There is no id");


        AccessToken accessToken=new AccessToken("EAAFZCyLWsVDYBAJYlmthkYIQmqZBeuNu6J1TkK79ulZBBZCYMfqIhEeQuPsV0cOe3ovWACMxZC2cjfVGRqQFtoMNdRDB1iDWl8ERap02WNcfHZAzylYErxx0ZCDvTuDB7vSD8nSU8auopFwUxZCZBUBgW5YB3orRKZAVoZD","421974994867254","10214004472684462",null,null,null,null,null);
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

                                for (int i = 0; i < objArray.length(); i++) {

                                    JSONObject objJson = (JSONObject)objArray.get(i);

                                    if (objJson.getString("id").equals(strIdUser)) {

                                        eventsPresenter.UpdateAssistButton();

                                        break;
                                    }
                                }


                            }
                            catch (Exception ex) {

                            }


                        }
                    }
            ).executeAsync();


    }


    public static void PostAttendEventFromFacebook(final String eventId, final Context context) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
        {

            //Context to get the Facebook token of the logged in user.
            Context mainContext = MainActivity.getContextMain();

            SharedPreferences shPref = mainContext.getSharedPreferences("pref",Context.MODE_PRIVATE);

            String strTokenUsr = shPref.getString("TokenFacebook", "There is no token");
            String strIdUser = shPref.getString("UserIdFacebook", "There is no id");


            if (!strTokenUsr.equals("There is no token") && !strIdUser.equals("There is no id")) {

                AccessToken accessToken=new AccessToken(strTokenUsr,"421974994867254", strIdUser, null,null,null,null,null);
                //make the API call
                new GraphRequest(
                        accessToken,
                        "/" + eventId + "/attending",
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
                                        Toast.makeText(context, "Evento caducado, ya no disponible.", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(context, "Error al conectarse con el servidor.", Toast.LENGTH_LONG).show();
                                    }
                                }

                            }
                        }
                ).executeAsync();

            }

        }

    }
}
