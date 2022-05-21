package conta;

import java.math.BigDecimal;

public class ContaCorrente extends Conta{
  private static final BigDecimal taxaJuros = BigDecimal.ZERO;
  private static final BigDecimal taxaManutencao = new BigDecimal("18.53");
  public ContaCorrente(String donoConta, Integer numeroAgencia) {
    super(donoConta, numeroAgencia, taxaJuros, taxaManutencao);
  }

  public ContaCorrente(String donoConta, Integer numeroAgencia, BigDecimal saldoInicial) {
    super(donoConta, numeroAgencia, taxaJuros, taxaManutencao, saldoInicial);
  }
}
