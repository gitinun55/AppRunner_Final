package com.example.apprunner.Organizer.menu4_check_distance.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu4_check_distance.Adapter.Event_distanceAdapter;
import com.example.apprunner.Organizer.menu1_home.Fragment.MainFragment;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class
DistanceEventFragment extends Fragment {

    String first_name, last_name, type;
    int id_user;
    RecyclerView recycleview_event_organizer;
    SwipeRefreshLayout refreshlayout_status_event;

    public DistanceEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentHandle = inflater.inflate(R.layout.fragment_status_event, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            id_user = getArguments().getInt("id_user");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");
            String email = getArguments().getString("email");
            String password = getArguments().getString("password");

            refreshlayout_status_event = fragmentHandle.findViewById(R.id.refreshlayout_status_event);
            refreshlayout_status_event.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Event_profile();
                }
            });
            Event_profile();
        }
        return fragmentHandle;
    }

    private void Event_profile() {
        MainFragment mainFragment = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.event_payment_organizer(id_user);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                refreshlayout_status_event.setRefreshing(false);
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                recycleview_event_organizer = (RecyclerView) getView().findViewById(R.id.recycler_view_profile_organizer);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recycleview_event_organizer.setLayoutManager(layoutManager);
                Event_distanceAdapter adapter = new Event_distanceAdapter(requireContext(), resultQueries);
                recycleview_event_organizer.setAdapter(adapter);
                Toast.makeText(requireActivity(),"งานวิ่งของคุณ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(requireContext(),"ไม่พบงานวิ่งของคุณ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}