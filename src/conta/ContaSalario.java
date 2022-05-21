package conta;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ContaSalario extends Conta {
  private static final BigDecimal taxaJuros = BigDecimal.ZERO;
  private static final BigDecimal taxaManutencao = new BigDecimal("5.21");
  public ContaSalario(String donoConta, Integer numeroAgencia) {
    super(donoConta, numeroAgencia, taxaJuros, taxaManutencao);
  }

  public ContaSalario(String donoConta, Integer numeroAgencia, BigDecimal saldoInicial) {
    super(donoConta, numeroAgencia, taxaJuros, taxaManutencao, saldoInicial);
  }

  @Override
  public void requisitarDeposito(@NotNull BigDecimal valorDeposito) {
    reportarErro(new Exception("A conta salário não pode receber dinheiro a partir de depósito, somente via transferência"));
  }

  @Override
  public boolean recebeDeposito() {
    return false;
  }

  @Override
  public void requisitarPix(@NotNull String chave, @NotNull BigDecimal valor) {
    reportarErro(new Exception("A conta salário não disponibiliza a funcionalidade PIX"));
  }

  @Override
  public boolean recebePix(){
    return true;
  }

}
