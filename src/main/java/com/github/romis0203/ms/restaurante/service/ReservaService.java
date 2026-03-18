package com.github.romis0203.ms.restaurante.service;

import com.github.romis0203.ms.restaurante.dto.ReservaDTO;
import com.github.romis0203.ms.restaurante.entities.Reserva;
import com.github.romis0203.ms.restaurante.entities.Restaurante;
import com.github.romis0203.ms.restaurante.exceptions.ResourceNotFoundException;
import com.github.romis0203.ms.restaurante.repositories.ReservaRepository;
import com.github.romis0203.ms.restaurante.repositories.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Transactional(readOnly = true)
    public List<ReservaDTO> findAllReservas() {
        return reservaRepository.findAll()
                .stream()
                .map(ReservaDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservaDTO findReservaById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id));

        return new ReservaDTO(reserva);
    }

    @Transactional
    public ReservaDTO saveReserva(ReservaDTO inputDTO) {
        Reserva reserva = new Reserva();
        copyDtoToReserva(inputDTO, reserva);
        reserva = reservaRepository.save(reserva);
        return new ReservaDTO(reserva);
    }

    private void copyDtoToReserva(ReservaDTO inputDTO, Reserva reserva) {

        // Campos básicos
        reserva.setNomeCliente(inputDTO.getNomeCliente());
        reserva.setQtdePessoas(inputDTO.getQtdePessoas());
        reserva.setDataReserva(inputDTO.getDataReserva());

        // 🔥 REGRA CRÍTICA: restaurante é obrigatório
        if (inputDTO.getRestaurante() == null || inputDTO.getRestaurante().getId() == null) {
            throw new ResourceNotFoundException("Restaurante é obrigatório para a reserva");
        }

        // Busca no banco
        Restaurante restaurante = restauranteRepository
                .findById(inputDTO.getRestaurante().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurante não encontrado. ID: " + inputDTO.getRestaurante().getId()
                ));

        // Associa corretamente
        reserva.setRestaurante(restaurante);
    }

    @Transactional
    public ReservaDTO updateReserva(Long id, ReservaDTO inputDto) {
        try {
            Reserva reserva = reservaRepository.getReferenceById(id);
            copyDtoToReserva(inputDto, reserva);
            reserva = reservaRepository.save(reserva);
            return new ReservaDTO(reserva);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional
    public void deleteReservaById(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
        reservaRepository.deleteById(id);
    }
}