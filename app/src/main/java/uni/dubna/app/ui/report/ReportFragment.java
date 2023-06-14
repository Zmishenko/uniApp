package uni.dubna.app.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import uni.dubna.app.MainActivity;
import uni.dubna.app.R;
import uni.dubna.app.data.model.Role;
import uni.dubna.app.data.model.UserData;
import uni.dubna.app.databinding.FragmentReportBinding;
import uni.dubna.app.ui.edit_event.EditEventFragment;
import uni.dubna.app.ui.event.Event;
import uni.dubna.app.ui.event.EventAdapter;
import uni.dubna.app.ui.event.EventType;
import uni.dubna.app.ui.event.MyItemDecoration;

public class ReportFragment extends Fragment {

    private FragmentReportBinding binding;
    private RecyclerView recyclerView;
    private ReportViewModel viewModel;
    private EventAdapter adapter;

    private UserData user;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(requireView());

        viewModel = new ViewModelProvider(this, new ReportViewModelFactory()).get(ReportViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new MyItemDecoration());
        adapter = new EventAdapter(id -> {
            if (user.getRole() != Role.STUDENT) {
                Bundle b = new Bundle();
                Event event = null;
                for (int i = 0; i < adapter.getCurrentList().size(); i++) {
                    if (Objects.equals(adapter.getCurrentList().get(i).getId(), id)) {
                        event = adapter.getCurrentList().get(i);
                    }
                }
                b.putString(EditEventFragment.EVENT_ARG, new Gson().toJson(event));
                b.putString(EditEventFragment.USER_ARG, new Gson().toJson(user));

                navController.navigate(R.id.nav_edit_event, b);
            }

        }, user.getRole(), false);
        recyclerView.setAdapter(adapter);

        viewModel.eventList.observe(getViewLifecycleOwner(), events -> {
            adapter.submitList(events);
            binding.btnCreate.setEnabled(!events.isEmpty());
        });

        viewModel.showErrorToast.observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
        });

        viewModel.showSuccessToast.observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show();
            navController.popBackStack();
        });

        initViewListeners();
    }

    private void initViewListeners() {
        binding.btnAccept.setOnClickListener(v -> {
            Event event = new Event();

            String groups = binding.cvGroup.paramItemEt.getText().toString();
            if (!groups.isEmpty()) {
                event.setGroup(groups);
            }
            String eventType = binding.cvEventType.paramItemEt.getText().toString();
            if (!eventType.isEmpty()) {
                if (!EventType.contains(eventType)) {
                    Toast.makeText(requireContext(), "Неправильный тип уведомления", Toast.LENGTH_LONG).show();
                    return;
                }
                event.setEventType(EventType.fromString(eventType));
            }
            String teacher = binding.cvTeacher.paramItemEt.getText().toString();
            if (!teacher.isEmpty()) {
                event.setTeacherName(teacher);
            }
            String date = binding.cvDate.paramItemEt.getText().toString();
            if (!date.isEmpty()) {
                event.setDateFrom(date);
            }
            String reason = binding.cvReason.paramItemEt.getText().toString();
            if (!reason.isEmpty()) {
                event.setReason(reason);
            }

            viewModel.requestEventList(event);
        });
        binding.btnCreate.setOnClickListener(v -> viewModel.createReport());

        binding.btnClear.setOnClickListener(v -> {
            binding.cvGroup.paramItemEt.setText("");
            binding.cvEventType.paramItemEt.setText("");
            binding.cvTeacher.paramItemEt.setText("");
            binding.cvDate.paramItemEt.setText("");
            binding.cvReason.paramItemEt.setText("");
        });

        binding.ivShow.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(binding.cvFilter, new AutoTransition());
            if (binding.clFilter.getVisibility() == View.VISIBLE) {
                binding.clFilter.setVisibility(View.GONE);
                binding.ivShow.setImageResource(R.drawable.ic_expand_more);
            } else {
                binding.clFilter.setVisibility(View.VISIBLE);
                binding.ivShow.setImageResource(R.drawable.ic_expand_less);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = ((MainActivity) requireActivity()).user;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}