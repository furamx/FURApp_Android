package fura.com.furapp_android.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fura.com.furapp_android.R;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    //Context variable for the Facebook token.
    public static Context contextSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Initialize context for the token and userId.
        contextSignIn = getApplicationContext();

        String callerActivity = getIntent().getStringExtra("caller-activity");

        if (callerActivity == null) {
            //ORIGINAL METHOD
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().setPrivacyPolicyUrl("https://drive.google.com/file/d/1PSAP6i3L-mx-YM7_TWNqEZxOo1Vh4WC5/view?usp=sharing")
                            .setAvailableProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build())
                            ).build(),
                    RC_SIGN_IN);
        }
        else if (callerActivity.equals("EventsAdapter")) {
            //Only Facebook
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().setPrivacyPolicyUrl("https://drive.google.com/file/d/1PSAP6i3L-mx-YM7_TWNqEZxOo1Vh4WC5/view?usp=sharing")
                            .setAvailableProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build())
                            ).build(),
                    RC_SIGN_IN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {

                if (response.getProviderType().equals("facebook.com"))
                {
                    AsyncGetUserId taskToken = new AsyncGetUserId();
                    taskToken.execute(response.getIdpToken());
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(R.string.unknown_error);
                    return;
                }
            }

            showSnackbar(R.string.unknown_sign_in_response);
        }
    }

    private void showSnackbar(int text_id){
        String text = getResources().getString(text_id);
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }


    public class AsyncGetUserId extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String strURL = "https://graph.facebook.com/me?fields=id&access_token=" + strings[0];

            try
            {
                URL netURL = new URL(strURL);
                HttpURLConnection conn = (HttpURLConnection) netURL.openConnection();
                conn.setReadTimeout(100000);
                conn.setConnectTimeout(150000);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");


                conn.connect();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader buffReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String strResponseContent = "";

                    String tempLine = "";
                    while ((tempLine = buffReader.readLine()) != null) {
                        strResponseContent += tempLine + "\n";
                    }
                    buffReader.close();

                    JSONObject objId = new JSONObject(strResponseContent);

                    String strUserId = objId.getString("id");

                    saveTokenAndUserId(strings[0], strUserId);
                }

            }
            catch (Exception e) {
                showSnackbar(R.string.unknown_error);
            }

            return "Finished";
        }
    }

    private void saveTokenAndUserId(String idToken, String idUser) {

        //SharedPreferences to save the Facebook token.
        SharedPreferences shPref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefEditor = shPref.edit();

        //Save the token with keyname TokenFacebook
        prefEditor.putString("TokenFacebook", idToken);
        prefEditor.putString("UserIdFacebook", idUser);
        prefEditor.commit();

    }

    //Function to get the context in order to retrieve SharedPreferences in non-Activity classes.
    public static Context getContextSignIN() {

        return contextSignIn;
    }
}
