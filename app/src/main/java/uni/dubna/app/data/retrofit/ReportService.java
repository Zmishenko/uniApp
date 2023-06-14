package uni.dubna.app.data.retrofit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import uni.dubna.app.ui.event.Event;

public interface ReportService {

    @POST("/report/create")
    Call<Void> createReport(@Body List<Long> eventIdList);

    @GET("/event/get-all")
    Call<List<Event>> getFilteredEventList();
}
