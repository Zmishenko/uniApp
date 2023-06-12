package uni.dubna.app.ui.edit_event;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.gson.Gson;

import java.util.Objects;

import uni.dubna.app.R;
import uni.dubna.app.data.model.UserData;
import uni.dubna.app.databinding.FragmentAddEventBinding;
import uni.dubna.app.databinding.OptionItemBinding;
import uni.dubna.app.ui.event.Event;

public class EditEventFragment extends Fragment {
    private FragmentAddEventBinding binding;
    private EditEventViewModel viewModel;

    private Event event;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String eventJson = getArguments() == null ? null : requireArguments().getString(EVENT_ARG);
        event = eventJson == null ? null : new Gson().fromJson(eventJson, Event.class);
        String userJson = requireArguments().getString(USER_ARG);
        UserData userData = new Gson().fromJson(userJson, UserData.class);

        viewModel = new EditEventViewModelFactory(event, userData).create(EditEventViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addFab.setOnClickListener(v -> {
            View options = LayoutInflater.from(requireContext()).inflate(R.layout.params_options, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    12,
                    getResources().getDisplayMetrics()
            );
            params.setMargins(0, px, 0, 0);
            options.setLayoutParams(params);
            binding.llParams.addView(options);
        });

        hideMenu();
        observeToasts();
        initListeners();

    }

