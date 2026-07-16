# Library API — CRUD REST API (Layered Architecture)

Sadə kitabxana idarəetmə sistemi üçün REST API. Java, Spring Boot, Spring Data JPA və PostgreSQL istifadə edilib, qatlı arxitektura (Controller → Service → Repository) prinsipi ilə qurulub.

## Texnologiyalar

- Java 17
- Spring Boot 4.1.0
- Spring Data JPA / Hibernate
- PostgreSQL
- Lombok
- Maven
- JUnit 5 + Mockito (testlər üçün)

## Quraşdırma addımları

### 1. Repository-ni klonla
```bash
git clone https://github.com/LamanZaman/library-api.git
cd library-api
```

### 2. PostgreSQL-də database yarat
pgAdmin (və ya psql) ilə boş bir database yarat:
```sql
CREATE DATABASE library_db;
```

### 3. `application.properties` faylını konfiqurasiya et

`src/main/resources/application.properties` faylında öz PostgreSQL məlumatlarını yaz:

  properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=postgres
spring.datasource.password=SIZIN_PAROLUNUZ

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


### 4. Layihəni işə sal

IntelliJ IDEA-da `LibraryApiApplication.java` faylını run et, və ya terminalda:
```bash
./mvnw spring-boot:run
```

Tətbiq `http://localhost:8080` ünvanında işə düşəcək.

## Endpoint-lər

| Metod | URL | Təsvir |
|-------|-----|--------|
| POST | /api/authors | Yeni müəllif yarat |
| GET | /api/authors | Bütün müəllifləri gətir (pagination/sorting dəstəklənir) |
| GET | /api/authors/{id} | Bir müəllifi gətir |
| PUT | /api/authors/{id} | Müəllifi yenilə |
| DELETE | /api/authors/{id} | Müəllifi sil |

Eyni struktur `/api/books` və `/api/members` üçün də mövcuddur.

### Pagination/Sorting nümunəsi 

GET /api/authors?page=0&size=10&sortBy=fullName


## API Sənədləşdirməsi

Swagger/OpenAPI Spring Boot 4.1.0 versiyası ilə uyğunsuzluq səbəbindən istifadə olunmadı. Bunun əvəzinə **Postman collection** hazırlanıb: [`postman_collection.json`](./postman_collection.json)

Collection-ı Postman-a import edib bütün endpoint-ləri hazır nümunələrlə sınaya bilərsiniz.

## Testlər

`AuthorService` üçün Mockito ilə unit testlər yazılıb (`src/test/java/.../service/AuthorServiceTest.java`). Testləri işə salmaq üçün:
  bash
./mvnw test
```