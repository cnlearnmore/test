package com.syj.test.service;

import com.syj.test.dao.LoginTicketDao;
import com.syj.test.dao.UserDao;
import com.syj.test.model.LoginTicket;
import com.syj.test.model.User;
import com.syj.test.util.MyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    public User getUser(int id){
        return userDao.selectById(id);
    }

    public Map<String, String> register(String username, String password){
        Map<String, String> map = new HashMap<String, String>();
        if(StringUtils.isEmpty(username)){
            map.put("msg", "账号不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDao.selectByName(username);
        if(user != null){
            map.put("msg", "用户名已经存在");
            return map;
        }
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(MyUtil.MD5(password + user.getSalt()));
        userDao.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public Map<String, String> login(String username, String password){
        Map<String, String> map = new HashMap<String, String>();
        if(StringUtils.isEmpty(username)){
            map.put("msg", "用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(username)){
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDao.selectByName(username);
        if(user == null){
            map.put("msg", "用户名不存在");
            return map;
        }
        if(!MyUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
            map.put("msg", "密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }
    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(now.getTime() + 1000*24*3600);
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

}
