package com.app.iccomm.Model;

public class ProfileModel {
    private int profileId;
    private byte[] profileImg;
    private String profileName, profileNumber, profileMailId, profilePassword;

    public ProfileModel() {
    }

    public ProfileModel(int profileId, byte[] profileImg, String profileName, String profileNumber, String profileMailId, String profilePassword) {
        this.profileId = profileId;
        this.profileImg = profileImg;
        this.profileName = profileName;
        this.profileNumber = profileNumber;
        this.profileMailId = profileMailId;
        this.profilePassword = profilePassword;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileNumber() {
        return profileNumber;
    }

    public void setProfileNumber(String profileNumber) {
        this.profileNumber = profileNumber;
    }

    public String getProfileMailId() {
        return profileMailId;
    }

    public void setProfileMailId(String profileMailId) {
        this.profileMailId = profileMailId;
    }

    public String getProfilePassword() {
        return profilePassword;
    }

    public void setProfilePassword(String profilePassword) {
        this.profilePassword = profilePassword;
    }
}
