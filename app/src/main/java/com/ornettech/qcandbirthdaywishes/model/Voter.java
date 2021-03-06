package com.ornettech.qcandbirthdaywishes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by New on 03/15/2018.
 */

public class Voter implements Serializable {

    @SerializedName("Voter_Cd")
    @Expose
    private int voter_cd;

    @SerializedName("Voter_Id")
    @Expose
    private int voter_id;

    @SerializedName("List_No")
    @Expose
    private int list_no;

    @SerializedName("Ac_No")
    @Expose
    private int ac_no;

    @SerializedName("Ward_no")
    @Expose
    private int ward_no;

    @SerializedName("NewVoter_Id")
    @Expose
    private int newvoter_id;

    @SerializedName("FamilyNo")
    @Expose
    private int family_no;

    @SerializedName("SubLocation_Cd")
    @Expose
    private int subloc_cd;

    @SerializedName("Booth_Cd")
    @Expose
    private int booth_cd;

    @SerializedName("BoothName")
    @Expose
    private String booth_name;

    @SerializedName("BoothNameMar")
    @Expose
    private String booth_name_mar;

    @SerializedName("SF")
    @Expose
    private int sf;

    @SerializedName("Site_Cd")
    @Expose
    private int site_cd;

    @SerializedName("SiteName")
    @Expose
    private String site_name;

    @SerializedName("FullName")
    @Expose
    private String fullname;

    @SerializedName("FullNameMar")
    @Expose
    private String fullname_mar;

    @SerializedName("Surname")
    @Expose
    private String surname;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("FirstNameNameM")
    @Expose
    private String name_mar;

    @SerializedName("MiddleName")
    @Expose
    private String middlename;

    @SerializedName("MiddleNameM")
    @Expose
    private String middlename_mar;

    @SerializedName("RoomNo")
    @Expose
    private String room_no;

    @SerializedName("MobileNo")
    @Expose
    private String mobno;

    @SerializedName("Religion")
    @Expose
    private String religion;

    @SerializedName("Recieved_Status")
    @Expose
    private int Recieved_Status;

    int count;
    int remaincount;
    int submitcount;

    public int getRemaincount() {
        return remaincount;
    }

    public void setRemaincount(int remaincount) {
        this.remaincount = remaincount;
    }

    public int getSubmitcount() {
        return submitcount;
    }

