package br.com.clinicaintegrada.seguranca.dao;

import br.com.clinicaintegrada.pessoa.Pessoa;
import br.com.clinicaintegrada.principal.DB;
import br.com.clinicaintegrada.seguranca.Usuario;
import br.com.clinicaintegrada.utils.Sessions;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

@SuppressWarnings("unchecked")
public class UsuarioDao extends DB {

    public List<Usuario> pesquisaTodosPorCliente(int idCliente) {
        try {
            Query query = getEntityManager().createQuery("SELECT U FROM Usuario AS U WHERE U.cliente.id = :cliente  ORDER BY U.pessoa.nome ASC ");
            query.setParameter("cliente", idCliente);
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return list;
            }
        } catch (Exception e) {
        }
        return new ArrayList();
    }

    public List<Usuario> pesquisaTodosPorDescricao(String descricaoPesquisa, int idCliente) {
        try {
            Query query;
            if(((Usuario) Sessions.getObject("sessaoUsuario")).getId() != 1) { 
                query = getEntityManager().createQuery("SELECT U FROM Usuario AS U WHERE U.cliente.id = :cliente AND U.id <> 1 AND (U.pessoa.nome) LIKE '%" + descricaoPesquisa.toUpperCase() + "%' OR UPPER(U.login) LIKE '%" + descricaoPesquisa.toUpperCase() + "%' ORDER BY U.pessoa.nome ASC ");
            } else {
                query = getEntityManager().createQuery("SELECT U FROM Usuario AS U WHERE U.cliente.id = :cliente AND (U.pessoa.nome) LIKE '%" + descricaoPesquisa.toUpperCase() + "%' OR UPPER(U.login) LIKE '%" + descricaoPesquisa.toUpperCase() + "%' ORDER BY U.pessoa.nome ASC ");                
            }
            query.setParameter("cliente", idCliente);
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return list;
            }
        } catch (Exception e) {
        }
        return new ArrayList();
    }

    public Pessoa ValidaUsuarioWeb(String login, String senha) {
        Pessoa result = null;
        try {
            Query qry = getEntityManager().createQuery(
                    "select pes"
                    + "  from Pessoa pes"
                    + " where pes.login = :log"
                    + "   and pes.senha = :sen");
            qry.setParameter("log", login);
            qry.setParameter("sen", senha);
            result = (Pessoa) qry.getSingleResult();
        } catch (Exception e) {
        }
        return result;
    }

    public Usuario ValidaUsuario(String login, String senha) {
        Usuario result = null;
        try {
            Query qry = getEntityManager().createQuery(
                    " SELECT usu"
                    + "   FROM Usuario usu"
                    + "  WHERE usu.login = :log"
                    + "    AND usu.senha = :sen");
            qry.setParameter("log", login);
            qry.setParameter("sen", senha);
            if (!qry.getResultList().isEmpty()) {
                result = (Usuario) qry.getSingleResult();
                if (result.getId() != 1) {
                    if (!result.getAtivo()) {
                        result = null;
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
        return result;
    }

    public Usuario ValidaUsuarioSuporteWeb(String login, String senha) {
        Usuario result = null;
        try {
            Query qry = getEntityManager().createQuery(
                    "select usu"
                    + "  from Usuario usu"
                    + " where usu.login = :log"
                    + "   and usu.senha = :sen");
            qry.setParameter("log", login);
            qry.setParameter("sen", senha);
            result = (Usuario) qry.getSingleResult();
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }

    public List pesquisaLogin(String login, int idPessoa) {
        List result = new ArrayList();
        String descricao = login.toLowerCase().toUpperCase();
        try {
            Query qry = getEntityManager().createQuery("select usu from Usuario usu where UPPER(usu.login) = :d_usuario"
                    + "    or usu.pessoa.id = :idPessoa");
            qry.setParameter("d_usuario", descricao);
            qry.setParameter("idPessoa", idPessoa);
            result = qry.getResultList();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public Pessoa ValidaUsuarioContribuinteWeb(int idPessoa) {
        Pessoa result = null;
        try {
            Query qry = getEntityManager().createQuery(
                    "select pes "
                    + "  from Pessoa pes, "
                    + "       Juridica jur, "
                    + "       CnaeConvencao cnaeCon "
                    + " where pes.id = :idPes"
                    + "   and jur.pessoa.id = pes.id "
                    + "   and cnaeCon.cnae.id = jur.cnae.id");
            qry.setParameter("idPes", idPessoa);
            result = (Pessoa) qry.getSingleResult();
        } catch (Exception e) {
        }
        return result;
    }

    public Pessoa ValidaUsuarioContabilidadeWeb(int idPessoa) {
        Pessoa result = null;
        try {
            Query qry = getEntityManager().createQuery(
                    "select pes "
                    + "  from Pessoa pes, "
                    + "       Juridica jur "
                    + " where pes.id = :idPes"
                    + "   and jur.pessoa.id = pes.id"
                    + "   and jur.id in (select j.contabilidade.id from Juridica j where j.contabilidade.id is not null)");
            qry.setParameter("idPes", idPessoa);
            result = (Pessoa) qry.getSingleResult();
        } catch (Exception e) {
        }
        return result;
    }

    public Pessoa ValidaUsuarioPatronalWeb(int idPessoa) {
        Pessoa result = null;
        try {
            Query qry = getEntityManager().createQuery(
                    "select pes "
                    + "  from Pessoa pes "
                    + " where pes.id in (select patro.pessoa.id from Patronal patro where patro.pessoa.id = :idPes )");
            qry.setParameter("idPes", idPessoa);
            result = (Pessoa) qry.getSingleResult();
        } catch (Exception e) {
        }
        return result;
    }

    public Usuario ValidaUsuarioContribuinte(int idUsuario) {
        Usuario result = null;
        try {
            Query qry = getEntityManager().createQuery(
                    "select usu "
                    + "  from Usuario usu, "
                    + "       Pessoa pes, "
                    + "       Juridica jur, "
                    + "       Contribuintes con "
                    + " where usu.id = :idUser "
                    + "   and usu.pessoa.id = pes.id "
                    + "   and jur.pessoa.id = pes.id "
                    + "   and con.juridica.id = jur.id");
            qry.setParameter("idUser", idUsuario);
            result = (Usuario) qry.getSingleResult();
        } catch (Exception e) {
        }
        return result;
    }

    public Usuario ValidaUsuarioContabilidade(int idUsuario) {
        try {
            Query qry = getEntityManager().createQuery(
                    "select usu "
                    + "  from Usuario usu, "
                    + "       Pessoa pes, "
                    + "       Juridica jur "
                    + " where usu.id = :idUser "
                    + "   and usu.pessoa.id = pes.id "
                    + "   and jur.pessoa.id = pes.id "
                    + "   and jur.id in ( select j.contabilidade.id "
                    + "                     from Juridica j "
                    + "                    where j.contabilidade.id is not null )");
            qry.setParameter("idUser", idUsuario);
            return (Usuario) qry.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void updateAcordoMovimento() {
        try {
            Query qry = getEntityManager().createNativeQuery("update fin_movimento set nr_ativo=0, nr_acordado=1 where nr_ativo = 1 and id_acordo > 0 and id_tipo_servico <> 4");
            getEntityManager().getTransaction().begin();
            qry.executeUpdate();
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
        }
    }

    public Usuario pesquisaUsuarioPorPessoa(int id_pessoa) {
        try {
            Query qry = getEntityManager().createQuery(
                    "select usu from Usuario usu where USU.pessoa.id = " + id_pessoa);
            return (Usuario) qry.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
