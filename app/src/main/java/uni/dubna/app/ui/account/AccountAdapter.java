package uni.dubna.app.ui.account;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uni.dubna.app.R;
import uni.dubna.app.databinding.EventInfoItemBinding;
import uni.dubna.app.ui.event.EventInfo;
import uni.dubna.app.ui.event.EventType;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private List<EventInfo> events;

    public AccountAdapter(List<EventInfo> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_info_item, parent, false);
        EventInfoItemBinding binding = EventInfoItemBinding.bind(view);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        EventInfo event = events.get(position);
        holder.binding.tvDate.setText(context.getResources().getString(R.string.event_when, event.getDate()));
        holder.binding.tvSubject.setText(context.getResources().getString(R.string.event_subject, event.getSubject()));
        if (event.getEventType() == EventType.REPLACEMENT) {
            holder.binding.tvEventType.setText(context.getResources().getString(R.string.replacement));
            holder.binding.tvWho.setVisibility(View.VISIBLE);
            holder.binding.tvWho.setText(context.getResources().getString(R.string.event_who, event.getWhoIsReplaced()));
        } else {
            holder.binding.tvEventType.setText(context.getResources().getString(R.string.shift));
            holder.binding.tvWho.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EventInfoItemBinding binding;

        public ViewHolder(EventInfoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class ItemDecoration extends RecyclerView.ItemDecoration {
        private int bottom;
        public ItemDecoration(int bottomSpacing){
            bottom = bottomSpacing;
        }
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom =bottom;
        }
    }


}
