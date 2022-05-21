import banco.Agencia;
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

    Agencia agencia = banco.criarNovaAgencia();
    logger.info("Criando uma nova agencia com numero: " + agencia.getNumeroAgencia());

    logger.info("Criando conta primaria com saldo de 1000 e secundaria sem saldo");
    Conta contaPrimaria = agencia.criarConta(new BigDecimal(1000));
    Conta contaSecundaria = agencia.criarConta();


    contaPrimaria.requisitarDeposito(new BigDecimal(1000));
    contaPrimaria.requisitarSaque(new BigDecimal(500));

    contaPrimaria.requisitarTransferencia(contaSecundaria, new BigDecimal(100));

    banco.executarTransacoes();

    agencia.removerConta(contaPrimaria);
    agencia.removerConta(contaSecundaria);

    logger.info(contaPrimaria.getExtratoBancario());
    logger.info(contaSecundaria.getExtratoBancario());


  }
}
