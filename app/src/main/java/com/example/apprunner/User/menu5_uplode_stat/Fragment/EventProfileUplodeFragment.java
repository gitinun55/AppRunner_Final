package com.example.apprunner.User.menu5_uplode_stat.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apprunner.User.menu5_uplode_stat.Adapter.EventUserAdapter;
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

public class EventProfileUplodeFragment extends Fragment {

    String first_name, last_name, type;
    TextView text_km2;
    int id_user;
    RecyclerView recycler_view_event_list;
    SwipeRefreshLayout refreshlayout_profile_uplode;

    public EventProfileUplodeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentHandle = inflater.inflate(R.layout.fragment_event_profile_uplode, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            id_user = getArguments().getInt("id_user");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");;

            refreshlayout_profile_uplode = fragmentHandle.findViewById(R.id.refreshlayout_profile_uplode);
            refreshlayout_profile_uplode.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                refreshlayout_profile_uplode.setRefreshing(false);
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                recycler_view_event_list = (RecyclerView) getView().findViewById(R.id.recycler_profile);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recycler_view_event_list.setLayoutManager(layoutManager);
                EventUserAdapter adapter = new EventUserAdapter(requireContext(), resultQueries);
                recycler_view_event_list.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {

            }
        });
    }
}