package com.example.apprunner.User.menu4_check_payment.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu4_check_payment.Adapter.StatusPaymentUserAdapter;
import com.example.apprunner.User.menu1_home.Fragment.SecondFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusPaymentUserFragment extends Fragment {

    String first_name,last_name,type;
    int id_user,id_add;
    RecyclerView recycler_view_status_payment;

    public StatusPaymentUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View fragmentHandle = inflater.inflate(R.layout.fragment_status_payment_user, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            id_user = getArguments().getInt("id_user");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");
            String email = getArguments().getString("email");
            String password = getArguments().getString("password");

            list_payment();
        }

        // Inflate the layout for this fragment
        return fragmentHandle;
    }

    private void list_payment(){
        SecondFragment secondFragment = new SecondFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(secondFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = service.check_user_payment_event(id_user);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                recycler_view_status_payment = (RecyclerView) getView().findViewById(R.id.recycler_view_status_payment);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recycler_view_status_payment.setLayoutManager(layoutManager);
                StatusPaymentUserAdapter adapter = new StatusPaymentUserAdapter(requireContext(),resultQueries);
                recycler_view_status_payment.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {

            }
        });
    }

}