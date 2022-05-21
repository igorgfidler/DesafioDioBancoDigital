import banco.Agencia;
import banco.Banco;
import conta.Conta;
import conta.ContaCorrente;

import java.math.BigDecimal;
import java.util.BitSet;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
  public static void main(final String[] args) {
    LogManager logm = LogManager.getLogManager();
    Logger logger = logm.getLogger(Logger.GLOBAL_LOGGER_NAME);

    Banco banco = Banco.getInstance();

    Agencia agencia = banco.criarNovaAgencia();
    Agencia agencia2 = banco.criarNovaAgencia();

    Conta contaPrimaria = agencia.criarContaCorrente("John", new BigDecimal(1000));
    banco.inserirChavePix("chave", contaPrimaria);
    Conta contaSecundaria = agencia.criarContaCorrente("Jane");
    banco.inserirChavePix("chave2", contaSecundaria);

    Conta contaPoupanca = agencia2.criarContaPoupanca("Foo");
    Conta contaSalario = agencia2.criarContaSalario("Bar");

    contaPrimaria.requisitarDeposito(new BigDecimal(1000));
    contaPrimaria.requisitarSaque(new BigDecimal(500));

    contaPrimaria.requisitarTransferencia(contaSecundaria, new BigDecimal(100));
    contaPrimaria.requisitarPix("chave2", new BigDecimal(100));
    banco.executarTransacoes();

    System.out.println("-----------Extrato bancario da conta primaria---------");
    System.out.println(contaPrimaria.getExtratoBancario());
    System.out.println("-----------Extrato bancário da conta secundária-------");
    System.out.println(contaSecundaria.getExtratoBancario());

    System.out.println("-----Todas possibilidades de erros entre contas correntes-------");

    contaPrimaria.requisitarSaque(new BigDecimal(100000));
    contaPrimaria.requisitarTransferencia(contaSecundaria, new BigDecimal(1000000));
    contaPrimaria.requisitarTransferencia(contaSecundaria, new BigDecimal( -5));
    Conta cfake = new ContaCorrente("akjdjkfasdf" ,123123);
    contaPrimaria.requisitarTransferencia(cfake, new BigDecimal(10));

    contaPrimaria.requisitarPix("chave2", new BigDecimal(-1));
    contaPrimaria.requisitarPix("chave2", new BigDecimal(100000));
    contaPrimaria.requisitarPix("chave323232", new BigDecimal(10));

    banco.executarTransacoes();

    System.out.println("\n-----Interações com a conta poupança------");
    contaPrimaria.requisitarDeposito(new BigDecimal(10000));
    contaPrimaria.requisitarTransferencia(contaPoupanca, new BigDecimal(1000));
    contaPoupanca.requisitarSaque(new BigDecimal(100));
    contaPoupanca.requisitarDeposito(new BigDecimal(100));
    contaPoupanca.requisitarTransferencia(contaPrimaria, new BigDecimal(50));

    banco.executarTransacoes();

    System.out.println(contaPoupanca.getExtratoBancario());

    System.out.println("\n-----------Falhas de uso em conta poupança--------");
    contaPoupanca.requisitarPix("chave", new BigDecimal(454));
    banco.inserirChavePix("deve falhar", contaPoupanca);


    System.out.println("\n---------Interações conta salário-------");
    contaPrimaria.requisitarTransferencia(contaSalario, new BigDecimal(1000));
    contaSalario.requisitarSaque(new BigDecimal(100));

    banco.executarTransacoes();
    System.out.println(contaSalario);

    System.out.println("\n-------Falhas de operação em conta salário");
    banco.inserirChavePix("deve falhar", contaSalario);
    contaSalario.requisitarDeposito(new BigDecimal(100));


    System.out.println("\n------Executar algumas operações com Conta poupança e salário-------");
    contaPoupanca.requisitarTransferencia(contaSalario, new BigDecimal(50));
    contaSalario.requisitarTransferencia(contaPoupanca, new BigDecimal(50));
    banco.executarTransacoes();

    System.out.println("\n------Mostrar todos os extrados das contas exemplo--------");
    System.out.println("Conta primaria:");
    System.out.printf(contaPrimaria.getExtratoBancario());
    System.out.println("\nConta secundaria");
    System.out.println(contaSecundaria.getExtratoBancario());
    System.out.println("\nConta poupanca");
    System.out.println(contaPoupanca.getExtratoBancario());
    System.out.println("\nConta Salário");
    System.out.println(contaSalario.getExtratoBancario());
  }
}
