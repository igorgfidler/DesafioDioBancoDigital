package transacao;

import banco.Banco;
import conta.Conta;

import java.math.BigDecimal;
import java.util.Optional;

public class Pix extends Transacao {

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
      emFalha();
    } else {
      contaDestino = possivelConta.get();
      emSucesso();
    }
  }

  @Override
  protected void emFalha() {
    contaOrigem.reportarErro("A chave não existe");
  }

  @Override
  protected void emSucesso() {
    contaOrigem.enviarPix(contaDestino, chavePix, valor);
    contaDestino.receberPix(contaOrigem, valor);
  }
}
