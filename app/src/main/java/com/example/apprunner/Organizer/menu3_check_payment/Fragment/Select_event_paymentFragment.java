package com.example.apprunner.Organizer.menu3_check_payment.Fragment;

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
import com.example.apprunner.Organizer.menu3_check_payment.Adapter.Select_event_paymentAdapter;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Select_event_paymentFragment extends Fragment {

    String first_name, last_name, type, name_organizer;
    int id_user;
    RecyclerView recycleview_profile_organizer;
    SwipeRefreshLayout refreshlayout_event_payment;

    public Select_event_paymentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentHandle = inflater.inflate(R.layout.fragment_select_event_payment, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            name_organizer = getArguments().getString("name_organizer");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");
            id_user = getArguments().getInt("id_user");

            refreshlayout_event_payment = fragmentHandle.findViewById(R.id.refreshlayout_event_payment);
            refreshlayout_event_payment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Event_Payment();
                }
            });

            Event_Payment();
        }
        return fragmentHandle;
    }

    private void Event_Payment() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.event_payment_organizer(id_user);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                refreshlayout_event_payment.setRefreshing(false);
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                recycleview_profile_organizer = (RecyclerView) getView().findViewById(R.id.recycler_view_eventpayment_organizer);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recycleview_profile_organizer.setLayoutManager(layoutManager);
                Select_event_paymentAdapter adapter = new Select_event_paymentAdapter(requireContext(), resultQueries);
                recycleview_profile_organizer.setAdapter(adapter);
                Toast.makeText(requireContext(), "งานวิ่งของคุณ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(requireContext(),"ไม่พบงานวิ่งของคุณ",Toast.LENGTH_SHORT).show();
            }
        });
    }

}