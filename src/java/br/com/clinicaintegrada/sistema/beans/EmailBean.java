package br.com.clinicaintegrada.sistema.beans;

import br.com.clinicaintegrada.pessoa.Pessoa;
import br.com.clinicaintegrada.seguranca.Registro;
import br.com.clinicaintegrada.seguranca.Rotina;
import br.com.clinicaintegrada.seguranca.Usuario;
import br.com.clinicaintegrada.seguranca.controleUsuario.SessaoCliente;
import br.com.clinicaintegrada.sistema.Email;
import br.com.clinicaintegrada.sistema.EmailArquivo;
import br.com.clinicaintegrada.sistema.EmailPessoa;
import br.com.clinicaintegrada.sistema.EmailPrioridade;
import br.com.clinicaintegrada.sistema.dao.EmailDao;
import br.com.clinicaintegrada.utils.Dao;
import br.com.clinicaintegrada.utils.DaoInterface;
import br.com.clinicaintegrada.utils.DataHoje;
import br.com.clinicaintegrada.utils.Messages;
import br.com.clinicaintegrada.utils.Sessions;
import br.com.clinicaintegrada.utils.Mail;
import br.com.clinicaintegrada.utils.PF;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

@ManagedBean
@SessionScoped
@SuppressWarnings("unchecked")
public class EmailBean implements Serializable {

    private Registro registro;
    private Email email;
    private EmailArquivo emailArquivo;
    private EmailPessoa emailPessoa;
    private Email[] selectedEmail;
    /**
     * <ul>
     * <li>[0] Rotina</li>
     * <li>[1] Prioridade</li>
     * </ul>
     */
    private List[] listSelectItem;
    private Integer[] index;
    private List<Email> emails;
    private List<EmailPessoa> addEmailPessoas;
    private List<EmailPessoa> listEmailPessoas;
    private List<EmailPessoa> showEmailPessoas;
    private List<EmailArquivo> emailArquivos;
    private List<File> files;
    private Boolean openModal;
    private String html;
    private String assunto;
    private String emailString;
    private String urlRetorno;
    private Date[] date;
    /**
     * <ul>
     * <li>[0] Filtrar</li>
     * <li>[1] Nova Mensagem</li>
     * <li>[2] Por Rotina</li>
     * </ul>
     */
    private boolean filter;
    private boolean filterNewMessage;
    private boolean filterByRotina;
    private boolean filterRascunho;
    private String orderBy;
    private String filterBy;
    private String descricaoPesquisa;

    @PostConstruct
    public void init() {
        registro = new Registro();
        email = new Email();
        selectedEmail = null;
        listSelectItem = new ArrayList[]{
            new ArrayList<>(),
            new ArrayList<>()
        };
        emailPessoa = new EmailPessoa();
        index = new Integer[]{0, 0};
        emails = new ArrayList<>();
        addEmailPessoas = new ArrayList<>();
        listEmailPessoas = new ArrayList<>();
        showEmailPessoas = new ArrayList<>();
        emailArquivo = new EmailArquivo();
        emailArquivos = new ArrayList<>();
        files = new ArrayList<>();
        openModal = false;
        html = "";
        assunto = "";
        emailString = "";
        date = new Date[2];
        date[0] = null;
        date[1] = null;
        filter = false;
        filterNewMessage = false;
        filterByRotina = false;
        filterRascunho = false;
        orderBy = "";
        descricaoPesquisa = "";
        urlRetorno = "";
    }

    @PreDestroy
    public void destroy() {
        Sessions.remove("emailBean");
    }

    public void clear() {
        Sessions.remove("emailBean");
    }

    public void clear(int tcase) {
        if (tcase == 0) {
            emailPessoa = new EmailPessoa();
            index = new Integer[]{0, 0};
            emails = new ArrayList<>();
            addEmailPessoas = new ArrayList<>();
            listEmailPessoas = new ArrayList<>();
            showEmailPessoas = new ArrayList<>();
            emailArquivo = new EmailArquivo();
            emailArquivos = new ArrayList<>();
            files = new ArrayList<>();
            openModal = false;
            html = "";
            assunto = "";
            emailString = "";
            date = new Date[2];
            date[0] = null;
            date[1] = null;
            filter = false;
            filterNewMessage = false;
            filterByRotina = false;
            filterRascunho = false;
            descricaoPesquisa = "";
        } else if (tcase == 1) {
            date[0] = null;
            date[1] = null;
            listEmailPessoas = new ArrayList<>();
        }
    }

