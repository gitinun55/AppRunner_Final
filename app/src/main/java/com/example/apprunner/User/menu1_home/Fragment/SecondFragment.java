package com.example.apprunner.User.menu1_home.Fragment;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu1_home.Adapter.MyRecyclerAdapter_event;
import com.example.apprunner.Organizer.menu1_home.Fragment.MainFragment;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu4_check_payment.Activity.DetailPaymentUserActivity;
import com.example.apprunner.UserListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondFragment extends Fragment  {

    TextView tv_FName,tv_LName,tv_stuts;
    String first_name,last_name,type,name_event;
    ImageView ic_username;
    Button bt_search_event;
    EditText edt_search_event;
    private RecyclerView recyclerView;
    List<UserListResponse> userListResponses;
    int id_user;
    SwipeRefreshLayout refreshlayout_second;

        public static final String url = "http://10.199.66.18:3000/";
//        public static final String url = "http://10.0.2.2:3000/";

    public SecondFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentHandle = inflater.inflate(R.layout.fragment_second, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            id_user = getArguments().getInt("id_user");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");

            refreshlayout_second = fragmentHandle.findViewById(R.id.refreshlayout_second);
            tv_FName = fragmentHandle.findViewById(R.id.tv_FName_user);
            tv_LName = fragmentHandle.findViewById(R.id.tv_LName_user);
            tv_stuts = fragmentHandle.findViewById(R.id.tv_stuts_user);
            ic_username = fragmentHandle.findViewById(R.id.ic_username);
            tv_FName.setText(first_name);
            tv_LName.setText(last_name);
            tv_stuts.setText(type);

            refreshlayout_second.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    callData();
                }
            });
            callData();
            pic_profile(id_user);
        }

        edt_search_event = (EditText) fragmentHandle.findViewById(R.id.edt_search_user);
        bt_search_event = (Button) fragmentHandle.findViewById(R.id.bt_search_event);
        fragmentHandle.findViewById(R.id.bt_search_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_event = edt_search_event.getText().toString();
                if(name_event.equals("")){
                    callData();
                    refreshlayout_second.setRefreshing(false);
                }
                SecondFragment secondFragment = new SecondFragment();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(secondFragment.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                OrganizerAPI service = retrofit.create(OrganizerAPI.class);
                int status = 1;
                Call call = service.search_event(status,name_event);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        refreshlayout_second.setRefreshing(false);
                        if(response.isSuccessful()){
                            List<UserListResponse> userListResponses = (List<UserListResponse>) response.body();
                            recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view_user);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                            recyclerView.setLayoutManager(layoutManager);
                            MyRecyclerAdapter_event adapter = new MyRecyclerAdapter_event(requireContext(),userListResponses);
                            recyclerView.setAdapter(adapter);
                            Toast.makeText(requireActivity(), "ค้นหา", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(requireContext(), "ไม่พบงานวิ่ง", Toast.LENGTH_SHORT).show();
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
                    //Toast.makeText(requireContext(), "get link", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show();
                    ic_username.setImageResource(R.drawable.ic_baseline_person_24);
                }
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void callData() {
        SecondFragment secondFragment = new SecondFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(secondFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        int type = 1;
        Call call = service.getUserName(type);
        call.enqueue(new Callback(){
            @Override
            public void onResponse(Call call, Response response) {
                refreshlayout_second.setRefreshing(false);
                if(response.isSuccessful()){
                    List<UserListResponse> userListResponses = (List<UserListResponse>) response.body();
//                    Toast.makeText(SecondActivity.this, "Response_eventList" +"  "+ userListResponses.size(), Toast.LENGTH_SHORT).show();
                    recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_user);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(layoutManager);
                    MyRecyclerAdapter_event adapter = new MyRecyclerAdapter_event(requireContext(),userListResponses);
                    recyclerView.setAdapter(adapter);;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}