import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Agencia {
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
    if (!(o instanceof Agencia)) return false;

    Agencia agencia = (Agencia) o;

    return numeroAgencia == agencia.numeroAgencia;
  }

  public void inserirConta(@NotNull Conta novaConta) {
    contas.put(novaConta.getNumeroConta(), novaConta);
  }

  // Conta deve obrigatoriamente existir, função do banco checar isso
  public void removerConta(@NotNull Conta conta) {
    contas.remove(conta.getNumeroConta());
  }

  public boolean checarExistenciaConta(@NotNull Conta conta) {
    return contas.get(conta.getNumeroAgencia()) != null;
  }
}
