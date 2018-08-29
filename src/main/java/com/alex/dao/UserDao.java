package com.alex.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveStu() {
        StringBuilder sql = new StringBuilder("insert into student(name,email,telephone,address) values (?,?,?,?)");
        return jdbcTemplate.update(sql.toString(), new Object[]{"闫辉", "12165@163.com", "18500000000", "北京市朝阳区"});
    }

    public int savePerson() {
        StringBuilder sql = new StringBuilder("insert into person(username,password,phone,address,remark,created) values (?,?,?,?,?,?)");
        return jdbcTemplate.update(sql.toString(), new Object[]{"闫辉", "123456", "18500000000", "北京市朝阳区", "这是一条测试数据", System.currentTimeMillis()});
    }
}
