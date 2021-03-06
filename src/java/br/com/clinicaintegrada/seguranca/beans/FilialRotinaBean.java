package br.com.clinicaintegrada.seguranca.beans;

import br.com.clinicaintegrada.logSistema.Logger;
import br.com.clinicaintegrada.pessoa.Filial;
import br.com.clinicaintegrada.pessoa.dao.FilialDao;
import br.com.clinicaintegrada.seguranca.FilialRotina;
import br.com.clinicaintegrada.seguranca.Rotina;
import br.com.clinicaintegrada.seguranca.controleUsuario.SessaoCliente;
import br.com.clinicaintegrada.seguranca.dao.FilialRotinaDao;
import br.com.clinicaintegrada.utils.Dao;
import br.com.clinicaintegrada.utils.Messages;
import br.com.clinicaintegrada.utils.Sessions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class FilialRotinaBean implements Serializable {

    private FilialRotina filialRotina;
    private Integer filial_id;
    private Integer rotina_id;
    private List<FilialRotina> listFilialRotina;
    private List<SelectItem> listFiliais;
    private List<SelectItem> listRotinas;

    @PostConstruct
    public void init() {
        filialRotina = new FilialRotina();
        filial_id = 0;
        rotina_id = 0;
        listFilialRotina = new ArrayList<>();
        listFiliais = new ArrayList<>();
        listRotinas = new ArrayList<>();
    }

    @PreDestroy
    public void destroy() {
        clear();
    }

    public void clear() {
        Sessions.remove("filialRotinaBean");
    }

    public void save() {
        Dao dao = new Dao();
        Logger novoLog = new Logger();
        filialRotina.setFilial((Filial) dao.find(new Filial(), Integer.parseInt(getListFiliais().get(filial_id).getDescription())));
        filialRotina.setRotina((Rotina) dao.find(new Rotina(), Integer.parseInt(getListRotinas().get(rotina_id).getDescription())));
        dao.openTransaction();
        if (filialRotina.getId() == -1) {
            filialRotina.setCliente(SessaoCliente.get());
            if (new FilialRotinaDao().exist(filialRotina.getFilial().getId(), filialRotina.getRotina().getId())) {
                Messages.warn("Validação", "Registro já cadastrado!");
                return;
            }
            if (dao.save(filialRotina)) {
                String saveString
                        = "ID: " + filialRotina.getId()
                        + " - Filial: (" + filialRotina.getFilial().getId() + ") " + filialRotina.getFilial().getFilial().getPessoa().getNome()
                        + " - Departamento: (" + filialRotina.getRotina().getId() + ") " + filialRotina.getRotina().getPagina();
                novoLog.save(saveString);
                dao.commit();
                Messages.info("Sucesso", "Registro inserido");
                //novoLog.setTabela("seg_filial_rotina");
                //novoLog.setCodigo(filialRotina.getId());
                filialRotina = new FilialRotina();
                listFilialRotina.clear();
            } else {
                dao.rollback();
                Messages.warn("Erro", "Ao inserir esse registro!");
            }
        } else {
            FilialRotina fr = (FilialRotina) new Dao().find(filialRotina);
            String saveString
                    = "ID: " + filialRotina.getId()
                    + " - Filial: (" + filialRotina.getFilial().getId() + ") " + filialRotina.getFilial().getFilial().getPessoa().getNome()
                    + " - Departamento: (" + filialRotina.getRotina().getId() + ") " + filialRotina.getRotina().getPagina();
            String beforeUpdate
                    = "ID: " + fr.getId()
                    + " - Filial: (" + fr.getFilial().getId() + ") " + fr.getFilial().getFilial().getPessoa().getNome()
                    + " - Departamento: (" + fr.getRotina().getId() + ") " + fr.getRotina().getPagina();

            if (dao.update(filialRotina)) {
                Messages.info("Sucesso", "Registro atualizado");
                //novoLog.setTabela("seg_filial_rotina");
                //novoLog.setCodigo(filialRotina.getId());
                novoLog.update(beforeUpdate, saveString);
                filialRotina = new FilialRotina();
                dao.commit();
                Messages.info("Atualizado", "Computador atualizado com sucesso!");
                filialRotina = new FilialRotina();
                listFilialRotina.clear();
            } else {
                dao.rollback();
                Messages.warn("Erro", "Ao inserir esse registro!");
            }

        }
    }

    public void edit(FilialRotina fr) {
        filialRotina = fr;
        for (int i = 0; i < listRotinas.size(); i++) {
            if (Objects.equals(Integer.valueOf(listRotinas.get(i).getDescription()), filialRotina.getRotina().getId())) {
                rotina_id = i;
                break;
            }
        }
        for (int i = 0; i < listFiliais.size(); i++) {
            if (Objects.equals(Integer.valueOf(listFiliais.get(i).getDescription()), filialRotina.getFilial().getId())) {
                filial_id = i;
                break;
            }
        }
    }

    public void delete(FilialRotina fr) {
        filialRotina = fr;
        Dao dao = new Dao();
        Logger novoLog = new Logger();
        dao.openTransaction();
        if (dao.delete(filialRotina)) {
            novoLog.delete(
                    "ID: " + filialRotina.getId()
                    + " - Filial: (" + filialRotina.getFilial().getId() + ") " + filialRotina.getFilial().getFilial().getPessoa().getNome()
                    + " - Rotina: (" + filialRotina.getRotina().getId() + ") " + filialRotina.getRotina().getRotina()
            );
            dao.commit();
            Messages.info("Sucesso", "Registro excluído com sucesso");
            listFilialRotina.clear();
        } else {
            dao.rollback();
            Messages.info("Sucesso", "Erro ao excluir computador!");

        }
        filialRotina = new FilialRotina();
    }

    public List<SelectItem> getListRotinas() {
        if (listRotinas.isEmpty()) {
            List<Rotina> list = new Dao().list(new Rotina(), true);
            for (int i = 0; i < list.size(); i++) {
                listRotinas.add(new SelectItem(i,
                        list.get(i).getRotina(),
                        Integer.toString(list.get(i).getId())));
            }
        }
        return listRotinas;
    }

    public List<SelectItem> getListFiliais() {
        if (listFiliais.isEmpty()) {
            FilialDao filialDao = new FilialDao();
            List<Filial> list = filialDao.pesquisaTodasCliente();
            for (int i = 0; i < list.size(); i++) {
                listFiliais.add(new SelectItem(i,
                        list.get(i).getFilial().getPessoa().getNome(),
                        Integer.toString(list.get(i).getId())));
            }
        }
        return listFiliais;
    }

    public FilialRotina getFilialRotina() {
        return filialRotina;
    }

    public void setFilialRotina(FilialRotina filialRotina) {
        this.filialRotina = filialRotina;
    }

    public Integer getFilial_id() {
        return filial_id;
    }

    public void setFilial_id(Integer filial_id) {
        this.filial_id = filial_id;
    }

    public Integer getRotina_id() {
        return rotina_id;
    }

    public void setRotina_id(Integer rotina_id) {
        this.rotina_id = rotina_id;
    }

    public List<FilialRotina> getListFilialRotina() {
        if (listFilialRotina.isEmpty()) {
            Dao dao = new Dao();
            listFilialRotina = dao.list(new FilialRotina(), true);
        }
        return listFilialRotina;
    }

    public void setListFilialRotina(List<FilialRotina> listFilialRotina) {
        this.listFilialRotina = listFilialRotina;
    }

    public void setListFiliais(List<SelectItem> listFiliais) {
        this.listFiliais = listFiliais;
    }

    public void setListRotinas(List<SelectItem> listRotinas) {
        this.listRotinas = listRotinas;
    }

}
