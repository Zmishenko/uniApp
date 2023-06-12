package uni.dubna.app;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uni.dubna.app.data.retrofit.EventService;
import uni.dubna.app.data.retrofit.UserService;

public class MyApplication extends Application {
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static UserService getUserService() {
        return getRetrofitInstance().create(UserService.class);
    }

    public static EventService getEventService() {
        return getRetrofitInstance().create(EventService.class);
    }
}
