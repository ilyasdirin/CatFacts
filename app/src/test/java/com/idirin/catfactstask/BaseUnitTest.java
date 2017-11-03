package com.idirin.catfactstask;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * BaseUnitTest class is used for general mocking tasks for test classes
 * such as preferences, api, db
 * or static classes like crashlytics, analytics
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(PreferenceManager.class)
public abstract class BaseUnitTest {

    protected Context context = mock(Context.class);
    protected MockSharedPreference sharedPrefs = new MockSharedPreference();

    @Before
    public void setUpPreferences() throws Exception {
        mockStatic(PreferenceManager.class);
        when(PreferenceManager.getDefaultSharedPreferences(any(Context.class))).thenReturn(sharedPrefs);
    }




    public String loadJSONFromAsset(@NonNull String fileName) {
        String json;
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
