package br.com.ada.ifome.entregador;


import br.com.ada.ifome.dadosbancarios.DadosBancarios;
import br.com.ada.ifome.dadosbancarios.TipoContaEnum;
import br.com.ada.ifome.documento.Documento;
import br.com.ada.ifome.exceptions.*;
import br.com.ada.ifome.veiculo.Veiculo;
import br.com.ada.ifome.veiculo.VeiculoRepository;
import br.com.ada.ifome.veiculo.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Optional;

import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EntregadorTest {

    @Mock
    private EntregadorRepository entregadorRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private EntregadorService entregadorService;

    @InjectMocks
    private VeiculoService veiculoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(entregadorService, "veiculoService", veiculoService);
        ReflectionTestUtils.setField(veiculoService, "veiculoRepository", veiculoRepository);
    }

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
    public void entregadorNulo() {
        assertThrows(UsuarioInvalidoException.class, () -> entregadorService.salvar(null));
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
        var entregador = new Entregador();
        entregador.setCpf("04455566633");
        entregador.setRg("4447487");
        entregador.setDocumento(getDocumento());

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2010);
        veiculo.setPlaca("ABC1D23");
        veiculo.setRenavam(12345678901L);

        entregador.setVeiculo(veiculo);

        var dadosBancarios = new DadosBancarios();
        dadosBancarios.setNumeroAgencia("1");
        dadosBancarios.setNumeroConta("1");
        dadosBancarios.setTipoConta(TipoContaEnum.POUPANCA);
        dadosBancarios.setInstituicaoBancaria("1");

        entregador.setDadosBancarios(dadosBancarios);

        when(veiculoRepository.findByRenavam(anyLong())).thenReturn(Optional.empty());
        when(entregadorRepository.save(any())).thenReturn(entregador);

        assertNotNull(entregadorService.salvar(entregador));
        // Validar se foi chamado o save do repository
        verify(entregadorRepository, times(1)).save(entregador);
    }

    @Test
    public void entregadorComVeiculoInvalidoPorAnoModeloInvalido() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("12345678910");
        entregador.setRg("1234567");

        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099");
        Date dataEmiss = formato.parse("31/12/2010");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        entregador.setDocumento(documento);

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2005);
        veiculo.setPlaca("ABC1234");
        veiculo.setRenavam(12345678901L);

        entregador.setVeiculo(veiculo);

        assertThrows(VeiculoInvalidoException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorComVeiculoInvalidoPorPlacaInvalida() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("12345678910");
        entregador.setRg("1234567");

        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099");
        Date dataEmiss = formato.parse("31/12/2010");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        entregador.setDocumento(documento);

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2010);
        veiculo.setPlaca("ABC123E");
        veiculo.setRenavam(12345678901L);

        entregador.setVeiculo(veiculo);

        assertThrows(VeiculoInvalidoException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorComVeiculoInvalidoPorRenavamNulo() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("12345678910");
        entregador.setRg("1234567");

        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099");
        Date dataEmiss = formato.parse("31/12/2010");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        entregador.setDocumento(documento);

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2010);
        veiculo.setPlaca("ABC1D23");
        veiculo.setRenavam(null);

        entregador.setVeiculo(veiculo);

        assertThrows(VeiculoInvalidoException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorComVeiculoInvalidoPorRenavamInvalido() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("12345678910");
        entregador.setRg("1234567");

        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099");
        Date dataEmiss = formato.parse("31/12/2010");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        entregador.setDocumento(documento);

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2010);
        veiculo.setPlaca("ABC1D23");
        veiculo.setRenavam(90000000000L);

        entregador.setVeiculo(veiculo);

        // Configurar comportamento do mock
        when(veiculoRepository.findByRenavam(anyLong())).thenReturn(Optional.empty());

        // Executar o teste
        assertThrows(VeiculoInvalidoException.class, () -> entregadorService.salvar(entregador));

        // Verificar se o método foi chamado
        verify(veiculoRepository, times(1)).findByRenavam(anyLong());
    }

    @Test
    public void entregadorComDadosBancariosInvalidosPorNumeroContaInvalido() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("12345678910");
        entregador.setRg("1234567");

        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099");
        Date dataEmiss = formato.parse("31/12/2010");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        entregador.setDocumento(documento);

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2010);
        veiculo.setPlaca("ABC1D23");
        veiculo.setRenavam(12345678901L);

        entregador.setVeiculo(veiculo);

        var dadosBancarios = new DadosBancarios();
        dadosBancarios.setNumeroConta("A");

        entregador.setDadosBancarios(dadosBancarios);

        when(veiculoRepository.findByRenavam(anyLong())).thenReturn(Optional.empty());

        assertThrows(ContaBancariaInvalidaException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorComDadosBancariosInvalidosPorNumeroContaNulo() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("12345678910");
        entregador.setRg("1234567");

        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099");
        Date dataEmiss = formato.parse("31/12/2010");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        entregador.setDocumento(documento);

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2010);
        veiculo.setPlaca("ABC1D23");
        veiculo.setRenavam(12345678901L);

        entregador.setVeiculo(veiculo);

        var dadosBancarios = new DadosBancarios();
        dadosBancarios.setNumeroConta(null);

        entregador.setDadosBancarios(dadosBancarios);

        when(veiculoRepository.findByRenavam(anyLong())).thenReturn(Optional.empty());

        assertThrows(ContaBancariaInvalidaException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorComDadosBancariosInvalidosPorNumeroAgenciaInvalido() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("12345678910");
        entregador.setRg("1234567");

        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099");
        Date dataEmiss = formato.parse("31/12/2010");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        entregador.setDocumento(documento);

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2010);
        veiculo.setPlaca("ABC1D23");
        veiculo.setRenavam(12345678901L);

        entregador.setVeiculo(veiculo);

        var dadosBancarios = new DadosBancarios();
        dadosBancarios.setNumeroConta("1");
        dadosBancarios.setNumeroAgencia("A");

        entregador.setDadosBancarios(dadosBancarios);

        when(veiculoRepository.findByRenavam(anyLong())).thenReturn(Optional.empty());

        assertThrows(ContaBancariaInvalidaException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorComDadosBancariosInvalidosPorTipoContaInvalido() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("12345678910");
        entregador.setRg("1234567");

        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099");
        Date dataEmiss = formato.parse("31/12/2010");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        entregador.setDocumento(documento);

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2010);
        veiculo.setPlaca("ABC1D23");
        veiculo.setRenavam(12345678901L);

        entregador.setVeiculo(veiculo);

        var dadosBancarios = new DadosBancarios();
        dadosBancarios.setNumeroAgencia("1");
        dadosBancarios.setNumeroConta("1");
        dadosBancarios.setTipoConta(null);

        entregador.setDadosBancarios(dadosBancarios);

        when(veiculoRepository.findByRenavam(anyLong())).thenReturn(Optional.empty());

        assertThrows(TipoContaInvalidoException.class, () -> entregadorService.salvar(entregador));
    }

    @Test
    public void entregadorComDadosBancariosInvalidosPorInstituicaoBancariaInvalida() throws ParseException {
        var entregador = new Entregador();
        entregador.setCpf("12345678910");
        entregador.setRg("1234567");

        var documento = new Documento();
        documento.setId(1L);
        documento.setEstado("SP");
        documento.setNumero(12345678901L);
        documento.setCategoria("ABCD");

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVcto = formato.parse("31/12/2099");
        Date dataEmiss = formato.parse("31/12/2010");

        documento.setDataVencimento(dataVcto);
        documento.setDataEmissao(dataEmiss);

        entregador.setDocumento(documento);

        var veiculo = new Veiculo();
        veiculo.setAnoModelo(2010);
        veiculo.setPlaca("ABC1D23");
        veiculo.setRenavam(12345678901L);

        entregador.setVeiculo(veiculo);

        var dadosBancarios = new DadosBancarios();
        dadosBancarios.setNumeroAgencia("1");
        dadosBancarios.setNumeroConta("1");
        dadosBancarios.setTipoConta(TipoContaEnum.CORRENTE);
        dadosBancarios.setInstituicaoBancaria("A");

        entregador.setDadosBancarios(dadosBancarios);

        when(veiculoRepository.findByRenavam(anyLong())).thenReturn(Optional.empty());

        assertThrows(InstituicaoBancariaInvalidaException.class, () -> entregadorService.salvar(entregador));
    }
}
