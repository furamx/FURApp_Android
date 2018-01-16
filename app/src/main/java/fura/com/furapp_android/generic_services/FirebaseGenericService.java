package fura.com.furapp_android.generic_services;

import com.facebook.AccessToken;

/**
 * Created by jorge on 15/01/2018.
 */

public class FirebaseGenericService {

    public static AccessToken getGenericFacebookToken() {
        AccessToken accessToken = new AccessToken("EAACEdEose0cBAAw1EptAvnjEXXCcfeb0EfHMJLFlbFTspzVChGl80XVZB8aDfMyWIZCQTeRFlr8NQzcehfnJDiyyv6vsCVaSKzSCv2k1DIGaWEkKvw6WAxlGL8XgGnWYN2kT6vt9qBUCWAyJmPA6GuRCQKfhiafwRa3URhh33PRTC4WEfD6rYGof3zrG8FzOJ2ZABS0MjRqXtZAPmc4Dx6udnetuMicZD","421974994867254","100009500341520",null,null,null,null,null);

        return accessToken;
    }

    public static AccessToken getUserFacebookToken(String strTokenUsr, String strIdUser) {
        AccessToken accessToken = new AccessToken(strTokenUsr,"421974994867254", strIdUser, null,null,null,null,null);

        return accessToken;
    }
}
