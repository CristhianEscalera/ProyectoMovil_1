package com.example.proyectofinalmovil;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_Productos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Productos extends Fragment {

    private ArrayList<Producto> ListaProductos;
    private Adaptador adaptador;
    RecyclerView rvProd;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_Productos() {
        // Required empty public constructor
    }
    public fragment_Productos(ArrayList<Producto> L) {
        ListaProductos = L;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_Productos.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_Productos newInstance(String param1, String param2) {
        fragment_Productos fragment = new fragment_Productos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__productos, container, false);

        rvProd = view.findViewById(R.id.rvFragProductos);
        rvProd.setLayoutManager(new GridLayoutManager(requireContext(),1));

        if (ListaProductos != null) {
            adaptador = new Adaptador(ListaProductos, requireContext());
            rvProd.setAdapter(adaptador);
        } else {
            Toast.makeText(requireContext(), "Algo cargó mal", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    /*public void recibirDatos(ArrayList<Producto> lista) {
        ListaProductos = lista;
        if (ListaProductos != null) {
            adaptador = new Adaptador(ListaProductos, requireContext());
            rvProd.setAdapter(adaptador);
        } else {
            Toast.makeText(requireContext(), "Algo cargó mal", Toast.LENGTH_SHORT).show();
        }
    }*/
}