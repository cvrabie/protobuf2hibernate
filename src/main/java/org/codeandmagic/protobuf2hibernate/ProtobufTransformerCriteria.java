package org.codeandmagic.protobuf2hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;

import java.util.List;

import static org.codeandmagic.protobuf2hibernate.ProtobufTransformer.protobufBuilderToMessage;

public class ProtobufTransformerCriteria extends WrappedCriteria {

    public ProtobufTransformerCriteria(final Criteria wrapped){
        super(wrapped);
    }

    @Override
    protected Criteria newLikeMe(Criteria toBeWrapped) {
        return new ProtobufTransformerCriteria(toBeWrapped);
    }

    @Override
    public List list() throws HibernateException {
        return protobufBuilderToMessage(wrapped.list());
    }

    @Override
    public Object uniqueResult() throws HibernateException {
        return protobufBuilderToMessage(wrapped.uniqueResult());
    }
}
