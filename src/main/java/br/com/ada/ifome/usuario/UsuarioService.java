package br.com.ada.ifome.usuario;

import br.com.ada.ifome.commonsvalidation.Validator;
import br.com.ada.ifome.exceptions.CpfInvalidoException;
import br.com.ada.ifome.exceptions.UsuarioInvalidoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private Validator validator = new Validator();

    public Usuario salvar(Usuario usuario) {
        if (usuario == null) {
            throw new UsuarioInvalidoException();
        }
        var isCpfValido = validator.validaCpf(usuario.getCpf());
        if (!isCpfValido) {
            throw new CpfInvalidoException();
        }
        return usuarioRepository.save(usuario); // Mockar o usuário repository...
    }
}
