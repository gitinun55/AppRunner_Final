package com.example.apprunner.Organizer.menu1_home.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.service.controls.actions.FloatAction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu1_home.Adapter.MyRecyclerAdapter_event;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivityAddEvent;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu1_home.Fragment.SecondFragment;
import com.example.apprunner.UserListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment implements View.OnClickListener {

    private FloatAction ic_add;
    private RecyclerView recyclerView;
    Button bt_search_event;
    EditText edt_search_event;
    TextView tv_FName,tv_LName,tv_stuts;
    String first_name,last_name,type;
    int id_user;
    ImageView ic_username;
    List<UserListResponse> userListResponses;
    SwipeRefreshLayout refreshlayout_organizer;

        public static final String url = "http://10.199.66.18:3000/";
//        public static final String url = "http://10.0.2.2:3000/";

    public MainFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentHandle = inflater.inflate(R.layout.fragment_main, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            id_user = getArguments().getInt("id_user");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");

            if(type.equals("นักวิ่ง")){
                fragmentHandle.findViewById(R.id.floating_action_button).setVisibility(View.INVISIBLE);
            }
            refreshlayout_organizer = fragmentHandle.findViewById(R.id.refreshlayout_organizer);
            tv_FName = fragmentHandle.findViewById(R.id.tv_FName);
            tv_LName = fragmentHandle.findViewById(R.id.tv_LName);
            tv_stuts = fragmentHandle.findViewById(R.id.tv_stuts);
            ic_username = fragmentHandle.findViewById(R.id.ic_username);
            tv_FName.setText(first_name);
            tv_LName.setText(last_name);
            tv_stuts.setText(type);

            refreshlayout_organizer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    callData();
                }
            });

            callData();
            pic_profile(id_user);
        }

        fragmentHandle.findViewById(R.id.floating_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivityAddEvent.class);
                intent.putExtra("id_user", id_user);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name",last_name);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });

        edt_search_event = (EditText) fragmentHandle.findViewById(R.id.edt_search_producer);
        bt_search_event = (Button) fragmentHandle.findViewById(R.id.bt_search_event);
        fragmentHandle.findViewById(R.id.bt_search_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_event = edt_search_event.getText().toString();
                if(name_event.equals("")){
                    callData();
                    refreshlayout_organizer.setRefreshing(false);
                }
                MainFragment mainFragament = new MainFragment();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(mainFragament.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                OrganizerAPI service = retrofit.create(OrganizerAPI.class);
                int status = 1;
                Call call = service.search_event(status,name_event);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        refreshlayout_organizer.setRefreshing(false);
                        if(response.isSuccessful()){
                            List<UserListResponse> userListResponses = (List<UserListResponse>) response.body();
                            //Toast.makeText(MainOrganizer.this, "Response_eventList" +"  "+ userListResponses.size(), Toast.LENGTH_SHORT).show();
                            recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                            recyclerView.setLayoutManager(layoutManager);
                            MyRecyclerAdapter_event adapter = new MyRecyclerAdapter_event(requireContext(),userListResponses);
                            recyclerView.setAdapter(adapter);
                            Toast.makeText(requireContext(),"ค้นหา",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(requireActivity(), "ไม่พบงานวิ่ง", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return fragmentHandle;
    }

    private void pic_profile(int id_user) {
        SecondFragment secondFragment = new SecondFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(secondFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.profile_user(id_user);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                if(!resultQuery.getPic_profile().isEmpty() && resultQuery.getPic_profile() != null){
                    Picasso.with(requireContext()).load(resultQuery.getPic_profile()).into(ic_username);
                }else {
                    ic_username.setImageResource(R.drawable.ic_baseline_person_24);
                }
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void callData() {
        MainFragment mainFragment = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        int status = 1;
        Call call = service.getUserName(status);
        call.enqueue(new Callback(){
            @Override
            public void onResponse(Call call, Response response) {
                refreshlayout_organizer.setRefreshing(false);
                if(response.isSuccessful()){
                    List<UserListResponse> userListResponses = (List<UserListResponse>) response.body();
                    recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(layoutManager);
                    MyRecyclerAdapter_event adapter = new MyRecyclerAdapter_event(requireContext(),userListResponses);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(requireContext(), "ไม่พบงานวิ่ง", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View fragmentHandle) {

    }
}