package fura.com.furapp_android.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Arrays;

import fura.com.furapp_android.R;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    //Context variable for the Facebook token.
    public static Context contextSignIn;

    //Login Button and CallbackManager for writing permissions (for the events).
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    //Password login button.
    private Button btnLoginPassword;

    //Loading text.
    private TextView txtLoading;

    //Firebase variable authenticate.
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Firebase variable authenticate.
        auth = FirebaseAuth.getInstance();

        //Initialize context for the token and userId.
        contextSignIn = getApplicationContext();

        //Button for password authentication.
        btnLoginPassword = (Button) findViewById(R.id.btn_login_password);


        //Loading TextView
        txtLoading = (TextView) findViewById(R.id.txv_loading_text);

        //Initialization for the Login Button.
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btn_login_facebook);
        loginButton.setPublishPermissions(Arrays.asList("rsvp_event"));

        //To set visibility of password button.
        String callerActivity = getIntent().getStringExtra("caller-activity");
        if (callerActivity != null) {
            btnLoginPassword.setVisibility(View.GONE);

            loginButton.performClick();
        }
        else {
            //Make invisible the login buttons to prevent bugs.
            btnLoginPassword.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);

            txtLoading.setVisibility(View.GONE);
        }


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //Saving the access token and user id from the user for later use.
                saveTokenAndUserId(loginResult.getAccessToken().getToken(), loginResult.getAccessToken().getUserId());

                //Make invisible the login buttons to prevent bugs.
                btnLoginPassword.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);

                txtLoading.setVisibility(View.VISIBLE);

                //Use Facebook access token to authenticate with Firebase.
                authenticateToFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        //Authenticate with password.
        btnLoginPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //ORIGINAL METHOD
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder().setPrivacyPolicyUrl("https://drive.google.com/file/d/1PSAP6i3L-mx-YM7_TWNqEZxOo1Vh4WC5/view?usp=sharing")
                                .setAvailableProviders(
                                        Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build())
                                ).build(),
                        RC_SIGN_IN);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Code to enable the callback from Facebook authentication.
        callbackManager.onActivityResult(requestCode, resultCode, data);

        //Code used for password authentication.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, null);
                finish();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    setResult(RESULT_OK, null);
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

    private void authenticateToFirebase(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Successful code.
                            setResult(RESULT_OK, null);
                            finish();

                        } else {
                            //Unsuccessful code.
                            showSnackbar(R.string.unknown_sign_in_response);
                        }

                    }
                });

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
