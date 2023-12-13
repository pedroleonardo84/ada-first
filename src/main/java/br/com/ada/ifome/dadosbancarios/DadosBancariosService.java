package br.com.ada.ifome.dadosbancarios;

import br.com.ada.ifome.exceptions.ContaBancariaInvalidaException;
import br.com.ada.ifome.exceptions.InstituicaoBancariaInvalidaException;
import br.com.ada.ifome.exceptions.TipoContaInvalidoException;

public class DadosBancariosService {

    public void validarContaBancaria(DadosBancarios dadosBancarios) {
        if (!validarNumeroAgencia(dadosBancarios.getNumeroAgencia()) ||
                !validarNumeroConta(dadosBancarios.getNumeroConta())) {
            throw new ContaBancariaInvalidaException();
        }
    }

    public void validarTipoConta(DadosBancarios dadosBancarios) {
        if (dadosBancarios.getTipoConta() == null ||
                (!TipoContaEnum.CORRENTE.equals(dadosBancarios.getTipoConta()) &&
                        !TipoContaEnum.POUPANCA.equals(dadosBancarios.getTipoConta()))) {
            throw new TipoContaInvalidoException();
        }
    }

    public void validarInstituicaoBancaria(DadosBancarios dadosBancarios) {
        if (dadosBancarios.getInstituicaoBancaria() == null ||
                !instituicaoBancariaValida(dadosBancarios.getInstituicaoBancaria())) {
            throw new InstituicaoBancariaInvalidaException();
        }
    }

    boolean validarNumeroAgencia(String numeroAgencia) {
        if (numeroAgencia == null || numeroAgencia.isEmpty() || !numeroAgencia.matches("\\d+")) {
            return false;
        }
        return true;
    }

    boolean validarNumeroConta(String numeroConta) {
        if (numeroConta == null || numeroConta.isEmpty() || !numeroConta.matches("\\d+")) {
            return false;
        }
        return true;
    }

    boolean instituicaoBancariaValida(String instituicaoBancaria) {
        if (instituicaoBancaria == null || instituicaoBancaria.isEmpty() || !instituicaoBancaria.matches("\\d+")) {
            return false;
        }
        return true;
    }
}
