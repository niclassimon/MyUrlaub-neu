package com.example.my_urlaub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Urlaub_recycler_View_Adapter extends RecyclerView.Adapter<Urlaub_recycler_View_Adapter.MyViewHolder> {

    Context context;
    ArrayList<UrlaubModel> urlaubModels;

    public Urlaub_recycler_View_Adapter(Context context, ArrayList<UrlaubModel> urlaubModels){
        this.context = context;
        this.urlaubModels = urlaubModels;
    }

    @NonNull
    @Override
    public Urlaub_recycler_View_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent,false);

        return new Urlaub_recycler_View_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Urlaub_recycler_View_Adapter.MyViewHolder holder, int position) {
        holder.location.setText(urlaubModels.get(position).getLocation());
        holder.description.setText(urlaubModels.get(position).getDescription());
        holder.date.setText(urlaubModels.get(position).getStartDate());
       // holder.date.setText("hallo");
    }

    @Override
    public int getItemCount() {
        return urlaubModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView location, description, date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.textView);
            description = itemView.findViewById(R.id.textView2);
            date = itemView.findViewById(R.id.textView3);
        }
    }
}
