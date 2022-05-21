# Criando um Banco Digital com Java e Orientação a Objetos

Este repositório contém todo o código relevante para o desafio de projeto do 
Módulo IV - Java Essencial do bootcamp GFT Start #5 Java.

## Arquitetura do Projeto

O projeto está divido em três abstrações principais, ``Banco``, ``Conta``, 
``Agência`` e ``Transações`` descritos a seguir.

### Banco
O ``Banco`` é a abstração central, ele atua como um dono de todas as
agências e contas disponíveis no serviço. 
O ``Banco`` é um ``Singleton`` uma vez que neste sistema não há sentido a existência
de multiplos bancos, pode-se pensar no ``Banco`` como uma espécie de Banco Central.

O ``Banco`` é responsável por garantir a existência de todas as Agências, Contas, executar
as transações que foram requisitadas. Ele contém métodos auxiliares como
verificar a existência de uma ``Conta``, a quem uma chave PIX pertence entre outros
similares.

A função principal do banco é descrita pela função ``executarTransacoes`` 
que executa todas as transações que foram requisitadas até o momento.

### Agência
A ``Agência`` é uma abstração que representa cada sede do ``Banco`` modelado. 
A ``Agência`` atua principalmente como uma ``Factory`` de ``Contas``, ou seja,
sua função principal é manter novas ``Contas``.

### Conta
A ``Conta`` é a abstração que mantém todas as informações relacionadas a uma 
conta de banco. A ``Conta`` é responsável por criar as ``Transações`` que
serão executadas, e reportar mensagens de erro ao usuário caso a especialização
da ``Conta`` não suporte algum tipo de operação

O sistema contém três especializações de ``Conta`` sendo elas: `ContaCorrente`, 
`ContaPoupança` e `ContaSalário`. Para criar uma nova especialização basta
extender a ``classe abstrata Conta`` e atualizar os métodos referentes
a ``interface RequisicaoTransacao``.

Cada transação possível nas ``Contas`` atua utilizando
o padrão de projeto ``Comando`` que é eventualmente
executado pelo ``Banco``.

Cada ``Conta`` suporta a requisição do extrato bancário,
neste caso simplificado e mostrando todas as transações que 
foram realizadas até o momento.

A ``interface EfetuarTransacao`` é um modo de reduzir
um conjunto de métodos similiares para cada nova transação. Esta interface
suporta três operações ``debitarTransação`` que decresce o saldo da conta após
a operação, ``receberTransação`` que incrementa o saldo da conta após a transação e 
``reportarErro`` que tem a função de mostrar algum erro que ocorreu durante
a execução da ``Transação``.

Cada ``Conta`` possui a capacidade de calcular a própria taxa de manutenção
e calcular juros sobre o dinheiro que elas armazenam. Esse dois paramêtros
ficam fixos para cada especialização de ``Conta``.

### Transações
As transações representam todas as operações
que as ``Contas podem realizar no sistema``.
Cada transação atua notificando o banco de sua
existência, ou seja, cada ``Transação`` se auto insere
na lista de execução do ``Banco``.

Os métodos suportados pela ``Transação`` são `notificar` que faz
com que a `Transação` possa ser observada pelo banco e `executar`
que é utilizada quando a `Transação` vai ser executada pelo `Banco`.

Para adicionar um novo tipo de ``Transação`` no sistema
basta inserir a nova operação em ``conta/RequisicaoTransacao``
e então implementar a classe relevante em ``transacao/``.


## Considerações finais
O sistema não utiliza ``Cliente`` de forma ativa,
apenas o seu nome é utilizado para identificação da ``Conta`` 
no extrato bancário.

Não ocorreu a necessidade de separação de `Cliente Físico` 
e ``Cliente Jurídico``, uma vez que este sistema focou nas relações 
de trocas entre as contas.

