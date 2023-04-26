package uni.dubna.app.ui.edit_event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import uni.dubna.app.R;
import uni.dubna.app.databinding.ChangeEventItemBinding;
import uni.dubna.app.ui.event.EventInfo;
import uni.dubna.app.ui.event.EventType;

public class EditEventFragment extends Fragment {
    private ChangeEventItemBinding binding;
    private EditEventViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String eventId = requireArguments().getString(EVENT_ID_ARG);
        viewModel = new EditEventViewModelFactory(eventId).create(EditEventViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangeEventItemBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hideMenu();

        viewModel.eventInfo.observe(getViewLifecycleOwner(), new Observer<EventInfo>() {
            @Override
            public void onChanged(EventInfo eventInfo) {
                binding.etDescription.setText(eventInfo.getDescription());
                if (eventInfo.getEventType() == EventType.REPLACEMENT) {
                    binding.rbReplace.setChecked(true);
                }else {
                    binding.rbShift.setChecked(false);
                }
                binding.save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo
                    }
                });
            }
        });
    }

    private void hideMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.action_filter);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },getViewLifecycleOwner());
    }

    public final static String EVENT_ID_ARG="id";
}
