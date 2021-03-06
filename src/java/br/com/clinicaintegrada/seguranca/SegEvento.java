package br.com.clinicaintegrada.seguranca;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "seg_evento")
@NamedQueries({
    @NamedQuery(name = "SegEvento.findAll", query = "SELECT EVE FROM SegEvento AS EVE ORDER BY EVE.descricao ASC "),
    @NamedQuery(name = "SegEvento.findName", query = "SELECT EVE FROM SegEvento AS EVE WHERE UPPER(EVE.descricao) LIKE :pdescricao ORDER BY EVE.descricao ASC ")
})
public class SegEvento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ds_descricao", length = 50, nullable = false, unique = true)
    private String descricao;

    public SegEvento() {
        this.id = -1;
        this.descricao = "";
    }

    public SegEvento(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
