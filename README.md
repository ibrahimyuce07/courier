
# Migros Courier Tracking



## API Usage

#### Update Courier Location

```http
  POST /courier/updateLocation
```

| Parameter | Type     | 
| :-------- | :------- | 
| `courierId` | `string` | 
| `lat` | `double` |  
| `lng` | `double` | 
| `time` | `LocalDateTime` |  

#### Get Total Travel Distance

```http
  GET /courier/totalTravelDistance/{courierId}
```

| Parameter | Type     | 
| :-------- | :------- | 
| `courierId`      | `string` | 





## Example Uses

```bash
curl -X POST 'localhost:8080/courier/updateLocation?courierId=1&lat=40.9923307&lng=29.1244229&time=2023-07-10T12:00:00'
```

  ```bash
curl -X GET 'http://localhost:8080/courier/totalTravelDistance/1'
```


## How to Run


Clone Project

```bash
  git clone https://github.com/ibrahimyuce07/courier
```

Go project directory

```bash
  cd courier
```

Run command

```bash
  mvn spring-boot:run
```



## Technology

Java 20  - Spring Boot 3.1.1 - Maven - Lombok - Slf4j


## Design Patterns

- Singleton Pattern
- Builder Pattern
- Observer Pattern
- Dependency Injection

  