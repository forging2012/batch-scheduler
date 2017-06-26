package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.DomainDao;
import com.asofdate.platform.dao.DomainShareDao;
import com.asofdate.platform.entity.DomainEntity;
import com.asofdate.platform.service.DomainService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@Service
public class DomainServiecImpl implements DomainService {

    @Autowired
    private DomainDao domainDao;
    @Autowired
    private DomainShareDao domainShareDao;

    @Override
    public JSONObject findAll(String domainId) {
        List<DomainEntity> list = domainDao.findAll();
        Set<String> set = domainShareDao.findAll(domainId);
        for (int i = 0; i < list.size(); i++) {
            if (!set.contains(list.get(i).getDomain_id())) {
                list.remove(i);
                i--;
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("domain_id", domainId);
        jsonObject.put("owner_list", list);
        return jsonObject;
    }

    @Override
    public List<DomainEntity> getAll() {
        return domainDao.getAll();
    }

    @Override
    public int update(DomainEntity domainEntity) {
        return domainDao.update(domainEntity);
    }

    @Override
    public String delete(JSONArray jsonArray) {
        return domainDao.delete(jsonArray);
    }

    @Override
    public int add(DomainEntity domainEntity) {
        return domainDao.add(domainEntity);
    }

    @Override
    public DomainEntity getDomainDetails(String domainId) {
        return domainDao.getDomainDetails(domainId);
    }
}