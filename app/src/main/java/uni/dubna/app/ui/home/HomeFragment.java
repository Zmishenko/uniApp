package uni.dubna.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import uni.dubna.app.R;
import uni.dubna.app.databinding.FragmentHomeBinding;
import uni.dubna.app.ui.event.Event;
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
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        deinitialize();

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter(getContext(), eventList));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        deinitialize();

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter(getContext(), eventList));
    }

    private void deinitialize() {
        eventList = new ArrayList<>();
        eventList.add(new Event("Перенос","ФИО преподавателя","почта@uni-dubna.ru","Переношу пару на завтра"));
        eventList.add(new Event("Перенос","ФИО преподавателя","почта@uni-dubna.ru","Переношу пару на завтра"));
        eventList.add(new Event("Замена","ФИО преподавателя","почта@uni-dubna.ru","Завтра меня будет заменять ФИО преподавателя"));
        eventList.add(new Event("Перенос","ФИО преподавателя","почта@uni-dubna.ru","Завтра пары не будет, проведем через неделю"));
        eventList.add(new Event("Замена","ФИО преподавателя","почта@uni-dubna.ru","Завтра меня будет заменять ФИО преподавателя"));
        eventList.add(new Event("Перенос","ФИО преподавателя","почта@uni-dubna.ru","Завтра пары не будет, конкретную дату сообщу позже"));

    }
}