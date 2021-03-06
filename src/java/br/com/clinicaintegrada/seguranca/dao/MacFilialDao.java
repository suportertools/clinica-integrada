package br.com.clinicaintegrada.seguranca.dao;

import br.com.clinicaintegrada.principal.DB;
import br.com.clinicaintegrada.seguranca.MacFilial;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

public class MacFilialDao extends DB {

    public MacFilial pesquisaMac(String mac) {
        try {
            Query query = getEntityManager().createQuery("SELECT MF FROM MacFilial AS MF WHERE MF.mac LIKE :mac");
            query.setParameter("mac", mac);
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return (MacFilial) query.getSingleResult();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public List pesquisaTodosPorMatriz(int matriz) {
        try {
            Query query = getEntityManager().createQuery("SELECT MF FROM MacFilial AS MF WHERE MF.filial.matriz.id = :matriz");
            query.setParameter("matriz", matriz);
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return list;
            }
        } catch (Exception e) {
            return new ArrayList();
        }
        return new ArrayList();
    }
}
