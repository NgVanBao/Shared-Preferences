package com.example.sharedpreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnXacNhan, btnShowPasswords;
    private EditText edtUsername, edtPassword;
    private CheckBox cbRemember, cbShowPassword;
    private TextView txtPasswordList;
    SharedPreferences sharedPreferences;
    List<String> passwordList;  // List to store passwords

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize components and the password list
        AnhXa();
        passwordList = new ArrayList<>();

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        edtUsername.setText(sharedPreferences.getString("taikhoan", ""));
        edtPassword.setText(sharedPreferences.getString("matkhau", ""));
        cbRemember.setChecked(sharedPreferences.getBoolean("checked", false));

        // Xử lý sự kiện khi nhấn nút xác nhận
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (username.equals("Baodeptrai") && password.equals("1234")) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    passwordList.add(password);  // Add password to the list

                    if (cbRemember.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("taikhoan", username);
                        editor.putString("matkhau", password);
                        editor.putBoolean("checked", true);
                        editor.commit();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
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

        // Toggle password visibility
        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        // Show stored passwords when clicking the "Show Passwords" button
        btnShowPasswords.setOnClickListener(view -> {
            if (!passwordList.isEmpty()) {
                StringBuilder passwords = new StringBuilder();
                for (String pass : passwordList) {
                    passwords.append(pass).append("\n");
                }
                txtPasswordList.setText(passwords.toString());  // Display the passwords
            } else {
                txtPasswordList.setText("No passwords saved yet.");
            }
        });
    }

    // Phương thức ánh xạ các thành phần UI với ID trong layout
    private void AnhXa() {
        btnXacNhan = findViewById(R.id.buttonXacNhan);
        edtUsername = findViewById(R.id.editTextUsername);
        edtPassword = findViewById(R.id.editTextPassword);
        cbRemember = findViewById(R.id.checkBoxRemember);
        cbShowPassword = findViewById(R.id.checkBoxShowPassword);
        btnShowPasswords = findViewById(R.id.buttonShowPasswords);  // Button to show passwords
        txtPasswordList = findViewById(R.id.textViewPasswordList);  // TextView to display passwords
    }
}
