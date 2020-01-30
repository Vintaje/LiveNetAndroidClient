package com.example.livenet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.livenet.BBDD.DBC;
import com.example.livenet.REST.APIUtils;
import com.example.livenet.REST.AmigosRest;
import com.example.livenet.model.Chat;
import com.example.livenet.model.FireUser;
import com.example.livenet.model.Usuario;
import com.example.livenet.ui.Adapter.MensajeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensajeActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView foto;
    FirebaseUser fuser;
    DatabaseReference reference;
    TextView username;
    ArrayList<String[]> remota;
    private DBC dbc;
    String usern;
    Intent intent;
    String receiverid;

    RecyclerView recyclerView;
    ArrayList<Chat> mChat;
    MensajeAdapter adapter;

    //Elementos UI
    private EditText text_send;
    private ImageButton bt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);


        Toolbar toolbar = findViewById(R.id.MensajeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        foto = findViewById(R.id.mensaje_foto);
        username = findViewById(R.id.mensajes_username);
        intent = getIntent();
        usern = intent.getStringExtra("username");
        username.setText(usern);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        rellenarChat();

        text_send = findViewById(R.id.tvMensajeEnviado);
        bt_send = findViewById(R.id.btEnviar);
        bt_send.setOnClickListener(this);

        recyclerView = findViewById(R.id.chatRvMensajes);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager l = new LinearLayoutManager(getApplicationContext());
        l.setStackFromEnd(true);
        recyclerView.setLayoutManager(l);

    }



    private void rellenarChat(){
        dbc = new DBC(getApplicationContext(),"localCfgBD", null, 1);


        reference = FirebaseDatabase.getInstance().getReference("Users").child(dbc.getTokenAmigo(usern));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    System.out.println(dataSnapshot.getChildrenCount());
                    FireUser fuser = dataSnapshot.getValue(FireUser.class);
                    receiverid = fuser.getId();
                    if (fuser.getImage().equals("defaultphoto")) {
                        foto.setImageResource(R.drawable.defaultphoto);
                    } else {
                        System.out.println("Otra foto rara");
                    }

                    readMensaje(FirebaseAuth.getInstance().getCurrentUser().getUid(), receiverid);
                }catch(Exception ex){

                    reference.removeEventListener(this);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("sender",sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);


        reference.child("Chats").push().setValue(hashMap);
    }

    @Override
    public void onClick(View v) {
        String msg = text_send.getText().toString();
        if(!msg.isEmpty()){
            sendMessage(fuser.getUid(), receiverid, msg);
            text_send.setText("");
        }else{
            Toast.makeText(getApplicationContext(), "No puedes enviar un mensaje vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private void readMensaje(String myid, String userid){
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        System.out.println(snapshot);
                        Chat chat = snapshot.getValue(Chat.class);
                        if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) || chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                            mChat.add(chat);
                        }
                        adapter = new MensajeAdapter(mChat, MensajeActivity.this, username.getText().toString());
                        recyclerView.setAdapter(adapter);
                    }
                }catch(Exception ignored){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
