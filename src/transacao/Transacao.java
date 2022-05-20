package transacao;

import conta.Conta;

import java.math.BigDecimal;

public abstract class Transacao {
  protected Conta contaOrigem = null;
  protected Conta contaDestino = null;
  protected BigDecimal valor = null;
  protected String chavePix = null;

  public abstract void executar();

  protected abstract void emFalha();

  protected abstract void emSucesso();
}
