package uni.dubna.app.ui.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import uni.dubna.app.R;
import uni.dubna.app.data.model.Role;
import uni.dubna.app.databinding.EventInfoItemBinding;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {
    public class EventViewHolder extends RecyclerView.ViewHolder {
        public EventInfoItemBinding binding;

        public EventViewHolder(@NonNull EventInfoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private EventCallback eventCallback;
    private Role role;
    private boolean teacherAllowed;

    public EventAdapter(EventCallback eventCallback, Role role, boolean teacherAllowed) {
        super(new MyDiffUtils());
        this.eventCallback = eventCallback;
        this.role = role;
        this.teacherAllowed = teacherAllowed;
    }

    @NonNull
    @NotNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new EventViewHolder(EventInfoItemBinding.bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_info_item, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Event event = getCurrentList().get(position);
        holder.binding.tvSubject.setText(context.getString(R.string.event_subject, event.getSubject()));
        holder.binding.tvEventType.setText(event.getEventType().toString());
        holder.binding.tvReason.setText(context.getString(R.string.event_reason, event.getReason()));
        holder.binding.tvGroup.setText(context.getString(R.string.event_group, event.getGroup()));

        switch (event.getEventType()) {
            case REPLACEMENT:
                holder.binding.tvDate.setText(event.getDateTo() + " занятие будет проводить " + event.getTeacherName());
                break;
            case SHIFT:
                holder.binding.tvDate.setText("С " + event.getDateFrom() + " занятие переносится на " + event.getDateTo());
                break;
            case REPLACEMENT_SHIFT:
                holder.binding.tvDate.setText("С " + event.getDateFrom() + " занятие переносится" + (event.getDateTo() != null ? " на " + event.getDateTo() : ", дата проведения будет указана позже") + "\nПроводить занятие будет " + event.getTeacherName());
                break;
            default:
                throw new UnsupportedOperationException("event " + event.getEventType() + " is not implemented");
        }
        holder.binding.ivEdit.setVisibility(role == Role.ADMIN || (teacherAllowed && role == Role.TEACHER) ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(v -> {
            Long id = getCurrentList().get(holder.getAdapterPosition()).getId();
            eventCallback.onClick(id);
        });
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }
}
