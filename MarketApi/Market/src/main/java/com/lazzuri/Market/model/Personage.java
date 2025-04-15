package com.lazzuri.Market.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Personage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "campo obrigatório")
    private String name;

    @NotNull(message = "campo obrigatório")
    @Enumerated(EnumType.STRING)
    private ClassPersonageType classType;

    @NotNull(message = "campo obrigatório")
    @Positive(message = "Deve ser no minimo 1")
    private int level;

    @NotNull(message = "campo obrigatório")
    @Positive(message = "A quantidade deve ser positiva")
    private Integer coins;


    
}
