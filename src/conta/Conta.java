package conta;

import org.jetbrains.annotations.NotNull;
import transacao.*;

import java.math.BigDecimal;

public abstract class Conta implements RequisicaoTransacao, EfetuarTransacao{
  private static Integer numeroNovaConta = 1;
  private final Integer numeroConta;
  private final Integer numeroAgencia;
  protected final BigDecimal taxaJuros;
  protected final StringBuilder extratoBancario;
  protected String dono;
  protected String chavePix = null;
  protected final BigDecimal taxaManutecao;
  protected BigDecimal saldo = BigDecimal.ZERO;


  public Conta(String donoConta, Integer numeroAgencia, BigDecimal taxaJuros, BigDecimal taxaManutecao) {
    extratoBancario = new StringBuilder();
    this.numeroAgencia = numeroAgencia;
    this.numeroConta = numeroNovaConta++;
    dono = donoConta;
    this.taxaJuros = taxaJuros;
    this.taxaManutecao = taxaManutecao;
  }

  public Conta(String donoConta, Integer numeroAgencia, BigDecimal taxaJuros,
               BigDecimal taxaManutecao, @NotNull BigDecimal saldoInicial) {
    this.numeroAgencia = numeroAgencia;
    this.numeroConta = numeroNovaConta++;
    this.saldo = saldoInicial;
    this.taxaJuros = taxaJuros;
    this.taxaManutecao = taxaManutecao;
    dono = donoConta;
    extratoBancario = new StringBuilder();
    extratoBancario.append("Saldo inicial de ");
    extratoBancario.append(saldoInicial);
    extratoBancario.append("R$\n");
  }

  /* deixar a taxa com um valor fixo
   * cada tipo de conta futura vai implementar a própria taxa
   *
   */
  public void debitarTaxaManutencao() {
    addExtratoBancarioDebitar("Taxa de manutencao", taxaManutecao);
    saldo = saldo.subtract(taxaManutecao);
  }

  public void aplicarJuros() {
    BigDecimal valorJuros = saldo.multiply(taxaJuros);
    addExtratoBancarioReceber("Juros", valorJuros);
    saldo = saldo.add(valorJuros);
  }

  @Override
  public void requisitarDeposito(@NotNull BigDecimal valorDeposito) {
    new Deposito(this, valorDeposito).notificar();
  }

  @Override
  public boolean recebeDeposito(){
    return true;
  }

  @Override
  public void requisitarTransferencia(@NotNull Conta contaParaTransferir,
                                           @NotNull BigDecimal valorTransferencia) {
    new Transferencia(this, contaParaTransferir, valorTransferencia).notificar();
  }

  @Override
  public boolean recebeTransferencia() {
    return true;
  }

  @Override
  public void requisitarSaque(@NotNull BigDecimal valorSaque) {
    new Saque(this, valorSaque).notificar();
  }

  @Override
  public void requisitarPix(@NotNull String chave, @NotNull BigDecimal valorPix) {
    new Pix(this, chave, valorPix).notificar();

  }

  @Override
  public boolean recebePix() {
    return false;
  }

  @Override
  public final void debitarTransacao(@NotNull TransacaoValor transacao) {
    addExtratoBancarioDebitar(transacao.transacao(), transacao.valor());
    saldo = saldo.subtract(transacao.valor());
  }

  @Override
  public final void debitarTransacao(@NotNull Conta contaDestino, @NotNull TransacaoValor transacao) {
    addExtratoBancarioDebitar(transacao.transacao(), transacao.valor(), contaDestino);
    saldo = saldo.subtract(transacao.valor());
  }

  @Override
  public final void receberTransacao(@NotNull Conta contaOrigem, @NotNull TransacaoValor transacao) {
    addExtratoBancarioReceber(transacao.transacao(), transacao.valor(), contaOrigem);
    saldo = saldo.add(transacao.valor());
  }

  @Override
  public final void receberTransacao(@NotNull TransacaoValor transacao) {
    addExtratoBancarioReceber(transacao.transacao(), transacao.valor());
    saldo = saldo.add(transacao.valor());
  }

  public String getDono() {
    return  dono;
  }

  public void setDono(@NotNull String novoNome) {
    dono = novoNome;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }
  public Integer getNumeroAgencia() {
    return numeroAgencia;
  }

  public Integer getNumeroConta() {
    return numeroConta;
  }

  public String getExtratoBancario() {
    StringBuilder addSaldo = new StringBuilder(extratoBancario);
    addSaldo.append("Saldo atual: ");
    addSaldo.append(saldo.toString());
    addSaldo.append("R$\n");
    return new String(addSaldo);
  }
  public void setChavePix(String chavePix) {
    this.chavePix = chavePix;
  }

  @Override
  public String toString() {
    StringBuilder contaAsString = new StringBuilder();
    contaAsString.append("Conta { ");
    contaAsString.append("Número: " + numeroConta + " ");
    contaAsString.append("Agência: " + numeroAgencia + " ");
    contaAsString.append("Dono: ");
    contaAsString.append(dono);
    contaAsString.append("}");
    return new String(contaAsString);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Conta conta)) {
      return false;
    }

    return numeroConta.equals(conta.getNumeroConta()) && numeroAgencia.equals(conta.getNumeroAgencia());
  }


  @Override
  public void reportarErro(@NotNull Exception e) {
    System.out.println(e.toString());
  }

  protected void addExtratoBancarioDebitar(String tipoOperacao, @NotNull BigDecimal valor) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$\n");
  }

  protected void addExtratoBancarioDebitar(String tipoOperacao, @NotNull BigDecimal valor,
                                         @NotNull Conta contaDestino) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" para a conta: ");
    extratoBancario.append(contaDestino);
    extratoBancario.append(" no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$\n");
  }

  protected void addExtratoBancarioReceber(String tipoOperacao, @NotNull BigDecimal valor) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" recebido(a), no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$\n");
  }

  protected void addExtratoBancarioReceber(String tipoOperacao, @NotNull BigDecimal valor,
                                         @NotNull Conta conta) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" recebido(a), no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$. ");
    extratoBancario.append("Conta de origem: ");
    extratoBancario.append(conta);
    extratoBancario.append("\n");
  }
}