    public void edit(Object o) {
        Dao dao = new Dao();
        EmailPessoa ep = (EmailPessoa) dao.rebind(o);
        email = ep.getEmail();
        for (int i = 0; i < getListEmailPrioridades().size(); i++) {
            if (Integer.parseInt(getListEmailPrioridades().get(i).getDescription()) == email.getEmailPrioridade().getId()) {
                index[1] = i;
                break;
            }
        }
        filter = false;
        filterNewMessage = true;
        filterByRotina = false;
        DaoInterface di = new Dao();
        addEmailPessoas.clear();
        addEmailPessoas = (List<EmailPessoa>) di.listQuery(new EmailPessoa(), "findByEmail", new Object[]{email.getId()});
        descricaoPesquisa = "";
    }

    public void saveDraft() {
        DaoInterface di = new Dao();
        if (email.getAssunto().isEmpty()) {
            Messages.warn("Validação", "Informar assunto!");
            return;
        }
        if (email.getMensagem().isEmpty()) {
            Messages.warn("Validação", "Informar mensagem!");
            return;
        }
        if (addEmailPessoas.isEmpty()) {
            Messages.warn("Validação", "Informar destinatário(s)!");
            return;
        }
        email.setRascunho(true);
        email.setUsuario((Usuario) Sessions.getObject("sessaoUsuario"));
        email.setRotina((Rotina) di.find(new Rotina(), 112));
        email.setEmailPrioridade((EmailPrioridade) di.find(new EmailPrioridade(), Integer.parseInt(getListEmailPrioridades().get(index[1]).getDescription())));
        email.setCliente(SessaoCliente.get());
        if (email.getId() == -1) {
            di.save(email, true);
            for (int i = 0; i < addEmailPessoas.size(); i++) {
                addEmailPessoas.get(i).setEmail(email);
                if (addEmailPessoas.get(i).getId() == -1) {
                    addEmailPessoas.get(i).setPessoa(null);
                }
                di.save(addEmailPessoas.get(i), true);
            }
            addEmailPessoas.clear();
            Messages.info("Sucesso", "Rascunho salvo com sucesso!");
        } else {
            di.update(email, true);
            Messages.info("Sucesso", "Rascunho atualizado com sucesso!");
        }
    }

    public void send() {
        DaoInterface di = new Dao();
        if (email.getAssunto().isEmpty()) {
            Messages.warn("Validação", "Informar assunto!");
            return;
        }
        if (email.getMensagem().isEmpty()) {
            Messages.warn("Validação", "Informar mensagem!");
            return;
        }
        if (addEmailPessoas.isEmpty()) {
            Messages.warn("Validação", "Informar destinatário(s)!");
            return;
        }
        email.setRascunho(true);
        if (email.getUsuario().getId() == null) {
            email.setUsuario((Usuario) Sessions.getObject("sessaoUsuario"));
        }
        email.setRotina((Rotina) di.find(new Rotina(), 112));
        email.setEmailPrioridade((EmailPrioridade) di.find(new EmailPrioridade(), Integer.parseInt(getListEmailPrioridades().get(index[1]).getDescription())));
        Mail mail = new Mail();
        mail.setFiles(files);
        mail.setEmail(email);
        if (email.isRascunho()) {
            email.setRascunho(false);
        }
        mail.setEmailPessoas(addEmailPessoas);
        String[] retorno = mail.send("");
        if (retorno[1].isEmpty()) {
            if (!addEmailPessoas.isEmpty()) {
                Messages.info("Sucesso", "Email(s) " + retorno[0]);
            } else {
                Messages.info("Sucesso", "Email " + retorno[0]);
            }
        } else {
            Messages.warn("Falha", "Email(s) " + retorno[1]);
        }
        clear();
    }

