package uni.dubna.app.data;

import retrofit2.Response;
import uni.dubna.app.data.retrofit.EventService;
import uni.dubna.app.ui.event.Event;

public class ChangeEventRepository {
    private final EventService eventService;

    public ChangeEventRepository(EventService eventService) {
        this.eventService = eventService;
    }

   public void saveEvent(Event event) throws Exception {

        Response response = eventService.saveEvent(event).execute();
        if (!response.isSuccessful()) {
            throw new Exception(response.errorBody().string());
        }
    }

    public void changeEvent(Event event) throws Exception {

        Response response = eventService.changeEvent(event).execute();
        if (!response.isSuccessful()) {
            throw new Exception(response.errorBody().string());
        }
    }
}
