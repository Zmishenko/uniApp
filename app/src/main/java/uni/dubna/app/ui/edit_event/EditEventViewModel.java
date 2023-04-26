package uni.dubna.app.ui.edit_event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import uni.dubna.app.ui.event.Event;
import uni.dubna.app.ui.event.EventInfo;
import uni.dubna.app.ui.event.EventType;

public class EditEventViewModel extends ViewModel {
    private String eventId;
    public MutableLiveData<EventInfo> eventInfo = new MutableLiveData<>();

    public EditEventViewModel(String eventId) {
        eventId = eventId;
        retrieveEventInfo();
    }

    private void retrieveEventInfo() {
        //todo getDataFromServer(eventId)
        eventInfo.postValue(new EventInfo(EventType.REPLACEMENT, "ТАФЯ", "Анна Петровна Петрова", "23.02.2023","Переношу пару на завтра"));
    }
}
