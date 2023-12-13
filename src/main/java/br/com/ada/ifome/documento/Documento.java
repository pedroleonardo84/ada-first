package br.com.ada.ifome.documento;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Documento {
    @Id
    @GeneratedValue
    private Long id;
    private String estado;
    private Long numero;
    private String categoria;
    private Date dataVencimento;
    private Date dataEmissao;

}
