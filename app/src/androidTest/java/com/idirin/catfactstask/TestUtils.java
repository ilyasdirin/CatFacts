package com.idirin.catfactstask;

import android.os.Build;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.uiautomator.UiDevice;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.xw.repo.BubbleSeekBar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by idirin on 03/05/2017.
 * Requirements for tests to run stable
 */

public class TestUtils {

    private static boolean deviceWakedUp = false;

    public static void setPreRequirements() {

        //wakeUpDevice();

        // In M+ Make sure
        // the permission is granted before running this test.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.SET_ANIMATION_SCALE");
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getInstrumentation().getUiAutomation().executeShellCommand("settings put global window_animation_scale 0.0");
            getInstrumentation().getUiAutomation().executeShellCommand("settings put global transition_animation_scale 0.0");
            getInstrumentation().getUiAutomation().executeShellCommand("settings put global animator_duration_scale 0.0");
        }
    }

    public static void setPostRequirements() {
        // In M+ Make sure
        // the permission is granted before running this test.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.SET_ANIMATION_SCALE");
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getInstrumentation().getUiAutomation().executeShellCommand("settings put global window_animation_scale 1.0");
            getInstrumentation().getUiAutomation().executeShellCommand("settings put global transition_animation_scale 1.0");
            getInstrumentation().getUiAutomation().executeShellCommand("settings put global animator_duration_scale 1.0");
        }
    }

    private static void wakeUpDevice() {

        if (deviceWakedUp) {
            return;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            UiDevice uiDevice = UiDevice.getInstance(getInstrumentation());
            try {
                uiDevice.pressHome();
                uiDevice.wakeUp();
                unlockBySwipe(uiDevice);
                Thread.sleep(3000);
                deviceWakedUp = true;

                uiDevice.pressRecentApps();
                for (int i = 0; i<10; i++) {
                    removeAppBySwipe(uiDevice);
                }
                uiDevice.pressHome();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private static void unlockBySwipe(UiDevice uiDevice) {
        int x1 = uiDevice.getDisplayWidth() / 2;
        int y1 = uiDevice.getDisplayHeight() / 2;
        int x2 = uiDevice.getDisplayWidth() / 6;
        int y2 = uiDevice.getDisplayHeight() / 6;
        uiDevice.swipe(x1, y1, x2, y2, 10);
        uiDevice.waitForIdle();
    }

    private static void removeAppBySwipe(UiDevice uiDevice) {
        int x1 = uiDevice.getDisplayWidth() / 6;
        int y1 = uiDevice.getDisplayHeight() / 2;
        int x2 = (int)(uiDevice.getDisplayWidth() / 1.2);
        int y2 = uiDevice.getDisplayHeight() / 2;
        uiDevice.swipe(x1, y1, x2, y2, 10);
        uiDevice.waitForIdle();
    }

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ((BubbleSeekBar) view).setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(BubbleSeekBar.class);
            }
        };
    }






}
