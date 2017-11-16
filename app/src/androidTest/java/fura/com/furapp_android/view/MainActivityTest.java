package fura.com.furapp_android.view;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import fura.com.furapp_android.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by ramon on 06/11/17.
 */
public class MainActivityTest {
    @Rule
    public IntentsTestRule<MainActivity> MainActivityRule=new IntentsTestRule<>(MainActivity.class);
    @Before
    public void SetUp(){
        MainActivityRule.getActivity();
    }
    /*@Test
    public void TestButtonGoToSignInActivity(){
        onView(withId(R.id.btn_access_sign_in)).perform(click());
        intended(hasComponent(SignInActivity.class.getName()));
    }
    @Test
    public void TestButonLogInIsDisplayed(){
        onView(withId(R.id.btn_access_sign_in)).check(matches((isDisplayed())));
    }*/
}