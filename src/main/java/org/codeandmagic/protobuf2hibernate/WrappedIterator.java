package org.codeandmagic.protobuf2hibernate;

import java.util.Iterator;

public class WrappedIterator<E> implements Iterator<E> {

    protected final Iterator<E> wrapped;

    public WrappedIterator(Iterator<E> wrapped){
        this.wrapped = wrapped;
    }

    @Override
    public boolean hasNext() {
        return wrapped.hasNext();
    }

    @Override
    public E next() {
        return wrapped.next();
    }

    @Override
    public void remove() {
        wrapped.remove();
    }
}
