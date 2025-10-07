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

## 🐳 **Containerização com Docker**

Este projeto está totalmente preparado para execução em containers Docker, garantindo consistência entre ambientes de desenvolvimento e produção.

### **Docker Compose - Infraestrutura Completa**

O `docker-compose.yml` provisiona automaticamente toda a infraestrutura necessária:

- **PostgreSQL 15**: Banco de dados relacional principal
- **PgAdmin 4**: Interface web para administração do banco
- **MinIO**: Serviço de armazenamento de objetos compatível com S3
- **Aplicação Spring Boot**: Container da aplicação principal

### **Configuração do Dockerfile**

Utilizamos multi-stage build para otimização:
- **Stage 1 (build)**: Baseado em Maven + Eclipse Temurin 17 para compilação
- **Stage 2 (runtime)**: Baseado em JRE Alpine minimalista para execução

```dockerfile
FROM maven:3.9.6-eclipse-temurin-17 AS build
# ... construção otimizada

FROM eclipse-temurin:17-jre-alpine AS runtime
# ... runtime minimalista
```

### **Execução com Docker Compose**

```bash
# Iniciar todos os serviços
docker-compose up -d

# Parar todos os serviços
docker-compose down

# Ver logs da aplicação
docker-compose logs app -f
```

---

## 🧪 **Estratégia de Testes**

A arquitetura em camadas facilita a implementação de testes em diferentes níveis:

### **Testes Unitários**
- Foco na camada **`domain/service`**
- Testam regras de negócio isoladamente
- Sem dependências externas (banco, rede, etc.)
- Execução rápida - ideais para pipeline CI/CD

```java
// Exemplo: Teste unitário de Service
@Test
void shouldCreateUserWithValidData() {
    // Arrange
    User user = new User("John", "john@email.com");
    
    // Act
    User result = userService.create(user);
    
    // Assert
    assertNotNull(result.getId());
    assertEquals("John", result.getName());
}
```

### **Testes de Integração**
- Validam integração entre camadas
- Testam controllers, repositórios e configurações
- Utilizam banco de dados em memória (H2) ou testcontainers
- Garantem que os componentes trabalham corretamente juntos

```java
// Exemplo: Teste de integração de Controller
@SpringBootTest
@AutoConfigureTestDatabase
class UserControllerIntegrationTest {
    
    @Test
    void shouldReturnUserWhenValidId() {
        // Testa desde o controller até o repositório
    }
}
```

---

## 🗄️ **Escolha do PostgreSQL**

### **Por que PostgreSQL?**

- **Open Source e Maduro**: Mais de 20 anos de desenvolvimento ativo
- **ACID Compliant**: Garante consistência e integridade dos dados
- **Recursos Avançados**: JSONB, Full-Text Search, Geospatial, etc.
- **Performance**: Otimizado para cargas de trabalho complexas
- **Ecosystem**: Ferramentas robustas de administração e monitoramento

### **Configuração no Docker**
```yaml
postgres-db:
  image: postgres:15-alpine  # Versão Alpine para menor footprint
  environment:
    POSTGRES_DB: base_db
    POSTGRES_USER: postgres
    POSTGRES_PASSWORD: 123456
  volumes:
    - postgres_data:/var/lib/postgresql/data  # Persistência de dados
```

---

## ⚙️ **Configuração do Application Properties**

### **Configurações Essenciais**

```properties
# Identificação da Aplicação
spring.application.name=blank
server.port=8080

# Banco de Dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/base_db
spring.datasource.username=postgres
spring.datasource.password=123456
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Documentação API
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.packages-to-scan=com.blank.infra.adapter.in.rest
```

### **Configuração para Ambientes**

**application-dev.properties** (Desenvolvimento):
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.com.blank=DEBUG
```

**application-prod.properties** (Produção):
```properties
spring.jpa.show-sql=false
logging.level.com.blank=INFO
management.endpoints.web.exposure.include=health,metrics
```

---

## ☕ **Java 17 - Por que Usar?**

### **Benefícios da Versão LTS**

- **Long Term Support**: Suporte estendido até 2029
- **Records**: Modelos de dados imutáveis de forma concisa
- **Pattern Matching**: Simplificação de condições e casts
- **Text Blocks**: Manipulação de strings multi-linha
- **Performance**: Melhorias no Garbage Collector e JIT compiler
- **Security**: Atualizações de segurança regulares

### **Configuração no Maven**
```xml
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <java.version>17</java.version>
</properties>
```

---

## 🚀 **Início Rápido**

### **Opção 1: Docker Compose (Recomendado)**
```bash
# Clone o projeto
git clone <seu-repositorio>
cd <projeto>

# Execute com Docker
docker-compose up -d

# Acesse:
# Aplicação: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
# PgAdmin: http://localhost:5050
# MinIO: http://localhost:9001
```

### **Opção 2: Desenvolvimento Local**
```bash
# Certifique-se de ter Java 17 e PostgreSQL rodando
./mvnw spring-boot:run

# Ou execute via IDE configurada com Java 17
```

### **Fluxo de Desenvolvimento**

1.  Comece definindo suas **Entidades** em `domain/model/`.
2.  Defina os **Contratos** de acesso a dados (Interfaces) em `domain/repository/`.
3.  Implemente a **Lógica de Negócio** em `domain/service/`.
4.  Crie os **Controllers** e **DTOs** em `infra/adapter/` para conectar tudo via HTTP.
5.  Escreva **Testes Unitários** para services e **Testes de Integração** para controllers.
6.  Execute `docker-compose up` para testar a integração completa.

---

## 📊 **Monitoramento e Debug**

- **Health Check**: `http://localhost:8080/actuator/health`
- **Metrics**: `http://localhost:8080/actuator/metrics`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **PgAdmin**: `http://localhost:5050` (user@blank.com / adminpassword)
- **MinIO Console**: `http://localhost:9001` (minioadmin / minioadmin)

Bom desenvolvimento! 🚀