<h1> Gerenciador de Estacionamento para Carros :oncoming_automobile:</h1>

<h3>:scroll: Projeto </h3>
<p>O projeto tem como objetivo desenvolver um sistema de gerenciamento de estacionamento para carros. O projeto faz com que cada cliente efetue um cadastro no sistema, fazendo assim o processo de estacionamento do seu veículo. O sistema geração de recibos diante as operações de Check-In e Check-Out, e o cliente poderá ter descontos com base no tempo estacionado.</p>

<h3>:wrench: Ferramentas Usadas </h3>

- Intellij
- MySQL
- Postman
- JasperReports
- Swagger

<h3>⚙️ Configuração do Projeto </h3>

- Spring Initializr
   - Project Maven
   - Language Java
   - Springboot 3.2.1
   - Packaging Jar
   - Java 17

 - Dependências
   - Spring Web
   - Spring Data JPA
   - Spring Boot Starter WebFlux
   - H2 DataBase
   - Spring Boot Test
   - Spring Boot DevTools
   - Validation
   - Spring Security
   - MySQL Driver
   - Springdoc OpenAPI Starter WebMVC UI (Swagger)
   - Lombok
   - ModelMapper
   - Jwt
   - JasperReports

A API está configurada com o Timezone, Locale do país e usando um sistema de auditoria. Registra a data de criação e última modificação dos registros. Registra o usuário que criou e o último que modificou um registro
<h3>:books: Entidades </h3>

- Cliente
  - ID
  - Nome
  - CPF
  - Usuario
  - Data de Criação
  - Data de Modificação
  - Criado Por
  - Modificado Por
    
Todas as ações exigem login para segurança. No cadastro de Cliente, é necessário fornecer nome completo e um CPF único, vinculado ao usuário. Apenas clientes podem se cadastrar, usando nome e CPF. Clientes são identificados por um número único gerado no cadastro. Administradores têm acesso exclusivo para listar todos os clientes. Clientes podem recuperar seus dados usando um token.
- ClienteVaga
  - ID
  - Recibo
  - Placa
  - Marca
  - Modelo
  - Cor
  - Data De Entrada
  - Data De Saida
  - Valor
  - Desconto
  - Cliente
  - Vaga
  - Data de Criação
  - Data de Modificação
  - Criado Por
  - Modificado Por
    
Todas ações requerem autenticação e são restritas ao administrador. Cada vaga deverá conter um código único que não deve ser o id. Cada vaga deverá conter um status de livre ou ocupada. Uma vaga poderá ser localizada pelo código.
 
- Usuário
  - ID
  - Username
  - Password
  - Role
  - Data de Criação
  - Data de Modificação
  - Criado Por
  - Modificado Por

Os usuários são identificados pelo e-mail único no sistema, as senhas devem ter no mínimo 6 caracteres. Cada usuário tem um perfil: administrador ou cliente, administradores podem buscar usuários usando o número de identificação, clientes só podem recuperar seus próprios dados. Alteração de senha é permitida apenas para usuários autenticados. Administradores podem ver todos os usuários do sistema.

- Vaga
  - ID
  - Código
  - Status
  - Data de Criacão
  - Data de Modificacão
  - Criado Por
  - Modificado Por

Todas ações requerem autenticação e são restritas ao administrador. Cada vaga deverá conter um código único que não deve ser o id. Cada vaga deverá conter um status de livre ou ocupada. Uma vaga poderá ser localizada pelo código.

<h3>:car: Estacionamento tem as seguinste regras</h3>

- Para Clientes Cadastrados
  - Apenas clientes registrados podem estacionar, usando o CPF cadastrado.
  - Ao entrar, o cliente fornece informações do veículo e recebe um número de recibo.
  - Ao sair, são registradas informações como a data de saída e o valor a pagar.

- Requisitos Adicionais
  - Uma vaga de estacionamento pode ser usada por vários veículos, mas não ao mesmo tempo.
  - Desconto de 30% é concedido após 10 estacionamentos do cliente.
  - O custo do estacionamento varia com o tempo de permanência, detalhado em uma tabela de cálculos.

- Operações de Administração
  - Check-in e check-out são realizados apenas por administradores.
  - Administradores e clientes podem buscar informações usando o número do recibo.
  - Administradores podem listar estacionamentos por CPF do cliente.
  - Clientes podem listar apenas seus próprios estacionamentos.

- Custo do Estacionamento
  - Primeiros 15 minutos: R$ 5.00
  - Primeiros 60 minutos: R$ 9.25
  - Após os primeiros 60 minutos, é adicionada uma taxa de R$ 1.75 para cada faixa de 15 minutos adicionais aos primeiros 60 minutos.

<h3>:bookmark_tabs: Relatório em PDF</h3>
O cliente poderá gerar um relatório em PDF com sua lista de estacionamentos. Exemplo abaixo
<div align="center">
  <img src="https://github.com/piedrohammer/demo-park-api/assets/89158456/87c71058-cc46-4f5b-b799-3f06c6d012fb" width="500px"/>
</div>





 

