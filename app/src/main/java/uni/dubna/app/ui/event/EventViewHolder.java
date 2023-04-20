package uni.dubna.app.ui.event;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import uni.dubna.app.R;

public class EventViewHolder extends RecyclerView.ViewHolder {

    private TextView eventName, creatorName, creatorEmail, creatorText;
    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        this.eventName = itemView.findViewById(R.id.event_name);
        this.creatorName = itemView.findViewById(R.id.event_creator_name);
        this.creatorEmail = itemView.findViewById(R.id.event_creator_email);
        this.creatorText = itemView.findViewById(R.id.event_creator_text);

    }

    public TextView getEventName() {
        return eventName;
    }

    public TextView getCreatorName() {
        return creatorName;
    }

    public TextView getCreatorEmail() {
        return creatorEmail;
    }

    public TextView getCreatorText() {
        return creatorText;
    }

    public void setEventName(TextView eventName) {
        this.eventName = eventName;
    }

    public void setCreatorName(TextView creatorName) {
        this.creatorName = creatorName;
    }

    public void setCreatorEmail(TextView creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public void setCreatorText(TextView creatorText) {
        this.creatorText = creatorText;
    }
}
