package fura.com.furapp_android.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;


import com.google.firebase.auth.FirebaseAuth;

import fura.com.furapp_android.R;

public class MainActivity extends AppCompatActivity implements
    EventsFragment.OnFragmentInteractionListener,
    BottomLoginFragment.OnFragmentInteractionListener {

    FrameLayout bottomMenuFragmentLayout;

    //Context variable for the Facebook token.
    public static Context contextMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventsFragment eventsFragment = new EventsFragment();

        //Instance fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Initialize fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Add fragments to transaction
        fragmentTransaction.add(R.id.fragment_events_cards, eventsFragment, "events_cards_tag");

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            // already signed in
            BottomInfoFragment bottomInfoFragment = new BottomInfoFragment();
            fragmentTransaction.add(R.id.fragment_bottom_main, bottomInfoFragment, "bottom_info_tag");
        } else {
            // not signed in
            //Fragments to charge in main activity
            BottomLoginFragment bottomLoginFragment = new BottomLoginFragment();
            fragmentTransaction.add(R.id.fragment_bottom_main, bottomLoginFragment,"bottom_login_tag");
        }

        //Make changes
        fragmentTransaction.commit();

        //Save the token and userId.
        getTokensAndUsersId();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void getTokensAndUsersId () {


        //Code to get the token and idUser.
        //Initialize the MainActivity context.
        contextMain = getApplicationContext();

        //Get the context from SignIn which has the information.
        Context signInContext = SignInActivity.getContextSignIN();

        //Trying to read the preferences from the SignInActivity.
        if (signInContext != null) {
            SharedPreferences shPrefSignIn = signInContext.getSharedPreferences("pref",Context.MODE_PRIVATE);

            String strTokenUsr = shPrefSignIn.getString("TokenFacebook", "There is no token");
            String strIdUser = shPrefSignIn.getString("UserIdFacebook", "There is no id");


            SharedPreferences shPrefMain = getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = shPrefMain.edit();
            prefEditor.putString("TokenFacebook", strTokenUsr);
            prefEditor.putString("UserIdFacebook", strIdUser);

            prefEditor.commit();
        }
    }

    //Function to get the context in order to retrieve SharedPreferences in non-Activity classes.
    public static Context getContextMain() {

        return contextMain;
    }
}
