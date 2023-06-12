package uni.dubna.app.ui.home;

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

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private HomeViewModel viewModel;
    private EventAdapter adapter;

    private UserData user;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.nav_edit_event, b);
            }

        }, user.getRole(), false);
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

    private void deinitialize() {
//        eventList = new ArrayList<>();
//        eventList.add(new Event("Перенос1", "ФИО преподавателя", "почта@uni-dubna.ru", "Переношу пару на завтра"));
//        eventList.add(new Event("Перенос2", "ФИО преподавателя", "почта@uni-dubna.ru", "Переношу пару на завтра"));
//        eventList.add(new Event("Замена3", "ФИО преподавателя", "почта@uni-dubna.ru", "Завтра меня будет заменять ФИО преподавателя"));
//        eventList.add(new Event("Перенос4", "ФИО преподавателя", "почта@uni-dubna.ru", "Завтра пары не будет, проведем через неделю"));
//        eventList.add(new Event("Замена5", "ФИО преподавателя", "почта@uni-dubna.ru", "Завтра меня будет заменять ФИО преподавателя"));
//        eventList.add(new Event("Перенос6", "ФИО преподавателя", "почта@uni-dubna.ru", "Завтра пары не будет, конкретную дату сообщу позже"));

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