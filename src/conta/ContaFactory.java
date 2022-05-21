package conta;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public interface ContaFactory {
  Conta criarContaCorrente(@NotNull String donoConta);
  Conta criarContaCorrente(@NotNull String donoConta, @NotNull BigDecimal saldoInicial);
  Conta criarContaPoupanca(@NotNull String donoConta);
  Conta criarContaPoupanca(@NotNull String donoConta, @NotNull BigDecimal saldoInicial);
  Conta criarContaSalario(@NotNull String donoConta);
  Conta criarContaSalario(@NotNull String donoConta, @NotNull BigDecimal saldoInicial);
}
