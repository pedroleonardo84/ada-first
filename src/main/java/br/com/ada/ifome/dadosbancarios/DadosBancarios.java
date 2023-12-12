package br.com.ada.ifome.dadosbancarios;

import br.com.ada.ifome.chavepix.ChavePix;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class DadosBancarios {
    @Id
    @GeneratedValue
    private Long id;
    private String numeroConta;
    private int digitoConta;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chave_id", referencedColumnName = "id")
    private ChavePix chavePix;
    private TipoContaEnum tipoConta;
    private String instituicaoBancaria;
    private String numeroAgencia;
}
