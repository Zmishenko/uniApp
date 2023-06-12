package uni.dubna.app.ui.edit_event;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import uni.dubna.app.data.ChangeEventRepository;
import uni.dubna.app.data.model.UserData;
import uni.dubna.app.ui.event.Event;
import uni.dubna.app.ui.event.EventType;

public class EditEventViewModel extends ViewModel {
    private Event commonEvent;
    private UserData userData;
    private ChangeEventRepository repository;

    private boolean isEditEvent;
    public MutableLiveData<Event> eventInfo = new MutableLiveData<>();
    public MutableLiveData<String> showErrorToast = new MutableLiveData<>();
    public MutableLiveData<String> showSuccessToast = new MutableLiveData<>();

    private List<Event> params = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();


    public EditEventViewModel(ChangeEventRepository repository, Event commonEvent, UserData userData) {
        this.repository = repository;
        this.userData = userData;
        this.commonEvent = commonEvent;
        if (commonEvent == null) {
            this.commonEvent = new Event();
            isEditEvent = false;
        } else {
            isEditEvent = true;
        }

        eventInfo.setValue(commonEvent);
    }


    public void changeReason(String reason) {
        commonEvent.setReason(reason);
        eventInfo.setValue(commonEvent);
    }

    public void changeEventType(String eventType) {
        commonEvent.setEventType(EventType.fromString(eventType));
        eventInfo.setValue(commonEvent);
    }

    public void changeEventType(EventType eventType) {
        commonEvent.setEventType(eventType);
    }

    public void changeDateFrom(String dateFrom, int index) {
        //commonEvent.setDateFrom(dateFrom);
        addNewParam(index);
        Event event = params.get(index);
        event.setDateFrom(dateFrom);
    }

    private void addNewParam(int index) {
        if (params.size() - 1 < index) {
            Event event = commonEvent.copy();
            event.setId((long) index);
            params.add(event);
        }
    }


    public void changeDateTo(String dateTo, int index) {
      //  commonEvent.setDateTo(dateTo);
        addNewParam(index);
        Event event = params.get(index);
        event.setDateTo(dateTo);
    }

    public void changeGroup(String group, int index) {
      //  commonEvent.setGroup(group);
        addNewParam(index);
        Event event = params.get(index);
        event.setGroup(group);
    }

    public void changeSubject(String subject, int index) {
      //  commonEvent.setSubject(subject);
        addNewParam(index);
        Event event = params.get(index);
        event.setSubject(subject);
    }

    public void save() {
        if (!isEditEvent) commonEvent.setTeacherName(userData.getFullName());

        disposable.clear();
        disposable.add(Single.create((SingleOnSubscribe<Boolean>) e -> {
                    try {
                        if (isEditEvent) {
                            for (int i = 0 ; i < params.size(); i++) {
                                Event event = params.get(i);
                                repository.changeEvent(event);
                            }
                        } else {
                            for (int i = 0 ; i < params.size(); i++) {
                                Event event = params.get(i);
                                repository.saveEvent(event);
                            }
                        }

                        e.onSuccess(true);
                    } catch (Exception ex) {
                        e.onError(ex);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(t -> {
                    showSuccessToast.setValue("Уведомление успешно " + (isEditEvent ? "изменено" : "добавлено"));
                }, e -> showErrorToast.setValue(e.getMessage())));
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
    }
}
