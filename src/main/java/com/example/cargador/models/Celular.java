package com.example.cargador.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "celulares")
public class Celular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private int almacenamiento; // GB
    private int ram; // GB
    private String sistemaOperativo;
    private double precio;

    @OneToMany(mappedBy = "celular", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Cargador> cargadores = new ArrayList<>();

    public Celular() {}

    public Celular(Long id, String marca, String modelo, int almacenamiento, int ram, String sistemaOperativo, double precio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.almacenamiento = almacenamiento;
        this.ram = ram;
        this.sistemaOperativo = sistemaOperativo;
        this.precio = precio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAlmacenamiento() { return almacenamiento; }
    public void setAlmacenamiento(int almacenamiento) { this.almacenamiento = almacenamiento; }

    public int getRam() { return ram; }
    public void setRam(int ram) { this.ram = ram; }

    public String getSistemaOperativo() { return sistemaOperativo; }
    public void setSistemaOperativo(String sistemaOperativo) { this.sistemaOperativo = sistemaOperativo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public List<Cargador> getCargadores() { return cargadores; }
    public void setCargadores(List<Cargador> cargadores) { this.cargadores = cargadores; }

    public void addCargador(Cargador c) {
        cargadores.add(c);
        c.setCelular(this);
    }

    public void removeCargador(Cargador c) {
        cargadores.remove(c);
        c.setCelular(null);
    }
}
