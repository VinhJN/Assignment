package com.vinhbqph33437.assignment;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.vinhbqph33437.assignment.Adapter.StudentAdapter;
import com.vinhbqph33437.assignment.databinding.ActivityMainBinding;
import com.vinhbqph33437.assignment.Model.Student;
import com.vinhbqph33437.assignment.Model.Response;
import com.vinhbqph33437.assignment.Services.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements StudentAdapter.StudentClick {
    ActivityMainBinding binding;
    private HttpRequest httpRequest;
    private StudentAdapter adapter;
    ArrayList<Student> ds = new ArrayList<>();
    boolean isSortedByName = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        httpRequest = new HttpRequest();
        httpRequest.callAPI().getListStudent().enqueue(getStudentAPI);
        userListener();

    }



    private void userListener () {
        binding.edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String key = binding.edSearch   .getText().toString().trim();
                    httpRequest.callAPI()
                            .searchStudent(key)
                            .enqueue(getStudentAPI);
                    Log.d(TAG, "onEditorAction: " + key);
                    return true;
                }
                return false;
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , AddStudentActivity.class));
                adapter.notifyDataSetChanged();
            }
        });
        binding.btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSortedByName) {
                    // Call API to get unsorted list
                    httpRequest.callAPI().getListStudent().enqueue(getStudentAPI);
                } else {
                    // Call API to get sorted list by name
                    httpRequest.callAPI().sortStudentsByName().enqueue(getStudentAPI);
                }
                isSortedByName = !isSortedByName;
            }
        });
    }

    Callback<Response<ArrayList<Student>>> getStudentAPI = new Callback<Response<ArrayList<Student>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Student>>> call, retrofit2.Response<Response<ArrayList<Student>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() ==200) {
                     ds = response.body().getData();
                    getData(ds);
//                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Student>>> call, Throwable t) {

        }
    };

    private void getData (ArrayList<Student> ds) {
        adapter = new StudentAdapter(this, ds,this );
        binding.rcvStudent.setAdapter(adapter);
    }

    Callback<Response<Student>> responseStudentAPI  = new Callback<Response<Student>>() {
        @Override
        public void onResponse(Call<Response<Student>> call, retrofit2.Response<Response<Student>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    httpRequest.callAPI()
                            .getListStudent()
                            .enqueue(getStudentAPI);
//                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Student>> call, Throwable t) {
            Log.e(TAG, "onFailure: "+t.getMessage() );
        }
    };

    Callback<Response<ArrayList<Student>>> sortStudentByNameAPI = new Callback<Response<ArrayList<Student>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Student>>> call, retrofit2.Response<Response<ArrayList<Student>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Student> sortedStudents = response.body().getData();
                    getData(sortedStudents); // Update UI with sorted list
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Student>>> call, Throwable t) {
            Log.e(TAG, "onFailure: " + t.getMessage());
        }
    };


    @Override
    public void delete(Student student) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm delete");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("yes", (dialog, which) -> {
            httpRequest.callAPI()
                    .deleteStudentById(student.get_id())
                    .enqueue(responseStudentAPI);
        });
        builder.setNegativeButton("no", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }

    @Override
    public void edit(Student student) {
        Intent intent =new Intent(MainActivity.this, UpdateStudentActivity.class);
        intent.putExtra("student", student);
        startActivity(intent);
    }
}