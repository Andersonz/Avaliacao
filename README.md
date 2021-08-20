# Avaliação técnica back-end V1

Criar uma solução backend para gerenciar sessões de votação.

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST: 

- Cadastrar novo usuário/pessoa/cooperado (nome, cpf)
- Cadastrar uma nova pauta (descricao, tempo_duracao (minutos));
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default)
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta)
- Contabilizar os votos e dar o resultado da votação na pauta

Para realizar o projeto, foi utilizado SpringBoot, Flyway, Mysql, Java8;

- Integrado com  https://user-info.herokuapp.com/ para avaliar se o cpf do usuário é valido;
- Versionamento da API - Utilizado o modelo PATH onde a versão é especificada após a base da url, pois é uma das abordagens mais utilizadas, deixando a visualização e entendimento mais simples.

Documentação dos serviços está disponível em: https://documenter.getpostman.com/view/5363356/TzzBpvq7

*Importante
*Arquivos de configuração estão disponíveis em "src/main/resources"
*Diagrama de entidade e relacionamento disponível em "/docs" (Abrir com MySqlWorkbench)
*Arquivo para importação de projeto de serviços em "/docs" (Importar no Postman)
