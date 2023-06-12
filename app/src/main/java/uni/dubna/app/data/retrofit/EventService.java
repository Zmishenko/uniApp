package uni.dubna.app.data.retrofit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import uni.dubna.app.data.model.UserData;
import uni.dubna.app.ui.event.Event;

public interface EventService {
    @GET("/event/get-all")
    Call<List<Event>> getEventList();

    @POST("/event/save")
    Call<Void> saveEvent(@Body Event event);

    @POST("/event/change")
    Call<Void> changeEvent(@Body Event event);
}
