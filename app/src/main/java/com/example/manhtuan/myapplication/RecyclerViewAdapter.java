package com.example.manhtuan.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<ListWeatherActivity.PropertiesDetail> propertiesDetailList;

    public RecyclerViewAdapter(Context context, List<ListWeatherActivity.PropertiesDetail> propertiesDetailList) {
        this.context = context;
        this.propertiesDetailList = propertiesDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recyclerview_list_weather,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewProperties.setText(propertiesDetailList.get(position).getProperties());
        holder.textViewDetail.setText(propertiesDetailList.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return propertiesDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewProperties,textViewDetail;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewDetail = itemView.findViewById(R.id.textViewDetail);
            textViewProperties = itemView.findViewById(R.id.textViewProperties);
        }
    }
}
