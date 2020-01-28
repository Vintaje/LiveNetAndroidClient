package com.example.livenet.ui.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livenet.R;
import com.example.livenet.model.Usuario;
import com.example.livenet.util.MyB64;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter  extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    private ArrayList<String[]> list;
    private Context context;

    public UsersAdapter(ArrayList<String[]> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listitem = layoutInflater.inflate(R.layout.user_item, parent ,false);
        UsersAdapter.ViewHolder viewHolder = new UsersAdapter.ViewHolder(listitem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String[] user = list.get(position);

        if(user[1].equals("default")){
            holder.photo.setImageDrawable(context.getDrawable(R.drawable.defaultphoto));
        }else {
            holder.photo.setImageBitmap(MyB64.base64ToBitmap(user[1]));
        }
        holder.nombre.setText(user[0]);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView photo;
        public TextView nombre;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.photo = itemView.findViewById(R.id.UsersPhoto);
            this.nombre = itemView.findViewById(R.id.UsersName);
        }
    }
}
