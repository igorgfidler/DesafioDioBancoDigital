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

    Banco banco = Banco.getInstance();

    Agencia agencia = banco.criarNovaAgencia();
    Conta contaPrimaria = agencia.criarConta(new BigDecimal(1000));
    banco.inserirChavePix("chave", contaPrimaria);
    Conta contaSecundaria = agencia.criarConta();
    banco.inserirChavePix("chave2", contaSecundaria);


    contaPrimaria.requisitarDeposito(new BigDecimal(1000));
    contaPrimaria.requisitarSaque(new BigDecimal(500));

    contaPrimaria.requisitarTransferencia(contaSecundaria, new BigDecimal(100));
    contaPrimaria.requisitarPix("chave2", new BigDecimal(100));
    banco.executarTransacoes();

    System.out.println("-----------Extrato bancario da conta primaria---------");
    System.out.println(contaPrimaria.getExtratoBancario());
    System.out.println("-----------Extrato bancário da conta secundária-------");
    System.out.println(contaSecundaria.getExtratoBancario());

    contaPrimaria.requisitarSaque(new BigDecimal(100000));

    contaPrimaria.requisitarTransferencia(contaSecundaria, new BigDecimal(1000000));
    contaPrimaria.requisitarTransferencia(contaSecundaria, new BigDecimal( -5));
    Conta cfake = new Conta(123123);
    contaPrimaria.requisitarTransferencia(cfake, new BigDecimal(10));

    contaPrimaria.requisitarPix("chave2", new BigDecimal(-1));
    contaPrimaria.requisitarPix("chave2", new BigDecimal(100000));
    contaPrimaria.requisitarPix("chave323232", new BigDecimal(10));

    banco.executarTransacoes();
    System.out.println("-----------Extrato bancario da conta primaria---------");
    System.out.println(contaPrimaria.getExtratoBancario());
    System.out.println("-----------Extrato bancário da conta secundária-------");
    System.out.println(contaSecundaria.getExtratoBancario());
  }
}
