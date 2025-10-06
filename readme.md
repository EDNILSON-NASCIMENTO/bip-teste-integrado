# 🏗️ Desafio Fullstack Integrado (Arquitetura Spring Boot Moderna)

Este projeto é uma aplicação Fullstack completa que gerencia "Benefícios". O objetivo inicial era refatorar uma aplicação legada baseada em EJB para uma arquitetura moderna, robusta e simplificada, utilizando Spring Boot para o backend e Angular para o frontend.

## ✨ Arquitetura e Decisões de Design

A aplicação foi reestruturada seguindo as melhores práticas do mercado para desenvolvimento de software com Java и Angular:

* **Backend Unificado com Spring Boot:** A complexidade de um módulo EJB separado foi eliminada. Toda a lógica de negócio agora reside em um único módulo Spring Boot, que é autossuficiente e roda com um servidor Tomcat embutido. Isso simplifica o desenvolvimento, os testes e o deploy.
* **Banco de Dados com Metadados (EAV):** A persistência utiliza um padrão de Entidade-Atributo-Valor, com uma tabela `BENEFICIO` principal e uma tabela `BENEFICIO_META` para atributos dinâmicos. Isso oferece grande flexibilidade para adicionar novos campos no futuro sem alterações no schema do banco.
* **Correção de Bug e Transações:** A lógica de transferência, que era suscetível a inconsistências, foi movida para uma classe de Serviço (`@Service`) e protegida pela anotação `@Transactional` do Spring. Isso garante que as operações sejam atômicas. Além disso, o controle de concorrência é garantido via Locking Otimista com o campo `@Version` na entidade principal.
* **Spring Data JPA:** A camada de acesso a dados foi drasticamente simplificada com o uso de Repositórios do Spring Data JPA, eliminando a necessidade de código repetitivo (`boilerplate`) para operações de CRUD.
* **Frontend com Angular Standalone:** A interface do usuário é uma Single-Page Application (SPA) construída com a arquitetura mais recente de componentes Standalone do Angular, garantindo uma aplicação mais otimizada e modular.

## 🛠️ Tecnologias Utilizadas

* **Backend:**
    * Java 17
    * Spring Boot 3.2.5
    * Spring Web
    * Spring Data JPA (Hibernate)
    * H2 Database Engine (Banco de dados em memória)
    * Maven
* **Frontend:**
    * Angular 17+
    * TypeScript
    * Node.js / npm
* **Documentação:**
    * Springdoc OpenAPI (Swagger UI)

## 🚀 Como Executar o Projeto

### Pré-requisitos
Certifique-se de ter as seguintes ferramentas instaladas na sua máquina:
* JDK 17 ou superior
* Maven 3.8+
* Node.js 18+ (que inclui o npm)
* Angular CLI (instale com `npm install -g @angular/cli`)

### 1. Backend (Spring Boot)

1.  Abra o projeto em sua IDE (ex: IntelliJ IDEA, VS Code).
2.  Navegue até a pasta `backend-module`.
3.  A IDE deve reconhecer o `pom.xml` e baixar as dependências automaticamente. Se não, force a atualização do Maven.
4.  Execute a classe principal `BackendApplication.java`.
5.  A API estará rodando em `http://localhost:8080`.

### 2. Frontend (Angular)

1.  Abra um novo terminal.
2.  Navegue até a pasta `frontend`.
3.  Instale as dependências (apenas na primeira vez):
    ```bash
    npm install
    ```
4.  Instale o pacote de animações, se necessário:
    ```bash
    npm install @angular/animations --save
    ```
5.  Inicie o servidor de desenvolvimento:
    ```bash
    ng serve --open
    ```
6.  A aplicação será aberta automaticamente no seu navegador em `http://localhost:4200`.

## 🗂️ Banco de Dados (H2 Console)

A aplicação utiliza um banco de dados H2 em memória, que é criado e populado automaticamente na inicialização a partir dos scripts em `backend-module/src/main/resources/db/`.

* **URL de Acesso ao Console:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:desafiodb`
* **User Name:** `sa`
* **Password:** `password`

## 📖 Documentação da API (Swagger)

A API do backend é autodocumentada e interativa através do Swagger UI.

* **URL de Acesso ao Swagger:** `http://localhost:8080/swagger-ui.html`

Nesta página, você pode visualizar todos os endpoints disponíveis, seus parâmetros, e até mesmo testar as requisições diretamente do navegador.

### Endpoints Principais

* `GET /api/v1/beneficios`: Lista todos os benefícios.
* `GET /api/v1/beneficios/{id}`: Busca um benefício por ID.
* `POST /api/v1/beneficios`: Cria um novo benefício.
* `PUT /api/v1/beneficios/{id}`: Atualiza um benefício existente.
* `DELETE /api/v1/beneficios/{id}`: Remove um benefício.
* `POST /api/v1/beneficios/transferir`: Realiza a transferência de valor entre dois benefícios (lógica de negócio principal).