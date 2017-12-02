package user.innovative.demoapp;

/**
 * Created by Lavisha_User on 10/7/2017.
 */

public interface ApiCallback<T> {

    void onSuccess(T t);

    void onFailure(String message);
}
