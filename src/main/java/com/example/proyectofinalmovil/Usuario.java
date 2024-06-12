package com.example.proyectofinalmovil;

public class Usuario {
    private String NombreUsuario;
    private String Contraseña;

    public Usuario(String nombreUsuario, String contraseña) {
        NombreUsuario = nombreUsuario;
        Contraseña = contraseña;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String toString() {
        return NombreUsuario + ";" +  Contraseña;
    }
}
