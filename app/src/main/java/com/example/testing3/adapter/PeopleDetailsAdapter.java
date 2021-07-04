package com.example.testing3.adapter;

import android.content.Context;
import android.view.View;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing3.R;
import com.example.testing3.pojo.PeopleDetailPojo;

import java.util.List;

public class PeopleDetailsAdapter extends RecyclerView.Adapter<PeopleDetailsAdapter.ViewHolder> {

    List<PeopleDetailPojo> detailPojoList;
    Context mContext;

    public PeopleDetailsAdapter(List<PeopleDetailPojo> detailPojoList, Context mContext) {
        this.detailPojoList = detailPojoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.recycler_people_detail_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
        holder.name.setText(detailPojoList.get(position).getName());
        holder.number.setText(detailPojoList.get(position).getMob());
        holder.email.setText(detailPojoList.get(position).getEmail());
        holder.city.setText(detailPojoList.get(position).getCity());
//        holder.name.setText(detailPojoList.get(position).getName());
//        holder.city.setText(detailPojoList.get(position).getCity());

    }

    @Override
    public int getItemCount() {
        return detailPojoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name , number ,email ,city;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_of_person_TV);
            number=itemView.findViewById(R.id.number_of_person_TV);
            email=itemView.findViewById(R.id.email_of_person_TV);
            city=itemView.findViewById(R.id.city_of_person_TV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
