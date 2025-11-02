
# Projeto Calendário de Feriados

Esta é uma aplicação desktop JavaFX desenvolvida para calcular dias úteis entre duas datas, levando em consideração fins de semana e feriados cadastrados.

O projeto também inclui um sistema completo de CRUD (Create, Read, Update, Delete) para gerenciar a lista de feriados, que são armazenados em um banco de dados MySQL.

## Funcionalidades

  * **Calculadora de Dias Úteis**: Permite ao usuário selecionar uma data inicial e uma data final. O sistema calcula e exibe o número total de dias úteis nesse intervalo, excluindo sábados, domingos e os feriados cadastrados no banco de dados.
  * **Gerenciamento de Feriados (CRUD)**:
      * **Listar**: Exibe todos os feriados cadastrados em uma tabela.
      * **Adicionar**: Permite adicionar um novo feriado (data, descrição, tipo) ao banco de dados.
      * **Editar**: Permite selecionar um feriado na tabela, alterar seus dados e salvar as modificações.
      * **Excluir**: Permite selecionar um feriado na tabela e removê-lo do banco de dados, com uma caixa de diálogo de confirmação.

## Tecnologias Utilizadas

  * **Java 17**
  * **JavaFX 17.0.10** 
  * **Maven** 
  * **MySQL Connector/J 8.4.0** 

## Pré-requisitos

Para executar o projeto, você vai precisar ter:

1.  JDK 17 ou superior.
2.  Apache Maven.
3.  Um servidor de banco de dados MySQL local (rodando no `localhost:3306`).

## Configuração do Banco de Dados

Antes de executar a aplicação, é necessário configurar o banco de dados.

1.  **Criação do Banco:**
    O projeto está configurado para se conectar a um banco de dados chamado `calendario`, com o usuário `root` e senha vazia (`""`), conforme definido em `src/main/java/model/db/DB.java`.

    ```sql
    CREATE DATABASE calendario;
    ```

2.  **Criação da Tabela:**
    A aplicação utiliza uma tabela chamada `feriados`. Você pode criá-la com o seguinte comando SQL, baseado na classe `Feriado.java` e `FeriadosDao.java`:

    ```sql
    USE calendario;

    CREATE TABLE feriados (
        id INT AUTO_INCREMENT PRIMARY KEY,
        nome VARCHAR(255) NOT NULL,
        dataFeriado DATE NOT NULL,
        tipo VARCHAR(100)
    );
    ```

## Como Executar

1.  Clone o repositório.
2.  Certifique-se de que seu servidor MySQL está rodando e que o banco `calendario` e a tabela `feriados` foram criados.
3.  Abra um terminal na raiz do projeto.
4.  Execute o projeto usando o plugin do Maven para JavaFX:
    ```sh
    mvn clean javafx:run
    ```
    (Esta ação está configurada no `nbactions.xml`).
