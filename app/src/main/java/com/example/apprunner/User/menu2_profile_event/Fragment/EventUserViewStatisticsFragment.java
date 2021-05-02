package com.example.apprunner.User.menu2_profile_event.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.User.menu2_profile_event.Adapter.MenuEventUserAdapter;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventUserViewStatisticsFragment extends Fragment {

    String first_name, last_name, type;
    int id_user;
    RecyclerView recycler_menu_event_user;
    TextView tv_producer_name_menu_event_user,tv_event_name_menu_event_user;
    SwipeRefreshLayout refreshlayout_statistics;

    public EventUserViewStatisticsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentHandle = inflater.inflate(R.layout.fragment_event_user_view_statistics, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            id_user = getArguments().getInt("id_user");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");

            refreshlayout_statistics = fragmentHandle.findViewById(R.id.refreshlayout_statistics);
            refreshlayout_statistics.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Event_list();
                }
            });
            Event_list();
        }
        return fragmentHandle;
    }

    private void Event_list() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.event_list(id_user);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                refreshlayout_statistics.setRefreshing(false);
                Toast.makeText(requireContext(),"งานวิ่งของคุณ",Toast.LENGTH_SHORT).show();
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                recycler_menu_event_user = (RecyclerView) getView().findViewById(R.id.recycler_menu_event_user);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recycler_menu_event_user.setLayoutManager(layoutManager);
                MenuEventUserAdapter adapter = new MenuEventUserAdapter(requireContext(), resultQueries);
                recycler_menu_event_user.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(requireContext(),"ไม่พบงานวิ่งของคุณ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}