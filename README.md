# Library app

## API REST para gerenciamento de bibliotecas

## Como executar:

**1. Primeiro clone o repositório:**
```sh
  # Com HTTPS:
  git clone https://github.com/DiegoOno/library-app-back-end.git

  # Com SSH
  git clone git@github.com:DiegoOno/library-app-back-end.git
```

**2. Configure e execute o banco de dados PosgreSQL:**
Nessa etapa é possível configurar o banco na instalação local ou utilizar uma imagem do __`Docker`__;
```sh
  # Configuração local
  Postgres: Versão 16
  Nome do banco: library-app-db
  Dados do Usuário:
    user: user-library
    password: libraryapp123

  # Para executar o banco com docker, primeiro certifique que tenha o mesmo instalado, e com isso basta executar o seguinte comando:

  docker run -d --name library-app-db -p 5432:5432 \
    -e POSTGRES_DB=library-app-db \
    -e POSTGRES_USER=user-library \
    -e POSTGRES_PASSWORD=libraryapp123 \
    postgres:latest

```

**3. Com o banco configurado com os parâmetros acima, basta executar o projeto**
**Observação: O projeto está configurado para compilar com a versão Java 21, portanto, é necessário ter a JDK 21 instalada para executar o projeto corretamente.**

```sh
# Com a versão correta instalada, basta executar os seguintes comandos: 
mvn clean package

java -jar target/library-app-0.0.1-SNAPSHOT.jar

```
Após o sucesso da execução o projeto estará sendo executado no endereço: http://localhost:8080

**Também é possível executar o projeto com uma IDE, por exemplo, o IntelliJ**
