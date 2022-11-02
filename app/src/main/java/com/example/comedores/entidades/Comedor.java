package com.example.comedores.entidades;

import java.io.Serializable;
import java.util.List;

public class Comedor implements Serializable {
    private long id;
    private long renacom;
    private String nombre;
    private String direccion;
    private String localidad;
    private String provincia;
    private String telefono;
    private long idResponsable;
    private String nombreResponsable;
    private String apellidoResponsable;
    private Estado estado;
    private List<Necesidad> listaNecesidades;

    public Comedor() { }

    public Comedor(long id, long renacom, String nombre,
                   String direccion, String localidad, String provincia,
                   String telefono, long idResponsable,
                   String nombreResponsable, String apellidoResponsable,
                   Estado estado, List<Necesidad> listaNecesidades) {
        this.id = id;
        this.renacom = renacom;
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.telefono = telefono;
        this.idResponsable = idResponsable;
        this.nombreResponsable = nombreResponsable;
        this.apellidoResponsable = apellidoResponsable;
        this.estado = estado;
        this.listaNecesidades = listaNecesidades;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getRenacom() { return renacom; }
    public void setRenacom(long renacom) { this.renacom = renacom; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public long getIdResponsable() { return idResponsable; }
    public void setIdResponsable(long idResponsable) { this.idResponsable = idResponsable; }

    public String getNombreResponsable() { return nombreResponsable; }
    public void setNombreResponsable(String nombreResponsable) { this.nombreResponsable = nombreResponsable; }

    public String getApellidoResponsable() { return apellidoResponsable; }
    public void setApellidoResponsable(String apellidoResponsable) {
        this.apellidoResponsable = apellidoResponsable;
    }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public List<Necesidad> getListaNecesidades() { return listaNecesidades; }
    public void setListaNecesidades(List<Necesidad> listaNecesidades) { this.listaNecesidades = listaNecesidades; }

    @Override
    public String toString() {
        return "Comedor{" +
                "id=" + id +
                ", renacom=" + renacom +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                ", telefono='" + telefono + '\'' +
                ", idResponsable=" + idResponsable +
                ", nombreResponsable='" + nombreResponsable + '\'' +
                ", apellidoResponsable='" + apellidoResponsable + '\'' +
                ", estado=" + estado +
                ", listaNecesidades=" + listaNecesidades +
                '}';
    }
}
