package banco;

import conta.Conta;
import org.jetbrains.annotations.NotNull;
import transacao.Transacao;

import java.util.*;

public class Banco {
  private static Banco banco;
  private final Map<Integer, Agencia> agencias;
  private final Map<String, Conta> chavePix;
  private final Queue<Transacao> transacoes;

  private Banco() {
    agencias = new HashMap<>();
    chavePix = new HashMap<>();
    transacoes = new LinkedList<>();
  }

  public static Banco getInstance() {
    if (banco == null) {
      banco = new Banco();
    }
    return banco;
  }

  public void executarTransacoes() {
    for (Transacao t : transacoes) {
      t.executar();
    }
    transacoes.clear();
  }

  public void adicionarTransacao(Transacao t) {
    transacoes.add(t);
  }

  public Optional<Conta> getContaPix(@NotNull String chave) {
    return Optional.of(chavePix.get(chave));
  }

  public boolean contaValida(@NotNull Conta conta) {
    Integer numeroAgencia = conta.getNumeroAgencia();

    Agencia agencia = agencias.get(numeroAgencia);
    if (agencia == null) {
      return false;
    }

    return agencia.checarExistenciaConta(conta);
  }

  public void inserirChavePix(@NotNull String chave, @NotNull Conta conta) {
    if (conta.recebePix()) {
      conta.reportarErro(new Exception("A conta " + conta + "não gerar uma chave Pix"));
      return;
    }
    conta.setChavePix(chave);
    chavePix.put(chave, conta);
  }

  public Agencia criarNovaAgencia() {
    Agencia agencia = new Agencia();
    agencias.put(agencia.getNumeroAgencia(), agencia);
    return agencia;
  }

  public boolean existeChavePix(String chave) {
    return chavePix.containsKey(chave);
  }
}
