package com.zak.person;

import com.fasterxml.jackson.annotation.JsonRootName;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

@JsonRootName(value = "person")
public class Person {
    private Name name;
    private DateTime dob;
    private Address address;

    public Person() {}

    public Person(Name name, DateTime dob) {
        setName(name);
        setDob(dob);
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

//    @JsonSerialize(using = DateTimeSerializer.class)
    public DateTime getDob() {
        return dob;
    }

//    @JsonDeserialize(using = DateTimeDeserializer.class)
    public void setDob(DateTime dob) {
        this.dob = dob;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person withAddress(Address address) {
        setAddress(address);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (address != null ? !address.equals(person.address) : person.address != null) return false;
        if (DateTimeComparator.getDateOnlyInstance().compare(dob, person.getDob()) != 0) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
