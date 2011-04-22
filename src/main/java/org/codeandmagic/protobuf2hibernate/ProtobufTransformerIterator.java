package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.Message;

import java.util.Iterator;

import static org.codeandmagic.protobuf2hibernate.ProtobufTransformer.transformQueryResult;

public class ProtobufTransformerIterator<E extends Message> extends WrappedIterator<E>{
    public ProtobufTransformerIterator(Iterator<E> wrapped) {
        super(wrapped);
    }

    @Override
    public E next() {
        return (E) transformQueryResult(wrapped.next());
    }
}
