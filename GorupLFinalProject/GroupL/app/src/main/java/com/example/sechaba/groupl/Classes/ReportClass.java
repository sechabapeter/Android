package com.example.sechaba.groupl.Classes;

import java.util.Date;

/**
 * Created by DLMLAPTOP on 9/14/2017.
 */

public class ReportClass {

    public ReportClass() {
        this.description = null;
        this.clases = null;
        this.problems = null;
        this.showdata=null;
        this.status= null;
        this.emails = null;


    }
    private String description;

    public String getShowdata() {
        return showdata;
    }

    public void setShowdata(String showdata) {
        this.showdata = showdata;
    }

    private  String showdata;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClases() {
        return clases;
    }

    public void setClases(String clases) {
        this.clases = clases;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    private String clases;
    private String problems;
    private  String emails;

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }



    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }





    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    private Date created;
    private Date updated;
}