    private void observeToasts() {
        viewModel.showSuccessToast.observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show();
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.popBackStack();
        });
        viewModel.showErrorToast.observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show();
        });
    }

    private OptionItemBinding getSelectedReason() {
        if (binding.reasonHealth.getRoot().getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.uni)) {
            return binding.reasonHealth;
        } else if (binding.reasonRest.getRoot().getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.uni)) {
            return binding.reasonRest;
        } else if (binding.reasonCommand.getRoot().getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.uni)) {
            return binding.reasonCommand;
        } else if (binding.reasonOther.getRoot().getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.uni)) {
            return binding.reasonOther;
        }
        return null;
    }

    private OptionItemBinding getReason(String title) {
        if (Objects.equals(title, getResources().getString(R.string.reason_health))) {
            return binding.reasonHealth;
        } else if (Objects.equals(title, getResources().getString(R.string.reason_rest))) {
            return binding.reasonRest;
        } else if (Objects.equals(title, getResources().getString(R.string.reason_command))) {
            return binding.reasonCommand;
        } else if (Objects.equals(title, getResources().getString(R.string.reason_other))) {
            return binding.reasonOther;
        }
        return null;
    }

    private OptionItemBinding getSelectedEvent() {
        if (binding.eventRepl.getRoot().getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.uni)) {
            return binding.eventRepl;
        } else if (binding.eventShift.getRoot().getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.uni)) {
            return binding.eventShift;
        } else if (binding.eventShiftRepl.getRoot().getCardBackgroundColor().getDefaultColor() == getResources().getColor(R.color.uni)) {
            return binding.eventShiftRepl;
        }
        return null;
    }

    private OptionItemBinding getEvent(String title) {
        if (Objects.equals(title, getResources().getString(R.string.shift))) {
            return binding.eventShift;
        } else if (Objects.equals(title, getResources().getString(R.string.replacement))) {
            return binding.eventRepl;
        } else if (Objects.equals(title, getResources().getString(R.string.shift_with_replacement))) {
            return binding.eventShiftRepl;
        }
        return null;
    }

    private void initListeners() {

        if (event != null) {
            viewModel.changeEventType(event.getEventType());
            viewModel.changeReason(event.getReason());
        } else {
            viewModel.changeReason(getString(R.string.reason_health));
            viewModel.changeEventType(getString(R.string.shift));
        }


        viewModel.eventInfo.observe(getViewLifecycleOwner(), event -> {
            if (getSelectedReason() == null || event.getReason() != getSelectedReason().optionItemTitle.getText()) {
                changeReasonBackground(getReason(event.getReason()));
            }
            if (getSelectedEvent() == null || event.getEventType().toString() != getSelectedEvent().optionItemTitle.getText()) {
                Log.d("PresentLayer", "no");
                changeEventBackground(getEvent(event.getEventType().toString()));
            }
        });

        binding.ivShow.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(binding.cvReason, new AutoTransition());
            if (binding.clReason.getVisibility() == View.VISIBLE) {
                binding.clReason.setVisibility(View.GONE);
                binding.ivShow.setImageResource(R.drawable.ic_expand_more);
            } else {
                binding.clReason.setVisibility(View.VISIBLE);
                binding.ivShow.setImageResource(R.drawable.ic_expand_less);
            }
        });
        binding.reasonHealth.optionItemTitle.setText(getString(R.string.reason_health));
        binding.reasonHealth.getRoot().setOnClickListener(v -> {
            viewModel.changeReason(getString(R.string.reason_health));
        });

        binding.reasonRest.optionItemTitle.setText(getString(R.string.reason_rest));
        binding.reasonRest.getRoot().setOnClickListener(v -> {
            viewModel.changeReason(getString(R.string.reason_rest));

        });

        binding.reasonCommand.optionItemTitle.setText(getString(R.string.reason_command));
        binding.reasonCommand.getRoot().setOnClickListener(v -> {
            viewModel.changeReason(getString(R.string.reason_command));

        });

        binding.reasonOther.optionItemTitle.setText(getString(R.string.reason_other));
        binding.reasonOther.getRoot().setOnClickListener(v -> {
            viewModel.changeReason(getString(R.string.reason_other));

        });


        binding.ivEventShow.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(binding.cvEvent, new AutoTransition());
            if (binding.clEventReason.getVisibility() == View.VISIBLE) {
                binding.clEventReason.setVisibility(View.GONE);
                binding.ivEventShow.setImageResource(R.drawable.ic_expand_more);
            } else {
                binding.clEventReason.setVisibility(View.VISIBLE);
                binding.ivEventShow.setImageResource(R.drawable.ic_expand_less);
            }
        });

        binding.eventShift.optionItemTitle.setText(getString(R.string.shift));
        binding.eventShift.getRoot().setOnClickListener(v -> {
            viewModel.changeEventType(getString(R.string.shift));
        });

        binding.eventRepl.optionItemTitle.setText(getString(R.string.replacement));
        binding.eventRepl.getRoot().setOnClickListener(v -> {
            viewModel.changeEventType(getString(R.string.replacement));
        });

        binding.eventShiftRepl.optionItemTitle.setText(getString(R.string.shift_with_replacement));
        binding.eventShiftRepl.getRoot().setOnClickListener(v -> {
            viewModel.changeEventType(getString(R.string.shift_with_replacement));
        });

        if (event != null) {
            binding.cvParams.cvDateFrom.paramItemEt.setText(event.getDateFrom());
            binding.cvParams.cvDateTo.paramItemEt.setText(event.getDateTo());
            binding.cvParams.cvDis.paramItemEt.setText(event.getSubject());
            binding.cvParams.cvGroup.paramItemEt.setText(event.getGroup());
        }


        binding.save.setOnClickListener(v -> {
            for (int i = 0; i < binding.llParams.getChildCount(); i++) {
                View view = binding.llParams.getChildAt(i);
                String dateFrom = ((TextView) view.findViewById(R.id.cv_date_from).findViewById(R.id.param_item_et)).getText().toString();
                String dateTo = ((TextView) view.findViewById(R.id.cv_date_to).findViewById(R.id.param_item_et)).getText().toString();
                String group = ((TextView) view.findViewById(R.id.cv_group).findViewById(R.id.param_item_et)).getText().toString();
                String subject = ((TextView) view.findViewById(R.id.cv_dis).findViewById(R.id.param_item_et)).getText().toString();

                viewModel.changeDateFrom(dateFrom, i);
                viewModel.changeDateTo(dateTo, i);
                viewModel.changeGroup(group, i);
                viewModel.changeSubject(subject, i);
            }

            viewModel.save();
        });

    }

    @SuppressLint("NewApi")
    private void changeReasonBackground(OptionItemBinding binding) {
        this.binding.reasonHealth.optionItemCircle.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.cardview_background)));
        this.binding.reasonHealth.getRoot().setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));

        this.binding.reasonRest.optionItemCircle.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.cardview_background)));
        this.binding.reasonRest.getRoot().setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));

        this.binding.reasonCommand.optionItemCircle.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.cardview_background)));
        this.binding.reasonCommand.getRoot().setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));

        this.binding.reasonOther.optionItemCircle.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.cardview_background)));
        this.binding.reasonOther.getRoot().setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));

        binding.getRoot().setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.uni));
        binding.optionItemCircle.setImageTintList(ColorStateList.valueOf(requireContext().getColor(R.color.white)));


    }

    @SuppressLint("NewApi")
    private void changeEventBackground(OptionItemBinding binding) {
        this.binding.eventRepl.optionItemCircle.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.cardview_background)));
        this.binding.eventRepl.getRoot().setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));

        this.binding.eventShift.optionItemCircle.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.cardview_background)));
        this.binding.eventShift.getRoot().setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));

        this.binding.eventShiftRepl.optionItemCircle.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.cardview_background)));
        this.binding.eventShiftRepl.getRoot().setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white));

        binding.getRoot().setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.uni));
        binding.optionItemCircle.setImageTintList(ColorStateList.valueOf(requireContext().getColor(R.color.white)));


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
        }, getViewLifecycleOwner());
    }

    public final static String EVENT_ARG = "event_info";
    public final static String USER_ARG = "user";
}
