package conta;

import cliente.Cliente;
import org.jetbrains.annotations.NotNull;
import transacao.*;

import java.math.BigDecimal;


// TODO: Faça conta ser uma classe abstrata
public class Conta implements RequisicaoTransacao, EfetuarTransacao{
  private static Integer numeroNovaConta = 1;
  private final Integer numeroConta;
  private final Integer numeroAgencia;
  private final BigDecimal taxaJuros = BigDecimal.ZERO;
  private final StringBuilder extratoBancario;
  private final Cliente _dono = null;
  private String chavePix = null;
  private final BigDecimal taxaManutecao = BigDecimal.ZERO;
  private BigDecimal saldo = BigDecimal.ZERO;


  public Conta(Integer numeroAgencia) {
    extratoBancario = new StringBuilder();
    this.numeroAgencia = numeroAgencia;
    this.numeroConta = numeroNovaConta++;
  }

  public Conta(Integer numeroAgencia, @NotNull BigDecimal saldoInicial) {
    this.numeroAgencia = numeroAgencia;
    this.numeroConta = numeroNovaConta++;
    this.saldo = saldoInicial;
    extratoBancario = new StringBuilder();
    extratoBancario.append("Saldo inicial de ");
    extratoBancario.append(saldoInicial);
    extratoBancario.append("R$\n");
  }

  @Override
  public void requisitarDeposito(@NotNull BigDecimal valorDeposito) {
    new Deposito(this, valorDeposito).notificar();
  }

  @Override
  public void requisitarTransferencia(@NotNull Conta contaParaTransferir,
                                           @NotNull BigDecimal valorTransferencia) {
    new Transferencia(this, contaParaTransferir, valorTransferencia).notificar();
  }

  @Override
  public void requisitarSaque(@NotNull BigDecimal valorSaque) {
    new Saque(this, valorSaque).notificar();
  }

  @Override
  public void requisitarPix(@NotNull String chave, @NotNull BigDecimal valorPix) {
    new Pix(this, chave, valorPix).notificar();

  }

  public String getExtratoBancario() {
    StringBuilder addSaldo = new StringBuilder(extratoBancario);
    addSaldo.append("Saldo atual: ");
    addSaldo.append(saldo.toString());
    addSaldo.append("R$\n");
    return new String(addSaldo);
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

  private void addExtratoBancarioDebitar(String tipoOperacao, @NotNull BigDecimal valor) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$\n");
  }

  private void addExtratoBancarioDebitar(String tipoOperacao, @NotNull BigDecimal valor, @NotNull Conta contaDestino) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" para a conta: ");
    extratoBancario.append(contaDestino);
    extratoBancario.append(" no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$\n");
  }

  private void addExtratoBancarioReceber(String tipoOperacao, @NotNull BigDecimal valor) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" recebido(a), no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$\n");
  }

  private void addExtratoBancarioReceber(String tipoOperacao, @NotNull BigDecimal valor, @NotNull Conta conta) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" recebido(a), no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$. ");
    extratoBancario.append("Conta de origem: ");
    extratoBancario.append(conta);
    extratoBancario.append("\n");
  }

  public Integer getNumeroConta() {
    return numeroConta;
  }

  public Integer getNumeroAgencia() {
    return numeroAgencia;
  }

  public BigDecimal getSaldo() {
    return saldo;
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
    contaAsString.append("}");
    return new String(contaAsString);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Conta conta)) return false;

    return numeroConta.equals(conta.getNumeroConta()) && numeroAgencia.equals(conta.getNumeroAgencia());
  }

  @Override
  public void debitarTransacao(@NotNull TransacaoValor transacao) {
    addExtratoBancarioDebitar(transacao.transacao(), transacao.valor());
    saldo = saldo.subtract(transacao.valor());
  }

  @Override
  public void debitarTransacao(@NotNull Conta contaDestino, @NotNull TransacaoValor transacao) {
    addExtratoBancarioDebitar(transacao.transacao(), transacao.valor(), contaDestino);
    saldo = saldo.subtract(transacao.valor());
  }

  @Override
  public void receberTransacao(@NotNull Conta contaOrigem, @NotNull TransacaoValor transacao) {
    addExtratoBancarioReceber(transacao.transacao(), transacao.valor(), contaOrigem);
    saldo = saldo.add(transacao.valor());
  }

  @Override
  public void receberTransacao(@NotNull TransacaoValor transacao) {
    addExtratoBancarioReceber(transacao.transacao(), transacao.valor());
    saldo = saldo.add(transacao.valor());
  }


  @Override
  public void reportarErro(Exception e) {
    System.out.println(e.toString());
  }
}
