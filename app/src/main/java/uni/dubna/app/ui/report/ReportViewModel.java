package uni.dubna.app.ui.report;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import uni.dubna.app.data.ReportRepository;
import uni.dubna.app.data.model.Filter;
import uni.dubna.app.ui.event.Event;

public class ReportViewModel extends ViewModel {

    private final ReportRepository reportRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<List<Event>> eventList = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<String> showErrorToast = new MutableLiveData<>();

    public MutableLiveData<String> showSuccessToast = new MutableLiveData<>();

    public ReportViewModel(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void requestEventList(Filter filter) {
        disposable.clear();
        disposable.add(Single.create((SingleOnSubscribe<List<Event>>) e -> {
                    try {
                        List<Event> eventList1 = reportRepository.getFilteredEventList(filter);

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

    public void createReport() {
        disposable.clear();
        disposable.add(Single.create((SingleOnSubscribe<Boolean>) e -> {
                    try {
                        List<Long> eventIdList = new ArrayList<>();
                        List<Event> _eventList = eventList.getValue();
                        for (Event event : _eventList) {
                            eventIdList.add(event.getId());
                        }
                        reportRepository.createReport(eventIdList);

                        e.onSuccess(true);
                    } catch (Exception ex) {
                        e.onError(ex);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(t -> {
                    showSuccessToast.setValue("Отчет отправлен на почту");
                }, e -> showErrorToast.setValue(e.getMessage())));
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
    }


}