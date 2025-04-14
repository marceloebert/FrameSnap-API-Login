# FrameSnap-API-Login

## Descrição do Projeto

FrameSnap-API-Login é um serviço de autenticação desenvolvido em Java Spring Boot que fornece funcionalidades de registro e login de usuários utilizando o Amazon Cognito como provedor de autenticação. Este serviço é parte do ecossistema FrameSnap, fornecendo autenticação segura para as aplicações do sistema.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal
- **Spring Boot 3.2.3**: Framework para desenvolvimento de aplicações Java
- **Amazon Cognito**: Serviço de gerenciamento de usuários e autenticação
- **AWS SDK v2**: Biblioteca para interação com serviços AWS
- **Gradle**: Ferramenta de automação de build
- **JUnit 5**: Framework para testes unitários
- **Mockito**: Framework para criação de mocks em testes
- **JaCoCo**: Ferramenta para cobertura de código
- **SonarQube**: Plataforma para análise de qualidade de código
- **Docker**: Containerização da aplicação
- **Kubernetes**: Orquestração de containers

## Arquitetura

O projeto segue os princípios da Clean Architecture, com as seguintes camadas:

- **Entities**: Contém as entidades de domínio (User)
- **Application**: Contém os casos de uso da aplicação (LoginUseCase, RegisterUserUseCase)
- **Infrastructure**: Implementações concretas dos gateways e configurações (CognitoUserGateway, TokenGatewayImpl)
- **Crosscutting**: Utilitários e componentes transversais

## Endpoints da API

### Registro de Usuário

```
POST /auth/register
```

**Descrição**: Registra um novo usuário no sistema.

**Corpo da Requisição**:
```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```

**Resposta de Sucesso (200 OK)**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "usuario@exemplo.com"
}
```

**Erros Possíveis**:
- **400 Bad Request**: Email ou senha inválidos
- **409 Conflict**: Usuário já existe

### Login

```
POST /auth/login
```

**Descrição**: Autentica um usuário e retorna um token JWT.

**Corpo da Requisição**:
```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```

**Resposta de Sucesso (200 OK)**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Erros Possíveis**:
- **400 Bad Request**: Email ou senha inválidos
- **401 Unauthorized**: Credenciais inválidas
- **404 Not Found**: Usuário não encontrado

## Configuração

### Variáveis de Ambiente

O projeto requer as seguintes variáveis de ambiente:

```
# AWS Cognito
aws.cognito.user-pool-id=seu-user-pool-id
aws.cognito.client-id=seu-client-id
aws.cognito.client-secret=seu-client-secret
aws.cognito.region=sua-regiao

# AWS Credentials
aws.credentials.access-key=sua-access-key
aws.credentials.secret-key=sua-secret-key
aws.credentials.session-token=seu-session-token
```

### Executando Localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/FrameSnap-API-Login.git
   cd FrameSnap-API-Login
   ```

2. Configure as variáveis de ambiente necessárias

3. Execute o projeto:
   ```bash
   ./gradlew bootRun
   ```

### Executando com Docker

1. Construa a imagem:
   ```bash
   docker build -t framesnap-api-login .
   ```

2. Execute o container:
   ```bash
   docker run -p 8080:8080 \
     -e aws.cognito.user-pool-id=seu-user-pool-id \
     -e aws.cognito.client-id=seu-client-id \
     -e aws.cognito.client-secret=seu-client-secret \
     -e aws.cognito.region=sua-regiao \
     -e aws.credentials.access-key=sua-access-key \
     -e aws.credentials.secret-key=sua-secret-key \
     -e aws.credentials.session-token=seu-session-token \
     framesnap-api-login
   ```

## Testes

### Executando Testes

```bash
./gradlew test
```

### Relatório de Cobertura

Após executar os testes, o relatório de cobertura estará disponível em:
```
build/reports/jacoco/test/html/index.html
```

## Qualidade de Código

O projeto utiliza SonarQube para análise de qualidade de código. Para executar a análise:

```bash
./gradlew sonarqube
```

## Segurança

- Todas as senhas são armazenadas de forma segura no Amazon Cognito
- A comunicação com o Cognito é feita via HTTPS
- Tokens JWT são utilizados para autenticação
- Validações são realizadas para evitar injeção de código e outros ataques comuns