package com.hookedonplay.androidbycode.countryrankking;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.hookedonplay.androidbycode.countryrankking.app.RankActivity;

/**
 * @author BRM 20150324
 *
 * Espresso tests
 */
@LargeTest
public class EspressoApplicationUITest extends ActivityInstrumentationTestCase2<RankActivity> {

    private RankActivity mActivity;
        public EspressoApplicationUITest() {
            super(RankActivity.class);
        }

        @Override
        public void setUp() throws Exception {
            super.setUp();
            mActivity = getActivity();
        }

        public void testClickAnswerButton() {
            //TODO: Expresso tests.
            // Note, Appears espresso does not handle drag and drop
        }

}
