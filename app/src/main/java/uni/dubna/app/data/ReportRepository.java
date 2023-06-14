package uni.dubna.app.data;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import uni.dubna.app.data.model.Filter;
import uni.dubna.app.data.retrofit.ReportService;
import uni.dubna.app.ui.event.Event;

public class ReportRepository {
    private final ReportService reportService;

    public ReportRepository(ReportService reportService) {
        this.reportService = reportService;
    }

    public List<Event> getFilteredEventList(Filter filter) throws Exception {
        Response<List<Event>> eventListResponse = reportService.getFilteredEventList().execute();
        if (eventListResponse.isSuccessful()) {
            List<Event> eventList = eventListResponse.body();
            return eventList;
        } else {
            throw new Exception(eventListResponse.errorBody().string());
        }
    }

    public void createReport(List<Long> eventIdList) throws Exception {
        Response<Void> response = reportService.createReport(eventIdList).execute();
        if (!response.isSuccessful()) {
            throw new Exception(response.errorBody().string());
        }
    }
}
