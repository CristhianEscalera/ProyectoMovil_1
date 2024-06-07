package com.example.proyectofinalmovil;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Menu extends AppCompatActivity implements Iauxiliar, Iaux {
    private static final String NOMBRE_ARCHIVO = "ArchivoProductos.txt";
    private static final String NOMBRE_ARCHIVO2 = "ArchivoUsuarios.txt";
    private MediaPlayer miaudio;
    private ArrayList<Producto> ListaProductos;
    private ArrayList<Usuario> ListaUsuarios;
    private Button btnProd, btnUsr;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    fragment_Productos fragmentProductos;
    fragment_Usuarios fragmentUsuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnProd = findViewById(R.id.btnProd);
        btnUsr = findViewById(R.id.btnUsr);

        // audio
        miaudio = MediaPlayer.create(getApplicationContext(),R.raw.musica);
        miaudio.start();

        // cargar lista Productos
        verificarArchivoProd();
        ListaProductos();

        // cargar lista Usuarios
        verificarArchivoUsuarios();
        ListaUsuarios();

        fragmentProductos = new fragment_Productos(ListaProductos);
        fragmentUsuarios = new fragment_Usuarios(ListaUsuarios);
        fragmentManager = getSupportFragmentManager();

        btnProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fragmentTransaction = fragmentManager.beginTransaction();

                    if (fragmentManager.findFragmentByTag("Producto") == null) {
                        if (fragmentManager.findFragmentByTag("Usuario") != null) {
                            fragmentTransaction.replace(R.id.contenedor,fragmentProductos,"Producto");
                        }
                        else {
                            fragmentTransaction.add(R.id.contenedor, fragmentProductos, "Producto");
                        }
                        fragmentTransaction.commit();
                    }
                    else {
                        Toast.makeText(Menu.this, "Productos ya esta cargado", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex) {
                    Log.e("Menu_Activity",ex.getMessage());
                }
            }
        });

        btnUsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fragmentTransaction = fragmentManager.beginTransaction();

                    if (fragmentManager.findFragmentByTag("Usuario") == null) {
                        if (fragmentManager.findFragmentByTag("Producto") != null) {
                            fragmentTransaction.replace(R.id.contenedor,fragmentUsuarios,"Usuario");
                        }
                        else {
                            fragmentTransaction.add(R.id.contenedor, fragmentUsuarios, "Usuario");
                        }
                        fragmentTransaction.commit();
                    }
                    else {
                        Toast.makeText(Menu.this, "Usuarios ya esta cargado", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex) {
                    Log.e("Menu_Activity",ex.getMessage());
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

    @Override
    public void ProdClick(int posicion) {
        Producto p = ListaProductos.get(posicion);
        String cadena = p.getNombre() + " comprado a " + p.getPrecio()  + " Bs.";
        Toast.makeText(this, cadena, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void UsrClick(int posicion) {
        Usuario u = ListaUsuarios.get(posicion);
        Toast.makeText(this, u.getNombreUsuario(), Toast.LENGTH_SHORT).show();
    }

    private void verificarArchivoProd() {
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
                CargarTexto();
            }

        } catch (Exception ex) {
            Log.e("ActivityProductos",ex.getMessage());
        }
    }

    private void ListaProductos() {
        try {
            ListaProductos = new ArrayList<Producto>();
            InputStreamReader inputStreamReader = new InputStreamReader(openFileInput((NOMBRE_ARCHIVO)));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linea = bufferedReader.readLine();
            String [] datos;
            while(linea!=null) {
                linea = bufferedReader.readLine();
                datos = linea.split(";");
                ListaProductos.add(new Producto(datos[0], Double.parseDouble(datos[1]), datos[2],datos[3]));
            }

        } catch (Exception ex) {
            Log.e("ActivityProductos",ex.getMessage());
        }
    }

    private void CargarTexto() {
        try {
            ArrayList<Producto> l = new ArrayList<>();
            l.add(new Producto("Pan", 0.50, "pan normal", "Pan"));
            l.add(new Producto("Croissant", 2.00, "croissant de chocolate", "Pastelería"));
            l.add(new Producto("Baguette", 2.80, "Baguette crujiente", "Pan"));
            l.add(new Producto("Magdalenas", 1.00, "Magdalenas de vainilla", "Pastelería"));
            l.add(new Producto("Pan de centeno", 3.00, "Pan saludable", "Pan"));
            l.add(new Producto("Panqueques", 1.80, "Panqueques normales", "Pastelería"));
            l.add(new Producto("Rosquillas", 1.50, "Rosquillas dulces", "Pastelería"));
            l.add(new Producto("Marraqueta", 2.50, "es una marraqueta", "Pan"));

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOMBRE_ARCHIVO,MODE_APPEND));
            for ( Producto p: l ) {
                outputStreamWriter.write("\n" + p.toString());
            }
            outputStreamWriter.flush();
            outputStreamWriter.close();

        } catch (Exception ex) {
            Log.e("MainActivity",ex.getMessage());
        }
    }


    private void verificarArchivoUsuarios() {
        try {
            // buscar archivo
            boolean b = false;
            String [] listaArchivos = fileList();
            for (String nombreArchivo : listaArchivos) {
                if (nombreArchivo.equals(NOMBRE_ARCHIVO2)){
                    b = true;
                    break;
                }

            }
            if (!b) {
                FileOutputStream fileOutputStream = openFileOutput(NOMBRE_ARCHIVO2,MODE_PRIVATE);
                fileOutputStream.close();
            }

        } catch (Exception ex) {
            Log.e("MainActivity",ex.getMessage());
        }
    }

    private void ListaUsuarios() {
        try {
            ListaUsuarios = new ArrayList<Usuario>();
            InputStreamReader inputStreamReader = new InputStreamReader(openFileInput((NOMBRE_ARCHIVO2)));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linea = bufferedReader.readLine();
            String [] datos;
            while(linea!=null) {
                linea = bufferedReader.readLine();
                datos = linea.split(";");
                ListaUsuarios.add(new Usuario(datos[0], datos[1]));

            }


        } catch (Exception ex) {
            Log.e("MainActivity",ex.getMessage());
        }


    }

}