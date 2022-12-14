package com.example.comedores.entidades;

import java.io.Serializable;

public class Usuario implements Serializable {
    protected long id;
    protected int tipo;
    protected String email;
    protected String password;
    protected boolean estado;
    protected String nombre;
    protected String apellido;
    protected String dni;
    protected String direccion;
    protected String localidad;
    protected String provincia;
    protected String telefono;
    protected Comedor comedor;

    public Usuario() { }
    public Usuario(long id, int tipo, String email,
                   String password, boolean estado, String nombre,
                   String apellido, String dni, String direccion,
                   String localidad, String provincia,
                   String telefono,Comedor comedor) {
        this.id = id;
        this.tipo = tipo;
        this.email = email;
        this.password = password;
        this.estado = estado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.telefono = telefono;
        this.comedor=comedor;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getTipo() { return tipo; }
    public void setTipo(int tipo) { this.tipo = tipo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Comedor getComedor() { return comedor; }
    public void setComedor(Comedor comedor) { this.comedor = comedor; }

    @Override
    public String toString() {
        return tipo +" - " +  email + " - " +  nombre + " " + apellido+" - "+provincia ;
    }
}
