package com.example.livenet.ui.chat;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.livenet.BBDD.DBC;
import com.example.livenet.MainActivity;
import com.example.livenet.R;
import com.example.livenet.REST.APIUtils;
import com.example.livenet.REST.AmigosRest;
import com.example.livenet.model.Usuario;
import com.example.livenet.ui.Adapter.UsersAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {
    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private ArrayList<String[]> mUsers;
    private DBC dbc;
    private AmigosRest amigosRest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.UsersRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsers = new ArrayList<>();
        dbc = new DBC(getContext(), "localCfgBD", null, 1);
        amigosRest = APIUtils.getAmigosService();

        callFriends();
        readUsers();


        dbc.close();
        return view;
    }

    private void readUsers() {
        mUsers = dbc.seleccionarData();
        adapter = new UsersAdapter(mUsers, getContext());
        recyclerView.setAdapter(adapter);
    }


    private void callFriends(){
        Call<ArrayList<String[]>> call = amigosRest.findAllByAlias(((MainActivity) getActivity()).getLogged().getAlias());
        call.enqueue(new Callback<ArrayList<String[]>>() {
            @Override
            public void onResponse(Call<ArrayList<String[]>> call, Response<ArrayList<String[]>> response) {
                if (response.isSuccessful()) {
                    //hay respuesta
                    mUsers = response.body();
                    for(String[] user : mUsers){
                        dbc.insert(user);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String[]>> call, Throwable t) {
                Toast.makeText(getContext(),"No se ha podido cargar la lista de amigos",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
