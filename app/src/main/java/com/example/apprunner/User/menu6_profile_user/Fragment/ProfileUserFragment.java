package com.example.apprunner.User.menu6_profile_user.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivityAddEvent;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu1_home.Fragment.SecondFragment;
import com.example.apprunner.User.menu6_profile_user.Activity.EditProfileUserActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileUserFragment extends Fragment {

    Button btn_edit_profile;
    String gender,first_name,last_name,type,email,password,Tel;
    TextView tv_FName,tv_LName,tv_Tel,tv_gender,tv_id,tv_bd;
    int id_user;
    FragmentManager fragmentManager;

    public ProfileUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentHandle = inflater.inflate(R.layout.fragment_profile_user, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            id_user = getArguments().getInt("id_user");
            first_name = getArguments().getString("first_name");
            last_name = getArguments().getString("last_name");
            type = getArguments().getString("type");

            tv_FName = fragmentHandle.findViewById(R.id.FName_text);
            tv_LName = fragmentHandle.findViewById(R.id.LName_text);
            tv_Tel = fragmentHandle.findViewById(R.id.tel_text);
            tv_gender = fragmentHandle.findViewById(R.id.tv_gender);
            tv_id = fragmentHandle.findViewById(R.id.tv_id);
            tv_bd = fragmentHandle.findViewById(R.id.tv_bd);

            tv_FName.setText(first_name);
            tv_LName.setText(last_name);

            profile(id_user);
        }

        btn_edit_profile = (Button) fragmentHandle.findViewById(R.id.btn_edit_profile);
        fragmentHandle.findViewById(R.id.btn_edit_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfileUserActivity.class);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });

        return fragmentHandle;
    }


    public void profile(final int id_user){
        SecondFragment secondFragment = new SecondFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(secondFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.profile_user(id_user);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                if(response.isSuccessful()){
                    ResultQuery resultQuery = (ResultQuery) response.body();
                    String tel = resultQuery.getTel();
                    String id_card = resultQuery.getId_card();
                    String gender = resultQuery.getGender();
                    String BD = resultQuery.getBd_date();
                    tv_Tel.setText(tel);
                    tv_id.setText(id_card);
                    tv_gender.setText(gender);
                    tv_bd.setText(BD);
                }else{
                    Toast.makeText(requireActivity(), "null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                Toast.makeText(requireActivity(), "fail", Toast.LENGTH_SHORT).show();
            }
        });

    }
}