package org.codeandmagic.protobuf2hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.Iterator;
import java.util.List;

import static org.codeandmagic.protobuf2hibernate.ProtobufTransformer.protobufBuilderToMessage;

public class ProtobufTransformerQuery extends WrappedQuery {
    public ProtobufTransformerQuery(Query wrapped){
        super(wrapped);
    }

    @Override
    protected Query newLikeMe(Query toBeWrapped) {
        return new ProtobufTransformerQuery(toBeWrapped);
    }

    @Override
    public Iterator iterate() throws HibernateException {
        return new ProtobufTransformerIterator(super.iterate());
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
