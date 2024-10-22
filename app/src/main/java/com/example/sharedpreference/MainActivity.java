package com.example.sharedpreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnXacNhan;
    private EditText edtUsername, edtPassword;
    private CheckBox cbRemember;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Ánh xạ các thành phần UI
        AnhXa();
        // getSharedPreferences() được truyền vào hai tham số là tên và mode. Tên được lưu vào bộ nhớ của ứng dụng (dạng Tên.xml). Save
        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
        // Get gia tri sharedPreferences tu file xml DataLogin
        edtUsername.setText(sharedPreferences.getString("taikhoan",""));
        edtPassword.setText(sharedPreferences.getString("matkhau",""));
        cbRemember.setChecked(sharedPreferences.getBoolean("checked",false));

        // Xử lý sự kiện khi nhấn nút xác nhận
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // Kiểm tra tài khoản và mật khẩu
                if (username.equals("Baodeptrai") && password.equals("1234")) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    // neu dang nhap hop le va click vao checkbox thi luu thong tin dang nhap vao sharedPreferences la DataLogin.xml
                    if(cbRemember.isChecked()){
                        SharedPreferences.Editor editor =sharedPreferences.edit();
                        editor.putString("taikhoan",username);// luu username vao file DataLogin
                        editor.putString("matkhau",password);// luu password vao file DataLogin
                        editor.putBoolean("checked",true);// Luu trang thai checkbox
                        editor.commit();
                    }
                    // neu dang nhap hop le va khong click vao checkbox thi bo luu thong tin dang nhap vao sharedPreferences
                    else
                    {
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.remove("taikhoan");
                        editor.remove("matkhau");
                        editor.remove("checked");
                        editor.commit();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Lỗi đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Phương thức ánh xạ các thành phần UI với ID trong layout
    private void AnhXa() {
        btnXacNhan = findViewById(R.id.buttonXacNhan);
        edtUsername = findViewById(R.id.editTextUsername);
        edtPassword = findViewById(R.id.editTextPassword);
        cbRemember = findViewById(R.id.checkBoxRemember);
    }
}
