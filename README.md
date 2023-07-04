# ecommerce

El proyecto se ejecuta en una terminal a través del comando:<br>
`mvnw` para el caso de windows.<br>
`./mvnw` para el caso de linux.

En el path `src/test/resources/postman/` se encuentra tanto la collection como el environment para ser importado en el Postman, para poder realizar las pruebas del servicio solicitado. Se debe ejecutar previamente el request llamado Login para obtener el token, y luego se pueden ejecutar las pruebas.

En la carpeta `src/main/resources/config/liquibase/fake-data` se encuentran los .csv de las tres entidades, las cuales se cargan al iniciar el proyecto con los datos contenidos en sus respectivos archivos. Se puede modificar los mismos para cargar los datos de prueba que considere.
