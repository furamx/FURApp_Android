package fura.com.furapp_android.model;

import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import fura.com.furapp_android.model.dataModels.Event;
import fura.com.furapp_android.view.MainActivity;

/**
 * Created by ramon on 22/11/17.
 */

public class FacebookRequest {
    public static void GetEventsFromFacebook(){
        AccessToken accessToken=new AccessToken("EAAFZCyLWsVDYBAJYlmthkYIQmqZBeuNu6J1TkK79ulZBBZCYMfqIhEeQuPsV0cOe3ovWACMxZC2cjfVGRqQFtoMNdRDB1iDWl8ERap02WNcfHZAzylYErxx0ZCDvTuDB7vSD8nSU8auopFwUxZCZBUBgW5YB3orRKZAVoZD","421974994867254","10214004472684462",null,null,null,null,null);
        /* make the API call */
            new GraphRequest(
                    accessToken,
                    "/681255215248937/events",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            JSONObject object = response.getJSONObject();
                            //Event obj = new Gson().fromJson(object, Event.class);
                        }
                    }
            ).executeAsync();
    }
}
