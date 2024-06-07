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
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private EditText etUser,etPassword;
    private Button btnEnter;

    private static final String NOMBRE_ARCHIVO = "ArchivoUsuarios.txt";

    ArrayList<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.etMainUser);
        etPassword = findViewById(R.id.etMainPassword);
        btnEnter = findViewById(R.id.btnMainEnter);

        verificarArchivoUsuarios();
        ListaUsuarios();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String passw = etPassword.getText().toString();
                Usuario usuario = new Usuario(user,passw);
                boolean hay = false;

                if (!listaUsuarios.equals(null)) {
                for (Usuario User: listaUsuarios) {
                    if (User.getNombreUsuario().equals(usuario.getNombreUsuario()) && User.getContraseña().equals(usuario.getContraseña())) {
                        hay = true;
                        break;
                    }
                }}
                if (hay) {
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,"Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.mnuIntegrantes:
                intent = new Intent(this,Integrantes.class);
                startActivity(intent);
                return true;
            case R.id.mnudescripcion:
                intent = new Intent(this,SobreProyecto.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void verificarArchivoUsuarios() {
        try {
            // buscar archivo
            boolean b = false;
            String [] listaArchivos = fileList();
            for (String nombreArchivo : listaArchivos) {
                if (nombreArchivo.equals(NOMBRE_ARCHIVO)){
                    b = true;
                    break;
                }

            }
            if (!b) {
                FileOutputStream fileOutputStream = openFileOutput(NOMBRE_ARCHIVO,MODE_PRIVATE);
                fileOutputStream.close();
                CargarDatosTxtUsuario();
            }

        } catch (Exception ex) {
            Log.e("MainActivity",ex.getMessage());
        }
    }

    private void ListaUsuarios() {
        try {
            listaUsuarios = new ArrayList<Usuario>();
            InputStreamReader inputStreamReader = new InputStreamReader(openFileInput((NOMBRE_ARCHIVO)));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linea = bufferedReader.readLine();
            String [] datos;
            while(linea!=null) {
                linea = bufferedReader.readLine();
                datos = linea.split(";");
                listaUsuarios.add(new Usuario(datos[0], datos[1]));

            }


        } catch (Exception ex) {
            Log.e("MainActivity",ex.getMessage());
        }


    }

    private void CargarDatosTxtUsuario() {
            try {
                ArrayList<Usuario> l = new ArrayList<>();
                l.add(new Usuario("cris","1"));
                l.add(new Usuario("igna","2"));
                l.add(new Usuario("perez","3"));
                l.add(new Usuario("rojas","4"));


                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOMBRE_ARCHIVO,MODE_APPEND));
                for (Usuario u: l) {
                    outputStreamWriter.write("\n" + u.toString());
                }
                outputStreamWriter.flush();
                outputStreamWriter.close();

            } catch (Exception ex) {
                Log.e("MainActivity",ex.getMessage());
            }
    }
    
}