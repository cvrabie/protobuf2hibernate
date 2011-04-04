package org.codeandmagic.protobuf2hibernate;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveEventListener;

public class ProtobufSaveEventListener extends DefaultSaveEventListener {
    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
        super.onSaveOrUpdate(ProtobufSaveOrUpdateEventListener.handleProtobufSaveOrUpdateEvent(event));
    }
}
