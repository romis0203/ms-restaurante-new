package com.github.romis0203.ms.restaurante.dto;

import com.github.romis0203.ms.restaurante.entities.Restaurante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class RestauranteDTO {


    private Long id;

    @NotBlank(message = "campo é obrigatório")
    private String nome;

    @NotBlank(message = "campo é obrigatório")
    private String endereco;

    @NotBlank(message = "campo é obrigatório")
    @Size(min = 5, max = 100)
    private String cidade;

    @NotBlank(message = "campo é obrigatório")
    @Size(min = 2, max = 2)
    private String uf;

    public RestauranteDTO(Restaurante restaurante){

        id = restaurante.getId();
        nome = restaurante.getNome();
        endereco = restaurante.getEndereco();
        cidade = restaurante.getCidade();
        uf = restaurante.getUf();
    }
}
