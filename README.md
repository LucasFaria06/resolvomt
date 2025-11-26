# 🛠️ ResolvoMT - Plataforma de Agendamento de Serviços

Bem-vindo ao repositório do **ResolvoMT**, uma API RESTful desenvolvida para conectar clientes e prestadores de serviços (estilo Uber/GetNinjas).

Este projeto é um MVP (Minimum Viable Product) focado em boas práticas de engenharia de software, arquitetura em camadas e segurança.

---

## 🚀 Tecnologias Utilizadas

* **Java 17** (LTS)
* **Spring Boot 3** (Framework Web)
* **PostgreSQL** (Banco de Dados)
* **Docker & Docker Compose** (Containerização)
* **Spring Security** (BCrypt para criptografia)
* **Lombok** (Produtividade)
* **Maven** (Gerenciador de Dependências)

---

## ⚙️ Funcionalidades (Endpoints)

### 👤 Usuários
- [x] **Cadastro de Usuários:** Com validação de e-mail único e campos obrigatórios.
- [x] **Segurança:** Senhas criptografadas automaticamente (Hash BCrypt).
- [ ] **Login:** Autenticação via Token JWT (Em breve).

### 📅 Agendamentos
- [x] **Solicitar Serviço:** Cliente escolhe prestador, data e valor.
- [x] **Listagem:** Histórico completo de serviços.
- [x] **Atualização de Status:** Fluxo de `PENDENTE` -> `CONFIRMADO` -> `CONCLUIDO` (Verbo PATCH).

---

## 🔧 Como Rodar o Projeto

### Pré-requisitos
* Docker e Docker Compose instalados.

### Passo a Passo

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SEU-USUARIO/resolvomt-freelance.git](https://github.com/SEU-USUARIO/resolvomt-freelance.git)
    cd resolvomt-freelance
    ```

2.  **Compile o Backend:**
    ```bash
    cd api
    ./mvnw clean package -DskipTests
    cd ..
    ```

3.  **Suba os Containers (App + Banco):**
    ```bash
    docker compose up --build
    ```

4.  **Acesse a API:**
    * O servidor rodará em: `http://localhost:8080`
    * Teste via Postman ou Insomnia.

---

## 📂 Arquitetura do Projeto

O sistema segue a arquitetura em camadas para facilitar manutenção e testes:

* **Controller:** Camada REST que recebe as requisições HTTP.
* **Service:** Camada de regras de negócio (validações, criptografia).
* **Repository:** Camada de acesso a dados (JPA/Hibernate).
* **Model/DTO:** Representação dos dados e objetos de transferência.

---

Made with ☕ and Java by [Lucas Faria](https://github.com/Lucasfaria2024)