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
    // TODO throw error em saldo insuficiente
    contaOrigem.realizarSaque(valor);
  }
  @Override
  public void notificar() {
    Banco banco = Banco.getInstance();
    banco.adicionarTransacao(this);
  }
}
