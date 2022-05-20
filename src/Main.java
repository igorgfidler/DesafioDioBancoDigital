import banco.Banco;
import conta.Conta;

import java.math.BigDecimal;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
  public static void main(final String[] args) {
    LogManager logm = LogManager.getLogManager();
    Logger logger = logm.getLogger(Logger.GLOBAL_LOGGER_NAME);

    logger.info("Criando o banco");
    Banco banco = Banco.getInstance();

    Integer numeroAgencia = banco.criarNovaAgencia();
    logger.info("Criando uma nova agencia com numero: " + numeroAgencia);

    logger.info("Criando conta primaria com saldo de 1000 e secundaria sem saldo");
    Conta contaPrimaria = new Conta(numeroAgencia, new BigDecimal(1000));
    Conta contaSecundaria = new Conta(numeroAgencia);

    banco.inserirConta(numeroAgencia, contaPrimaria);
    banco.inserirConta(numeroAgencia, contaSecundaria);

    banco.adicionarTransacao(contaPrimaria.requisitarDeposito(new BigDecimal(1000)));
    banco.adicionarTransacao(contaPrimaria.requisitarSaque(new BigDecimal(500)));

    banco.adicionarTransacao(contaPrimaria.requisitarTransferencia(contaSecundaria, new BigDecimal(100)));

    banco.executarTransacoes();

    logger.info(contaPrimaria.getExtratoBancario());
    logger.info(contaSecundaria.getExtratoBancario());


  }
}
