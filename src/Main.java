import java.math.BigDecimal;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
  public static void main(final String[] args) {
    LogManager logm = LogManager.getLogManager();
    Logger logger = logm.getLogger(Logger.GLOBAL_LOGGER_NAME);
    logger.info("Starting main function");
    Conta contaPrimaria = new Conta();
    Conta contaSecundaria = new Conta();

    logger.info("Realizando um deposito de 10.000,00 R$ na conta primaria");
    contaPrimaria.realizarDeposito(new BigDecimal(10000));

    logger.info("Realizando uma transfencia de 1.000,00 R$ da conta primaria para a secundaria");
    contaPrimaria.realizarTransferencia(contaSecundaria, new BigDecimal(1000));

    logger.info("Realizando um saque de 500 na conta primaria");
    contaPrimaria.realizarSaque(new BigDecimal(500));

    logger.info("Aplicando juros na conta primaria");
    contaPrimaria.aplicarJuros();

    logger.info("Enviando pix a partir da conta primaria");
    contaPrimaria.enviarPix("akshjdakjsd", new BigDecimal(10));

    logger.info("Recebendo pix na conta primaria");
    contaPrimaria.receberPix(new BigDecimal(1));

    logger.info("Debitando os custos de manutenção da conta");
    contaPrimaria.debitarTaxaManutencao();

    logger.info("Mostrando o extrato bancario da conta");
    logger.info(contaPrimaria.getExtratoBancario());

  }
}
