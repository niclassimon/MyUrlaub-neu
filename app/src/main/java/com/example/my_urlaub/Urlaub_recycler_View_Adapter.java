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
    ArrayList<Urlaub> urlaubModels;


    public Urlaub_recycler_View_Adapter(Context context, ArrayList<Urlaub> urlaubModels, Recycler_view_Interface recycler_view_interface){
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

    //Hier werden dem ViewHolder die passenden Werte für den jeweiligen TextView übergeben
    @Override
    public void onBindViewHolder(@NonNull Urlaub_recycler_View_Adapter.MyViewHolder holder, int position) {
        holder.location.setText(urlaubModels.get(position).getLocation());
        holder.description.setText(urlaubModels.get(position).getDescription());
        holder.startdate.setText(urlaubModels.get(position).getStartDate());
        holder.enddate.setText(urlaubModels.get(position).getEndDate());
        holder.moveItem.setImageResource(urlaubModels.get(position).getMove());
    }


    @Override
    public int getItemCount() {
        return urlaubModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView location, description, startdate, enddate;
        ImageView moveItem;

        public MyViewHolder(@NonNull View itemView, Recycler_view_Interface recycler_view_interface) {
            super(itemView);

            location = itemView.findViewById(R.id.description_view);
            description = itemView.findViewById(R.id.description_view);
            startdate = itemView.findViewById(R.id.startdate_view);
            enddate = itemView.findViewById(R.id.endDate_view);
            moveItem = itemView.findViewById(R.id.move_view);



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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(recycler_view_interface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recycler_view_interface.onItemLongClick(pos);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
