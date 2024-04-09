package com.vinhbqph33437.assignment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vinhbqph33437.assignment.databinding.ActivityUpdateStudentBinding;
import com.vinhbqph33437.assignment.Model.Student;
import com.vinhbqph33437.assignment.Model.Response;
import com.vinhbqph33437.assignment.Services.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UpdateStudentActivity extends AppCompatActivity {
    ActivityUpdateStudentBinding binding;
    private Student student;
    private String id;
    private HttpRequest httpRequest;
    private ArrayList<File> ds_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUpdateStudentBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        ds_image = new ArrayList<>();
        httpRequest = new HttpRequest();
        getDataIntent();
        userListener();
    }

    private void getDataIntent() {
        //get data object intent
        Intent intent = getIntent();
        student = (Student) intent.getSerializableExtra("student");
        Log.d("aaaaaa", "getDataIntent: " + student.getAvatar().get(0));
        id = student.get_id();
        String url = student.getAvatar().get(0);
        String newUrl = url.replace("localhost", "10.0.2.2");
        Glide.with(this)
                .load(newUrl)
                .thumbnail(Glide.with(this).load(R.drawable.broken))
                .into(binding.avatar);
        binding.edName.setText(student.getName());
        binding.edMssv.setText(student.getMssv());
        binding.edPoint.setText(student.getPoint());
    }


    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    private void userListener() {
        binding.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, RequestBody> mapRequestBody = new HashMap<>();
                String _name = binding.edName.getText().toString().trim();
                String _mssv = binding.edMssv.getText().toString().trim();
                String _point = binding.edPoint.getText().toString().trim();
                mapRequestBody.put("name", getRequestBody(_name));
                mapRequestBody.put("mssv", getRequestBody(_mssv));
                mapRequestBody.put("point", getRequestBody(_point));
                ArrayList<MultipartBody.Part> _ds_image = new ArrayList<>();
//
                // Kiểm tra xem người dùng đã chọn ảnh mới hay không


                // Nếu có ảnh mới, thêm các ảnh mới vào danh sách
                ds_image.forEach(file1 -> {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file1);
                    MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData("avatar", file1.getName(), requestFile);
                    _ds_image.add(multipartBodyPart);
                });


                // Gửi yêu cầu cập nhật lên server
                httpRequest.callAPI().updateStudentWithFileImage(mapRequestBody,
                        student.get_id(), _ds_image).enqueue(responseStudent);


            }
        });
    }

    Callback<Response<Student>> responseStudent = new Callback<Response<Student>>() {
        @Override
        public void onResponse(Call<Response<Student>> call, retrofit2.Response<Response<Student>> response) {
            if (response.isSuccessful()) {
                Log.d("123123", "onResponse: " + response.body().getStatus());
                if (response.body().getStatus() == 200) {
                    Toast.makeText(UpdateStudentActivity.this, "Sửa thành công thành công", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Student>> call, Throwable t) {
            Toast.makeText(UpdateStudentActivity.this, "Sửa sai rồi ", Toast.LENGTH_SHORT).show();
            onBackPressed();
            Log.e("zzzzzzzzzz", "onFailure: " + t.getMessage());
        }
    };


    private void chooseImage() {
//        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        Log.d("123123", "chooseAvatar: " + 123123);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        getImage.launch(intent);
//        }else {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//
//        }
    }

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        ds_image.clear();
                        Intent data = o.getData();
                        if (data.getClipData() != null) {
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();

                                File file = createFileFormUri(imageUri, "image" + i);
                                ds_image.add(file);
                            }


                        } else if (data.getData() != null) {
                            // Trường hợp chỉ chọn một hình ảnh
                            Uri imageUri = data.getData();
                            // Thực hiện các xử lý với imageUri
                            File file = createFileFormUri(imageUri, "image");
                            ds_image.add(file);

                        }
                        Glide.with(UpdateStudentActivity.this)
                                .load(ds_image.get(0))
                                .thumbnail(Glide.with(UpdateStudentActivity.this).load(R.drawable.broken))
                                .centerCrop()
                                .circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(binding.avatar);
                    }
                }
            });

    private File createFileFormUri(Uri path, String name) {
        File _file = new File(UpdateStudentActivity.this.getCacheDir(), name + ".png");
        try {
            InputStream in = UpdateStudentActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("123123", "createFileFormUri: " + _file);
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}