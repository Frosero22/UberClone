package com.uberclone.Modelos;

public class Usuario {

    String id;
    String Nombre;
    String email;
    String apellidos;
    String contraseña;

    public Usuario(String id, String nombre, String email, String apellidos, String contraseña) {
        this.id = id;
        this.Nombre = nombre;
        this.email = email;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
