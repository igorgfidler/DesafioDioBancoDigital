package transacao;

import banco.Banco;
import conta.Conta;

import java.math.BigDecimal;

public class Saque implements Transacao {
  Conta contaOrigem;
  BigDecimal valor;
  public Saque(Conta contaOrigem, BigDecimal valor) {
    this.contaOrigem = contaOrigem;
    this.valor = valor;
  }

  @Override
  public void executar() {
    if(contaOrigem.getSaldo().compareTo(valor) < 0 ) {
      contaOrigem.reportarErro(new Exception("Saldo insuficiente para o saque!"));
    }
    else {
      TransacaoValor transacao = new TransacaoValor("Saque", valor);
      contaOrigem.debitarTransacao(transacao);
    }
  }
  @Override
  public void notificar() {
    Banco banco = Banco.getInstance();
    banco.adicionarTransacao(this);
  }
}
