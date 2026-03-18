package com.github.romis0203.ms.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

//anotations para gerar tudo bonitinho automatico
@Entity
@Table(name = "tb_reserva")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataReserva;
    private String nomeCliente;
    private Integer qtdePessoas;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;
}