    public void setSubmitcount(int submitcount) {
        this.submitcount = submitcount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public Voter() {

    }

    public int getRecieved_Status() {
        return Recieved_Status;
    }

    public void setRecieved_Status(int recieved_Status) {
        Recieved_Status = recieved_Status;
    }


    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    private int iconId;
    private String catTitle;


    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    @SerializedName("Caste")
    @Expose
    private String caste;

    @SerializedName("SubCaste")
    @Expose
    private String subcaste;

    @SerializedName("MarNmar")
    @Expose
    private String marnmar;

    @SerializedName("MarNmar_det")
    @Expose
    private String marnmar_det;

    @SerializedName("BirthDate")
    @Expose
    private String birthdate;

    @SerializedName("Education")
    @Expose
    private String education;

    @SerializedName("Occupation")
    @Expose
    private String occupation;

    @SerializedName("Hstatus")
    @Expose
    private String house;

    @SerializedName("Dead")
    @Expose
    private int dead;

    @SerializedName("VidhanSabha")
    @Expose
    private int voted;

    @SerializedName("VoterDone")
    @Expose
    private int voterdone;

    @SerializedName("OwnerName")
    @Expose
    private String owner_name;

    @SerializedName("OwnerMobileNo")
    @Expose
    private String owner_mobno;

    @SerializedName("StayOutside")
    @Expose
    private int stay_out_side;

    @SerializedName("AnniversaryDate")
    @Expose
    private String anniversary_date;

    @SerializedName("District")
    @Expose
    private String district;

    @SerializedName("LockedButSurvey")
    @Expose
    private String locked_but_survey;

    @SerializedName("Remark")
    @Expose
    private String remark;

    @SerializedName("Age")
    @Expose
    private int age;

    @SerializedName("Sex")
    @Expose
    private String gender;

    @SerializedName("Col1")
    @Expose
    private String form_6_8;

    @SerializedName("Col2")
    @Expose
    private String col2;

    @SerializedName("Col3")
    @Expose
    private String col3;

    @SerializedName("Col4")
    @Expose
    private String col4;

    @SerializedName("Col5")
    @Expose
    private String col5;

    @SerializedName("UpdateByUser")
    @Expose
    private String updatebyuser;

    @SerializedName("QC_UpdateByUser")
    @Expose
    private String qcupdatebyuser;

    @SerializedName("UpdatedDate")
    @Expose
    private String updateddate;

    @SerializedName("QC_UpdatedDate")
    @Expose
    private String qcupdateddate;

    public Voter(String updatedDate) {
        this.updateddate = updatedDate;
    }

    public int getVoter_cd() {
        return voter_cd;
    }

    public void setVoter_cd(int voter_cd) {
        this.voter_cd = voter_cd;
    }

    public int getVoter_id() {
        return voter_id;
    }

    public void setVoter_id(int voter_id) {
        this.voter_id = voter_id;
    }

    public int getList_no() {
        return list_no;
    }

    public void setList_no(int list_no) {
        this.list_no = list_no;
    }

    public int getAc_no() {
        return ac_no;
    }

    public void setAc_no(int ac_no) {
        this.ac_no = ac_no;
    }

    public int getWard_no() {
        return ward_no;
    }

    public void setWard_no(int ward_no) {
        this.ward_no = ward_no;
    }

    public int getNewvoter_id() {
        return newvoter_id;
    }

    public void setNewvoter_id(int newvoter_id) {
        this.newvoter_id = newvoter_id;
    }

    public int getFamily_no() {
        return family_no;
    }

    public void setFamily_no(int family_no) {
        this.family_no = family_no;
    }

    public int getSubloc_cd() {
        return subloc_cd;
    }

    public void setSubloc_cd(int subloc_cd) {
        this.subloc_cd = subloc_cd;
    }

    public int getBooth_cd() {
        return booth_cd;
    }

    public void setBooth_cd(int booth_cd) {
        this.booth_cd = booth_cd;
    }

    public String getBooth_name() {
        return booth_name;
    }

    public void setBooth_name(String booth_name) {
        this.booth_name = booth_name;
    }

    public String getBooth_name_mar() {
        return booth_name_mar;
    }

    public void setBooth_name_mar(String booth_name_mar) {
        this.booth_name_mar = booth_name_mar;
    }

    public int getSf() {
        return sf;
    }

    public void setSf(int sf) {
        this.sf = sf;
    }

    public int getSite_cd() {
        return site_cd;
    }

    public void setSite_cd(int site_cd) {
        this.site_cd = site_cd;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname_mar() {
        return fullname_mar;
    }

    public void setFullname_mar(String fullname_mar) {
        this.fullname_mar = fullname_mar;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_mar() {
        return name_mar;
    }

    public void setName_mar(String name_mar) {
        this.name_mar = name_mar;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getMiddlename_mar() {
        return middlename_mar;
    }

    public void setMiddlename_mar(String middlename_mar) {
        this.middlename_mar = middlename_mar;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMarnmar() {
        return marnmar;
    }

    public void setMarnmar(String marnmar) {
        this.marnmar = marnmar;
    }

    public String getMarnmar_det() {
        return marnmar_det;
    }

    public void setMarnmar_det(String marnmar_det) {
        this.marnmar_det = marnmar_det;
    }

    public String getSubcaste() {
        return subcaste;
    }

    public void setSubcaste(String subcaste) {
        this.subcaste = subcaste;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public int getVoted() {
        return voted;
    }

    public void setVoted(int voted) {
        this.voted = voted;
    }

    public int getVoterdone() {
        return voterdone;
    }

    public void setVoterdone(int voterdone) {
        this.voterdone = voterdone;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_mobno() {
        return owner_mobno;
    }

    public void setOwner_mobno(String owner_mobno) {
        this.owner_mobno = owner_mobno;
    }

    public int getStay_out_side() {
        return stay_out_side;
    }

    public void setStay_out_side(int stay_out_side) {
        this.stay_out_side = stay_out_side;
    }

    public String getAnniversary_date() {
        return anniversary_date;
    }

    public void setAnniversary_date(String anniversary_date) {
        this.anniversary_date = anniversary_date;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocked_but_survey() {
        return locked_but_survey;
    }

    public void setLocked_but_survey(String locked_but_survey) {
        this.locked_but_survey = locked_but_survey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getForm_6_8() {
        return form_6_8;
    }

    public void setForm_6_8(String form_6_8) {
        this.form_6_8 = form_6_8;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public String getCol5() {
        return col5;
    }

    public void setCol5(String col5) {
        this.col5 = col5;
    }

    public String getUpdatebyuser() {
        return updatebyuser;
    }

    public void setUpdatebyuser(String updatebyuser) {
        this.updatebyuser = updatebyuser;
    }

    public String getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(String updateddate) {
        this.updateddate = updateddate;
    }

    public String getQcupdatebyuser() {
        return qcupdatebyuser;
    }

    public void setQcupdatebyuser(String qcupdatebyuser) {
        this.qcupdatebyuser = qcupdatebyuser;
    }

    public String getQcupdateddate() {
        return qcupdateddate;
    }

    public void setQcupdateddate(String qcupdateddate) {
        this.qcupdateddate = qcupdateddate;
    }

}
