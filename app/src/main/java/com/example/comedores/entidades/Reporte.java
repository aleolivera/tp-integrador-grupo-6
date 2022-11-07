package com.example.comedores.entidades;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class Reporte implements Serializable {
    private long id;
    private Usuario usuarioAlta;
    private Tipo tipo;
    private Date fechaAlta;
    private String descripcion;
    private long idReportado;
    private String respuesta;
    private Estado estado;

    private Usuario UsuarioReportado;
    private Necesidad NecesidadReportada;

    public Reporte() {
    }

    public Reporte(long id, Usuario usuario, Tipo tipo,
                   Date fechaAlta, String descripcion,
                   long idReportado, String respuesta,
                   Estado estado) {
        this.id = id;
        this.usuarioAlta = usuario;
        this.tipo = tipo;
        this.fechaAlta = fechaAlta;
        this.descripcion = descripcion;
        this.idReportado = idReportado;
        this.respuesta = respuesta;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuarioAlta;
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioAlta = usuario;
    }

    public Usuario getUsuarioRep() {
        return UsuarioReportado;
    }

    public void setUsuarioRep(Usuario usuario) {
        this.UsuarioReportado = usuario;
    }

    public Necesidad getNecesidadRep() {
        return NecesidadReportada;
    }

    public void setNecesidadRep(Necesidad NecesidadReportada) {
        this.NecesidadReportada = NecesidadReportada;
    }


    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getIdReportado() {
        return idReportado;
    }

    public void setIdReportado(long idReportado) {
        this.idReportado = idReportado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Id reporte: " + id + " Tipo: " + tipo.toString() + " Fecha de Alta:" + fechaAlta.toString() + "  Usuario Alta: " + usuarioAlta.getEmail() + " Estado: " + estado.getDescripcion();
    }
}
