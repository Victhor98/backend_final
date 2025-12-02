package com.example.cargador.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "cargadores")
public class Cargador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;
    private String tipo; // por ejemplo: USB-C, Micro-USB
    private int puertos;
    private double potencia; // en watts
    private double precio;

    @ManyToOne
    @JoinColumn(name = "celular_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonBackReference
    private Celular celular;

    public Cargador() {}

    public Cargador(Long id, String marca, String tipo, int puertos, double potencia, double precio) {
        this.id = id;
        this.marca = marca;
        this.tipo = tipo;
        this.puertos = puertos;
        this.potencia = potencia;
        this.precio = precio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getPuertos() { return puertos; }
    public void setPuertos(int puertos) { this.puertos = puertos; }

    public double getPotencia() { return potencia; }
    public void setPotencia(double potencia) { this.potencia = potencia; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public Celular getCelular() { return celular; }
    public void setCelular(Celular celular) { this.celular = celular; }
}
