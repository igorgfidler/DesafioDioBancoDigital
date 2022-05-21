package transacao;

import banco.Banco;
import conta.Conta;

import java.math.BigDecimal;
import java.util.Optional;

public class Pix implements Transacao {
  Conta contaOrigem;
  Conta contaDestino;
  String chavePix;
  BigDecimal valor;
  public Pix(Conta contaOrigem, String chave, BigDecimal valor) {
    this.contaOrigem = contaOrigem;
    this.chavePix = chave;
    this.valor = valor;
  }


  @Override
  public void executar() {
    Banco banco = Banco.getInstance();
    Optional<Conta> possivelConta = banco.getContaPix(chavePix);
    if (possivelConta.isEmpty()) {
      contaOrigem.reportarErro("conta não existente");
    } else {
      contaDestino = possivelConta.get();
      contaDestino.receberPix(contaOrigem, valor);
      contaOrigem.enviarPix(contaDestino, valor);
    }
  }

  @Override
  public void notificar() {
    Banco banco = Banco.getInstance();
    banco.adicionarTransacao(this);
  }
}
