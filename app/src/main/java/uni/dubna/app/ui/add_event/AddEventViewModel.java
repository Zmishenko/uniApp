package uni.dubna.app.ui.add_event;

import androidx.lifecycle.ViewModel;

public class AddEventViewModel extends ViewModel {

    private Long date = System.currentTimeMillis();

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
