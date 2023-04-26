package uni.dubna.app.ui.add_event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

import uni.dubna.app.R;
import uni.dubna.app.databinding.FragmentAddEventBinding;

public class AddEventFragment extends Fragment {
    private FragmentAddEventBinding binding;
    private AddEventViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddEventViewModel.class);

        binding = FragmentAddEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            binding.datePicker.setDate(viewModel.getDate()*1000);
        }

        binding.datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                viewModel.setDate(view.getDate());

            }
        });

        binding.rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton option = group.findViewById(checkedId);
                String optionTitle = option.getText().toString();
                //todo you may update views

            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long dateL = binding.datePicker.getDate();
                String description = binding.etDescription.getText().toString();
                RadioButton option = binding.rgOptions.findViewById(binding.rgOptions.getCheckedRadioButtonId());
                String optionTitle = option.getText().toString();

                if (optionTitle.equals(getContext().getString(R.string.replacement))) {
                    //todo
                } else {
                    int f = 3;
                    //todo
                }
            }
        });
    }
}
