package com.example.sechaba.groupl.Classes;

/**
 * Created by DLMLAPTOP on 9/19/2017.
 */

public class ReportLearner {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJustinfor() {
        return justinfor;
    }

    public void setJustinfor(String justinfor) {
        this.justinfor = justinfor;
    }

    public String getInforparents() {
        return inforparents;
    }

    public void setInforparents(String inforparents) {
        this.inforparents = inforparents;
    }

    public String getGetprofhelp() {
        return getprofhelp;
    }

    public void setGetprofhelp(String getprofhelp) {
        this.getprofhelp = getprofhelp;
    }

    public String getDatareport() {
        return datareport;
    }

    public void setDatareport(String datareport) {
        this.datareport = datareport;
    }


    private String title;
    private String justinfor;
    private String inforparents;
    private String getprofhelp;
    private String datareport;
    private String objectId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ReportLearner() {
        this.title = null;
        this.justinfor = null;
        this.inforparents = null;
        this.getprofhelp = null;
        this.datareport = null;
        this.status = null;
    }


}
