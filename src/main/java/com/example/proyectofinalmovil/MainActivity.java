package com.example.proyectofinalmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    public static String currentUsername = null;
    private EditText etUser,etPassword;
    private Button btnEnter;
    private Button btnReg;

    private static final String FILE_NAME = "Login.txt";

    ArrayList<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.etMainUser);
        etPassword = findViewById(R.id.etMainPassword);
        btnEnter = findViewById(R.id.btnMainEnter);
        btnReg=findViewById(R.id.btnReg);

        verificarArchivoUsuarios();
        ListaUsuarios();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String passw = etPassword.getText().toString();
                Usuario usuario = new Usuario(user, passw);
                boolean hay = false;

                if (listaUsuarios != null) {
                    for (Usuario User : listaUsuarios) {
                        if (User.getNombreUsuario().equals(usuario.getNombreUsuario()) && User.getContraseña().equals(usuario.getContraseña())) {
                            hay = true;
                            currentUsername = user; // Guardar el nombre de usuario logueado
                            break;
                        }
                    }
                }
                if (hay) {
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_register.class);
                startActivity(intent);
            }
        });
    }

    private void verificarArchivoUsuarios() {
        try {
            File file = new File(getFilesDir(), FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
                // Carga los datos iniciales en el archivo
                CargarDatosTxtUsuario(file);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void ListaUsuarios() {
        try {
            listaUsuarios = new ArrayList<>();
            File file = new File(getFilesDir(), FILE_NAME);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(";");
                listaUsuarios.add(new Usuario(datos[0], datos[1]));
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void CargarDatosTxtUsuario(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            ArrayList<Usuario> l = new ArrayList<>();
            l.add(new Usuario("cris", "1"));
            l.add(new Usuario("igna", "2"));
            l.add(new Usuario("perez", "3"));
            l.add(new Usuario("rojas", "4"));

            for (Usuario u : l) {
                fileWriter.write(u.getNombreUsuario() + ";" + u.getContraseña() + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}