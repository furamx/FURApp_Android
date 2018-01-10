package fura.com.furapp_android.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import fura.com.furapp_android.R;
import fura.com.furapp_android.events.view.EventsFragment;

public class MainActivity extends AppCompatActivity implements
    EventsFragment.OnFragmentInteractionListener,
    BottomLoginFragment.OnFragmentInteractionListener {

    //region GLOBAL FIELDS
    FirebaseAuth auth;
    Menu menu;
    //endregion

    //Context variable for the Facebook token.
    public static Context contextMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set view to activity main layout
        setContentView(R.layout.activity_main);
        //Create EventsFragment object
        EventsFragment eventsFragment = new EventsFragment();
        //Instance fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Initialize fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Add fragments to transaction
        fragmentTransaction.add(R.id.fragment_events_cards, eventsFragment, "events_cards_tag");
        //Gets the firebase authentication's instance
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            BottomInfoFragment bottomInfoFragment = new BottomInfoFragment();
            //Fragment with user info
            fragmentTransaction.add(R.id.fragment_bottom_main, bottomInfoFragment, "bottom_info_tag");
        } else {
            // not signed in
            //Fragments to charge in main activity
            BottomLoginFragment bottomLoginFragment = new BottomLoginFragment();
            //Fragment to log in
            fragmentTransaction.add(R.id.fragment_bottom_main, bottomLoginFragment,"bottom_login_tag");
        }
        //Apply changes
        fragmentTransaction.commit();

        //Save the token and userId.
        getTokensAndUsersId();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //region MENU IN NAV BAR

    //Create the menu with the menu options, menu design located in res->menu->menu_options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Check if there's a user log in
        if(auth.getCurrentUser()!=null&&LoginManager.getInstance()!=null) {
            this.menu=menu;
            getMenuInflater().inflate(R.menu.menu_options, menu);
        }
        return true;
    }

    //When menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Logout case
        if (id == R.id.action_logout) {
            //sign out the firebase user
            auth.signOut();
            //sign out facebook user
            LoginManager.getInstance().logOut();
            //return to the main view
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            BottomLoginFragment bottomLoginFragment = new BottomLoginFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_bottom_main, bottomLoginFragment, "bottom_login_tag").commit();
            //delete the options menu
            menu.clear();
            //Shows log out message
            Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.logout_message), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion


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
