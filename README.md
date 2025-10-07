
# 🚀 **Modelo Base para Aplicações REST com Spring Boot**

Esse é um modelo arquitetural (`blank`) de aplicações REST construído com Spring Boot.

Este projeto foi estruturado seguindo o padrão **Arquitetura em Camadas (Layered Architecture)**, priorizando a **Separação de Responsabilidades** e o **Baixo Acoplamento**, pilares fundamentais para sistemas manuteníveis, escaláveis e testáveis.

## 🧱 Princípios Arquiteturais

A estrutura é dividida em duas grandes áreas para isolar o **O QUÊ** (Domínio/Regras) do **COMO** (Infraestrutura/Tecnologia).

1.  **Camadas Externas (Infraestrutura):** Lidam com a tecnologia (HTTP, Banco de Dados, Frameworks).
2.  **Camadas Internas (Domínio):** Lidam exclusivamente com a lógica de negócio principal.

A regra fundamental é: **Camadas Externas podem depender das Internas, mas as Internas NUNCA devem depender das Externas.**

---

## 📁 Estrutura de Pacotes

Abaixo, detalhamos a função de cada pacote no projeto:

### 1. Pacote `domain/` (Coração da Aplicação)

Esta camada é o núcleo do seu negócio. Deve ser **livre de dependências** de frameworks como Spring Boot, JPA ou qualquer protocolo de rede.

| Pacote | Função | Conteúdo Típico |
| :--- | :--- | :--- |
| **`model/`** | **Entidades de Domínio.** | As classes que representam os dados e as regras de negócio intrínsecas (Ex: `User`, `Product`). |
| **`service/`** | **Lógica de Negócio.** | Implementa as regras complexas, orquestra operações e realiza validações baseadas no *estado* do domínio. |
| **`repository/`** | **Contratos de Persistência (Portas).** | **Apenas Interfaces.** Define *o que* deve ser salvo/buscado, sem se importar *como* (Ex: `UserRepository` interface). |
| **`exception/`** | **Erros de Negócio.** | Classes de exceção que representam falhas na regra de negócio (Ex: `ResourceNotFoundException`). |

### 2. Pacote `infra/` (Infraestrutura e Adaptação)

Esta camada contém todos os **detalhes técnicos** e **adaptadores** que ligam o domínio ao mundo exterior.

| Pacote | Função | Conteúdo Típico |
| :--- | :--- | :--- |
| **`configuration/`** | **Configuração do Aplicativo.** | Classes `@Configuration` do Spring (Ex: configuração do banco de dados, `RestTemplate` beans). |
| **`security/`** | **Implementação de Segurança.** | Classes de configuração do Spring Security, filtros de autenticação (Ex: `SecurityConfig`, `JwtTokenFilter`). |
| **`adapter/`** | **Adaptadores de Entrada/Saída.** | Onde ficam os **Controllers**, **Mappers** e **DTOs** (estruturas de dados de entrada/saída). |
| **`rest/`** | **Clientes REST Externos.** | Se a aplicação consumir outras APIs, este é o lugar dos clientes (`@FeignClient` ou `WebClient`). |
| **`util/`** | **Classes Auxiliares.** | Funcionalidades técnicas genéricas (Ex: classes de utilidade para *logs*, *hashing*, ou *helpers*). |
| **Implementação de Repositório** | **Implementa as Interfaces.** | A classe que implementa a interface de `domain/repository` usando a tecnologia escolhida (Ex: `JpaUserRepositoryImpl` com `@Repository`). |

---

## 🎯 Por Que Usar Essa Arquitetura?

Este modelo é ideal para construir **aplicações robustas de nível empresarial (enterprise)**.

### Vantagens Chave:

* **Testabilidade Elevada:** A camada **`domain/service`** pode ser testada isoladamente, sem precisar subir o servidor web, o banco de dados ou o sistema de segurança.
* **Manutenibilidade:** É fácil localizar onde corrigir um *bug* ou adicionar uma nova funcionalidade, pois cada responsabilidade está em seu pacote dedicado.
* **Flexibilidade (Baixo Acoplamento):** Se for necessário trocar o banco de dados (de PostgreSQL para MongoDB, por exemplo), a alteração será limitada à camada **`infra/`**, sem tocar nas regras de negócio em **`domain/`**.

### Projetos Adequados:

Esta arquitetura é **mais adequada** para projetos que exigem:

1.  **Regras de Negócio Complexas:** Onde a lógica do *Service* é mais importante que a interface de usuário.
2.  **Vida Longa (Long-lived applications):** Sistemas que evoluirão ao longo dos anos e precisarão de manutenção constante.
3.  **Ambiente de Múltiplas Tecnologias:** Projetos onde a tecnologia (framework, DB) tem alta probabilidade de mudar ao longo do tempo.

---

### Início Rápido

1.  Comece definindo suas **Entidades** em `domain/model/`.
2.  Defina os **Contratos** de acesso a dados (Interfaces) em `domain/repository/`.
3.  Implemente a **Lógica de Negócio** em `domain/service/`.
4.  Crie os **Controllers** e **DTOs** em `infra/adapter/` para conectar tudo via HTTP.

Bom desenvolvimento!