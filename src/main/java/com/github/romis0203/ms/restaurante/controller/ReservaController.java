package com.github.romis0203.ms.restaurante.controller;

import com.github.romis0203.ms.restaurante.dto.ReservaDTO;
import com.github.romis0203.ms.restaurante.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservas")

public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getAllReservas(){

        List<ReservaDTO> reservas = reservaService.findAllReservas();

        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReservaById(@PathVariable Long id){

        ReservaDTO reservaDTO = reservaService.findReservaById(id);
        return ResponseEntity.ok(reservaDTO);
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> createReserva(@Valid @RequestBody ReservaDTO reservaDTO){

        reservaDTO = reservaService.saveReserva(reservaDTO);

        URI uri  = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(reservaDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(reservaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> updateReserva (@PathVariable Long id, @RequestBody @Valid ReservaDTO reservaDTO){

        reservaDTO = reservaService.updateReserva(id, reservaDTO);
        return ResponseEntity.ok(reservaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservaById(@PathVariable Long id){

        reservaService.deleteReservaById(id);
        return ResponseEntity.noContent().build();
    }
}
