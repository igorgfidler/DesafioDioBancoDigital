package transacao;

import conta.Conta;

import java.math.BigDecimal;

public interface Transacao {
  void executar();
  void notificar();
}
