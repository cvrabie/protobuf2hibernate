package org.codeandmagic.protobuf2hibernate;

import com.google.protobuf.Message;
import org.hibernate.HibernateException;

import java.util.Arrays;
import java.util.List;

public class ProtobufTransformer {
    private ProtobufTransformer(){}

    public static Message.Builder protobufMessageToBuilder(Object object) throws HibernateException {
        if(null == object)
            return null;
        if(object instanceof Message.Builder)
            return (Message.Builder) object;
        if(!(object instanceof Message))
            throw new HibernateException("Can only handle Protobuf Message objects! " +
                    "Found "+object.getClass().getName()+" instead.");
        try{
            Message msg = (Message)object;
            Message.Builder builder = MessageUtil.newBuilderForMessage(msg.getClass());
            builder.mergeFrom(msg);
            return builder;
        }catch (Exception e){
            throw new HibernateException("Could not convert Message to Message.Builder");
        }
    }

    public static Message protobufBuilderToMessage(Object object) throws HibernateException {
        if(null == object)
            return null;
        if(object instanceof Message)
            return (Message) object;
        if(!(object instanceof  Message.Builder))
            throw new HibernateException("Can only handle Protobuf Message.Builder objects! " +
                    "Found "+object.getClass().getName()+" instead.");
        try{
            Message.Builder builder = (Message.Builder) object;
            return builder.build();
        }catch (Exception e){
            throw new HibernateException("Could not convert Message.Builder to Message");
        }
    }

    public static Class<? extends  Message.Builder> protobufBuilderClassFromMessageClass(Class<?> messageClass){
        try {
            return MessageUtil.builderClassFromMessageClass((Class<? extends Message>) messageClass);
            //TODO Cache!
        } catch (Exception e) {
            throw new HibernateException("Could not determine builder class for class "+messageClass.getName());
        }
    }

    public static List<Message> protobufBuilderToMessage(List<?> list){
        int size = list.size();
        Message[] transformed = new Message[size];
        for(int i=0;i<size;++i){
            transformed[i] = protobufBuilderToMessage(list.get(i));
        }
        return Arrays.asList(transformed);
    }
}
