package org.codeandmagic.protobuf2hibernate.sample;

import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class App {
    public static void main(String[] args){
        BeanFactory factory = new ClassPathXmlApplicationContext("sample-context.xml");
        //DogDao dd = (DogDao) factory.getBean("dog-dao");
        CatDao cd = (CatDao) factory.getBean("cat-dao");

        /*Dog d = new Dog();
        d.setUuid("u1");
        d.setName("Fluffy");
        d.setCreated(new Date());
        d.setPuppies(Arrays.asList(new String[]{"K1","K2"}));
        dd.save(d);
        */
        /*
        Dog d = dd.getByName("Fluffy").get(0);
        System.out.print("======>"+d.getUuid());
        */

        Cat c = Cat.newBuilder()
                .setName("Eve")
                .setCreated(100L)
                .setUuid(UUID.randomUUID().toString())
                .setHairLength(2.31f)
                .addAllKittens(Arrays.asList(new String[]{"Caty","Nutty"}))
                .build();

        cd.save(c);
    }
}
