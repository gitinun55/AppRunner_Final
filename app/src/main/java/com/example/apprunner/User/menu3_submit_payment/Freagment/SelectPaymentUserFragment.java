package com.example.apprunner.User.menu3_submit_payment.Freagment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu3_submit_payment.Adapter.SelectPaymentUserAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectPaymentUserFragment extends Fragment {

    String first_name, last_name, type;
    int id_user,id_add_his,id_add_reg;
    RecyclerView recycler_select_user;
    SwipeRefreshLayout refreshlayout_select_payment;

    public SelectPaymentUserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentHandle = inflater.inflate(R.layout.fragment_select_payment_user, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            id_user = getArguments().getInt("id_user");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");

            refreshlayout_select_payment = fragmentHandle.findViewById(R.id.refreshlayout_select_payment);
            refreshlayout_select_payment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    show_event_payment();
                }
            });
            show_event_payment();
        }
        return fragmentHandle;
    }

    private void show_event_payment() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.list_payment_event(id_user);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                refreshlayout_select_payment.setRefreshing(false);
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                if(resultQueries.isEmpty()){
                    Toast.makeText(requireContext(),"ไม่พบงานวิ่งที่ต้องชำระ", Toast.LENGTH_SHORT).show();
                }
                recycler_select_user = (RecyclerView) getView().findViewById(R.id.recycler_event_select_payment);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recycler_select_user.setLayoutManager(layoutManager);
                SelectPaymentUserAdapter adapter = new SelectPaymentUserAdapter(requireContext(), resultQueries);
                recycler_select_user.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {

            }
        });
    }
}