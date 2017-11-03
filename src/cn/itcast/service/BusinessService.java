package cn.itcast.service;

import cn.itcast.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yvettee on 2017/10/24.
 */
public class BusinessService {

    private static List<User> list = new ArrayList();

    static {
        list.add(new User("aaa", "123"));
        list.add(new User("bbb", "123"));
        list.add(new User("ccc", "123"));
    }

    public User login(String userName, String passWord) {
        for (User user : list) {
            if (user.getUserName().equals(userName) && user.getPassWord().equals(passWord)) {
                return user;
            }
        }
        return null;
    }

    public User findUser(String userName) {
        for (User user : list) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}
