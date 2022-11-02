package com.example.comedores.entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Solicitud implements Serializable {
    private long id;
    private Comedor comedor;
    private LocalDate fechaAlta;
    private boolean estado;
    private long idSupervisor;

    public Solicitud() { }
    public Solicitud(Comedor comedor, LocalDate fechaAlta,
                     boolean estado, long idSupervisor) {
        this.comedor = comedor;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        this.idSupervisor = idSupervisor;
    }
    public Solicitud(long id, Comedor comedor, LocalDate fechaAlta,
                     boolean estado, long idSupervisor) {
        this.id = id;
        this.comedor = comedor;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        this.idSupervisor = idSupervisor;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Comedor getComedor() { return comedor; }
    public void setComedor(Comedor comedor) { this.comedor = comedor; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public long getIdSupervisor() { return idSupervisor; }
    public void setIdSupervisor(long idSupervisor) { this.idSupervisor = idSupervisor; }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", comedor=" + comedor.toString() +
                ", fechaAlta=" + fechaAlta +
                ", estado=" + estado +
                ", idSupervisor=" + idSupervisor +
                '}';
    }
}
