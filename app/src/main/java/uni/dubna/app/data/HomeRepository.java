package uni.dubna.app.data;

import android.util.Log;

import java.util.List;

import retrofit2.Response;
import uni.dubna.app.data.retrofit.EventService;
import uni.dubna.app.ui.event.Event;

public class HomeRepository {
    private final EventService eventService;

    public HomeRepository(EventService eventService) {
        this.eventService = eventService;
    }

    public List<Event> getEventList() throws Exception {
        Response<List<Event>> eventListResponse = eventService.getEventList().execute();
        if (eventListResponse.isSuccessful()) {
            List<Event> eventList = eventListResponse.body();
            return eventList;
        } else {
            throw new Exception(eventListResponse.errorBody().string());
        }
    }
}
