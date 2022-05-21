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
    TransacaoValor transacao = new TransacaoValor("Transferência", valor);

    if (valor.compareTo(BigDecimal.ZERO) < 0) {
      contaOrigem.reportarErro(new Exception("O valor de transferência não pode ser negativo"));
      return;
    }

    if(contaOrigem.getSaldo().compareTo(valor) < 0 ) {
      contaOrigem.reportarErro(new Exception("Saldo insuficiente para a transferência!"));
      return;
    }

    Banco banco = Banco.getInstance();
    if(!banco.contaValida(contaDestino)){
      contaOrigem.reportarErro(new Exception("A conta de destino informada não existe!"));
      return;
    }

    contaOrigem.debitarTransacao(contaDestino, transacao);
    contaDestino.receberTransacao(contaOrigem, transacao);
  }

  @Override
  public void notificar() {
    Banco banco = Banco.getInstance();
    banco.adicionarTransacao(this);
  }
}
