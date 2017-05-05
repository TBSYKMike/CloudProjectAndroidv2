package com.example.miketest.cloudprojectandroid;

/**
 * Created by Henrik on 2017-05-05.
 */

public class SensorEntity {

    private String partitionKey;
    private String rowKey;

    public SensorEntity(String partitionKey, String rowKey) {
        this.partitionKey = partitionKey;
        this.rowKey = rowKey;
    }

    public SensorEntity() { }

    private String email;
    private String phoneNumber;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
