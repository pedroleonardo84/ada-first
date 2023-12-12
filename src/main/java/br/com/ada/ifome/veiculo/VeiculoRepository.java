package br.com.ada.ifome.veiculo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    boolean existsByRenavam(Long renavam);
}
