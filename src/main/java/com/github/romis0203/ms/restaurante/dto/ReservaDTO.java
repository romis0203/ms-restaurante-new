package com.github.romis0203.ms.restaurante.dto;

import com.github.romis0203.ms.restaurante.entities.Reserva;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // Adicione isso para o Postman conseguir preencher o DTO
public class ReservaDTO {

    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 a 100 caracteres")
    private String nomeCliente;

    @NotNull(message = "A quantidade de pessoas é obrigatória")
    @Min(value = 1, message = "A reserva deve ter no mínimo uma pessoa")
    private Integer qtdePessoas; // Padronizado para camelCase

    @NotNull(message = "A data da reserva é obrigatória") // Corrigido: era @NotBlank
    private LocalDate dataReserva;

    @NotNull(message = "O restaurante é obrigatório")
    private RestauranteDTO restaurante;

    public ReservaDTO(Reserva reserva){
        id = reserva.getId();
        nomeCliente = reserva.getNomeCliente();
        qtdePessoas = reserva.getQtdePessoas();
        dataReserva = reserva.getDataReserva();
        // Garante que não dê erro se o restaurante for nulo na entidade
        if (reserva.getRestaurante() != null) {
            restaurante = new RestauranteDTO(reserva.getRestaurante());
        }
    }
}
