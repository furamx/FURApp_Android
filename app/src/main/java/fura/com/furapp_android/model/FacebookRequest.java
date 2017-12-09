package fura.com.furapp_android.model;

import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;

import org.json.JSONObject;

import fura.com.furapp_android.model.dataModels.EventHelpers.EventRoot;
import fura.com.furapp_android.presenter.EventsPresenter;
import fura.com.furapp_android.view.EventsFragment;

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
}
