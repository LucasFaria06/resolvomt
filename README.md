# 🛠️ ResolvoMT

> **Marketplace de Serviços** - Conectando clientes e prestadores de serviços em Mato Grosso

## 🛠️ Tecnologias & Ferramentas

☕ **Java 17** | 🍃 **Spring Boot 3** | 🔒 **Spring Security** | 🐘 **PostgreSQL**  
🐳 **Docker** | ⚛️ **React** | 📘 **TypeScript** | 🔧 **Git**
---

## 📋 Sobre o Projeto

**ResolvoMT** é uma plataforma que conecta clientes a prestadores de serviços qualificados. Inspirado no modelo GetNinjas, permite que usuários encontrem profissionais para diversas áreas como **eletricista, encanador, diarista, professor particular** e muito mais.

### 🎯 Objetivo
Facilitar a contratação de serviços profissionais em Mato Grosso, criando uma ponte confiável entre clientes e prestadores autônomos.

---

## ✨ Funcionalidades

### ✅ Implementadas
- 🔐 **Autenticação JWT** - Sistema seguro de login com tokens
- 👥 **Três tipos de usuário** - Cliente, Prestador e Admin
- 🔒 **Spring Security** - Controle de acesso granular por roles
- 📊 **DTOs** - Transferência segura de dados
- 🔑 **Criptografia BCrypt** - Senhas protegidas
- 🐳 **Docker Compose** - Ambiente containerizado

### 🚧 Em Desenvolvimento
- 📅 **Sistema de Agendamentos** - Marcação de serviços
- ⭐ **Avaliações** - Clientes avaliam prestadores
- 🔍 **Busca Avançada** - Filtros por categoria, localização e preço
- 🎨 **Frontend React** - Interface moderna e responsiva

---

## 🛠️ Tecnologias

### Backend
```
Java 17
Spring Boot 3.5.8
Spring Security 6
Spring Data JPA
JWT (JSON Web Tokens)
Hibernate
Maven
Swagger/OpenAPI
```

### Banco de Dados
```
PostgreSQL 15
Flyway (Migrations)
```

### DevOps
```
Docker
Docker Compose
```

### Frontend (Futuro)
```
React.js
TypeScript
TailwindCSS
Axios
```

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Docker e Docker Compose
- Maven (ou use o Maven Wrapper incluído)

### Passo a Passo

**1. Clone o repositório**
```bash
git clone https://github.com/LucasFaria06/resolvomt.git
cd resolvomt
```

**2. Configure as variáveis de ambiente**

Crie um arquivo `.env` na raiz com:
```env
POSTGRES_DB=resolvomt
POSTGRES_USER=seu_usuario
POSTGRES_PASSWORD=sua_senha
JWT_SECRET=seu_secret_super_seguro_aqui
```

**3. Suba o banco de dados**
```bash
docker compose up -d
```

**4. Configure o application.properties**

Em `api/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/resolvomt
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

app.jwt.secret=seu_secret_super_seguro_aqui
app.jwt.expiration-ms=86400000

server.port=8081
```

**5. Execute a aplicação**
```bash
cd api
./mvnw spring-boot:run
```

**6. Acesse a aplicação**
- API: `http://localhost:8081`
- Documentação Swagger: `http://localhost:8081/swagger-ui.html`

---

## 📡 Endpoints da API

### 🔐 Autenticação
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/auth/register/client` | Cadastrar cliente | ❌ |
| POST | `/api/auth/login` | Fazer login | ❌ |

### 👤 Cliente
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/api/cliente/me` | Dados do cliente logado | ✅ JWT |

### 🔧 Prestador (em desenvolvimento)
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/prestador/register` | Cadastrar prestador | ❌ |
| GET | `/api/prestador/servicos` | Listar serviços | ✅ JWT |

### 👑 Admin
| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/prestadores` | Listar prestadores | ✅ ADMIN |
| PATCH | `/prestadores/{id}/verificacao` | Verificar prestador | ✅ ADMIN |

---

## 🏗️ Arquitetura do Projeto

```
api/
├── src/main/java/com/resolvomt/api/
│   ├── config/              # Configurações (Security, CORS, etc)
│   ├── controller/          # Endpoints REST
│   ├── dto/                 # Data Transfer Objects
│   │   ├── cliente/
│   │   ├── jwt/
│   │   └── usuario/
│   ├── enums/              # Enumerações (TipoUsuario, Status)
│   ├── model/              # Entidades JPA
│   ├── repository/         # Repositórios Spring Data
│   ├── security/           # JWT, Filtros, UserDetailsService
│   └── service/            # Regras de negócio
└── src/main/resources/
    ├── application.properties
    └── db/migration/       # Scripts Flyway
```

### 🎨 Padrões Aplicados
- **MVC** (Model-View-Controller)
- **Repository Pattern**
- **DTO Pattern**
- **Service Layer**
- **Dependency Injection**

---

## 🔐 Segurança

- ✅ Senhas criptografadas com **BCrypt**
- ✅ Tokens JWT com expiração de 24h
- ✅ Endpoints protegidos por roles
- ✅ Validações de entrada com **Bean Validation**
- ✅ Tratamento global de exceções
- ✅ CORS configurado
- ✅ DTOs evitam exposição de dados sensíveis

---

## 📈 Roadmap

### Curto Prazo (1-2 meses)
- [ ] Sistema completo de agendamentos
- [ ] Upload de fotos de perfil
- [ ] Busca de prestadores com filtros
- [ ] Frontend React (páginas principais)

### Médio Prazo (3-6 meses)
- [ ] Sistema de notificações (email/push)
- [ ] Chat em tempo real
- [ ] Avaliações e comentários
- [ ] Painel administrativo completo

### Longo Prazo (6+ meses)
- [ ] Integração de pagamento
- [ ] App mobile (React Native)
- [ ] Sistema de assinatura para prestadores
- [ ] Deploy em produção (AWS/Heroku)

---

## 🤝 Como Contribuir

Contribuições são bem-vindas! Para contribuir:

1. Faça um **Fork** do projeto
2. Crie uma **branch** para sua feature (`git checkout -b feature/MinhaFeature`)
3. **Commit** suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. **Push** para a branch (`git push origin feature/MinhaFeature`)
5. Abra um **Pull Request**

---

## 📝 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👤 Autor

**Lucas Augusto**

- GitHub: [@LucasFaria06](https://github.com/LucasFaria06)
- LinkedIn: [lucas-augusto-dev27](https://linkedin.com/in/lucas-augusto-dev27)
- Email: lucas.a.coder@email.com

---

## 🙏 Agradecimentos

- Spring Boot Documentation
- Comunidade Stack Overflow
- Desenvolvedores que contribuem com projetos open source

---

<div align="center">

### ⭐ Se este projeto te ajudou, deixe uma estrela!

**Feito com ☕ e Java por Lucas Augusto**

</div>
