package com.uberclone.Modelos;

public class Cliente {

    String id;
    String Nombre;
    String email;
    String apellidos;
    String contraseña;


    public Cliente(String id, String nombre, String email, String apellidos, String contraseña) {
        this.id = id;
        Nombre = nombre;
        this.email = email;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
    }

    public Cliente() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
