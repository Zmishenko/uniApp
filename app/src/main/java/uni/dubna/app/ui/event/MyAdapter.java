package uni.dubna.app.ui.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import uni.dubna.app.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private EventCallback eventCallback;
    private List<Event> events;

    public MyAdapter(List<Event> events, EventCallback eventCallback) {
        this.events = events;
        this.eventCallback = eventCallback;
    }

    @NonNull
    @NotNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.getEventName().setText(events.get(position).getEventName());
        holder.getCreatorName().setText(events.get(position).getCreatorName());
        holder.getCreatorEmail().setText(events.get(position).getCreatorEmail());
        holder.getCreatorText().setText(events.get(position).getCreatorMessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventCallback.onClick(events.get(holder.getAdapterPosition()).getEventName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
