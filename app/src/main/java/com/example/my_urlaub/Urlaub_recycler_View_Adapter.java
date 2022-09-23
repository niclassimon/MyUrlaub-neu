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
    private final Recycler_view_Interface recycler_view_interface;


    Context context;
    ArrayList<UrlaubModel> urlaubModels;

    public Urlaub_recycler_View_Adapter(Context context, ArrayList<UrlaubModel> urlaubModels, Recycler_view_Interface recycler_view_interface){
        this.context = context;
        this.urlaubModels = urlaubModels;
        this.recycler_view_interface = recycler_view_interface;
    }

    @NonNull
    @Override
    public Urlaub_recycler_View_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent,false);

        return new Urlaub_recycler_View_Adapter.MyViewHolder(view, recycler_view_interface);
    }

    @Override
    public void onBindViewHolder(@NonNull Urlaub_recycler_View_Adapter.MyViewHolder holder, int position) {
        holder.location.setText(urlaubModels.get(position).getLocation());
        holder.description.setText(urlaubModels.get(position).getDescription());
        holder.date.setText(urlaubModels.get(position).getStartDate());
    }

    @Override
    public int getItemCount() {
        return urlaubModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView location, description, date;


        public MyViewHolder(@NonNull View itemView, Recycler_view_Interface recycler_view_interface) {
            super(itemView);

            location = itemView.findViewById(R.id.textView);
            description = itemView.findViewById(R.id.textView2);
            date = itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycler_view_interface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recycler_view_interface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
