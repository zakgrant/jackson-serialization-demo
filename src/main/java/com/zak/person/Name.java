package com.zak.person;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "name")
public class Name {
    private String surname;
    private String firstName;

    public Name() {}

    public Name(String firstName, String surname) {
        setFirstName(firstName);
        setSurname(surname);
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;

        Name name = (Name) o;

        if (firstName != null ? !firstName.equals(name.firstName) : name.firstName != null) return false;
        if (surname != null ? !surname.equals(name.surname) : name.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = surname != null ? surname.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }
}
