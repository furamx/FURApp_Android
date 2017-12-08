package fura.com.furapp_android.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fura.com.furapp_android.R;
import fura.com.furapp_android.model.dataModels.EventHelpers.EventRoot;
import fura.com.furapp_android.presenter.EventsPresenter;
import fura.com.furapp_android.view.MainActivity;

/**
 * Created by ramon on 22/11/17.
 */

public class FacebookRequest {
    public static void GetEventsFromFacebook(final EventsPresenter eventsPresenter){
        AccessToken accessToken=new AccessToken("EAAFZCyLWsVDYBAJYlmthkYIQmqZBeuNu6J1TkK79ulZBBZCYMfqIhEeQuPsV0cOe3ovWACMxZC2cjfVGRqQFtoMNdRDB1iDWl8ERap02WNcfHZAzylYErxx0ZCDvTuDB7vSD8nSU8auopFwUxZCZBUBgW5YB3orRKZAVoZD","421974994867254","10214004472684462",null,null,null,null,null);
        //make the API call
            new GraphRequest(
                    accessToken,
                    "/681255215248937/events",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            JSONObject object = response.getJSONObject();
                            EventRoot eventRoot= new Gson().fromJson(object.toString(), EventRoot.class);
                            eventsPresenter.SetEvents(eventRoot);
                        }
                    }
            ).executeAsync();
    }

    // Graph method to attend an event on Facebook.
    public static void GetAttendedPersonsFromEventFromFacebook(final String eventId, final Button btnEvent) {

        Context mainContext = MainActivity.getContextMain();
        SharedPreferences shPref = mainContext.getSharedPreferences("pref",Context.MODE_PRIVATE);
        final String strIdUser = shPref.getString("UserIdFacebook", "There is no id");


        AccessToken accessToken=new AccessToken("EAAFZCyLWsVDYBAJYlmthkYIQmqZBeuNu6J1TkK79ulZBBZCYMfqIhEeQuPsV0cOe3ovWACMxZC2cjfVGRqQFtoMNdRDB1iDWl8ERap02WNcfHZAzylYErxx0ZCDvTuDB7vSD8nSU8auopFwUxZCZBUBgW5YB3orRKZAVoZD","421974994867254","10214004472684462",null,null,null,null,null);
        //make the API call
            new GraphRequest(
                    accessToken,
                    "/" + eventId + "/attending",
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

                                        btnEvent.setBackgroundColor(Color.GREEN);
                                        btnEvent.setText(R.string.attend_button_check);
                                        btnEvent.setEnabled(false);

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

    public static void PostAttendEventFromFacebook(final String eventId) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        List<String> lstPermissions = new ArrayList<>();
        lstPermissions.add("rsvp_event");


        if (auth.getCurrentUser() != null)
        {

            //Context to get the Facebook token of the logged in user.
            Context mainContext = MainActivity.getContextMain();

            SharedPreferences shPref = mainContext.getSharedPreferences("pref",Context.MODE_PRIVATE);

            String strTokenUsr = shPref.getString("TokenFacebook", "There is no token");
            String strIdUser = shPref.getString("UserIdFacebook", "There is no id");


            if (!strTokenUsr.equals("There is no token") && !strIdUser.equals("There is no id")) {

                AccessToken accessToken=new AccessToken(strTokenUsr,"421974994867254", strIdUser, lstPermissions,null,null,null,null);
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
                            }
                        }
                ).executeAsync();

            }

        }

    }
}
