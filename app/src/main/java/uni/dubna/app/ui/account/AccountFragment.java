package uni.dubna.app.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uni.dubna.app.databinding.FragmentAccountBinding;
import uni.dubna.app.ui.event.EventInfo;
import uni.dubna.app.ui.event.EventType;

public class AccountFragment extends Fragment {

    private ArrayList<EventInfo> eventInfos = new ArrayList<>();
    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        AccountViewModel accountFragmernt =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Павел Александрович Павлов");
        initEventList();
        AccountAdapter adapter = new AccountAdapter(eventInfos);
        binding.rv.setAdapter(adapter);
        binding.rv.addItemDecoration(new AccountAdapter.ItemDecoration(10));
    }

    private void initEventList() {
        Random random = new Random(1);
        for (int i = 0; i < 20; i++) {
            EventInfo eventInfo = new EventInfo();
            if (random.nextBoolean()) {
                eventInfo.setEventType(EventType.REPLACEMENT);
            } else {
                eventInfo.setEventType(EventType.SHIFT);
            }
            eventInfo.setDate("25 окт 2022");
            eventInfo.setSubject("ТАФЯ");

            eventInfo.setWhoIsReplaced("Анна Петровна Петрова");
            eventInfos.add(eventInfo);
        }
    }

}
