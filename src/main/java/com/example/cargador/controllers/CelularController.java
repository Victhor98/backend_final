package com.example.cargador.controllers;

import com.example.cargador.models.Celular;
import com.example.cargador.models.Cargador;
import com.example.cargador.repositories.CargadorRepository;
import com.example.cargador.repositories.CelularRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/celulares")
@CrossOrigin(origins = "https://victhor98.github.io")
public class CelularController {

    @Autowired
    private CelularRepository repository;

    @Autowired
    private CargadorRepository cargadorRepository;

    @Operation(summary = "agregar un celular nuevo (opcional: pasar cargadorIds como query para asociarlos)")
    @PostMapping
    public String agregar(@RequestBody Celular celular, @RequestParam(required = false) List<Long> cargadorIds) {
        celular.setId(null);
        if (cargadorIds != null && !cargadorIds.isEmpty()) {
            List<Cargador> cargadores = cargadorRepository.findAllById(cargadorIds);
            Set<Long> idsEncontrados = cargadores.stream()
                    .map(Cargador::getId)
                    .collect(Collectors.toSet());

            List<Long> faltantes = cargadorIds.stream()
                    .filter(id -> !idsEncontrados.contains(id))
                    .collect(Collectors.toList());

            if (!faltantes.isEmpty()) {
                return "No se encontraron cargadores con IDs: " + faltantes;
            }

            cargadores.forEach(celular::addCargador);
        }
        Celular guardado = repository.save(celular);
        return "Celular agregado con ID: " + guardado.getId();
    }

    @Operation(summary = "listar todos los celulares")
    @GetMapping
    public List<Celular> listar() {
        return repository.findAll();
    }

    @Operation(summary = "listar cargadores asociados a un celular")
    @GetMapping("/{id}/cargadores")
    public List<Cargador> listarCargadores(@PathVariable Long id) {
        return repository.findById(id).map(Celular::getCargadores).orElse(List.of());
    }

    @Operation(summary = "buscar celular por id")
    @GetMapping("/{id}")
    public Celular buscar(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(summary = "actualizar celular completo")
    @PutMapping("/{id}")
    public String actualizar(@PathVariable Long id, @RequestBody Celular nuevo) {
        Optional<Celular> opt = repository.findById(id);
        if (opt.isPresent()) {
            nuevo.setId(id);
            repository.save(nuevo);
            return "Celular actualizado con ID: " + id;
        }
        return "No se encontró celular con ID: " + id;
    }

    @Operation(summary = "actualizar celular parcialmente")
    @PatchMapping("/{id}")
    public String actualizarParcial(@PathVariable Long id, @RequestBody Celular parcial) {
        Optional<Celular> opt = repository.findById(id);
        if (opt.isPresent()) {
            Celular actual = opt.get();
            if (parcial.getMarca() != null) actual.setMarca(parcial.getMarca());
            if (parcial.getModelo() != null) actual.setModelo(parcial.getModelo());
            if (parcial.getAlmacenamiento() != 0) actual.setAlmacenamiento(parcial.getAlmacenamiento());
            if (parcial.getRam() != 0) actual.setRam(parcial.getRam());
            if (parcial.getSistemaOperativo() != null) actual.setSistemaOperativo(parcial.getSistemaOperativo());
            if (parcial.getPrecio() != 0) actual.setPrecio(parcial.getPrecio());
            repository.save(actual);
            return "Celular actualizado parcialmente con ID: " + id;
        }
        return "No se encontró celular con ID: " + id;
    }

    @Operation(summary = "eliminar celular")
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Celular eliminado con ID: " + id;
        }
        return "No se encontró celular con ID: " + id;
    }

    @Operation(summary = "agregar un cargador al celular (por id)")
    @PostMapping("/{id}/cargadores")
    public String agregarCargadorAlCelular(@PathVariable Long id, @RequestBody Cargador cargador) {
        return repository.findById(id).map(cel -> {
            cargador.setId(null);
            cel.addCargador(cargador);
            // al usar cascade ALL, guardar el celular persistirá el cargador
            repository.save(cel);
            return "Cargador agregado al celular con ID: " + cel.getId();
        }).orElse("No se encontró celular con ID: " + id);
    }

}
