package transacao;

import conta.Conta;

import java.math.BigDecimal;

public class Saque extends Transacao {


  public Saque(Conta contaOrigem, BigDecimal valor) {
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
    contaOrigem.reportarErro("O valor de saque não pode ser negativo");
  }

  @Override
  protected void emSucesso() {
    contaOrigem.realizarSaque(valor);
  }
}
