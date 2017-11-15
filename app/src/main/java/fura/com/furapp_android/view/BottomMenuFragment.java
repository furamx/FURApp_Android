package fura.com.furapp_android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;

import fura.com.furapp_android.R;

public class BottomMenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);
        Button _btn_access= view.findViewById(R.id.btn_access_sign_in);
        _btn_access.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sign_in = new Intent(getContext(), SignInActivity.class);
                startActivity(intent_sign_in);
            }
        });
        return view;
    }

}

