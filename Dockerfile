
FROM node:20-alpine AS angular

WORKDIR /app/front

COPY front/package.json front/package-lock.json ./
RUN npm ci

COPY front/ ./
RUN npm run build


FROM maven:3.9-eclipse-temurin-17 AS spring

WORKDIR /app/backend

COPY backend/pom.xml ./
RUN mvn dependency:go-offline -B

COPY backend/src ./src

COPY --from=angular /app/front/dist/front/browser ./src/main/resources/static

RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=spring /app/backend/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]