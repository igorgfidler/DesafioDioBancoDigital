package transacao;

import banco.Banco;
import conta.Conta;

import java.math.BigDecimal;

public class Transferencia implements Transacao {
  Conta contaOrigem;
  Conta contaDestino;
  BigDecimal valor;
  public Transferencia(Conta contaOrigem, Conta contaDestino, BigDecimal valorTransferencia) {
    this.contaOrigem = contaOrigem;
    this.contaDestino = contaDestino;
    this.valor = valorTransferencia;
  }


  @Override
  public void executar() {
    contaOrigem.debitarTransferencia(contaDestino, valor);
    contaDestino.receberTransferencia(contaOrigem, valor);
  }

  @Override
  public void notificar() {
    Banco banco = Banco.getInstance();
    banco.adicionarTransacao(this);
  }
}
