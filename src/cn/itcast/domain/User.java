package cn.itcast.domain;

/**
 * Created by yvettee on 2017/10/24.
 */
public class User {
    private String userName;
    private String passWord;

    public User(String userName, String passWord) {
        super();
        this.userName = userName;
        this.passWord = passWord;
    }

    public User() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
