package user.innovative.demoapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lavisha_User on 10/7/2017.
 */

class ApiHelper {
    private static App app;
    private static ApiHelper apiHelper;
    private static ApiService apiService;

    public static ApiHelper init(App instance) {
        if (apiHelper == null) {
            apiHelper = new ApiHelper();
            apiHelper.initApiService();
            setApp(app);
        }
        return apiHelper;
    }

    public static void setApp(App app) {
        ApiHelper.app = app;
    }

    private void initApiService() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.androidhive.info/contacts/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(ApiService.class);
    }
}
