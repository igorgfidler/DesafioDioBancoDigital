package conta;

import org.jetbrains.annotations.NotNull;
import transacao.Transacao;
import transacao.TransacaoValor;

import java.math.BigDecimal;

public interface EfetuarTransacao {
  void debitarTransacao(@NotNull TransacaoValor transacao);
  void debitarTransacao(@NotNull Conta contaDestino, @NotNull TransacaoValor transacao);
  void receberTransacao(@NotNull Conta contaOrigem, @NotNull TransacaoValor transacao);
  void receberTransacao(@NotNull TransacaoValor transacaoValor);
  void reportarErro(Exception e);

}
