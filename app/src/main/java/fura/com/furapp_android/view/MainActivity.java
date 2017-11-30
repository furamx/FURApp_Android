package fura.com.furapp_android.view;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import fura.com.furapp_android.R;
import fura.com.furapp_android.model.FacebookRequest;

public class MainActivity extends AppCompatActivity implements
    EventsFragment.OnFragmentInteractionListener,
    BottomMenuFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
        } else {
            // not signed in
        }
        setContentView(R.layout.activity_main);
        //Fragments to charge in main activity
        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
        EventsFragment eventsFragment=new EventsFragment();
        //Instance fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Initialize fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Add fragments to transaction
        fragmentTransaction.add(R.id.fragment_events_cards, eventsFragment, "events_cards_tag");
        fragmentTransaction.add(R.id.fragment_bottom_info,bottomMenuFragment,"bottom_menu_tag");
        //Make changes
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
