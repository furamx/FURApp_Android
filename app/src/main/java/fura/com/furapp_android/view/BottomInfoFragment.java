package fura.com.furapp_android.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import fura.com.furapp_android.R;

/**
 * Created by edago on 12/1/17.
 */

public class BottomInfoFragment extends Fragment {

    Button sign_out;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_info, container, false);
        sign_out = view.findViewById(R.id.btn_logout_bottom_fragment);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                BottomLoginFragment bottomLoginFragment = new BottomLoginFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_bottom_main, bottomLoginFragment, "bottom_login_tag").commit();
            }
        });
        return view;
    }
}
