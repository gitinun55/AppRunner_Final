package com.example.apprunner.User.menu7_check_reward.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apprunner.User.menu7_check_reward.Adapter.EventCheckStatusAdapter;
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

public class CheckStatusRewardFragment extends Fragment {

    String first_name, last_name, type;
    int id_user,status_reward;
    RecyclerView recycler_check_status_reward;
    SwipeRefreshLayout refreshlayout_status_reward;

    public CheckStatusRewardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentHandle = inflater.inflate(R.layout.fragment_check_status_reward, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            id_user = getArguments().getInt("id_user");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");

            refreshlayout_status_reward = fragmentHandle.findViewById(R.id.refreshlayout_status_reward);
            refreshlayout_status_reward.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    check_status_reward();
                }
            });
            check_status_reward();
        }
        return fragmentHandle;
    }

    private void check_status_reward() {
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
                refreshlayout_status_reward.setRefreshing(false);
                Toast.makeText(requireContext(),"งานวิ่งของคุณ",Toast.LENGTH_SHORT).show();
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                for(int i=0;i<resultQueries.size();i++){
                    status_reward = resultQueries.get(i).getStatus_reward();
                    if(status_reward == 1){
                        recycler_check_status_reward = (RecyclerView) getView().findViewById(R.id.recycler_check_status_reward);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                        recycler_check_status_reward.setLayoutManager(layoutManager);
                        EventCheckStatusAdapter adapter = new EventCheckStatusAdapter(requireContext(), resultQueries);
                        recycler_check_status_reward.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(requireContext(),"ไม่พบงานวิ่ง",Toast.LENGTH_SHORT).show();
            }
        });
    }
}