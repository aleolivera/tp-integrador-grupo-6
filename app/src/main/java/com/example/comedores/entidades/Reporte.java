package com.example.comedores.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Reporte implements Serializable {
    private long id;
    private Usuario usuario;
    private Tipo tipo;
    private LocalDate fechaAlta;
    private String descripcion;
    private long idReportado;
    private String respuesta;
    private Estado estado;

    public Reporte() { }

    public Reporte(long id, Usuario usuario, Tipo tipo,
                   LocalDate fechaAlta, String descripcion,
                   long idReportado, String respuesta,
                   Estado estado) {
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.fechaAlta = fechaAlta;
        this.descripcion = descripcion;
        this.idReportado = idReportado;
        this.respuesta = respuesta;
        this.estado = estado;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public long getIdReportado() { return idReportado; }
    public void setIdReportado(long idReportado) { this.idReportado = idReportado; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @Override
    public String toString() {
        return id + " - " + usuario.getEmail() +" - " +tipo.toString();
    }
}
