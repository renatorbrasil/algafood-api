Comandos Docker:

Para fazer o build do projeto:
- mvn clean package -Dmaven.test.skip
- mvn clean package -Pdocker -Dmaven.test.skip

Para construir a imagem:
- docker image build -t algafood-api .

Para criar a network:
- docker network create --driver bridge algafood-network

Para subir o mySQL:
- docker container run -d -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -e MYSQL_USER=admin -e MYSQL_PASSWORD=123 --name algafood-mysql mysql:8.0 --network algafood-network

Para subir o projeto:
- docker container run --rm -p 8080:8080 -e DB_HOST=localhost -e DB_USER=admin -e DB_PASSWORD=123 --network=host algafood-api
- docker container run --rm -p 8080:8080 -e DB_HOST=algafood-mysql --network=algafood-network algafood-api
