# Vehicle Management API

API REST para gerenciamento de veículos desenvolvida com Java 17 e Spring Boot.

## Tecnologias

- Java 17
- Spring Boot 3.5
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- Flyway
- Redis
- OpenFeign
- Swagger / OpenAPI
- Docker
- Docker Compose
- Gradle

---

# Funcionalidades

- Cadastro de veículos
- Listagem paginada
- Busca por ID
- Filtros utilizando Specification
- Atualização completa (PUT)
- Atualização parcial (PATCH)
- Soft Delete
- Relatório por marca
- Conversão automática de BRL para USD utilizando API externa
- Cache da cotação do dólar utilizando Redis
- Fallback entre APIs de cotação
- Autenticação JWT
- Controle de acesso por perfil (ADMIN / USER)

---

# Estrutura do Projeto

```
src
 ├── controller
 ├── controller.swagger
 ├── service
 ├── repository
 ├── entity
 ├── config
 ├── security
 ├── dto
 ├── mapper
 ├── specification
 ├── exception
 └── utils
```

---

# Pré-requisitos

É necessário possuir instalado:

- Docker
- Docker Compose

ou

- Java 17
- Gradle

---

# Executando com Docker

Na raiz do projeto execute:

```bash
docker compose up --build
```

A aplicação iniciará juntamente com:

- PostgreSQL
- Redis

A API ficará disponível em:

```
http://localhost:8080/api
```

---

# Executando Localmente

Suba apenas o banco e o Redis.

```bash
docker compose up db redis
```

Depois execute a aplicação.

Linux

```bash
./gradlew bootRun
```

Windows

```cmd
gradlew.bat bootRun
```

---

# Banco de Dados

O Flyway executará automaticamente todas as migrations.

Serão criadas automaticamente:

- tabelas
- índices
- roles
- usuários
- veículos de exemplo

Não é necessário executar scripts manualmente.

---

# Redis

O Redis é utilizado para armazenar em cache a cotação do dólar utilizada no cadastro e atualização de veículos.

Após a primeira consulta, a cotação permanece armazenada durante o tempo configurado.

---

# Swagger

Após iniciar a aplicação acesse:

```
http://localhost:8080/api/swagger-ui/index.html
```

---

# Usuários para Teste

Os usuários abaixo são criados automaticamente pelo Flyway.

## Administrador

Email

```
admin@vehiclemanagement.com
```

Senha

```
admin123
```

Permissões

- GET
- POST
- PUT
- PATCH
- DELETE

---

Email

```
manager@vehiclemanagement.com
```

Senha

```
manager123
```

Permissões

- GET
- POST
- PUT
- PATCH
- DELETE

---

## Usuário

Email

```
user@vehiclemanagement.com
```

Senha

```
user123
```

Permissões

- GET

---

Email

```
viewer@vehiclemanagement.com
```

Senha

```
viewer123
```

Permissões

- GET

---

Email

```
tester@vehiclemanagement.com
```

Senha

```
tester123
```

Permissões

- GET

---

Email

```
guest@vehiclemanagement.com
```

Senha

```
guest123
```

Permissões

- GET

---

# Como obter o JWT

Realize uma requisição:

```
POST /api/auth/login
```

Body

```json
{
  "email": "admin@vehiclemanagement.com",
  "password": "admin123"
}
```

Resposta

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

# Como autenticar no Swagger

Clique em:

```
Authorize
```

Informe:

```
Bearer <seu_token>
```

Exemplo

```
Bearer eyJhbGciOiJIUzI1NiJ9...
```

Clique em **Authorize**.

Todos os endpoints protegidos estarão disponíveis.

---

# Endpoints

## Autenticação

| Método | Endpoint |
|---------|----------|
| POST | /api/auth/login |

---

## Veículos

| Método | Perfil |
|---------|---------|
| GET /api/v1/vehicles | USER / ADMIN |
| GET /api/v1/vehicles/{id} | USER / ADMIN |
| GET /api/v1/vehicles/reports/by-brand | USER / ADMIN |
| POST /api/v1/vehicles | ADMIN |
| PUT /api/v1/vehicles/{id} | ADMIN |
| PATCH /api/v1/vehicles/{id} | ADMIN |
| DELETE /api/v1/vehicles/{id} | ADMIN |

---

# Conversão de Moeda

Ao cadastrar ou atualizar um veículo:

1. O preço é informado em BRL.
2. A aplicação consulta a AwesomeAPI.
3. Caso a AwesomeAPI esteja indisponível, utiliza automaticamente a Frankfurter API.
4. A cotação é armazenada em cache utilizando Redis.
5. O valor é persistido em USD.

---

# Cache

A cotação do dólar é armazenada no Redis.

Chave utilizada

```
usd-rate::current
```

---

# Testando o Cache

Após realizar um cadastro de veículo, execute:

```bash
docker exec -it vehicle-management-redis redis-cli
```

Depois:

```redis
KEYS *
```

Resultado esperado

```
usd-rate::current
```

Consultar valor

```redis
GET "usd-rate::current"
```

---

# Executando Testes

```bash
./gradlew test
```

---

# Observações

- O projeto utiliza Soft Delete para remoção de veículos.
- As migrações são executadas automaticamente pelo Flyway.
- A autenticação é realizada utilizando JWT.
- As senhas dos usuários são armazenadas utilizando BCrypt.
- O acesso aos endpoints é controlado por perfil (ADMIN e USER).