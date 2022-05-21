package transacao;

import banco.Banco;
import conta.Conta;

import java.math.BigDecimal;
import java.util.Optional;

public class Pix implements Transacao {
  Conta contaOrigem;
  Conta contaDestino;
  String chavePix;
  BigDecimal valor;
  public Pix(Conta contaOrigem, String chave, BigDecimal valor) {
    this.contaOrigem = contaOrigem;
    this.chavePix = chave;
    this.valor = valor;
  }


  @Override
  public void executar() {
    Banco banco = Banco.getInstance();
    if(contaOrigem.getSaldo().compareTo(valor) < 0 ){
      contaOrigem.reportarErro(new Exception("Saldo insuficiente para o PIX"));
      return;
    }

    if(valor.compareTo(BigDecimal.ZERO) < 0 ){
      contaOrigem.reportarErro(new Exception("O valor do PIX não pode ser negativo"));
      return;
    }
    if(!banco.existeChavePix(chavePix)) {
      contaOrigem.reportarErro(new Exception("A chave PIX informada não existe!"));
      return;
    }

    Optional<Conta> possivelConta = banco.getContaPix(chavePix);
    if (possivelConta.isEmpty()) {
      contaOrigem.reportarErro(new Exception("A chave PIX não está mapeada para nenhuma conta!"));
      return;
    }

    contaDestino = possivelConta.get();
    TransacaoValor transacao = new TransacaoValor("PIX", valor);
    contaDestino.receberTransacao(contaOrigem, transacao);
    contaOrigem.debitarTransacao(contaDestino, transacao);

  }

  @Override
  public void notificar() {
    Banco banco = Banco.getInstance();
    banco.adicionarTransacao(this);
  }
}
