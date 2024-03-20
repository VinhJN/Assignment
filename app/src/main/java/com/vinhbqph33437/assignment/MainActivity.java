package com.vinhbqph33437.assignment;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.vinhbqph33437.assignment.Adapter.StudentAdapter;
import com.vinhbqph33437.assignment.Interface.ApiService;
import com.vinhbqph33437.assignment.Model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    List<Student> list;

    StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lvCustomListView);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.162:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Student>> call = apiService.getStudents();

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    studentAdapter = new StudentAdapter(getApplicationContext(), list);
                    listView.setAdapter(studentAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {

            }
        });
    }
}