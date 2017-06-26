package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.MenuDao;
import com.asofdate.platform.entity.MenuEntity;
import com.asofdate.platform.entity.ThemeValueEntity;
import com.asofdate.platform.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuEntity> findAll() {
        return menuDao.findAll();
    }

    @Override
    public MenuEntity getDetails(String resId) {
        return menuDao.getDetails(resId);
    }

    @Override
    public String update(String resId, String resDesc, String resUpId) {
        return menuDao.update(resId, resDesc, resUpId);
    }

    @Override
    public ThemeValueEntity getThemeDetails(String themeId, String resId) {
        return menuDao.getThemeDetails(themeId, resId);
    }

    @Override
    public String add(ThemeValueEntity themeValueEntity) {
        return menuDao.add(themeValueEntity);
    }

    @Override
    public String delete(String resId) {
        return menuDao.delete(resId);
    }

    @Override
    public String updateTheme(ThemeValueEntity themeValueEntity) {
        return menuDao.updateTheme(themeValueEntity);
    }
}
