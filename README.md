
# :rocket: Desafio Solutis 

Desafio proposto para a construção de uma API, onde uma cooperativa possui associados e cada associado possui um voto, e as decisões são tomadas através de assembleias, por votação

## :warning: Requisitos

Para que a aplicação execute com sucesso, se faz necessário que alguns itens estejam instalados na máquina na qual será executada a aplicação.

Itens necessários:

- Maven 3.8.6
- Java 11
- Docker

## :memo: Execução do projeto

1. Ir até a pasta onde desejar realizar o donwload da aplicação.

2. Abrir o terminal na pasta escolhida e digitar o seguinte comando:

        git clone https://github.com/RenanUmV/vote-manager

3. Ir para a pasta do projeto, poderá utilizar o terminal para isso digitando:

        cd vote-manager

4. Levantar as configurações e comunição do banco de dados:

        docker compose up -d
        
5. Instalar as dependências do projeto:

        mvn clean install

6. Abrir o projeto em uma IDE de sua escolha.

7. Rodar a aplicação clicando com o botão direito no arquivo: **VoteManagerApplication** e executando.

8. Seguindo todos os passos, a aplicação ficará disponível em: http://localhost:8080 .

Junto com a aplicação foi disponibilizado uma coleção do Postman, que poderá executar os endpoints.

## :link: Links

 - [Projeto no Github](https://github.com/RenanUmV/vote-manager)

 - [Swagger](http://localhost:8080/swagger-ui/index.html)

 - [RebbitMQ](http://localhost:15672)
        ```login: admin  senha: 123123```

 - [Coleção do Postman](https://github.com/RenanUmV/vote-manager/blob/main/VoterManagerSolutis.postman_collection.json)

## :heavy_check_mark: Tarefas Principais

- <details open>
  <summary>(RF0) Autenticação e autorização.</summary>
  <br/>
  <pre>
  - Todo usuário, seja ele administrador ou cooperado (pessoa que votam) deve estar devidamente autenticado para
    operar o sistema.(Utilize bearer token JWT).
  - Usuários administradores podem realizar todas as operações do sistema. 
  - Usuários cooperados podem apenas votar. 
  - Deve ser possível adicionar, alterar ou excluir usuários.
  - Os dados requisitados para cadastro de usuário são: CPF, nome, tipo (administrador ou cooperado) e e-mail.
  </pre>


- <details open>
  <summary>(RF1) Cadastrar pauta.</summary>
  <br/>
  <pre>
  - A pauta pode ser apenas cadastrada.
  - Toda pauta deve conter um nome da pauta e uma descrição.
  </pre>

- <details open>
  <summary>(RF2) Abrir uma sessão de votação para uma pauta.</summary>
  <br/>
  <pre>
  - Cada pauta deve comportar apenas uma sessão de votação. 
  - A sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por padrão.
  </pre>

- <details open>
  <summary>(RF3) Receber votos dos cooperados em pautas abertas.</summary>
  <br/>
  <pre>
  - Os votos são apenas 'Sim'/'Não'.
  - Cada cooperado é identificado por um id único e pode votar apenas uma vez por pauta. 
  - Registre a data/hora do voto. de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por padrão.
  </pre>

- <details open>
  <summary>(RF4) Contabilizar os votos e dar o resultado da votação na pauta.</summary>
  <br/>
  <pre>
  - Exibir vencedor por maioria simples.
  - Exibir quantidade de votos para cada um dos grupos 'Sim'/'Não'.
  - Exibir percentual para cada um dos grupos 'Sim'/'Não'.
  </pre>

## :dart: Tarefas bônus

- <details open>
  <summary>Tarefa Bônus 1 - Integração com sistemas externos</summary>
  <br/>
  <pre>
  Integrar com um sistema externo que verifica o CPF antes de cadastrar um usuário. Caso o sistema esteja indisponível, você deve
   fazer mais duas tentativas (retry) no intervalo de 2 e 4 segundos respectivamente (você deve registrar as tentativas no log). 
   Se ainda assim o serviço externo esteja indisponível, permita o cadastro do usuário, mas registre no log que o serviço não
   pode ser consultado.
  </pre>

- <details open>
  <summary>Tarefa Bônus 2 - Contabilização automática</summary>
  <br/>
  <pre>
   A contabilização de votos de pautas encerradas (RF4), deve ser feita de forma automática pelo sistema. A rotina de
   contabilização deve ser executada a cada minuto. Os dados devem ser persistidos no banco de dados.
  </pre>

- <details open>
  <summary>Tarefa Bônus 3 - Mensageria e fila</summary>
  <br/>
  <pre>
   Quando cada sessão de votação for fechada, poste uma mensagem em uma mensageria
   https://kafka.apache.org/, https://www.rabbitmq.com/ ou qualquer outra) com o resultado da votação.
   Forneça dockerfile ou configurações necessárias para o serviço de mensageiria utilizado.
  </pre>

- <details open>
  <summary>Tarefa Bônus 6 - Versionamento da API</summary>
  <br/>
  <pre>
   Sistemas evoluem constantemente logo é possível que sua API mude e seja necessário que você mantenha diferentes versões em 
   produção (ex: 1.0, 2.0) até que seus clientes (pessoas/sistemas que consomem sua api) realizem as mudanças necessárias para
   consumir a nova versão da API. Escreva um texto dizendo qual estratégia você utilizaria para versionar a sua API.
   <br>
   <br>
   Versionamento escolhido
   O versionamento da API será via URL, onde é especificado a versão da API na qual está sendo utilizado, por exemplo 
   http://localhost:80080/v1/..., ficando mais fácil de realizar as mudanças necessárias quando surgir
   uma nova versão da aplicação.
  </pre>
