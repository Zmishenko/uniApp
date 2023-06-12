package uni.dubna.app.ui.edit_event;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import uni.dubna.app.MyApplication;
import uni.dubna.app.data.ChangeEventRepository;
import uni.dubna.app.data.LoginDataSource;
import uni.dubna.app.data.LoginRepository;
import uni.dubna.app.data.model.UserData;
import uni.dubna.app.ui.event.Event;
import uni.dubna.app.ui.login.LoginViewModel;

public class EditEventViewModelFactory implements ViewModelProvider.Factory {
    private Event event;
    private UserData userData;

    public EditEventViewModelFactory(Event event, UserData userData) {
        this.event = event;
        this.userData = userData;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditEventViewModel.class)) {
            return (T) new EditEventViewModel(new ChangeEventRepository(MyApplication.getEventService()), event, userData);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
