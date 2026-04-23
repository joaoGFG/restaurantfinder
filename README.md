# Restaurant Finder 🍔🔍

Um sistema baseado em arquitetura de microsserviços (Spring Boot & Spring Cloud) focado em encontrar restaurantes e gerar recomendações inteligentes alimentadas por Inteligência Artificial (Google Gemini).

## 🏗️ Arquitetura

O projeto é composto por 4 microsserviços principais e uma infraestrutura de mensageria:

- **Eureka Server (`8761`)**: Service Discovery, responsável por registrar e localizar todos os microsserviços.
- **Restaurant Service (`8081`)**: API para gerenciamento do catálogo de restaurantes.
- **User Service (`8082`)**: API para gerenciamento de usuários e suas preferências.
- **Recommendation Service (`8083`)**: API que consome as preferências do usuário, os restaurantes disponíveis e utiliza o **Spring AI + Google Gemini** para retornar as melhores opções de restaurantes com explicações geradas via inteligência artificial.
- **RabbitMQ (`5672` / `15672`)**: Message Broker para comunicação assíncrona.

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3**
- **Spring Cloud** (Netflix Eureka)
- **Spring AI** (Google Vertex AI / Gemini)
- **RabbitMQ**
- **Docker** & **Docker Compose**
- **Gradle**

## ⚙️ Como executar

### Pré-requisitos
- [Docker](https://www.docker.com/) instalado.
- [Java 17+](https://adoptium.net/) instalado.
- Token da API do Google Gemini (`GEMINI_API_KEY`).

### Rodando com Docker Compose (Serviço de Mensageria)

O projeto foi configurado para subir o RabbitMQ isolado via Docker, garantindo menor consumo de recursos locais e contornando possíveis bloqueios de download do Docker Hub em redes restritas.
Para subir o RabbitMQ e deixar a infraestrutura base pronta:

```cmd
docker-compose up -d
```

### Rodando as aplicações (Local via Gradle)

Será necessário abrir **4 terminais separados** na raiz do projeto e executar os serviços na seguinte ordem:

1. **Eureka Server**:
   ```cmd
   .\gradlew.bat :eureka-server:bootRun
   ```
2. **Restaurant Service**:
   ```cmd
   .\gradlew.bat :restaurant-service:bootRun
   ```
3. **User Service**:
   ```cmd
   .\gradlew.bat :user-service:bootRun
   ```
4. **Recommendation Service**:
   Antes de rodar, é fundamental exportar a sua chave do Gemini.
   ```cmd
   set GEMINI_API_KEY=sua_chave_aqui
   .\gradlew.bat :recommendation-service:bootRun
   ```

*(Certifique-se de aguardar o Eureka Server iniciar antes de subir os demais).*

### Verificando a Infraestrutura
- **Eureka Dashboard:** Acesse [http://localhost:8761](http://localhost:8761)
- **RabbitMQ Management:** Acesse [http://localhost:15672](http://localhost:15672) (user: `guest`, pass: `guest`)

## 🧪 Testando o Fluxo da Aplicação

O projeto possui um script em PowerShell pronto para testar as rotas ponta a ponta (`test-api.ps1`).

```powershell
powershell -ExecutionPolicy Bypass -File .\test-api.ps1
```

O script fará:
1. A criação de um restaurante exemplo.
2. A criação de um usuário com preferências declaradas.
3. Chamará o `Recommendation Service` para solicitar ao Gemini que combine os dados e responda com o racional por trás da escolha dos restaurantes!
