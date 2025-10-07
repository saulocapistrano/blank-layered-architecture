package com.blank.domain.model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Esta Entidade de Domínio é criada utilizando o código Java tradicional (boilerplate).
 *
 * Ao contrário de outras classes que podem usar Lombok, esta demonstra a criação manual
 * de construtores, Getters, Setters, e métodos de utilidade (equals, hashCode, toString).
 * Isso é fundamental para entender o código gerado pelo Lombok.
 */
@Entity
public class BoilerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String name;
    private String description;

    // --- Construtores ---

    /**
     * Construtor padrão (vazio), necessário para que o JPA (Hibernate)
     * consiga instanciar a entidade ao recuperar dados do banco.
     */
    public BoilerEntity() {
    }

    /**
     * Construtor com todos os argumentos, útil para instanciar a entidade
     * com todos os valores de uma só vez no código.
     */
    public BoilerEntity(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // --- Getters e Setters ---

    /**
     * Getter para o campo 'id'. Permite obter o valor do identificador único da entidade.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter para o campo 'id'. Permite definir ou alterar o identificador.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter para o campo 'name'.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter para o campo 'name'.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter para o campo 'description'.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter para o campo 'description'.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    // --- Métodos de Utilidade (Sobrescritos) ---

    /**
     * Método toString() sobrescrito.
     * Facilita a depuração e o logging, pois fornece uma representação de string legível do objeto.
     */
    @Override
    public String toString() {
        return "BoilerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Método equals() sobrescrito.
     * Define como dois objetos desta classe são considerados iguais.
     * Essencial para comparar entidades e geralmente baseia-se no campo ID.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        // Verifica se o objeto comparado é uma instância de BoilerEntity
        if (!(object instanceof BoilerEntity that)) return false;
        // A comparação de igualdade deve ser feita com base nos campos relevantes
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription());
    }

    /**
     * Método hashCode() sobrescrito.
     * Retorna um código hash numérico para o objeto.
     * Deve ser implementado em conjunto com equals() para que a entidade funcione corretamente em coleções como Hashes (Ex: HashMap, HashSet).
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }
}