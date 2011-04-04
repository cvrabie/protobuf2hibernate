package org.codeandmagic.protobuf2hibernate.sample;

import java.util.Date;
import java.util.List;

public class Dog {
    private String uuid;
    private String name;
    private Date created;
    private List<String> puppies;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<String> getPuppies() {
        return puppies;
    }

    public void setPuppies(List<String> puppies) {
        this.puppies = puppies;
    }
}
