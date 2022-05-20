import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Random;


// TODO: Faça conta ser uma classe abstrata
// TODO: Refatorar as funções para uma interface
// TODO: Fazer as funções retornarem um objeto para ser executado pelo banco
public class Conta {
  private final Integer numeroConta;
  private final Integer numeroAgencia;
  private final BigDecimal taxaJuros = BigDecimal.ZERO;
  private final StringBuilder extratoBancario;
  private final Cliente _dono = null;
  private final BigDecimal taxaManutecao;
  private BigDecimal saldo = BigDecimal.ZERO;

  public Conta() {
    Random rng = new Random();
    int parteInteira = Math.abs(rng.nextInt()) % 15;
    int parteDecimal = Math.abs(rng.nextInt()) % 100;
    double taxa = (double) parteInteira + (double) parteDecimal;

    numeroConta = rng.nextInt();
    numeroAgencia = rng.nextInt();
    taxaManutecao = new BigDecimal(taxa);
    extratoBancario = new StringBuilder();
  }

  public void realizarDeposito(@NotNull BigDecimal valorDeposito) {
    addExtratoBancario("Deposito realizado com valor de", valorDeposito);
    saldo = saldo.add(valorDeposito);
  }

  public Transferencia requisitarTransferencia(@NotNull Conta contaParaTransferir,
                                               @NotNull BigDecimal valorTransferencia) {
    // TODO: throw error on saldo insuficiente
    return new Transferencia(this, contaParaTransferir, valorTransferencia);
  }

  public void receberTransferencia(Conta contaOrigem, @NotNull BigDecimal valorRecebido) {
    saldo = saldo.add(valorRecebido);
    addExtratoBancario("Transferencia", valorRecebido, contaOrigem);
  }

  public void debitarTransferencia(Conta contaDestino, BigDecimal valor) {
    saldo = saldo.subtract(valor);
    addExtratoBancario("Transferencia", contaDestino, valor);
  }

  public void realizarSaque(@NotNull BigDecimal valorSaque) {
    // TODO: throw error on saldo insuficiente
    addExtratoBancario("Saque", valorSaque);
    saldo = saldo.subtract(valorSaque);
  }

  public Pix requisitarPix(String chave, BigDecimal valorPix) {
    // TODO: throw error on saldo insuficiente
    return new Pix(this, chave, valorPix);

  }

  public void receberPix(Conta contaOrigem, BigDecimal valorPix) {
    saldo = saldo.add(valorPix);
    addExtratoBancario("Pix recebido", valorPix);
  }

  public void enviarPix(Conta contaDestino, @NotNull String chave, BigDecimal valorPix) {
    saldo = saldo.subtract(valorPix);
    addExtratoBancario("Pix enviado", valorPix);
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
    addExtratoBancario("Taxa de manutencao", taxaManutecao);
    saldo = saldo.subtract(taxaManutecao);
  }

  public void aplicarJuros() {
    BigDecimal valorJuros = saldo.multiply(taxaJuros);
    addExtratoBancario("Juros", valorJuros);
    saldo = saldo.add(valorJuros);
  }

  private void addExtratoBancario(String tipoOperacao, @NotNull BigDecimal valor) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$\n");
  }

  private void addExtratoBancario(String tipoOperacao, @NotNull Conta conta, @NotNull BigDecimal valor) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append(" para a conta: ");
    extratoBancario.append(conta);
    extratoBancario.append(" no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$\n");
  }

  private void addExtratoBancario(String tipoOperacao, @NotNull BigDecimal valor, @NotNull Conta conta) {
    extratoBancario.append(tipoOperacao);
    extratoBancario.append("recebida, no valor de: ");
    extratoBancario.append(valor);
    extratoBancario.append("R$.");
    extratoBancario.append("Conta de origem: ");
    extratoBancario.append(conta);
  }

  public void reportarErro(String msg) {
    System.out.println(msg);
  }

  public Integer getNumeroConta() {
    return numeroConta;
  }

  public Integer getNumeroAgencia() {
    return numeroAgencia;
  }

  @Override
  public String toString() {
    StringBuilder contaAsString = new StringBuilder();
    contaAsString.append("Conta { ");
    contaAsString.append("Numero: " + numeroConta + " ");
    contaAsString.append("Agencia: " + numeroAgencia + " ");
    contaAsString.append("Agencia: " + numeroAgencia + " ");
    contaAsString.append("}");
    return new String(contaAsString);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Agencia)) return false;

    Conta conta = (Conta) o;

    return numeroConta == conta.getNumeroConta() && numeroAgencia == conta.getNumeroAgencia();
  }
}
