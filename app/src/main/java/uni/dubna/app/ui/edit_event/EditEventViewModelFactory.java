package uni.dubna.app.ui.edit_event;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import uni.dubna.app.data.LoginDataSource;
import uni.dubna.app.data.LoginRepository;
import uni.dubna.app.ui.login.LoginViewModel;

public class EditEventViewModelFactory implements ViewModelProvider.Factory {
    private String eventId;
    public EditEventViewModelFactory(String eventId){
        this.eventId = eventId;
    }
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditEventViewModel.class)) {
            return (T) new EditEventViewModel(eventId);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
