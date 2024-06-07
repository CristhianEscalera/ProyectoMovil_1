package com.example.proyectofinalmovil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AdaptadorU extends RecyclerView.Adapter<AdaptadorU.ViewHolder>{
    private ArrayList<Usuario> ListaUsuarios;
    //para el click
    WeakReference<Context> Contexto;
    public AdaptadorU(ArrayList<Usuario> listaUsr, Context context) {
        ListaUsuarios = listaUsr;
        Contexto = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public AdaptadorU.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout2, parent, false);
        Context context = Contexto.get();
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorU.ViewHolder holder, int position) {
        holder.actualizarDatos(ListaUsuarios.get(position));
    }

    @Override
    public int getItemCount() {
        return ListaUsuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImagen;
        TextView tvNombre;
        ConstraintLayout clItemLayout;
        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.ivImagen2);
            tvNombre = itemView.findViewById(R.id.tvItem2UserName);
            clItemLayout = itemView.findViewById(R.id.clItemUsr);

            //click
            clItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //se crea una interfaz con un metodo abstracto click
                    ((Menu)context).UsrClick(getAdapterPosition());
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
    }
}
