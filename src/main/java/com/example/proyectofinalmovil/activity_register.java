package com.example.proyectofinalmovil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class activity_register extends AppCompatActivity {

    private EditText etUser, etPassword;
    private Button btnRegister;

    private static final String FILE_NAME = "Login.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegistrarse);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String passw = etPassword.getText().toString();

                if (user.isEmpty() || passw.isEmpty()) {
                    Toast.makeText(activity_register.this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (userExists(user)) {
                        Toast.makeText(activity_register.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            File file = new File(getFilesDir(), FILE_NAME);
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            FileWriter fileWriter = new FileWriter(file, true);
                            fileWriter.append(user).append(";").append(passw).append("\n");
                            fileWriter.flush();
                            fileWriter.close();
                            Toast.makeText(activity_register.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity_register.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Opcional: cierra esta actividad después del registro
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            Toast.makeText(activity_register.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    private boolean userExists(String username) {
        try {
            File file = new File(getFilesDir(), FILE_NAME);
            if (!file.exists()) {
                return false;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].equals(username)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}