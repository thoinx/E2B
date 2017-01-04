package com.uncommontrade.e2b.Entities;

/**
 * Created by thoin_000 on 5/16/2016.
 */
public class EnProfile {
    private String uid;
    private String username;
    private String fbid;
    private String twitter_id;
    private String email;
    private String avatar;
    private String cover;
    private String password;
    private String full_name;
    private String birth_day;
    private String sex;
    private String introduce;
    private String vacation_mode;
    private String status;
    private String last_login;
    private String joined;
    private String new_email;
    private Boolean has_password;

    public EnProfile() {
    }

    public EnProfile(String uid, String username, String fbid, String twitter_id, String email, String avatar, String cover, String password, String full_name, String birth_day, String sex, String introduce, String vacation_mode, String status, String last_login, String joined, String new_email, Boolean has_password) {
        this.uid = uid;
        this.username = username;
        this.fbid = fbid;
        this.twitter_id = twitter_id;
        this.email = email;
        this.avatar = avatar;
        this.cover = cover;
        this.password = password;
        this.full_name = full_name;
        this.birth_day = birth_day;
        this.sex = sex;
        this.introduce = introduce;
        this.vacation_mode = vacation_mode;
        this.status = status;
        this.last_login = last_login;
        this.joined = joined;
        this.new_email = new_email;
        this.has_password = has_password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getVacation_mode() {
        return vacation_mode;
    }

    public void setVacation_mode(String vacation_mode) {
        this.vacation_mode = vacation_mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public String getNew_email() {
        return new_email;
    }

    public void setNew_email(String new_email) {
        this.new_email = new_email;
    }

    public Boolean getHas_password() {
        return has_password;
    }

    public void setHas_password(Boolean has_password) {
        this.has_password = has_password;
    }
}
