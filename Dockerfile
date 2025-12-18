# --- Estágio 1: Build (Compilação) ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar o cache de dependências
COPY pom.xml .
# Baixa as dependências (isso acelera builds futuros se o pom não mudar)
RUN mvn dependency:go-offline

# Copia o código fonte do projeto
COPY src ./src

# Compila o projeto e gera o arquivo .jar (pula os testes para agilizar o deploy)
RUN mvn clean package -DskipTests

# --- Estágio 2: Run (Execução) ---
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 (padrão do Spring Boot)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]