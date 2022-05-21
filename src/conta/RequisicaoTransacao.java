package conta;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public interface RequisicaoTransacao {
  void requisitarDeposito(@NotNull BigDecimal valorDeposito);
  void requisitarTransferencia(@NotNull Conta contaDestino, @NotNull BigDecimal valorTransferencia);
  void requisitarSaque(@NotNull BigDecimal valorSaque);
  void requisitarPix(@NotNull String chave, @NotNull BigDecimal valor);
}
