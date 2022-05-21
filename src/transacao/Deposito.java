package transacao;

import banco.Banco;
import conta.Conta;

import java.math.BigDecimal;

public class Deposito implements Transacao {
  Conta contaOrigem;
  BigDecimal valor;
  public Deposito(Conta contaOrigem, BigDecimal valor) {
    this.contaOrigem = contaOrigem;
    this.valor = valor;
  }

  @Override
  public void executar() {
    if (valor.compareTo(BigDecimal.ZERO) < 0) {
      contaOrigem.reportarErro(new Exception("O valor de depósito deve ser maior que zero!"));
    } else {
      TransacaoValor transacao = new TransacaoValor("Depósito", valor);
      contaOrigem.receberTransacao(transacao);
    }
  }

  @Override
  public void notificar() {
    Banco banco = Banco.getInstance();
    banco.adicionarTransacao(this);
  }

}
