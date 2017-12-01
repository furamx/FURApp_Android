package fura.com.furapp_android.view;

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
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
