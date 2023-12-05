package br.com.ada.ifome.entregador;

import br.com.ada.ifome.commonsvalidation.Validator;
import br.com.ada.ifome.documento.DocumentoService;
import br.com.ada.ifome.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntregadorService {

    private final EntregadorRepository entregadorRepository;

    private Validator validator = new Validator();

    private final DocumentoService documentoService = new DocumentoService();

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

        return entregadorRepository.save(entregador); // Mockar o usuário repository...

    }
}
