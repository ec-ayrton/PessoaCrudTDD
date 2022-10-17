package com.ayrton.pessoacrud.testConfig;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import java.util.List;

@Component
@Transactional
public class DbIntegrationConfig {
    @PersistenceContext
    private EntityManager entityManager;

    public void resetaBase(List<Class> classList){
        classList.forEach(this::limpaTabela);
        flushAndClear();
    }
    private void limpaTabela(Class classe){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete delete = cb.createCriteriaDelete(classe);
        delete.from(classe);
        entityManager.createQuery(delete).executeUpdate();
    }

    private void flushAndClear(){
        entityManager.flush();
        entityManager.clear();
    }
}
