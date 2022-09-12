## Bookkeeping API

REST API для ведения бухгалтерии в бизнесе по покупке и продаже рекламы в Телеграме.
Создано специально для этой предметной области и позволяет учитывать продажи и покупки для отдельного канала, вести учёт
покупателей, настраивать форматы рекламы. Может быть полезно для администраторов Телеграм-каналов.

### Стек

- [Spring Boot](https://github.com/spring-projects/spring-boot)
- [Spring Security](https://github.com/spring-projects/spring-security)
- [Spring Data JPA](https://github.com/spring-projects/spring-data-jpa) (Hibernate)
- [JJWT](https://github.com/jwtk/jjwt)
- [springdoc-openapi](https://github.com/springdoc/springdoc-openapi)
- [PostgreSQL](https://github.com/postgres/postgres)

### Основные доменные объекты

- `Channel` - администрируемый Телеграм-канал, в котором продаётся и покупается реклама
- `Sale` и `Purchase` запись о продаже или покупке рекламы.
  Привязана к конкретному каналу. Содержит следующие данные:
    - Дату и время рекламного слота
    - `AdFormat` - формат размещения рекламы
    - `Customer` - покупатель или продавец рекламы
    - Стоимость
    - `PaymentStatus` - статус сделки. Например: "Ожидает оплаты" или "Оплачено".

### Особенности

Разделение логики приложения на слои: `Controller`-`Service`-`Repository`.  
Валидация присылаемых данных.  
Отдельные DTO для каждого класса, которые не содержат ничего лишнего и позволяют тонко настроить формат возвращаемых данных.   
Управление доступом к API посредством JWT.  
Реализован аудит данных: для каждой записи сохраняется время и источник создания и последнего обновления.  
Информативные сообщения об ошибках в теле HTTP ответа.  
Доступна документация Open API 3.0 + Swagger.

### Безопасность

Чтобы начать использовать API, пользователь должен зарегистрироваться, создав свой профиль через открытый
метод `auth/signup`.
Чтобы пройти аутентификацию, пользователю нужно отправить свой логин и пароль на `auth/signin`, после чего ему выдаётся
refresh и access JWT токены.
Для доступа к закрытым эндпоинтам, в заголовке HTTP-запроса нужно отправить валидный
токен: `-H 'Authorization: Bearer your_jwt_access_token'`.
Когда срок действия access-токена истечёт, пользователю нужно отправить на сервер refresh-токен в метод `auth/token` и
получить новый access-token.
Также пользователь может отправить авторизованный запрос на `auth/refresh` передав в теле свой валидный refresh-токен,
чтобы получить новую пару токенов.

### Как установить и запустить

На компьютере должна быть установлена Java 11 и PostgreSQL.

1. Скопировать репозиторий

```shell
git clone https://github.com/postfedor/BookkeepingAPI.git
```

2. Подготовить базу данных

- Создать новую базу данных. Например, в psql: `create database bd;`
- Ввести логин и пароль от БД в application.properties
- Выполнить скрипт `src/main/resources/sql/schema.sql`

3. В application.properties прописать два секретных ключа для access и refresh токенов. Ключи должны быть закодированы в
   Base64. Например:

```properties
jwt.secret.access=dGhpc2lzbXliZWF0aWZ1bHNlY3JldGZvcmVuY3J5cHRpbmdhY2Nlc3N0b2tlbgo=
jwt.secret.refresh=dGhpc2lzbXliZWF0aWZ1bHNlY3JldGZvcmVuY3J5cHRpbmdyZWZyZXNodG9rZW4K
```

4. Запустить: `mvn spring-boot:run`

