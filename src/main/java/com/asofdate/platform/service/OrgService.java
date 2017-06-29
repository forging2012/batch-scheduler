package com.asofdate.platform.service;

import com.asofdate.platform.entity.OrgEntity;
import com.asofdate.platform.entity.RoleEntity;
import com.asofdate.utils.RetMsg;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface OrgService {
    List<OrgEntity> findAll(String domainId);

    List<OrgEntity> findSub(String domainId, String orgId);

    RetMsg add(OrgEntity orgEntity);

    RetMsg delete(List<OrgEntity> list);

    RetMsg update(OrgEntity orgEntity);
}
