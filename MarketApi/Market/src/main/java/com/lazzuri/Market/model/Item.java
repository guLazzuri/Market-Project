package com.lazzuri.Market.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Item {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "campo obrigatório")
    private String name;

    @NotNull(message = "campo obrigatório")
    @Enumerated(EnumType.STRING)
    private TypeItem typeItem;

    @NotNull(message = "campo obrigatório")
    @Enumerated(EnumType.STRING)
    private RareItem rareItem;

    @NotNull(message = "campo obrigatório")
    @Positive(message = "A quantidade deve ser positiva")
    private Integer price;

    @ManyToOne
    @NotNull(message = "Personagem obrigatório")
    private Personage personage;
}