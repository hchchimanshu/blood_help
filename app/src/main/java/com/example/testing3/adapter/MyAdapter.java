package com.example.testing3.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing3.pojo.DataModal;
import com.example.testing3.R;

import org.bson.BsonUndefined;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<DataModal> arrayList;
    Context context;
    NavController navController;

    public MyAdapter(List<DataModal> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        holder.bloodGroup.setText(arrayList.get(position).getBlood());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bloodGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bloodGroup= itemView.findViewById(R.id.bloodName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String item = bloodGroup.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("item",item);
                    navController = Navigation.findNavController((Activity) context,R.id.nav_host_fragment);
                    navController.navigate(R.id.navigation_dashboard,bundle);

                }
            });

        }
    }
}
