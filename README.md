# API Clube Campo
`API REST para o gerenciamento de um clube de campo com Associados, Áreas, Reservas, Pagamento e mais  
Feito com Java, Spring e MySQL, para a disciplina de Laboratório de Engenharia da FATEC ZL`

---

# Sumário

- [API Clube Campo](#api-clube-campo)
- [Enunciado](#enunciado)
- [Integrantes do grupo](#integrantes-do-grupo)
- [Tecnologias](#tecnologias)
- [Endpoints](#endpoints)
  - [TipoAssociado](#tipoassociado)
  - [Associado](#associado)
  - [Dependente](#dependente)
  - [Area](#area)
  - [Reserva](#reserva)
  - [Cobranca Mensal](#cobranca-mensal)
  - [PagamentoRealizado](#pagamentorealizado)
  - [Turma](#turma)

---

## Enunciado

Miguel é gerente de um clube de campo, e para que fosse mais fácil gerenciar as atividades relacionadas aos associados, te contratou para desenvolver um software para melhorar as suas atividades.  
O clube possui três quadras poliesportivas, duas quadras de futebol de salão, uma sala de jogos, dois salões de festas, três piscinas infantil e adulto com tobogã, parque infantil, sala de recreação com monitor (para crianças entre 2 e 7 anos), além de haras, campo de golfe, área para realização de trilhas e quinze churrasqueiras.

Há três tipos de associados: **Standard, Gold e Black**. Para realizar o cadastro dos associados, é necessário: Nome, RG, CPF, endereço, CEP, bairro, cidade, estado, telefone residencial, telefone comercial, celular e data de cadastro.

O associado pode cadastrar dependentes, onde para tais, é exigido na hora do cadastro, nome completo e RG. Para a utilização do clube, os associados devem realizar reservas das dependências.

Os pagamentos são realizados todos os meses e as cobranças devem ser geradas e administradas pelo sistema. Em caso de atraso nos pagamentos, o valor da mensalidade será acrescido de 5%.  
- Caso a inadimplência se acumule por dois meses, o associado perde o direito de realizar reservas para o haras, campo de golfe e piscina.  
- Quando a inadimplência ultrapassa os três meses, o associado terá acesso apenas às quadras.  
- Ultrapassados quatro meses, a carteirinha é bloqueada.  

Quando pagamentos são realizados, são registrados o valor, a forma de pagamento e a data. Miguel necessita também ter controle sobre as reservas das áreas do clube, sendo necessário cadastrar no sistema cada área e a quantidade de itens da mesma disponíveis.

Áreas que são reserváveis: **churrasqueira e salão de festas.**  
Os passeios no haras são agendados em 10 turmas, com no máximo 20 pessoas.  
As trilhas duram 1 hora e meia cada, com turmas de no máximo 30 pessoas, agendadas em 7 turmas por dia.

---

## Integrantes do grupo

- Sabrina Carvalho Da Silva
- Sara Félix Souza
- Thiago Silva Antenor
- Vitoria Rodrigues Borges

---

## Tecnologias

- Java
- Maven
- Spring
- MySQL
- Lombok

---

## Endpoints

### TipoAssociado

- **GET**
  - `/tipoAssociado` → retorna todos os tipos de associado
  - `/tipoAssociado/{id}` → retorna o tipo de associado do ID informado
- **POST**
  - `/tipoAssociado` → `{ string nome, number valor }`
- **PUT**
  - `/tipoAssociado/{id}` → `{ string nome, number valor }`
- **DELETE**
  - `/tipoAssociado/{id}` → exclui o tipo de associado do ID informado

---

### Associado

- **GET**
  - `/associado` → retorna todos os associados
  - `/associado/{id}` → retorna o associado do ID informado
- **POST**
  - `/associado` → `{ string nomeCompleto, string rg, string cpf, number tipoId,
    string telefoneResidencial, string telefoneComercial, string telefoneCelular,
    string cep, string logradouro, string bairro, string cidade, string estado }`
- **PUT**
  - `/associado/{id}` → `{ string nomeCompleto, string rg, string cpf, number tipoId, boolean carteirinhaBloqueada,
    string telefoneResidencial, string telefoneComercial, string telefoneCelular,
    string cep, string logradouro, string bairro, string cidade, string estado }`
- **DELETE**
  - `/associado/{id}` → exclui o associado do ID informado

---

### Dependente

- **GET**
  - `/dependente` → retorna todos os dependentes cadastrados
  - `/dependente/associado/{id}` → retorna todos os dependentes do associado do ID informado
  - `/dependente/{id}` → retorna o dependente do ID informado
- **POST**
  - `/dependente/` → `{ number associadoId, string nome, string rg }`
- **PUT**
  - `/dependente/` → `{ number associadoId, string nome, string rg }`
- **DELETE**
  - `/dependente/{id}` → exclui o dependente do ID informado

---

### Area

- **GET**
  - `/area` → retorna todas as áreas
  - `/area/{id}` → retorna a área do ID informado
- **POST**
  - `/area` → `{ string nomeArea, boolean reservavel, number quantidade }`
- **PUT**
  - `/area/{id}` → `{ string nomeArea, boolean reservavel, number quantidade }`
- **DELETE**
  - `/area/{id}` → exclui a área do ID informado

---

### Reserva

- **GET**
  - `/reserva` → retorna todas as reservas
  - `/reserva/{id}` → retorna a reserva do ID informado
- **POST**
  - `/reserva` → `{ Long areaId, long associadoId, string("dd-MM-yy HH:mm") dtReservaInicio,
    string("dd-MM-yy HH:mm") dtReservaFim, string statusReserva }`
- **PUT**
  - `/reserva/{id}` → `{ Long areaId, long associadoId, string("dd-MM-yy HH:mm") dtReservaInicio,
    string("dd-MM-yy HH:mm") dtReservaFim, string statusReserva }`
- **DELETE**
  - `/reserva/{id}` → exclui a reserva do ID informado

---

### Cobranca Mensal

- **GET**
  - `/cobranca` → retorna todas as cobranças
  - `/cobranca/{id}` → retorna a cobrança do ID informado
  - `/cobranca/associado/{id}` → retorna as cobranças do associado que tenha o ID informado
- **POST**
  - `/cobranca` → `{ long associadoId, string("dd-MM-yyyy") dtVencimento, string("MM-yyyy") mesAno }`
- **PUT**
  - `/cobranca/{id}` → `{ long associadoId, string("dd-MM-yyyy") dtVencimento, number valorPadrao,
    string valorFinal, string(pattern="MM-yyyy") mesAno, boolean pago }`
- **DELETE**
  - `/cobranca/{id}` → exclui a cobrança do ID informado

---

### PagamentoRealizado

- **GET**
  - `/pagamento` → retorna todos os pagamentos
  - `/pagamento/{id}` → retorna o pagamento do ID informado
  - `/pagamento/associado/{id}` → retorna os pagamentos do associado que tenha o ID informado
- **POST**
  - `/pagamento` → `{ string(pattern = "dd-MM-yyyy") dtPagamento, string formaPagamento, long cobrancaId }`
- **PUT**
  - `/pagamento/{id}` → `{ string(pattern = "dd-MM-yyyy") dtPagamento, string formaPagamento, long cobrancaId }`
- **DELETE**
  - `/pagamento/{id}` → exclui o pagamento do ID informado

---

### Turma

- **GET**
  - `/turma` → retorna todas as turmas e seus participantes
  - `/turma/{id}` → retorna a turma do ID informado
- **POST**
  - `/turma` → `{ string nomeTurma, string(pattern="dd-MM-yyyy HH:mm") dtHorario, number vagasDisponiveis }`
  - `/turma/associado` → Cadastra um associado como participante de uma turma `{ long turmaId, long associadoId }`
  - `/turma/dependente` → Cadastra um associado como participante de uma turma `{ long turmaId, long dependenteId }`
- **PUT**
  - `/turma/{id}` → `{ string nomeTurma, string(pattern="dd-MM-yyyy HH:mm") dtHorario }`
- **DELETE**
  - `/turma/{id}` → exclui a turma do ID informado
  - `/turma/associado/{idParticipanteAssociado}` → retira o participante associado da turma
  - `/turma/dependente/{idParticipanteDependente}` → retira o participante dependente da turma
