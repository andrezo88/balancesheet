<h1 align="center">balanceSheet</h1>

## Descrição do Projeto

O balanceSheets é um projeto desenvolvido para gerenciar e manter o controle de balanços financeiros. Ele oferece funcionalidades para criar, visualizar, atualizar e excluir balanços, permitindo que os usuários organizem suas finanças de maneira eficaz.

## Classes

### 1. balanceModel

A classe `balanceModel` representa um balanço financeiro. Ela contém informações como data, descrição e valor do balanço.

### 2. User

A classe `User` representa um usuário do sistema. Ela inclui informações como nome de usuário, senha e permissões de acesso.

## Tecnologias Utilizadas

- **MongoDB**: Banco de dados NoSQL utilizado para armazenar os dados do aplicativo.
- **Spring Boot**: Framework utilizado para criar aplicativos Java baseados em Spring de forma rápida e fácil.
- **Spring Security**: Framework de segurança utilizado para autenticação e autorização de usuários.

## Instalação e Execução

1. Clone o repositório do projeto:

```bash
git clone https://github.com/seu-usuario/balanceSheets.git
```

Navegue até o diretório do projeto:

```bash
cd balanceSheets
```

Compile e execute o projeto usando Maven:

```bash
gradle build
```
O aplicativo estará disponível em http://localhost:8080.

## Configuração do Banco de Dados
Certifique-se de ter um servidor MongoDB em execução localmente ou configure as propriedades do banco de dados no arquivo application.properties conforme necessário.

## Contribuindo
Contribuições são bem-vindas! Sinta-se à vontade para abrir problemas ou enviar solicitações de pull.
