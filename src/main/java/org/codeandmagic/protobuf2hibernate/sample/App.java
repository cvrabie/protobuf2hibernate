package org.codeandmagic.protobuf2hibernate.sample;

import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args){
        BeanFactory factory = new ClassPathXmlApplicationContext("sample-context.xml");
        //DogDao dd = (DogDao) factory.getBean("dog-dao");
        CatDao cd = (CatDao) factory.getBean("cat-dao");
        DinosaurDao dd = (DinosaurDao) factory.getBean("dinosaur-dao");

        /*Dog d = new Dog();
        d.setUuid("u1");
        d.setName("Fluffy");
        d.setCreated(new Date());
        d.setPuppies(Arrays.asList(new String[]{"K1","K2"}));
        dd.save(d);

        Dog d = dd.getByName("Fluffy").get(0);
        System.out.print("======>"+d.getUuid());
        */


        Cat c1 = Cat.newBuilder()
                .setUuid(UUID.randomUUID().toString())
                .setName("FriendOfEve")
                .setCreated(98L)
                .setHairLength(2f)
                .build();

        Cat c2 = Cat.newBuilder()
                .setUuid(UUID.randomUUID().toString())
                .setName("FriendOfEve2")
                .setCreated(99L)
                .setHairLength(1f)
                .build();


        Cat c = Cat.newBuilder()
                .setName("Eve")
                .setCreated(100L)
                .setUuid(UUID.randomUUID().toString())
                .setHairLength(2.31f)
                .addAllKittens(Arrays.asList(new String[]{"Caty","Nutty"}))
                .setOwner(Person.newBuilder().setName("CatOwner").build())
                .addToys(Toy.newBuilder().setName("Curtains").build())
                .addToys(Toy.newBuilder().setName("OwnersNose").setDescription("The bigger the better").build())
                .addFriends(c1)
                .addFriends(c2)
                .setHairType(HairType.CURLY)
                .build();
        cd.save(c);

        List<Cat> cats = cd.getByName("Eve");
        System.out.print(cats);

        Dinosaur d = Dinosaur.newBuilder()
                     .setName("Fluffy")
                     .setAge(9999)
                     .setTailLength(47.6f)
                     .setUuid(UUID.randomUUID().toString())
                     .addAllVictims(Arrays.asList(new String[]{"PunyHuman","Meteorite"}))
                     .build();
        dd.save(d);
        List<Dinosaur> dinos = dd.getByName("Fluffy");
        System.out.print(dinos);

    }
}