    public void openDialogModal() {
        openModal = true;
        PF.openDialog("dlg_address");
    }

    public void closeDialogModal() {
        openModal = false;
        PF.closeDialog("dlg_address");
    }

    public void addEmail() {
        if (emailPessoa.getDestinatario().isEmpty()) {
            if (emailPessoa.getPessoa().getId() != -1) {
                if (!emailPessoa.getPessoa().getEmail1().isEmpty()) {
                    emailPessoa.setDestinatario(assunto);
                }
            } else {
                emailPessoa.setPessoa(null);
            }
        }
        DaoInterface di = new Dao();
        if (emailPessoa.getDestinatario().isEmpty()) {
            Messages.warn("Validação", "Informar email do destinatário");
            return;
        }
        if (email.getId() == -1) {
            addEmailPessoas.add(emailPessoa);
        } else {
            if (emailPessoa.getPessoa() != null) {
                if (emailPessoa.getPessoa().getId() == -1) {
                    emailPessoa.setPessoa(null);
                }
            }
            emailPessoa.setEmail(email);
            di.save(emailPessoa, true);
            addEmailPessoas.clear();
        }
        emailPessoa = new EmailPessoa();
    }

    public void removeEmail(int rowKey) {
        if (addEmailPessoas.size() == 1) {
            Messages.warn("Erro", "Não é possível remover todos destinatários!");
            return;
        }
        DaoInterface di = new Dao();
        for (int i = 0; i < addEmailPessoas.size(); i++) {
            if (i == rowKey) {
                if (addEmailPessoas.get(i).getId() != -1) {
                    di.delete(addEmailPessoas.get(i), true);
                    addEmailPessoas.clear();
                } else {
                    addEmailPessoas.remove(i);
                }
                break;
            }
        }
    }

    public void showEmailPessoa(Email e) {
        DaoInterface di = new Dao();
        showEmailPessoas.clear();
        showEmailPessoas = (List<EmailPessoa>) di.listQuery(new EmailPessoa(), "findByEmail", new Object[]{e.getId()});
    }

    public List<SelectItem> getListRotinas() {
        if (listSelectItem[0].isEmpty()) {
            DaoInterface di = new Dao();
            List<Rotina> list = (List<Rotina>) di.list(new Rotina(), true);
            for (int i = 0; i < list.size(); i++) {
                listSelectItem[0].add(new SelectItem(i, list.get(i).getRotina(), "" + list.get(i).getId()));
            }
            if (listSelectItem[0].isEmpty()) {
                listSelectItem[0] = new ArrayList<>();
            }
        }
        return listSelectItem[0];
    }

