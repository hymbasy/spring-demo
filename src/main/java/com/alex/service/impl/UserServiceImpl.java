package com.alex.service.impl;

import com.alex.dao.UserDao;
import com.alex.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveStuAndPerson() {
        int i = userDao.saveStu();
        System.out.println(i);
        int i1 = userDao.savePerson();
        System.out.println(i1);
    }
}
