package uni.dubna.app.ui.edit_event;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    private Event event;
    private UserData userData;
    private ChangeEventRepository repository;

    private boolean isEditEvent;
    public MutableLiveData<Event> eventInfo = new MutableLiveData<>();
    public MutableLiveData<String> showErrorToast = new MutableLiveData<>();
    public MutableLiveData<String> showSuccessToast = new MutableLiveData<>();


    private CompositeDisposable disposable = new CompositeDisposable();


    public EditEventViewModel(ChangeEventRepository repository, Event event, UserData userData) {
        this.repository = repository;
        this.userData = userData;
        this.event = event;
        if (event == null) {
            this.event = new Event();
            isEditEvent = false;
        } else {
            isEditEvent = true;
        }

        eventInfo.setValue(event);
    }


    public void changeReason(String reason) {
        event.setReason(reason);
        eventInfo.setValue(event);
    }

    public void changeEventType(String eventType) {
        event.setEventType(EventType.fromString(eventType));
        eventInfo.setValue(event);
    }

    public void changeEventType(EventType eventType) {
        event.setEventType(eventType);
    }

    public void changeDateFrom(String dateFrom) {
        event.setDateFrom(dateFrom);
    }
    public void changeDateTo(String dateTo) {
        event.setDateTo(dateTo);
    }

    public void changeGroup(String group) {
        event.setGroup(group);
    }
    public void changeSubject(String subject) {
        event.setSubject(subject);
    }

    public void save() {
        if (!isEditEvent) event.setTeacherName(userData.getFullName());

        disposable.clear();
        disposable.add(Single.create((SingleOnSubscribe<Boolean>) e -> {
                    try {
                        if (isEditEvent) {
                            repository.changeEvent(event);
                        } else {
                            repository.saveEvent(event);
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
