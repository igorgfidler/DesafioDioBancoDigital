package conta;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {
  private static final BigDecimal taxaJuros = new BigDecimal("5.87");
  private static final BigDecimal taxaManutencao = new BigDecimal("2.34");
  public ContaPoupanca(String donoConta, Integer numeroAgencia) {
    super(donoConta, numeroAgencia, taxaJuros, taxaManutencao);
  }

  public ContaPoupanca(String donoConta, Integer numeroAgencia, BigDecimal saldoInicial) {
    super(donoConta, numeroAgencia, taxaJuros, taxaManutencao, saldoInicial);
  }

  @Override
  public void requisitarPix(@NotNull String chave, @NotNull BigDecimal valor){
    reportarErro(new Exception("A conta poupançao não pode executar uma transferência PIX"));
  }

  @Override
  public boolean recebePix() {
    return true;
  }
}
