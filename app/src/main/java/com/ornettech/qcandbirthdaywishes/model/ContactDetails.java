package com.ornettech.qcandbirthdaywishes.model;

import java.io.Serializable;

/**
 * Created by Ornet27 on 5/28/2018.
 */

public class ContactDetails implements Serializable {
    String corporator;
    String date;
    String wardno;
    String hitchintak;
    String rahivashi;
    String karyakarta;
    String mobHitchintak;
    String mobRahivashi;
    String mobkk;
    String mobCorporator;

    public ContactDetails() {

    }

    public ContactDetails(String corporator, String date, String wardno, String hitchintak, String rahivashi) {
        this.corporator = corporator;
        this.date = date;
        this.wardno = wardno;
        this.hitchintak = hitchintak;
        this.rahivashi = rahivashi;
    }

    public String getCorporator() {
        return corporator;
    }

    public void setCorporator(String corporator) {
        this.corporator = corporator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWardno() {
        return wardno;
    }

    public void setWardno(String wardno) {
        this.wardno = wardno;
    }

    public String getHitchintak() {
        return hitchintak;
    }

    public void setHitchintak(String hitchintak) {
        this.hitchintak = hitchintak;
    }

    public String getRahivashi() {
        return rahivashi;
    }

    public void setRahivashi(String rahivashi) {
        this.rahivashi = rahivashi;

    }

    public String getKaryakarta() {
        return karyakarta;
    }

    public void setKaryakarta(String karyakarta) {
        this.karyakarta = karyakarta;
    }

    public String getMobHitchintak() {
        return mobHitchintak;
    }

    public void setMobHitchintak(String mobHitchintak) {
        this.mobHitchintak = mobHitchintak;
    }

    public String getMobRahivashi() {
        return mobRahivashi;
    }

    public void setMobRahivashi(String mobRahivashi) {
        this.mobRahivashi = mobRahivashi;
    }

    public String getMobkk() {
        return mobkk;
    }

    public void setMobkk(String mobkk) {
        this.mobkk = mobkk;
    }

    public String getMobCorporator() {
        return mobCorporator;
    }

    public void setMobCorporator(String mobCorporator) {
        this.mobCorporator = mobCorporator;
    }
}
