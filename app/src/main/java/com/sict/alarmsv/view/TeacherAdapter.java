package com.sict.alarmsv.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.sict.alarmsv.R;
import com.sict.alarmsv.model.Teacher_info;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.DataViewHolder> {

    private List<Teacher_info> people;
    private Context context;

    public TeacherAdapter(Context context, List<Teacher_info> people) {
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
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item, parent, false);
        return new DataViewHolder(itemView);
    }

    public void onBindViewHolder(DataViewHolder holder, int position) {
        String name = people.get(position).getName();
        holder.tvName.setText(name);
        String cv = people.get(position).getCv();
        holder.tvCv.setText(cv);
        String nganh = people.get(position).getNganh();
        holder.tvNganh.setText(nganh);
        String linhvuc = people.get(position).getLinh_vuc();
        holder.tvLinhvuc.setText(linhvuc);
        Glide.with(context)
                .load(people.get(position).getImg_teacher())
                .error(R.drawable.ic_launcher_background)
                .override(300,300)
                .into(holder.tvImg);
    }

    /**
     * Data ViewHolder class.
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private ImageView tvImg;
        private TextView tvCv;
        private TextView tvNganh;
        private TextView tvLinhvuc;

        public DataViewHolder(View itemView) {
            super(itemView);
            tvImg = (ImageView) itemView.findViewById(R.id.img_teacher);
            tvName = (TextView) itemView.findViewById(R.id.name_teacher);
            tvCv = itemView.findViewById(R.id.cv_teacher);
            tvNganh = itemView.findViewById(R.id.nganh_teacher);
            tvLinhvuc = itemView.findViewById(R.id.lv_teacher);
        }
    }
}
