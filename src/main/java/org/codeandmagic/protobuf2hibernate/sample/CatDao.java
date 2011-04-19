package org.codeandmagic.protobuf2hibernate.sample;

import org.codeandmagic.protobuf2hibernate.ProtobufTransformerSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CatDao {
    private Session session;

    @Required
    public void setCurrentSession(Session currentSession){
        this.session = currentSession;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Cat cat){
        session.save(cat);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Cat> getByName(String name){
        return session
                .createCriteria(Cat.class)
                .add(Restrictions.eq("name", name))
                .list();
    }
}
