# Pedido API

### Requisitos
* Java 1.8+
* Maven 3.6.3+

### Executar
```
mvnw spring-boot:run
```

### Executar testes de integração
```
mvnw verify
```

### Script do banco de dados
Para o teste foi utilizado o bando de dados H2, ao executar é iniciado um banco de dados em memória e a migração do script inicial do banco.
Segue o link do script executado pelo flywaydb(responsável pela a evolução do esquema):
* [V0.0.1__base_inicial.sql](src/main/resources/db/migration/V0.0.1__base_inicial.sql)


### Modelo de chamada no Postman
Segue as colections utilizada para chamada dos endpoints através do Postman.
* [Pedido_JSON.postman_collection.json](postman/Pedido_JSON.postman_collection.json)
* [Pedido_XML.postman_collection.json](postman/Pedido_XML.postman_collection.json)
