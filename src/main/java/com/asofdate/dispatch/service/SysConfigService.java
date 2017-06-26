package com.asofdate.dispatch.service;

import com.asofdate.dispatch.entity.SysConfigEntity;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/15.
 */
public interface SysConfigService {
    List<SysConfigEntity> findAll(String domainId);

    int setValue(String domainId, String configId, String configValue);

    String getValue(String domainId, String configId);
}
