package br.com.ada.ifome.veiculo;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Veiculo {

    @Id
    @GeneratedValue
    private Long id;
    private TipoVeiculoEnum tipoVeiculo;
    private String marca;
    private String placa;
    private String cor;
    private Long renavam;
    private boolean ativo;

}
