package br.com.ada.ifome.documento;

import br.com.ada.ifome.exceptions.CnhInvalidoException;
import br.com.ada.ifome.exceptions.CnhVencidaException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DocumentoServiceTest {
    private DocumentoService documentoService = new DocumentoService();

    @Test
    public void testValidaDocumentoCnhValida() throws CnhInvalidoException, CnhVencidaException {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, 1);
        Date validDate = calendar.getTime();

        //Documento documentoValido = new Documento();
        var documentoValido = new Documento();
        documentoValido.setId(1L);
        documentoValido.setEstado("SP");
        documentoValido.setNumero(12345678901L);
        documentoValido.setCategoria("ABCD");
        documentoValido.setDataVencimento(validDate);
        documentoValido.setDataEmissao(validDate);
        documentoService.validaDocumento(documentoValido); // Não deve lançar exceção
    }

    @Test
    public void testValidaDocumentoCnhInvalida() {
        Date today = new Date();
        var documentoInvalido = new Documento();
        documentoInvalido.setId(1L);
        documentoInvalido.setEstado("SP");
        documentoInvalido.setNumero(12345678901L);
        documentoInvalido.setCategoria("ABCD");
        documentoInvalido.setDataVencimento(today);
        documentoInvalido.setDataEmissao(today);

        assertThrows(CnhInvalidoException.class, () -> documentoService.validaDocumento(documentoInvalido));
    }

    @Test
    public void testValidaDocumentoCnhVencida() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date expiredDate = calendar.getTime();

        //Documento documentoVencido = new Documento(1L,"sao paulo",1L, "12345678901", expiredDate, expiredDate);
        var documentoVencido = new Documento();
        documentoVencido.setId(1L);
        documentoVencido.setEstado("SP");
        documentoVencido.setNumero(12345678901L);
        documentoVencido.setCategoria("ABCD");
        documentoVencido.setDataVencimento(expiredDate);
        documentoVencido.setDataEmissao(expiredDate);
        assertThrows(CnhVencidaException.class, () -> documentoService.validaDocumento(documentoVencido));
    }

    @Test
    public void testValidaDocumentoValidade() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date validDate = calendar.getTime();

        var documentoValido = new Documento();
        documentoValido.setId(1L);
        documentoValido.setEstado("SP");
        documentoValido.setNumero(12345678901L);
        documentoValido.setCategoria("ABCD");
        documentoValido.setDataVencimento(validDate);
        documentoValido.setDataEmissao(validDate);
        assertTrue(documentoService.validaDocumentoValidade(documentoValido));

        calendar.add(Calendar.DATE, -2);
        Date expiredDate = calendar.getTime();

        var documentoVencido = new Documento();
        documentoVencido.setId(1L);
        documentoVencido.setEstado("SP");
        documentoVencido.setNumero(12345678901L);
        documentoVencido.setCategoria("ABCD");
        documentoVencido.setDataVencimento(expiredDate);
        documentoVencido.setDataEmissao(expiredDate);
        assertFalse(documentoService.validaDocumentoValidade(documentoVencido));
    }
}