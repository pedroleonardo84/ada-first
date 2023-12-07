package br.com.ada.ifome.entregador;


import br.com.ada.ifome.commonsvalidation.Validator;
import br.com.ada.ifome.documento.Documento;
import br.com.ada.ifome.documento.DocumentoService;
import br.com.ada.ifome.exceptions.CnhInvalidoException;
import br.com.ada.ifome.exceptions.CnhVencidaException;
import br.com.ada.ifome.exceptions.CpfInvalidoException;
import br.com.ada.ifome.exceptions.RgInvalidoException;
import org.apache.tomcat.jni.Time;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EntregadorTest {

    @Mock
    private EntregadorRepository entregadorRepository;

    @InjectMocks
    private EntregadorService entregadorService;


    public Documento getDocumento() throws ParseException {
        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099)");
        Date dataEmiss = formato.parse("31/12/2010)");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        return documento;
    }

    @Test
    public void entregadorCpfInvalidoComLetra() {
        var entregador = new Entregador();
        entregador.setCpf("1234567891e");
        //when(entregadorRepository.save(any())).thenReturn(entregador);
        assertThrows(CpfInvalidoException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorCpfInvalidoCom12Digitos() {
        var entregador = new Entregador();
        entregador.setCpf("123456789012");
        assertThrows(CpfInvalidoException.class, () -> entregadorService.salvar(entregador));

    }

    @Test
    public void entregadorCnhInvalidoCom12Digitos() {
        var entregador = new Entregador();
        var documento = new Documento();
        documento.setNumero(123456789012L);
        entregador.setCpf("04455566633");
        entregador.setRg("3281139");
        entregador.setDocumento(documento);
        assertThrows(CnhInvalidoException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorCnhInvalidoComDataVencida() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("04455566633");
        entregador.setRg("3281139");
        var documento = new Documento();
        documento.setNumero(12345678901L);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formato.parse("01/01/2023");
        documento.setDataVencimento(data);
        entregador.setDocumento(documento);
        assertThrows(CnhVencidaException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorRgInvalidoCom8Digitos() {
        var entregador = new Entregador();
        entregador.setCpf("04455566633");
        entregador.setCpf("04455566633");
        entregador.setRg("32811399");
        assertThrows(RgInvalidoException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorComInformacoesCorretas() throws ParseException {
        // Mockar ação de save
        var entregador = new Entregador();
        entregador.setCpf("04455566633");
        entregador.setRg("4447487");
        entregador.setDocumento(getDocumento());
        when(entregadorRepository.save(any())).thenReturn(entregador);
        var entregadorSalvo = entregadorService.salvar(entregador);

        assertNotNull(entregadorSalvo);
        // Validar se foi chamado o save do repository
        verify(entregadorRepository, Mockito.times(1)).save(entregador);
    }
}
