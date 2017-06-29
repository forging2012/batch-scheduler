package com.asofdate.platform.service;

import com.asofdate.platform.dto.AuthDTO;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hzwy23 on 2017/6/19.
 */
public interface AuthService {
    AuthDTO domainAuth(HttpServletRequest request, String domainId, String mode);

    AuthDTO basicAuth(HttpServletRequest request);
}
