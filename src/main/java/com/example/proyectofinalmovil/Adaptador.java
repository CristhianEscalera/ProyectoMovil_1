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

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{
    private ArrayList<Producto> ListaProductos;
    //para el click
    WeakReference<Context> Contexto;
    public Adaptador(ArrayList<Producto> listaprod, Context context) {
        ListaProductos = listaprod;
        Contexto = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        Context context = Contexto.get();
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        holder.actualizarDatos(ListaProductos.get(position));
    }

    @Override
    public int getItemCount() {
        return ListaProductos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrecio, tvNombre, tvDescripcion;
        ImageView ivImagen;
        
        ConstraintLayout clItemLayout;
        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            tvPrecio = itemView.findViewById(R.id.tvItemPrecio);
            tvNombre = itemView.findViewById(R.id.tvItemNombre);
            tvDescripcion = itemView.findViewById(R.id.tvItemDescripcion);
            clItemLayout = itemView.findViewById(R.id.clItemLayout);
            ivImagen = itemView.findViewById(R.id.ivImagen);

            //click
            clItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //se crea una interfaz con un metodo abstracto click
                    ((Menu)context).ProdClick(getAdapterPosition());
                }
            });
        }
        public void actualizarDatos(Producto prod) {
            tvNombre.setText(prod.getNombre());
            tvPrecio.setText(String.valueOf(prod.getPrecio()) + " Bs.");
            tvDescripcion.setText(prod.getDescripcion() + "");
            if (prod.getCategoria().equals("Pastelería")) {
                ivImagen.setImageResource(R.drawable.pastel);
            } else {
                ivImagen.setImageResource(R.drawable.pan);
            }
        }
    }
}
