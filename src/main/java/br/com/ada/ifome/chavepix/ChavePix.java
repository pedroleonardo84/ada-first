package br.com.ada.ifome.chavepix;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class ChavePix {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String valor;
    private TipoChaveEnum tipoChave;
}
