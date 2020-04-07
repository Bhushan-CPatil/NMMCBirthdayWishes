package com.ornettech.nmmcqcandbirthdaywishes.model;

import java.io.Serializable;

/**
 * Created by Ornet on 11/18/2017.
 */

public class Ward implements Serializable {

    private String title;
    private String corporators;
    private String hitchintak;
    private String kk;
    private String voter;
    private String count;
    private String birthday;
    private String birthmonth;
    private String birthdate;
    private String message;
    private String mobcorp;
    private String mobkk;
    private String mobhit;
    private String mobvoter;
    private int warno;
    private int srno;
    String designation;
    private int listno;
    private int voterid;
    String tablename;
    int commoncd;

    public Ward() {
    }

    public Ward(String title, String corporators, String message) {
        this.title = title;
        this.corporators = corporators;
        this.message = message;
    }

    public Ward(String corporators, String mobcorp) {
        this.corporators = corporators;
        this.mobcorp = mobcorp;
    }

    public Ward(String title, String corporators, String message, String birthday, String birthmonth, String birthdate) {
        this.title = title;
        this.corporators = corporators;
        this.message = message;
        this.birthday = birthday;
        this.birthmonth = birthmonth;
        this.birthdate = birthdate;
    }

    public Ward(int srno, String designation, int warno, String birthday, String birthmonth) {
        this.birthday = birthday;
        this.birthmonth = birthmonth;
        this.warno = warno;
        this.srno = srno;
        this.designation = designation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCorporators() {
        return corporators;
    }

    public void setCorporators(String corporators) {
        this.corporators = corporators;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthmonth() {
        return birthmonth;
    }

    public void setBirthmonth(String birthmonth) {
        this.birthmonth = birthmonth;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getHitchintak() {
        return hitchintak;
    }

    public void setHitchintak(String hitchintak) {
        this.hitchintak = hitchintak;
    }

    public String getKk() {
        return kk;
    }

    public void setKk(String kk) {
        this.kk = kk;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public String getMobcorp() {
        return mobcorp;
    }

    public void setMobcorp(String mobcorp) {
        this.mobcorp = mobcorp;
    }

    public String getMobkk() {
        return mobkk;
    }

    public void setMobkk(String mobkk) {
        this.mobkk = mobkk;
    }

    public String getMobhit() {
        return mobhit;
    }

    public void setMobhit(String mobhit) {
        this.mobhit = mobhit;
    }

    public String getMobvoter() {
        return mobvoter;
    }

    public void setMobvoter(String mobvoter) {
        this.mobvoter = mobvoter;
    }

    public int getWarno() {
        return warno;
    }

    public void setWarno(int warno) {
        this.warno = warno;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public int getCommoncd() {
        return commoncd;
    }

    public void setCommoncd(int commoncd) {
        this.commoncd = commoncd;
    }

    public int getListno() {
        return listno;
    }

    public void setListno(int listno) {
        this.listno = listno;
    }

    public int getVoterid() {
        return voterid;
    }

    public void setVoterid(int voterid) {
        this.voterid = voterid;
    }
}
