package org.codeandmagic.protobuf2hibernate.sample;

import org.codeandmagic.protobuf2hibernate.ProtobufTransformerSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CatDao {
    private Session session;

    @Required
    public void setSessionFactory(SessionFactory sessionFactory){
        this.session = new ProtobufTransformerSession(sessionFactory);
    }

    @Transactional(readOnly = false)
    public void save(Cat cat){
        session.save(cat);
    }

    @Transactional(readOnly = true)
    public List<Cat> getByName(String name){
        return session
                .createCriteria(Cat.class)
                .add(Restrictions.eq("name", name))
                .list();
    }
}
