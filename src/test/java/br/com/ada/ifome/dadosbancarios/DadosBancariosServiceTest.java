package br.com.ada.ifome.dadosbancarios;

import br.com.ada.ifome.exceptions.ContaBancariaInvalidaException;
import br.com.ada.ifome.exceptions.InstituicaoBancariaInvalidaException;
import br.com.ada.ifome.exceptions.TipoContaInvalidoException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DadosBancariosServiceTest {
    private DadosBancariosService dadosBancariosService = new DadosBancariosService();

//    @Test
//   public void testValidarContaBancaria() {
//        DadosBancarios dadosValidos = new DadosBancarios("123", "456");
//        DadosBancarios dadosInvalidos = new DadosBancarios(null, "abc");

        // Dados válidos
//        dadosBancariosService.validarContaBancaria(dadosValidos);

        // Dados inválidos
//        assertThrows(ContaBancariaInvalidaException.class, () -> dadosBancariosService.validarContaBancaria(dadosInvalidos));
//    }

    @Test
    public void testValidarTipoConta() {
        DadosBancarios dadosValidos = new DadosBancarios();
        dadosValidos.setTipoConta(TipoContaEnum.CORRENTE);

        DadosBancarios dadosInvalidos = new DadosBancarios();

        // Dados válidos
        dadosBancariosService.validarTipoConta(dadosValidos);

        // Dados inválidos
        assertThrows(TipoContaInvalidoException.class, () -> dadosBancariosService.validarTipoConta(dadosInvalidos));
    }

    @Test
    public void testValidarInstituicaoBancaria() {
        DadosBancarios dadosValidos = new DadosBancarios();
        dadosValidos.setInstituicaoBancaria("12345");

        DadosBancarios dadosInvalidos = new DadosBancarios();

        // Dados válidos
        dadosBancariosService.validarInstituicaoBancaria(dadosValidos);

        // Dados inválidos
        assertThrows(InstituicaoBancariaInvalidaException.class, () -> dadosBancariosService.validarInstituicaoBancaria(dadosInvalidos));
    }

    @Test
    public void testValidarNumeroAgencia() {
        assertTrue(dadosBancariosService.validarNumeroAgencia("123"));
        assertFalse(dadosBancariosService.validarNumeroAgencia(null));
        assertFalse(dadosBancariosService.validarNumeroAgencia(""));
        assertFalse(dadosBancariosService.validarNumeroAgencia("abc"));
    }

    @Test
    public void testValidarNumeroConta() {
        assertTrue(dadosBancariosService.validarNumeroConta("456"));
        assertFalse(dadosBancariosService.validarNumeroConta(null));
        assertFalse(dadosBancariosService.validarNumeroConta(""));
        assertFalse(dadosBancariosService.validarNumeroConta("abc"));
    }

    @Test
    public void testInstituicaoBancariaValida() {
        assertTrue(dadosBancariosService.instituicaoBancariaValida("12345"));
        assertFalse(dadosBancariosService.instituicaoBancariaValida(null));
        assertFalse(dadosBancariosService.instituicaoBancariaValida(""));
        assertFalse(dadosBancariosService.instituicaoBancariaValida("abc"));
    }
}