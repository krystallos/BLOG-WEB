package com.example.user.enity;

import com.example.util.enityUtil.userEnity;
import com.example.person.enity.Person;

import java.util.List;

public class User extends userEnity {

    private String userName;
    private String userPassWord;
    private String areaId;
    private String orgSessionId;
    private List<Person> personList;
    private Person person;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getOrgSessionId() {
        return orgSessionId;
    }

    public void setOrgSessionId(String orgSessionId) {
        this.orgSessionId = orgSessionId;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
