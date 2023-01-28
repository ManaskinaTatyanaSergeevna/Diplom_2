# Diplom_2 

Учебный проект по автотестированию API для приложения по заказу бургеров Stellar Burgers.

## Описание

Версия Java 11
Проект использует следующие библиотеки:
- JUnit 4
- RestAssured
- Allure

## Документация

[Ссылка](https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf) на документацию проекта.

### Запуск автотестов

Для запуска автотестов необходимо:

1. Скачать код

 ```sh
   git clone https://github.com/ManaskinaTatyanaSergeevna/Diplom_2.git
   ```
   
2. Запустить команду в проекте

```sh
mvn clean test
```

3. Для создания отчета в Allure ввести команду

```sh
mvn allure:report
```

### Структура проекта

```bash
.gitignore
pom.xml
README.md
src
   |-- main
   |   |-- java
   |   |   |-- api
   |   |   |   |-- model
   |   |   |   |   |-- Ingredient.java
   |   |   |   |   |-- Ingredients.java
   |   |   |   |   |-- Order.java
   |   |   |   |   |-- User.java
   |   |   |   |-- steps
   |   |   |   |   |-- OrderSteps.java
   |   |   |   |   |-- UserSteps.java
   |-- test
   |   |-- java
   |   |   |-- tests
   |   |   |   |-- order
   |   |   |   |   |-- CreateOrderTest.java
   |   |   |   |   |-- GetUserOrderTest.java
   |   |   |   |-- user
   |   |   |   |   |-- ChangeUserDataTest.java
   |   |   |   |   |-- CreateUserTest.java
   |   |   |   |   |-- LoginUserTest.java
   ```
