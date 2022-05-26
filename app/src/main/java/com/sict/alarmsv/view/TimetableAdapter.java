package com.sict.alarmsv.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.alarmsv.R;
import com.sict.alarmsv.model.Timetable_info;

import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.DataViewHolder> {

    private List<Timetable_info> people;
    private Context context;

    public TimetableAdapter(Context context, List<Timetable_info> people) {
        this.context = context;
        this.people = people;
    }

    @Override
    public int getItemCount() {
        return people == null ? 0 : people.size();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_item, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        String date = people.get(position).getDate();
        holder.tvdate.setText(date);
        String t1 = people.get(position).getT1();
        holder.tvt1.setText(t1);
        String t2 = people.get(position).getT2();
        holder.tvt2.setText(t2);
        String t3 = people.get(position).getT3();
        holder.tvt3.setText(t3);
        String t4 = people.get(position).getT4();
        holder.tvt4.setText(t4);
    }

    /**
     * Data ViewHolder class.
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private TextView tvdate;
        private TextView tvt1;
        private TextView tvt2;
        private TextView tvt3;
        private TextView tvt4;

        public DataViewHolder(View itemView) {
            super(itemView);
            tvdate = (TextView) itemView.findViewById(R.id.date);
            tvt1 = (TextView) itemView.findViewById(R.id.item_t1);
            tvt2 = itemView.findViewById(R.id.item_t2);
            tvt3 = itemView.findViewById(R.id.item_t3);
            tvt4 = itemView.findViewById(R.id.item_t4);
        }
    }
}