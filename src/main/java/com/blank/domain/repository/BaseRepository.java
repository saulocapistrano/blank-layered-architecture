package com.blank.domain.repository;

import com.blank.domain.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de dados para a entidade {@link BaseEntity}.
 *
 * Esta interface atua como um contrato para operações de acesso a dados (CRUD)
 * na camada de Domínio, seguindo o padrão Repository.
 *
 * Ao estender {@link JpaRepository}, o Spring Data JPA gera automaticamente
 * a implementação em tempo de execução, fornecendo métodos como save(), findById(),
 * findAll(), delete(), entre outros, sem a necessidade de codificação manual.
 *
 * O tipo {@link BaseEntity} define a Entidade de Domínio a ser gerenciada,
 * e {@link Long} define o tipo do seu identificador (ID).
 *
 * A anotação {@link Repository} indica ao Spring que esta interface é um componente
 * de acesso a dados, permitindo sua injeção em Services.
 */
@Repository
public interface BaseRepository extends JpaRepository<BaseEntity, Long> {
}