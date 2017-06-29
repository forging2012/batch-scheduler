package com.asofdate.platform.service;

import com.asofdate.platform.entity.MenuEntity;
import com.asofdate.platform.entity.ThemeValueEntity;
import com.asofdate.utils.RetMsg;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface MenuService {
    List<MenuEntity> findAll();

    MenuEntity getDetails(String resId);

    RetMsg update(String resId, String resDesc, String resUpId);

    ThemeValueEntity getThemeDetails(String themeId, String resId);

    String add(ThemeValueEntity themeValueEntity);

    String delete(String resId);

    String updateTheme(ThemeValueEntity themeValueEntity);
}
