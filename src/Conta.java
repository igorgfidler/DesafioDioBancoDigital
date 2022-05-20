import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Random;


// TODO: Faça conta ser uma classe abstrata
// TODO: Refatorar as funções para uma interface
// TODO: Fazer as funções retornarem um objeto para ser executado pelo banco
public class Conta {
  private final int numero;
  private final int agencia;
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

    numero = rng.nextInt();
    agencia = rng.nextInt();
    taxaManutecao = new BigDecimal(taxa);
    extratoBancario = new StringBuilder();
  }

  public void realizarDeposito(@NotNull BigDecimal valorDeposito) {
    addExtratoBancario("Deposito realizado com valor de", valorDeposito);
    saldo = saldo.add(valorDeposito);
  }

  public void realizarTransferencia(@NotNull Conta contaParaTransferir,
                                    @NotNull BigDecimal valorTransferencia) {
    // TODO: throw error on saldo insuficiente
    addExtratoBancario("Transferencia", contaParaTransferir, valorTransferencia);
    saldo = saldo.subtract(valorTransferencia);
    contaParaTransferir.realizarDeposito(valorTransferencia);
  }

  public void realizarSaque(@NotNull BigDecimal valorSaque) {
    // TODO: throw error on saldo insuficiente
    addExtratoBancario("Saque", valorSaque);
    saldo = saldo.subtract(valorSaque);
  }

  public void receberPix(BigDecimal valorPix) {
    saldo = saldo.add(valorPix);
    addExtratoBancario("Pix recebido", valorPix);
  }

  public void enviarPix(@NotNull String chave, BigDecimal valorPix) {
    //TODO: checar se a chave existe
    if (chave.isEmpty()) {
      return;
    }

    // TODO: throw an error on saldo insuficiente
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

  @Override
  public String toString() {
    StringBuilder contaAsString = new StringBuilder();
    contaAsString.append("Conta { ");
    contaAsString.append("Numero: " + numero + " ");
    contaAsString.append("Agencia: " + agencia + " ");
    contaAsString.append("}");
    return new String(contaAsString);
  }
}
