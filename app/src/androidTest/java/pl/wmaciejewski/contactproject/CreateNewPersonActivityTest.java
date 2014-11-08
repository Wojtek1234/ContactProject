package pl.wmaciejewski.contactproject;

import pl.wmaciejewski.contactproject.createnewperson.CreateNewPersonActivity;

/**
 * Created by w.maciejewski on 2014-11-03.
 */
public class CreateNewPersonActivityTest extends android.test.ActivityInstrumentationTestCase2<CreateNewPersonActivity> {
    private CreateNewPersonActivity mActivity;

    public CreateNewPersonActivityTest(Class<CreateNewPersonActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);

        mActivity = getActivity();
    }

    //TODO Zrobic testy, tylko nie wiem jak


}
