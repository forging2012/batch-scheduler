package com.asofdate.platform.service;

import com.asofdate.platform.entity.UserDetailsEntity;

/**
 * Created by hzwy23 on 2017/5/18.
 */
public interface UserDetailsService {
    UserDetailsEntity findById(String userId);
}