    public List<SelectItem> getListEmailPrioridades() {
        if (listSelectItem[1].isEmpty()) {
            DaoInterface di = new Dao();
            List<EmailPrioridade> list = (List<EmailPrioridade>) di.list(new EmailPrioridade());
            for (int i = 0; i < list.size(); i++) {
                listSelectItem[1].add(new SelectItem(i, list.get(i).getDescricao(), "" + list.get(i).getId()));
            }
            if (listSelectItem[1].isEmpty()) {
                listSelectItem[1] = new ArrayList<>();
            }
        }
        return listSelectItem[1];
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Boolean getOpenModal() {
        return openModal;
    }

    public void setOpenModal(Boolean openModal) {
        this.openModal = openModal;
    }

    public EmailArquivo getEmailArquivo() {
        return emailArquivo;
    }

    public void setEmailArquivo(EmailArquivo emailArquivo) {
        this.emailArquivo = emailArquivo;
    }

    public List<EmailArquivo> getEmailArquivos() {
        return emailArquivos;
    }

    public void setEmailArquivos(List<EmailArquivo> emailArquivos) {
        this.emailArquivos = emailArquivos;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Email[] getSelectedEmail() {
        return selectedEmail;
    }

    public void setSelectedEmail(Email[] selectedEmail) {
        this.selectedEmail = selectedEmail;
    }

    public Date[] getDate() {
        return date;
    }

    public void setDate(Date[] date) {
        this.date = date;
    }

    public Integer[] getIndex() {
        return index;
    }

    public void setIndex(Integer[] index) {
        this.index = index;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        if (!this.filter) {
            listSelectItem[0].clear();
            listSelectItem[1].clear();
            //listSelectItem[2].clear();
            assunto = "";
            emailString = "";
            date = new Date[2];
            date[0] = null;
            date[1] = null;
            index[0] = 0;
            index[1] = 0;
            openModal = false;
            descricaoPesquisa = "";
        }
        this.filter = filter;
    }

    public boolean isFilterRascunho() {
        return filterRascunho;
    }

    public void setFilterRascunho(boolean filterRascunho) {
        this.filterRascunho = filterRascunho;
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }

    public List<EmailPessoa> getShowEmailPessoas() {
        return showEmailPessoas;
    }

    public void setShowEmailPessoas(List<EmailPessoa> showEmailPessoas) {
        this.showEmailPessoas = showEmailPessoas;
    }

    public List<EmailPessoa> getAddEmailPessoas() {
        if (email.getId() != -1) {
            DaoInterface di = new Dao();
            addEmailPessoas = (List<EmailPessoa>) di.listQuery(new EmailPessoa(), "findByEmail", new Object[]{email.getId()});
        }
        return addEmailPessoas;
    }

    public void setAddEmailPessoas(List<EmailPessoa> addEmailPessoas) {
        this.addEmailPessoas = addEmailPessoas;
    }

    public List<EmailPessoa> getListEmailPessoas() {
        if (listEmailPessoas.isEmpty()) {
            int idRotina = 0;
            Date di = null;
            Date df = null;
            if (filter) {
                if (filterByRotina) {
                    idRotina = Integer.parseInt((getListRotinas().get(index[0]).getDescription()));
                }
                di = date[0];
                df = date[1];
            }
            EmailDao ed = new EmailDao();
            if (filterRascunho) {
                listEmailPessoas = ed.findRascunho();
            } else {
                listEmailPessoas = ed.findEmail(idRotina, di, df, filterBy, descricaoPesquisa, orderBy);
            }
        }
        return listEmailPessoas;
    }

    public void setListEmailPessoas(List<EmailPessoa> listEmailPessoas) {
        this.listEmailPessoas = listEmailPessoas;
    }

    public EmailPessoa getEmailPessoa() {
        if (Sessions.exists("pessoaPesquisa")) {
            emailPessoa.setPessoa((Pessoa) Sessions.getObject("pessoaPesquisa", true));
            if (emailPessoa.getDestinatario().isEmpty()) {
                if (!emailPessoa.getPessoa().getEmail1().isEmpty()) {
                    emailPessoa.setDestinatario(emailPessoa.getPessoa().getEmail1());
                }
            }
        }
        return emailPessoa;
    }

    public void setEmailPessoa(EmailPessoa emailPessoa) {
        this.emailPessoa = emailPessoa;
    }

    public void newMessage() {
        this.filterNewMessage = true;
        openModal = false;
        listEmailPessoas.clear();
        addEmailPessoas.clear();
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    public boolean isFilterNewMessage() {
        return filterNewMessage;
    }

    public void setFilterNewMessage(boolean filterNewMessage) {
        this.filterNewMessage = filterNewMessage;
    }

    public boolean isFilterByRotina() {
        return filterByRotina;
    }

    public void setFilterByRotina(boolean filterByRotina) {
        this.filterByRotina = filterByRotina;
    }

    public String getDescricaoPesquisa() {
        return descricaoPesquisa;
    }

    public void setDescricaoPesquisa(String descricaoPesquisa) {
        this.descricaoPesquisa = descricaoPesquisa;
    }

    public void selectedDataInicial(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
        this.date[0] = DataHoje.converte(format.format(event.getObject()));
    }

    public void selectedDataFinal(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
        this.date[1] = DataHoje.converte(format.format(event.getObject()));
    }

    public void setUrlRetorno(String urlRetorno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getUrlRetorno() {
        return urlRetorno;
    }
}
