package com.blank.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Esta classe utiliza a biblioteca Lombok para reduzir o boilerplate code (código repetitivo).
 *
 * O Lombok gera automaticamente:
 * 1. Construtores (@NoArgsConstructor, @AllArgsConstructor).
 * 2. Métodos Getters e Setters para todos os campos (@Getter, @Setter).
 * 3. Outros métodos padrões como equals(), hashCode() e toString() (que podem ser adicionados com @Data ou @ToString).
 *
 * Isso resulta em um código mais limpo e conciso, ideal para Entidades de Domínio.
 */
@Entity(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String name;

    private String description;
}