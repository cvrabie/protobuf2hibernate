package org.codeandmagic.protobuf2hibernate.sample;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DogDao {
    private SessionFactory sessionFactory;

    @Required
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = false)
    public void save(Dog dog){
        sessionFactory.getCurrentSession().save(dog);
    }

    @Transactional(readOnly = true)
    public List<Dog> getByName(String name){
        return sessionFactory.getCurrentSession()
                .createCriteria(Dog.class)
                .add(Restrictions.eq("name", name))
                .list();
    }
}
