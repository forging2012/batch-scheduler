package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.OrgDao;
import com.asofdate.platform.entity.OrgEntity;
import com.asofdate.platform.service.OrgService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Service
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrgDao orgDao;

    @Override
    public List<OrgEntity> findAll(String domainId) {
        return orgDao.findAll(domainId);
    }

    @Override
    public List<OrgEntity> findSub(String domainId, String orgId) {
        return orgDao.findSub(domainId, orgId);
    }

    @Override
    public int add(OrgEntity orgEntity) {
        return orgDao.add(orgEntity);
    }

    @Override
    public int delete(JSONArray jsonArray) {
        return orgDao.delete(jsonArray);
    }

    @Override
    public int update(OrgEntity orgEntity) {
        return orgDao.update(orgEntity);
    }
}
