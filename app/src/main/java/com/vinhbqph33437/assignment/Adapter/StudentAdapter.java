package com.vinhbqph33437.assignment.Adapter;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vinhbqph33437.assignment.Interface.ItemClickListener;
import com.vinhbqph33437.assignment.MainActivity;
import com.vinhbqph33437.assignment.Model.Response;
import com.vinhbqph33437.assignment.Model.Student;
import com.vinhbqph33437.assignment.R;
import com.vinhbqph33437.assignment.Services.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    Context context;
    private HttpRequest httpRequest = new HttpRequest();

    ArrayList<Student> list;

    private ItemClickListener itemClickListener;

    public StudentAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
        Student student = list.get(position);
//        String imageUrl = list.get(position).getThumbnailUrl();
//        Picasso.get().load(imageUrl).into(holder.imgAvatar);
        holder.tvId.setText(student.get_id());
        holder.tvName.setText(student.getName());
        holder.tvMSSV.setText(student.getMssv());
        holder.tvPoint.setText(String.valueOf(student.getPoint()));
        holder.btnDelete.setOnClickListener(v -> {
                showDeleteDialog(position);
        });
        holder.btnUpdate.setOnClickListener(v -> {
            showUpdateDialog(position, student); // Pass student object to update dialog
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tvName, tvMSSV, tvPoint;
        ImageView imgAvatar;
        ImageButton btnDelete, btnUpdate;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvMSSV = itemView.findViewById(R.id.tvMSSV);
            tvPoint = itemView.findViewById(R.id.tvPoint);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
    public void showDeleteDialog(int position) {
        Student student = list.get(position);
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa sinh viên " + student.getName() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteStudent( student.get_id());
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void deleteStudent( String id) {
        httpRequest.callAPI().deleteStudentById(id).enqueue(new Callback<Response<Student>>() {
            @Override
            public void onResponse(Call<Response<Student>> call, retrofit2.Response<Response<Student>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        notifyDataSetChanged();
                        Toast.makeText(context, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Response<Student>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
    private void showUpdateDialog(int position, Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_student, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        EditText edName = dialogView.findViewById(R.id.edNameUpdate);
        EditText edMSSV = dialogView.findViewById(R.id.edMSSVUpdate);
        EditText edPoint = dialogView.findViewById(R.id.edPointUpdate);

        // Populate EditText fields with current student data
        edName.setText(student.getName());
        edMSSV.setText(student.getMssv());
        edPoint.setText(String.valueOf(student.getPoint()));

        dialogView.findViewById(R.id.btnSaveUpdate).setOnClickListener(v -> {
            String name = edName.getText().toString();
            String mssv = edMSSV.getText().toString();
            float point = Float.parseFloat(edPoint.getText().toString());

            // Update the student object with new data
            student.setName(name);
            student.setMssv(mssv);
            student.setPoint(point);

            // Call API to update the student
            updateStudent(student);
            alertDialog.dismiss();
        });

        dialogView.findViewById(R.id.btnCancleUpdate).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void updateStudent(Student student) {
        httpRequest.callAPI()
                .updateStudentById(student.get_id(), student)
                .enqueue(new Callback<Response<Student>>() {
                    @Override
                    public void onResponse(Call<Response<Student>> call, retrofit2.Response<Response<Student>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                notifyDataSetChanged();
                                Toast.makeText(context, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<Student>> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}
