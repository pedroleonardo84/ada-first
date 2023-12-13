package br.com.ada.ifome.entregador;

import br.com.ada.ifome.dadosbancarios.DadosBancarios;
import br.com.ada.ifome.documento.Documento;
import br.com.ada.ifome.endereco.Endereco;
import br.com.ada.ifome.veiculo.Veiculo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Entregador {
    @Id
    private Long id;
    private String nome;
    @Column(unique = true, nullable = false)
    private String cpf;
    private String email;
    private String rg;
    private String tamanhoCamisa;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dadosBancarios_id", referencedColumnName = "id")
    private DadosBancarios dadosBancarios;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documento_id", referencedColumnName = "id")
    private Documento documento;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "veiculo_id", referencedColumnName = "id")
    private Veiculo veiculo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

}
