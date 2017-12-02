package user.innovative.demoapp;

import android.app.Application;

/**
 * Created by Lavisha_User on 10/7/2017.
 */

public class App extends Application {

    public static App instance;
    ApiHelper apihelper;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        apihelper = ApiHelper.init(instance);
    }

    public static App getAppContext(){
        return instance;
    }

    public ApiHelper getApihelper(){
        return apihelper;
    }

}
