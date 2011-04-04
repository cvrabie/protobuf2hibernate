package org.codeandmagic.protobuf2hibernate.sample;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CatDao {
    private SessionFactory sessionFactory;

    @Required
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = false)
    public void save(Cat cat){
        sessionFactory.getCurrentSession().save(cat);
    }

    @Transactional(readOnly = true)
    public List<Cat> getByName(String name){
        return sessionFactory.getCurrentSession()
                .createCriteria(Cat.class)
                .add(Restrictions.eq("name", name))
                .list();
    }
}
