package com.idirin.catfactstask;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.idirin.catfactstask.ui.activities.MainActivity;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.idirin.catfactstask.TestUtils.setProgress;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void preRequirement() {
        //In case of context needed but not for this situation
        Context context = getInstrumentation().getTargetContext();
        TestUtils.setPreRequirements();
    }

    @Before
    public void grantPhonePermissionForMethods() {
        //In case of context needed but not for this situation
        Context context = getInstrumentation().getTargetContext();
        TestUtils.setPreRequirements();
    }

    @AfterClass
    public static void postRequirement() {
        TestUtils.setPostRequirements();
    }


    @Test
    public void useAppContext() throws Exception {

        int sleep = 1000;
        int sleepLong = 5000;

        Thread.sleep(sleep);

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.idirin.catfactstask", appContext.getPackageName());


        //onView(withId(R.id.seekBar)).perform(swipeRight());

        onView(withId(R.id.seekBar)).perform(setProgress(150));
        Thread.sleep(sleepLong);

        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, TestUtils.clickChildViewWithId(R.id.imViewShare)));
        Thread.sleep(sleep);
        Thread.sleep(sleep);
        closeShareScreen();
        Thread.sleep(sleep);

        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, TestUtils.clickChildViewWithId(R.id.imViewShare)));
        Thread.sleep(sleep);
        Thread.sleep(sleep);
        closeShareScreen();
        Thread.sleep(sleep);

        onView(withId(R.id.recyclerView)).perform(swipeUp());
        Thread.sleep(sleep);

        onView(withId(R.id.recyclerView)).perform(swipeUp());
        Thread.sleep(sleep);

        onView(withId(R.id.recyclerView)).perform(swipeUp());
        Thread.sleep(sleep);

        onView(withId(R.id.recyclerView)).perform(swipeUp());
        Thread.sleep(sleep);

        onView(withId(R.id.recyclerView)).perform(swipeUp());
        Thread.sleep(sleep);




        onView(withId(R.id.seekBar)).perform(setProgress(250));
        Thread.sleep(sleepLong);

        onView(withId(R.id.recyclerView)).perform(swipeUp());
        Thread.sleep(sleep);

        onView(withId(R.id.recyclerView)).perform(swipeUp());
        Thread.sleep(sleep);

        onView(withId(R.id.recyclerView)).perform(swipeDown());
        Thread.sleep(sleep);

        onView(withId(R.id.recyclerView)).perform(swipeDown());
        Thread.sleep(sleep);


    }



    private void closeShareScreen() {
        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        int x = mDevice.getDisplayWidth() / 6;
        int y = mDevice.getDisplayHeight() / 6;
        mDevice.click(x, y);
        mDevice.waitForIdle();
    }


}
