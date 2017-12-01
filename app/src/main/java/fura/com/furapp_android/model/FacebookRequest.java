package fura.com.furapp_android.model;

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
    public static void AttendEventFromFacebook(final String eventId) {

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
                        }
                    }
            ).executeAsync();


    }
}
