package uni.dubna.app.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import uni.dubna.app.data.HomeRepository;
import uni.dubna.app.ui.event.Event;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final HomeRepository homeRepository;
    private CompositeDisposable disposable = new CompositeDisposable();


    public MutableLiveData<List<Event>> eventList = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<String> showErrorToast = new MutableLiveData<>();

    public HomeViewModel(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public void requestEventList() {
        disposable.clear();
        disposable.add(Single.create((SingleOnSubscribe<List<Event>>) e -> {
                    try {
                        List<Event> eventList1 = homeRepository.getEventList();

                        e.onSuccess(eventList1);
                    } catch (Exception ex) {
                        e.onError(ex);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(t -> {
                    eventList.setValue(t);
                }, e -> showErrorToast.setValue(e.getMessage())));
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
    }

}