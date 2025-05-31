# Проект Telegram Bot на Spring Boot
(@narrniar_task_bot - бот который используется для получения сообщений от пользователей и отправки их в Telegram)


## Стек
- **Spring Boot**: Фреймворк приложения
- **Spring Security**: Аутентификация и авторизация
- **PostgreSQL**: Система управления базой данных
- **Maven**: Управление зависимостями и сборка
- **JWT**: Аутентификация на основе токенов
- **Hibernate**: ORM для работы с базой данных
- **Lombok**: Уменьшение шаблонного кода

## Структура проекта

### Основные модели
#### User (Пользователь)
#### TelegramToken (Токен Telegram)
#### Message (Сообщение)

## Установка и настройка

### Требования
- JDK 17+
- Maven
- PostgreSQL
- Токен Telegram бота

## 2 конфигурации: Dev и Prod
### Dev:
- H2 база данных
### Prod:
- PostgreSQL база данных

## Как использовать:
- Запустить приложение с помощью команды `mvn spring-boot:run`
- Настроить токен и telegram username Telegram в `application.properties`
- Настроить базу данных в `application.properties` для прод если хочешь
- Использовать Swagger
- Настроить JWT secret key

## Sequence Diagram:
- register -> login -> telegram token generate -> connect bot to api -> send messages -> see all messages