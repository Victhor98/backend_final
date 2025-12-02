package com.example.cargador.controllers;

import com.example.cargador.models.Cargador;
import com.example.cargador.models.Celular;
import com.example.cargador.repositories.CargadorRepository;
import com.example.cargador.repositories.CelularRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cargadores")
@CrossOrigin(origins = "https://victhor98.github.io")
public class CargadorController {

    @Autowired
    private CargadorRepository repository;

    @Autowired
    private CelularRepository celularRepository;

    @Operation(summary = "agregar un cargador nuevo (opcional: pasar celularId como query para asociarlo)")
    @PostMapping
    public String agregarCargador(@RequestBody Cargador cargador, @RequestParam(required = false) Long celularId) {
        cargador.setId(null);
        if (celularId != null) {
            return celularRepository.findById(celularId).map(cel -> {
                cargador.setCelular(cel);
                Cargador guardado = repository.save(cargador);
                return "Cargador agregado con ID: " + guardado.getId() + " y asociado al celular " + cel.getId();
            }).orElse("No se encontró celular con ID: " + celularId);
        }
        Cargador guardado = repository.save(cargador);
        return "Cargador agregado con ID: " + guardado.getId();
    }

    @Operation(summary = "listar todos los cargadores")
    @GetMapping
    public List<Cargador> listar() {
        return repository.findAll();
    }

    @Operation(summary = "buscar cargador por id")
    @GetMapping("/{id}")
    public Cargador buscarPorId(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(summary = "actualizar cargador completo")
    @PutMapping("/{id}")
    public String actualizar(@PathVariable Long id, @RequestBody Cargador nuevo) {
        Optional<Cargador> existente = repository.findById(id);
        if (existente.isPresent()) {
            nuevo.setId(id);
            repository.save(nuevo);
            return "Cargador actualizado con ID: " + id;
        }
        return "No se encontró cargador con ID: " + id;
    }

    @Operation(summary = "actualizar cargador parcialmente")
    @PatchMapping("/{id}")
    public String actualizarParcial(@PathVariable Long id, @RequestBody Cargador parcial) {
        Optional<Cargador> opt = repository.findById(id);
        if (opt.isPresent()) {
            Cargador actual = opt.get();
            if (parcial.getMarca() != null) actual.setMarca(parcial.getMarca());
            if (parcial.getTipo() != null) actual.setTipo(parcial.getTipo());
            if (parcial.getPuertos() != 0) actual.setPuertos(parcial.getPuertos());
            if (parcial.getPotencia() != 0) actual.setPotencia(parcial.getPotencia());
            if (parcial.getPrecio() != 0) actual.setPrecio(parcial.getPrecio());
            repository.save(actual);
            return "Cargador actualizado parcialmente con ID: " + id;
        }
        return "No se encontró cargador con ID: " + id;
    }

    @Operation(summary = "eliminar cargador")
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Cargador eliminado con ID: " + id;
        }
        return "No se encontró cargador con ID: " + id;
    }

}
