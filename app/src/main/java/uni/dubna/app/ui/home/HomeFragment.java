package uni.dubna.app.ui.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import uni.dubna.app.R;
import uni.dubna.app.databinding.FragmentHomeBinding;
import uni.dubna.app.ui.edit_event.EditEventFragment;
import uni.dubna.app.ui.event.Event;
import uni.dubna.app.ui.event.EventCallback;
import uni.dubna.app.ui.event.MyAdapter;

import java.util.ArrayList;
import java.util.List;

@PackageManagerCompat.UnusedAppRestrictionsStatus
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private List<Event> eventList;
    private RecyclerView recyclerView;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //deinitialize();
        deinitialize();

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter(eventList, new EventCallback() {

            @Override
            public void onClick(String id) {
                Bundle b = new Bundle();
                b.putString(EditEventFragment.EVENT_ID_ARG, id);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_edit_event, b);
            }
        }));
    }


    private void deinitialize() {
        eventList = new ArrayList<>();
        eventList.add(new Event("Перенос1", "ФИО преподавателя", "почта@uni-dubna.ru", "Переношу пару на завтра"));
        eventList.add(new Event("Перенос2", "ФИО преподавателя", "почта@uni-dubna.ru", "Переношу пару на завтра"));
        eventList.add(new Event("Замена3", "ФИО преподавателя", "почта@uni-dubna.ru", "Завтра меня будет заменять ФИО преподавателя"));
        eventList.add(new Event("Перенос4", "ФИО преподавателя", "почта@uni-dubna.ru", "Завтра пары не будет, проведем через неделю"));
        eventList.add(new Event("Замена5", "ФИО преподавателя", "почта@uni-dubna.ru", "Завтра меня будет заменять ФИО преподавателя"));
        eventList.add(new Event("Перенос6", "ФИО преподавателя", "почта@uni-dubna.ru", "Завтра пары не будет, конкретную дату сообщу позже"));

    }
}