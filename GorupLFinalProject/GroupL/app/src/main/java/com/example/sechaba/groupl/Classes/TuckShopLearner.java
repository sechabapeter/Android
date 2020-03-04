package com.example.sechaba.groupl.Classes;

import java.util.Date;

/**
 * Created by DLMLAPTOP on 10/9/2017.
 */

public class TuckShopLearner {

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
    private Date created;
    private Date updated;
    public TuckShopLearner() {
        this.balancebefore = null;
        this.transactionamount = null;
        this.balanceafter = null;
        this.leranername = null;
        this.learnerclass = null;
    }

    private String balancebefore;

    public String getBalancebefore() {
        return balancebefore;
    }

    public void setBalancebefore(String balancebefore) {
        this.balancebefore = balancebefore;
    }

    public String getTransactionamount() {
        return transactionamount;
    }

    public void setTransactionamount(String transactionamount) {
        this.transactionamount = transactionamount;
    }

    public String getBalanceafter() {
        return balanceafter;
    }

    public void setBalanceafter(String balanceafter) {
        this.balanceafter = balanceafter;
    }

    public String getLeranername() {
        return leranername;
    }

    public void setLeranername(String leranername) {
        this.leranername = leranername;
    }

    public String getLearnerclass() {
        return learnerclass;
    }

    public void setLearnerclass(String learnerclass) {
        this.learnerclass = learnerclass;
    }

    private String transactionamount;
    private String balanceafter;
    private String leranername;
    private String learnerclass;



}
