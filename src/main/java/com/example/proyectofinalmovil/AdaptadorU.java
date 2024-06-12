package com.example.proyectofinalmovil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AdaptadorU extends RecyclerView.Adapter<AdaptadorU.ViewHolder>{
    private ArrayList<Usuario> listaUsuarios;
    private WeakReference<Context> contexto;

    private static final String FILE_NAME = "Login.txt";

    public AdaptadorU(ArrayList<Usuario> listaUsr, Context context) {
        listaUsuarios = listaUsr;
        contexto = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public AdaptadorU.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout2, parent, false);
        Context context = contexto.get();
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorU.ViewHolder holder, int position) {
        holder.actualizarDatos(listaUsuarios.get(position));
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImagen;
        TextView tvNombre;
        Button btnDelete;
        ConstraintLayout clItemLayout;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.ivImagen2);
            tvNombre = itemView.findViewById(R.id.tvItem2UserName);
            btnDelete = itemView.findViewById(R.id.btnDeleteUser);
            clItemLayout = itemView.findViewById(R.id.clItemUsr);

            // Manejo del click del botón de eliminar
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eliminarUsuario(getAdapterPosition());
                }
            });

            // Manejo del click del item layout
            clItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (context instanceof Menu) {
                        ((Menu) context).UsrClick(getAdapterPosition());
                    }
                }
            });
        }

        public void actualizarDatos(Usuario usuario) {
            tvNombre.setText(usuario.getNombreUsuario());
            if (usuario.getNombreUsuario().equals("cris")) {
                ivImagen.setImageResource(R.drawable.img_admin);
            } else {
                ivImagen.setImageResource(R.drawable.img_user);
            }
        }

        private void eliminarUsuario(int position) {
            Usuario usuario = listaUsuarios.get(position);
            if (usuario.getNombreUsuario().equals(MainActivity.currentUsername)) {
                Toast.makeText(contexto.get(), "No puedes eliminar el usuario actualmente logueado", Toast.LENGTH_SHORT).show();
                return;
            }
            listaUsuarios.remove(position);
            notifyItemRemoved(position);
            actualizarArchivoUsuarios();
        }

        private void actualizarArchivoUsuarios() {
            Context context = contexto.get();
            if (context != null) {
                File file = new File(context.getFilesDir(), FILE_NAME);
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    for (Usuario usuario : listaUsuarios) {
                        fileWriter.write(usuario.getNombreUsuario() + ";" + usuario.getContraseña() + "\n");
                    }
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
