package com.vinhbqph33437.assignment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vinhbqph33437.assignment.Model.Student;
import com.vinhbqph33437.assignment.R;
import com.vinhbqph33437.assignment.databinding.StudentListItemBinding;

import java.util.ArrayList;

public class StudentAdapter  extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Student> list;
    private StudentClick studentClick;

    public StudentAdapter(Context context, ArrayList<Student> list, StudentClick studentClick) {
        this.context = context;
        this.list = list;
        this.studentClick = studentClick;
    }

    public interface StudentClick {
        void delete(Student student);
        void edit(Student student);
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StudentListItemBinding binding = StudentListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        Student student = list.get(position);
        holder.binding.tvName.setText(student.getName());
        holder.binding.tvMssv.setText(student.getMssv());
        holder.binding.tvPoint.setText(student.getPoint());
        String url  = student.getAvatar().get(0);
        String newUrl = url.replace("localhost", "10.0.2.2");
        Glide.with(context)
                .load(newUrl)
                .thumbnail(Glide.with(context).load(R.drawable.broken))
                .into(holder.binding.img);
        Log.d("321321", "onBindViewHolder: "+list.get(position).getAvatar().get(0));
        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentClick.edit(student);
            }
        });
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentClick.delete(student);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        StudentListItemBinding binding;
        public ViewHolder(StudentListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}