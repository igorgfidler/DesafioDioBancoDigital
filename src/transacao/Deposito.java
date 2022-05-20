package transacao;

import conta.Conta;

import java.math.BigDecimal;

public class Deposito extends Transacao {

  public Deposito(Conta contaOrigem, BigDecimal valor) {
    this.contaOrigem = contaOrigem;
    this.valor = valor;
  }

  @Override
  public void executar() {
    if (valor.compareTo(BigDecimal.ZERO) < 0) {
      emFalha();
    } else {
      emSucesso();
    }
  }

  @Override
  protected void emFalha() {
    contaOrigem.reportarErro("O valor de depósito não pode ser negativo");
  }

  @Override
  protected void emSucesso() {
    this.contaOrigem.receberDeposito(valor);
  }
}
