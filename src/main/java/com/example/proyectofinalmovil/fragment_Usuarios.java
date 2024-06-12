package com.example.proyectofinalmovil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_Usuarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Usuarios extends Fragment {

    private ArrayList<Usuario> ListaUsuarios;
    private AdaptadorU adaptador;
    RecyclerView rvUsr;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_Usuarios() {
        // Required empty public constructor
    }
    public fragment_Usuarios(ArrayList<Usuario> L) {
        ListaUsuarios = L;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_Usuarios.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_Usuarios newInstance(String param1, String param2) {
        fragment_Usuarios fragment = new fragment_Usuarios();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__usuarios, container, false);

        rvUsr = view.findViewById(R.id.rvFragUsuarios);
        rvUsr.setLayoutManager(new GridLayoutManager(requireContext(),1));

        if (ListaUsuarios != null) {
            adaptador = new AdaptadorU(ListaUsuarios, requireContext());
            rvUsr.setAdapter(adaptador);
        } else {
            Toast.makeText(requireContext(), "Algo cargó mal", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}