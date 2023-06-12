package uni.dubna.app.ui.account;

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

import com.google.gson.Gson;

import java.util.Objects;

import uni.dubna.app.MainActivity;
import uni.dubna.app.R;
import uni.dubna.app.data.model.Role;
import uni.dubna.app.data.model.UserData;
import uni.dubna.app.databinding.FragmentHomeBinding;
import uni.dubna.app.ui.edit_event.EditEventFragment;
import uni.dubna.app.ui.event.Event;
import uni.dubna.app.ui.event.EventAdapter;
import uni.dubna.app.ui.event.MyItemDecoration;
import uni.dubna.app.ui.home.HomeViewModel;
import uni.dubna.app.ui.home.HomeViewModelFactory;

public class AccountFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private HomeViewModel viewModel;
    private EventAdapter adapter;

    private UserData user;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(requireView());

        if (user.getRole() == Role.ADMIN || user.getRole() == Role.TEACHER) {
            binding.homeFab.setVisibility(View.VISIBLE);
            binding.homeFab.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putString(EditEventFragment.USER_ARG, new Gson().toJson(user));

                navController.navigate(R.id.nav_add_event, b);
            });
        }
        viewModel = new ViewModelProvider(this, new HomeViewModelFactory()).get(HomeViewModel.class);

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

        }, user.getRole(), true);
        recyclerView.setAdapter(adapter);

        viewModel.requestEventList();

        viewModel.eventList.observe(getViewLifecycleOwner(), events -> {
            adapter.submitList(events);
        });

        viewModel.showErrorToast.observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = ((MainActivity) requireActivity()).user;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
