
# Client API

Este é um projeto de API de gerenciamento de clientes desenvolvido como parte de um teste técnico. A API foi construída com **Java 8** e **Spring Boot 2.7.18**, utilizando **PostgreSQL** como banco de dados. O projeto foi dockerizado para simplificar a execução e a configuração do ambiente.

## Tecnologias Utilizadas

- **Java 8**
- **Spring Boot 2.7.18**
- **PostgreSQL**
- **Flyway** para migrações de banco de dados
- **Docker** e **Docker Compose**
- **Maven** para gerenciamento de dependências

## Estrutura do Projeto

O projeto segue uma estrutura de pacotes organizada da seguinte forma:

```
src/main/java
├── tecnologia/sea/clientapi
│   ├── config
│   ├── controllers
│   ├── domain
│   │   ├── client
│   │   └── user
│   ├── services
│   └── ClientApiApplication.java
```

### Descrição dos Pacotes

- **config**: Contém classes de configuração de segurança e integração com o banco de dados.
- **controllers**: Contém os controladores da API que gerenciam as rotas e os endpoints.
- **domain**: Contém as classes de domínio e DTOs para clientes e usuários.
- **services**: Contém as classes de serviço que implementam a lógica de negócios.

## Requisitos de Configuração

Certifique-se de ter as seguintes dependências instaladas:
- Docker e Docker Compose
- JDK 8
- Maven

## Como Executar o Projeto

### 1. Clonar o Repositório

```bash
git clone https://github.com/iLuiszin/client-api.git
cd client-api
```

### 2. Configuração do Banco de Dados

O projeto está configurado para utilizar um banco de dados PostgreSQL executado via Docker.

- O arquivo `docker-compose.yml` já está preparado para subir o banco de dados PostgreSQL.
- As migrações de banco de dados são gerenciadas pelo **Flyway**.

### 3. Construir e Executar o Projeto com Docker

Para construir e executar o projeto e o banco de dados:

```bash
docker-compose up --build
```

Este comando irá:
- Subir o banco de dados PostgreSQL.
- Construir a aplicação Java com **Maven**.
- Executar a aplicação.

## Endpoints da API

### Usuários

- **POST /users/create**: Cria um novo usuário.
  - **Permissão**: Aberto a todos.
  - **Request Body**:
    ```json
    {
      "username": "string",
      "password": "string",
      "role": "ROLE_ADMIN" | "ROLE_USER"
    }
    ```

### Clientes

- **POST /clients/create**: Cria um novo cliente.
  - **Permissão**: Apenas para usuários com o papel `ROLE_ADMIN`.
  - **Request Body**:
    ```json
    {
      "name": "string",
      "cpf": "string",
      "address": {
          "cep": "string"
      },
      "phones": [
          {
              "number": "string",
              "type": "string"
          }
      ],
      "emails": [
          {
            "emailAddress": "string"
          }
      ]
    }
    ```

- **GET /clients**: Retorna todos os clientes.
  - **Permissão**: Usuários com papéis `ROLE_ADMIN` ou `ROLE_USER`.

- **GET /clients/cpf/{cpf}**: Retorna um cliente pelo CPF.
  - **Permissão**: Usuários com papéis `ROLE_ADMIN` ou `ROLE_USER`.

- **GET /clients/{id}**: Retorna um cliente pelo ID.
  - **Permissão**: Usuários com papéis `ROLE_ADMIN` ou `ROLE_USER`.

## Exemplos de Requisições cURL

### Criar Usuário

`POST /users/create`

**Exemplo de comando cURL:**

```bash
curl -X POST http://localhost:8080/users/create \
-H "Content-Type: application/json" \
-d '{
  "username": "admin",
  "password": "123qwe!@#",
  "role": "ROLE_ADMIN"
}'
```

### Criar Cliente

`POST /clients/create`

**Exemplo de comando cURL:**

```bash
curl -X POST http://localhost:8080/clients/create \
-H "Content-Type: application/json" \
-u adminUser:adminPassword \
-d '{
  "name": "Cliente Exemplo",
  "cpf": "12345678901",
  "address": {
    "cep": "12345678"
  },
  "phones": [
    {
      "number": "11987654321",
      "type": "CELULAR"
    }
  ],
  "emails": [
    {
      "emailAddress": "cliente.exemplo@example.com"
    }
  ]
}'
```

**Nota:** O parâmetro `-u` em `cURL` é usado para autenticação básica, onde `adminUser` e `adminPassword` devem ser substituídos pelas credenciais de um usuário com a role `ROLE_ADMIN`.


## Segurança

A API utiliza autenticação Basic Auth e tem as seguintes permissões:
- **ROLE_ADMIN** pode criar e visualizar clientes.
- **ROLE_USER** pode apenas visualizar clientes.

## Personalizações

O projeto inclui:
- **CustomAccessDeniedHandler**: Para mensagens de erro personalizadas em casos de acesso negado (403).
- **CustomAuthenticationEntryPoint**: Para mensagens de erro detalhadas em casos de autenticação falha (401).

## Licença

Este projeto é apenas para fins de avaliação e teste técnico.
