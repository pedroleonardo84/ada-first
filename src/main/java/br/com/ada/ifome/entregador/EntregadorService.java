package br.com.ada.ifome.entregador;

import br.com.ada.ifome.commonsvalidation.Validator;
import br.com.ada.ifome.dadosbancarios.DadosBancariosService;
import br.com.ada.ifome.documento.DocumentoService;
import br.com.ada.ifome.exceptions.*;
import br.com.ada.ifome.veiculo.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntregadorService {

    private final EntregadorRepository entregadorRepository;

    private Validator validator = new Validator();

    private final DocumentoService documentoService = new DocumentoService();

    private final VeiculoService veiculoService = new VeiculoService();

    private final DadosBancariosService dadosBancariosService = new DadosBancariosService();

    public Entregador salvar (Entregador entregador) {
        if (entregador == null) {
            throw new UsuarioInvalidoException();
        }
        var isCpfValido = validator.validaCpf(entregador.getCpf());
        if (!isCpfValido) {
            throw new CpfInvalidoException();
        }

        var isRGValido = validator.validaRG(entregador.getRg());
        if (!isRGValido) {
            throw new RgInvalidoException();
        }

        System.out.println(entregador.getDocumento());

        try {
            documentoService.validaDocumento(entregador.getDocumento());
        } catch (CnhInvalidoException e) {
            throw new CnhInvalidoException();
        } catch (CnhVencidaException e1){
            throw new CnhVencidaException();
        }

        if (entregador.getVeiculo() != null) {
            if (!veiculoService.validaDataModeloVeiculo(entregador.getVeiculo().getAnoModelo())
                    || !veiculoService.validaPlacaVeiculo(entregador.getVeiculo().getPlaca())
                    || !veiculoService.validaRenavamVeiculo(entregador.getVeiculo().getRenavam())) {
                throw new VeiculoInvalidoException();
            }
        }

        if (entregador.getDadosBancarios() != null) {
            dadosBancariosService.validarContaBancaria(entregador.getDadosBancarios());
            dadosBancariosService.validarTipoConta(entregador.getDadosBancarios());
            dadosBancariosService.validarInstituicaoBancaria(entregador.getDadosBancarios());
        }

        return entregadorRepository.save(entregador); // Mockar o usuário repository...
    }
}
