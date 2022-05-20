package transacao;

import banco.Banco;
import conta.Conta;

import java.math.BigDecimal;

public class Transferencia extends Transacao {

  public Transferencia(Conta contaOrigem, Conta contaDestino, BigDecimal valorTransferencia) {
    this.contaOrigem = contaOrigem;
    this.contaDestino = contaDestino;
    this.valor = valorTransferencia;
  }

  @Override
  public void executar() {
    Banco banco = Banco.getInstance();
    if (banco.contaValida(contaDestino)) {
      emSucesso();
    } else {
      emFalha();
    }
  }

  @Override
  protected void emFalha() {
    contaOrigem.reportarErro("A conta informada não existe");
  }

  @Override
  protected void emSucesso() {
    contaDestino.receberTransferencia(contaOrigem, valor);
    contaOrigem.debitarTransferencia(contaDestino, valor);
  }
}
