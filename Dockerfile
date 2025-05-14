# Используем официальный образ OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный jar файл в контейнер
COPY target/User-management-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт, на котором работает приложение
EXPOSE 8080

# Команда запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]