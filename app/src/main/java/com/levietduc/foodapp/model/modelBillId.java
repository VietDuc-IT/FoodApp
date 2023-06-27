package com.levietduc.foodapp.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class modelBillId implements Serializable {
    String nodeId;
    public  modelBillId(){}

    public modelBillId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
