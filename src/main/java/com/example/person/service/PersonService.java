package com.example.person.service;

import com.example.person.enity.Person;
import com.example.person.mapper.PersonMapper;
import com.example.util.rsaKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonService {

    @Resource
    private PersonMapper personMapper;

    /**
     * 建立新的人员
     * @param person 人员实体类
     * @return
     */
    public String createNewPerson(Person person){
        String psnId = rsaKey.uuid(null);
        person.setIds(psnId);
        person.getNowDate("");
        personMapper.createNewPerson(person);
        return psnId;
    }

    /**
     * 查询社区人员
     * @param uuid
     * @return
     */
    public List<Person> selectListPerson(String uuid){
        return personMapper.selectPersonList(uuid);
    }

    /**
     * 更新社区人员
     */
    public int updatePerson(Person person){
        person.getNowDate(null);
        return personMapper.updatePerson(person);
    }

    /**
     * 查询社区人员（根据查询条件）
     */
    public List<Person> selectAllListPerson(Person person){
        return personMapper.selectAllListPerson(person);
    }

    /**
     * 获取指定人员
     * @param ids
     * @return
     */
    public Person get(String ids){
        return personMapper.get(ids);
    }
}
