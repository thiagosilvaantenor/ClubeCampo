# API Clube Campo

## Enunciado:

Miguel é gerente de um clube de campo, e para que fosse mais fácil gerenciar as atividades relacionadas aos associados, te contratou para desenvolver um software para melhorar as suas atividades. 
<br> O clube possui três quadras poliesportivas, duas quadras de futebol de salão, uma sala de jogos, dois salões de festas, 3 piscinas infantil e adulto com tobogã, parque infantil, sala de recreação com monitor (para crianças entre 2 e 7 anos), além de haras, 
campo de golfe, área para realização de trilhas e quinze churrasqueiras.<br>
Há três tipos de associados: Standard, Gold e Black. Para realizar o cadastro dos associados, é necessário: Nome, RG, CPF, endereço, CEP, bairro, cidade, estado, telefone residencial, telefone comercial, celular e data de cadastro.
<br>O associado pode cadastrar dependentes, onde para tais, é exigido na hora do cadastro, nome completo e RG. Para a utilização do clube, os associados devem realizar reservas das dependências. 
<br> Os pagamentos são realizados todos os meses e as cobranças devem ser geradas e administradas pelo sistema. Em caso de atraso nos pagamentos, o valor da mensalidade será acrescido de 5%. 
Caso a inadimplência se acumule por dois meses, o associado perde o direito de realizar reservas para o haras, campo de golfe e piscina. 
Quando a inadimplência ultrapassa os três meses, o associado terá acesso apenas as quadras, e ultrapassados quatro meses, a carteirinha é bloqueada. 
Quando pagamentos são realizados, são registrados o valor, a forma de pagamento, e a data. Miguel necessita também ter controle sobre as reservas das áreas do clube, sendo necessário cadastrar no sistema cada área e a quantidade de itens da mesma disponíveis.
<br> Áreas que são reserváveis: churrasqueira e salão de festas. Os passeios no haras, são agendados 10 turmas, com no máximo 20 pessoa. As trilhas duram 1 hora e meia cada, com turmas no máximo de 30 pessoas, agendadas 7 turmas por dia

## Integrantes do grupo:
* Sabrina Carvalho Da Silva
* Sara Félix Souza
* Thiago Silva Antenor
* Vitoria Rodrigues Borges

## Tecnologias
- Java
- Maven
- Spring
- MySQL
- Lombok

## Endpoints
* TipoAssociado: 
  * GET:
    * `/tipoAssociado`: {retorna todos os associados}
    * `/tipoAssociado/{id}`: {retorna o associado do id que é enviado na URL}
  * POST: Cria o tipoAssociado
    * `/tipoAssociado`: `{string nome, number valor}`
  * PUT: Atualiza os campos informados no body da requisição
   * `/tipoAssociado/{id}`: `{string nome, number valor}`
  * DELETE: 
    * `/tipoAssociado/{id}`: {Exclui o tipoAssociado do id informado na URL}
* Associado: 
  * GET:
    * `/associado`: {retorna todos os associados}
    * `/associado/{id}`: {retorna o associado do id que é enviado na URL}
  * POST: Cria o Associado
    * `/associado`: `{string nomeCompleto, string rg, string cpf, number tipoId,
		string telefoneResidencial,string telefoneComercial, string telefoneCelular,
		string cep, string logradouro, string bairro, string cidade, string estado}`
  * PUT: Atualiza os campos informados no body da requisição
    * `/associado/{id}`: `{string nomeCompleto, string rg, string cpf, number tipoId, boolean carteirinhaBloqueada,
		string telefoneResidencial,string telefoneComercial, string telefoneCelular,
		string cep, string logradouro, string bairro, string cidade, string estado}` 
  * DELETE: 
    * `/associado/{id}`: {Exclui o associado do id informado na URL}
* Dependente:
  * GET:
    * `/dependente/associado/{id}`: {retorna todos os dependente do associado do id informado}
    * `/dependente/{id}`: {retorna o dependente do id que é enviado na URL} 
  * POST: Cria o Dependente
    * `/dependente/`: `{ number associadoId, string nome, string rg}`  
  * PUT: Atualiza os campos informados no body da requisição
    * `/dependente/`: `{ number associadoId, string nome, string rg}`  
  * DELETE: {Exclui o dependente do id informado na URL} 
    * `/dependente/{id}`
* Area:
  * GET
  * POST
  * PUT
  * DELETE
* Reserva
  * GET
  * POST
  * PUT
  * DELETE
* Cobranca
  * GET
  * POST
  * PUT
  * DELETE
* PagamentoRealizado
  * GET
  * POST
  * PUT
  * DELETE
* Turma
  * GET
  * POST
  * PUT
  * DELETE
