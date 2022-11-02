package com.example.comedores.entidades;

import java.io.Serializable;

public class Necesidad implements Serializable {
    private long id;
    private String descripcion;
    private Tipo tipo;
    private Estado estado;

    public Necesidad() { }
    public Necesidad(String descripcion, Tipo tipo, Estado estado) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.estado = estado;
    }
    public Necesidad(long id, String descripcion, Tipo tipo, Estado estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.estado = estado;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Necesidad{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", tipo=" + tipo +
                ", estado=" + estado +
                '}';
    }
}
