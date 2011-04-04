package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.Message;
import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

public class ProtobufSaveOrUpdateEventListener extends DefaultSaveOrUpdateEventListener {
    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
        super.onSaveOrUpdate(handleProtobufSaveOrUpdateEvent(event));
    }

    public static SaveOrUpdateEvent handleProtobufSaveOrUpdateEvent(SaveOrUpdateEvent event){
        Object obj = event.getObject();
        if(obj instanceof Message.Builder){
            //everything's Hunky-dory
        }else if(obj instanceof Message){
            //transform this to a Message.Builder
            Message msg = (Message) obj;
            try{
                Message.Builder builder = MessageUtil.newBuilderForMessage(msg.getClass());
                builder.mergeFrom(msg);
                event.setObject(builder);
            } catch (Exception e) {
                throw new HibernateException(e);
            }
        }else{
            throw new HibernateException("This EventListener should only be used to persist Message or Message.Builder objects!");
        }
        return event;
    }
}
