package com.example.miketest.cloudprojectandroid;

import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Created by Henrik on 2017-05-05.
 */

public class SensorEntity extends TableServiceEntity{


    public SensorEntity(String partitionKey, String rowKey) {
        this.partitionKey = partitionKey;
        this.rowKey = rowKey;
    }

    public SensorEntity() { }

    private String SensorAccelerometerX;
    private String SensorAccelerometerY;
    private String SensorAccelerometerZ;
    private String SensorLight;
    private String SensorProximity;
    private String METAData;
    private String SensorPlaceholder1;
    private String SensorPlaceholder2;
    private String SensorPlaceholder3;
    private String SensorPlaceholder4;
    private String SensorPlaceholder5;
    private String SensorPlaceholder6;
    private String SensorPlaceholder7;
    private String SensorPlaceholder8;
    private String SensorPlaceholder9;


    public String getSensorAccelerometerX() {
        return SensorAccelerometerX;
    }

    public void setSensorAccelerometerX(String sensorAccelerometerX) {
        SensorAccelerometerX = sensorAccelerometerX;
    }

    public String getSensorAccelerometerY() {
        return SensorAccelerometerY;
    }

    public void setSensorAccelerometerY(String sensorAccelerometerY) {
        SensorAccelerometerY = sensorAccelerometerY;
    }

    public String getSensorAccelerometerZ() {
        return SensorAccelerometerZ;
    }

    public void setSensorAccelerometerZ(String sensorAccelerometerZ) {
        SensorAccelerometerZ = sensorAccelerometerZ;
    }

    public String getSensorLight() {
        return SensorLight;
    }

    public void setSensorLight(String sensorLight) {
        SensorLight = sensorLight;
    }

    public String getSensorProximity() {
        return SensorProximity;
    }

    public void setSensorProximity(String sensorProximity) {
        SensorProximity = sensorProximity;
    }

    public String getMETAData() {
        return METAData;
    }

    public void setMETAData(String METAData) {
        this.METAData = METAData;
    }

    public String getSensorPlaceholder1() {
        return SensorPlaceholder1;
    }

    public void setSensorPlaceholder1(String sensorPlaceholder1) {
        SensorPlaceholder1 = sensorPlaceholder1;
    }

    public String getSensorPlaceholder2() {
        return SensorPlaceholder2;
    }

    public void setSensorPlaceholder2(String sensorPlaceholder2) {
        SensorPlaceholder2 = sensorPlaceholder2;
    }

    public String getSensorPlaceholder3() {
        return SensorPlaceholder3;
    }

    public void setSensorPlaceholder3(String sensorPlaceholder3) {
        SensorPlaceholder3 = sensorPlaceholder3;
    }

    public String getSensorPlaceholder4() {
        return SensorPlaceholder4;
    }

    public void setSensorPlaceholder4(String sensorPlaceholder4) {
        SensorPlaceholder4 = sensorPlaceholder4;
    }

    public String getSensorPlaceholder5() {
        return SensorPlaceholder5;
    }

    public void setSensorPlaceholder5(String sensorPlaceholder5) {
        SensorPlaceholder5 = sensorPlaceholder5;
    }

    public String getSensorPlaceholder6() {
        return SensorPlaceholder6;
    }

    public void setSensorPlaceholder6(String sensorPlaceholder6) {
        SensorPlaceholder6 = sensorPlaceholder6;
    }

    public String getSensorPlaceholder7() {
        return SensorPlaceholder7;
    }

    public void setSensorPlaceholder7(String sensorPlaceholder7) {
        SensorPlaceholder7 = sensorPlaceholder7;
    }

    public String getSensorPlaceholder8() {
        return SensorPlaceholder8;
    }

    public void setSensorPlaceholder8(String sensorPlaceholder8) {
        SensorPlaceholder8 = sensorPlaceholder8;
    }

    public String getSensorPlaceholder9() {
        return SensorPlaceholder9;
    }

    public void setSensorPlaceholder9(String sensorPlaceholder9) {
        SensorPlaceholder9 = sensorPlaceholder9;
    }
}
