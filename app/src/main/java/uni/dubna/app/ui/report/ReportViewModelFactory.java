package uni.dubna.app.ui.report;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import uni.dubna.app.MyApplication;
import uni.dubna.app.data.HomeRepository;
import uni.dubna.app.data.ReportRepository;
import uni.dubna.app.ui.home.HomeViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ReportViewModelFactory implements ViewModelProvider.Factory {
    public ReportViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReportViewModel.class)) {
            return (T) new ReportViewModel(new ReportRepository(MyApplication.getReportService()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}