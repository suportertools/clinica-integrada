package br.com.clinicaintegrada.seguranca;

import br.com.clinicaintegrada.pessoa.Filial;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "seg_filial_rotina",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_filial", "id_rotina"})
)
public class FilialRotina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_filial", referencedColumnName = "id", nullable = false)
    @OneToOne
    private Filial filial;
    @JoinColumn(name = "id_rotina", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Rotina rotina;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Cliente cliente;

    public FilialRotina() {
        this.id = null;
        this.filial = null;
        this.rotina = null;
        this.cliente = null;
    }

    public FilialRotina(Integer id, Filial filial, Rotina rotina, Cliente cliente) {
        this.id = id;
        this.filial = filial;
        this.rotina = rotina;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public Rotina getRotina() {
        return rotina;
    }

    public void setRotina(Rotina rotina) {
        this.rotina = rotina;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FilialRotina other = (FilialRotina) obj;
        return true;
    }

    @Override
    public String toString() {
        return "FilialRotina{" + "id=" + id + ", filial=" + filial + ", rotina=" + rotina + ", cliente=" + cliente + '}';
    }

}
