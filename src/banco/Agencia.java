package banco;

import conta.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Agencia implements ContaFactory{
  private static Integer numeroNovaAgencia = 1;
  private final Integer numeroAgencia;
  private final Map<Integer, Conta> contas;

  public Agencia() {
    numeroAgencia = numeroNovaAgencia++;
    contas = new HashMap<>();
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroAgencia);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Agencia agencia)) return false;

    return numeroAgencia.equals(agencia.numeroAgencia);
  }

  public void inserirConta(@NotNull Conta novaConta) {
    contas.put(novaConta.getNumeroConta(), novaConta);
  }

  public void removerConta(@NotNull Conta conta) {
    contas.remove(conta.getNumeroConta());
  }

  public Optional<Conta> encontrarConta(@NotNull Integer numeroConta) {
    return Optional.of(contas.get(numeroConta));
  }


  public boolean checarExistenciaConta(@NotNull Conta conta) {
    return contas.get(conta.getNumeroAgencia()) != null;
  }

  public Integer getNumeroAgencia() {
    return numeroAgencia;
  }

  @Override
  public Conta criarContaCorrente(@NotNull String donoConta) {
    Conta c =  new ContaCorrente(donoConta, numeroAgencia);
    contas.put(numeroAgencia, c);
    return c;
  }

  @Override
  public Conta criarContaCorrente(@NotNull String donoConta,
                                  @NotNull BigDecimal saldoInicial) {
    Conta c =  new ContaCorrente(donoConta, numeroAgencia, saldoInicial);
    contas.put(numeroAgencia, c);
    return c;
  }

  @Override
  public Conta criarContaPoupanca(@NotNull String donoConta) {
    Conta c =  new ContaPoupanca(donoConta, numeroAgencia);
    contas.put(numeroAgencia, c);
    return c;
  }

  @Override
  public Conta criarContaPoupanca(@NotNull String donoConta,
                                  @NotNull BigDecimal saldoInicial) {
    Conta c =  new ContaPoupanca(donoConta,numeroAgencia,saldoInicial);
    contas.put(numeroAgencia, c);
    return c;
  }

  @Override
  public Conta criarContaSalario(@NotNull String donoConta) {
    Conta c =  new ContaSalario(donoConta, numeroAgencia);
    contas.put(numeroAgencia, c);
    return c;
  }

  @Override
  public Conta criarContaSalario(@NotNull String donoConta,
                                 @NotNull BigDecimal saldoInicial) {
    Conta c =  new ContaSalario(donoConta,numeroAgencia,saldoInicial);
    contas.put(numeroAgencia, c);
    return c;
  }
}
