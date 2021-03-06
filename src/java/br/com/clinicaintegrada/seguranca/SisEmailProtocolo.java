package br.com.clinicaintegrada.seguranca;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "sis_email_protocolo")
@NamedQueries({
    @NamedQuery(name = "SisEmailProtocolo.findName", query = "SELECT SEP FROM SisEmailProtocolo SEP WHERE UPPER(SEP.descricao) LIKE :pdescricao ORDER BY SEP.descricao ASC ")
})
public class SisEmailProtocolo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ds_descricao", length = 50, nullable = false, unique = true)
    private String descricao;

    public SisEmailProtocolo() {
        this.id = -1;
        this.descricao = "";
    }

    public SisEmailProtocolo(Integer id, String descricao) {
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
