import java.math.BigDecimal;

public abstract class Transacao {
  protected Conta contaOrigem;
  protected Conta contaDestino;
  protected BigDecimal valor;
  protected String chavePix;

  public abstract void executar();

  protected abstract void emFalha();

  protected abstract void emSucesso();
}
