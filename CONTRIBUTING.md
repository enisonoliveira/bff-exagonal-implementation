Enison Oliveira
enisonoliveira@hotmail.com

Contribuindo para o Projeto
Obrigado por se interessar em contribuir para este projeto! Abaixo, você encontrará informações importantes sobre a estrutura do projeto e como você pode contribuir.

Arquitetura Hexagonal
Este projeto foi desenvolvido utilizando a Arquitetura Hexagonal, também conhecida como Ports and Adapters. A principal ideia por trás dessa arquitetura é separar a lógica de negócios do código de infraestrutura, permitindo que as partes do sistema interajam com o mundo externo (bancos de dados, serviços externos, interfaces de usuário) através de portas e adaptadores. Isso facilita a manutenção, testes e evolução do sistema, além de proporcionar uma maior flexibilidade na escolha de tecnologias.

Estrutura da Arquitetura:
Core (ou Domínio): Contém a lógica de negócios e as regras principais da aplicação. Não depende de nenhuma tecnologia externa.
Ports: Interfaces que definem as operações que o domínio pode realizar, como salvar ou consultar dados.
Adapters: Implementações concretas dessas interfaces, que interagem com tecnologias externas, como bancos de dados ou APIs externas.
Infraestrutura: Responsável por conectar o sistema com o mundo externo. Esta camada contém implementações de adaptadores e serviços para integração com a base de dados, filas, ou qualquer outra tecnologia externa necessária.
O objetivo principal da Arquitetura Hexagonal é garantir que o núcleo da aplicação permaneça isolado das mudanças nas tecnologias externas, proporcionando maior flexibilidade e escalabilidade ao longo do tempo.
