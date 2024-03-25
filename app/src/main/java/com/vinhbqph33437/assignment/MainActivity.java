package com.vinhbqph33437.assignment;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vinhbqph33437.assignment.Adapter.StudentAdapter;
import com.vinhbqph33437.assignment.Interface.ItemClickListener;
import com.vinhbqph33437.assignment.Model.Response;
import com.vinhbqph33437.assignment.Model.Student;
import com.vinhbqph33437.assignment.Services.HttpRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    EditText edId, edName, edMSSV, edPoint;
    private HttpRequest httpRequest;
    private RecyclerView rcvStudent;
    private StudentAdapter adapter;
    ArrayList<Student> listStudent = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvStudent = findViewById(R.id.rcvStudent);
        httpRequest = new HttpRequest();
        httpRequest.callAPI()
                .getListStudent()
                .enqueue(getStudentAPI);

        findViewById(R.id.fabStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddOrEditDialog();
            }
        });
//        adapter.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void UpdateItem(int position) {
//                Student student = listStudent.get(position);
//                showAddOrEditDialog(getApplicationContext(), 1, student);
//            }
//        });
    }
    private void getData(ArrayList<Student> students){
        adapter = new StudentAdapter(this,students);
        rcvStudent.setLayoutManager(new LinearLayoutManager(this));
        rcvStudent.setAdapter(adapter);
    }
    Callback<Response<ArrayList<Student>>> getStudentAPI = new Callback<Response<ArrayList<Student>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Student>>> call, retrofit2.Response<Response<ArrayList<Student>>> response) {
            if (response.isSuccessful()){
                if(response.body().getStatus() == 200){
                    ArrayList<Student> students = response.body().getData();
                    getData(students);
                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Student>>> call, Throwable t) {
            Log.d( ">>> GetListStudent", "onFailure: "+ t.getMessage());
        }
    };
    Callback<Response<Student>> responseStudentAPI = new Callback<Response<Student>>() {
        @Override
        public void onResponse(Call<Response<Student>> call, retrofit2.Response<Response<Student>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    httpRequest.callAPI()
                            .getListStudent()
                            .enqueue(getStudentAPI);
                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Student>> call, Throwable t) {
            Log.d( ">>> GetListStudent", "onFailure: "+ t.getMessage());
        }
    };

    private void showAddOrEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_add_student, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        edName = dialogView.findViewById(R.id.edName);
        edMSSV = dialogView.findViewById(R.id.edMSSV);
        edPoint = dialogView.findViewById(R.id.edPoint);
        dialogView.findViewById(R.id.btnSave).setOnClickListener(v -> {
            String name = edName.getText().toString();
            String mssv = edMSSV.getText().toString();
            Float point = Float.parseFloat(edPoint.getText().toString());
            Student student = new Student();
            student.setName(name);
            student.setMssv(mssv);
            student.setPoint(point);
            httpRequest.callAPI()
                    .addStudent(student)
                    .enqueue(responseStudentAPI);
            Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });
        dialogView.findViewById(R.id.btnCancle).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
}