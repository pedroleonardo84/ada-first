package br.com.ada.ifome.chavepix;

import br.com.ada.ifome.dadosbancarios.DadosBancarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChavePixRepository extends JpaRepository<DadosBancarios, Long> {
}
