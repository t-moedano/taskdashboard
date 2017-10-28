package com.dashboard.models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "dashboard-mobilehub-1772488431-TASKS")

public class TASKSDO {
    private String _nAME;
    private String _dESCRIPTION;
    private String _pRIORITY;
    private String _sTATUS;
    private String _uSERDONE;
    private String _uSERSENT;

    @DynamoDBHashKey(attributeName = "NAME")
    @DynamoDBAttribute(attributeName = "NAME")
    public String getNAME() {
        return _nAME;
    }

    public void setNAME(final String _nAME) {
        this._nAME = _nAME;
    }
    @DynamoDBAttribute(attributeName = "DESCRIPTION")
    public String getDESCRIPTION() {
        return _dESCRIPTION;
    }

    public void setDESCRIPTION(final String _dESCRIPTION) {
        this._dESCRIPTION = _dESCRIPTION;
    }
    @DynamoDBAttribute(attributeName = "PRIORITY")
    public String getPRIORITY() {
        return _pRIORITY;
    }

    public void setPRIORITY(final String _pRIORITY) {
        this._pRIORITY = _pRIORITY;
    }
    @DynamoDBAttribute(attributeName = "STATUS")
    public String getSTATUS() {
        return _sTATUS;
    }

    public void setSTATUS(final String _sTATUS) {
        this._sTATUS = _sTATUS;
    }
    @DynamoDBAttribute(attributeName = "USER_DONE")
    public String getUSERDONE() {
        return _uSERDONE;
    }

    public void setUSERDONE(final String _uSERDONE) {
        this._uSERDONE = _uSERDONE;
    }
    @DynamoDBAttribute(attributeName = "USER_SENT")
    public String getUSERSENT() {
        return _uSERSENT;
    }

    public void setUSERSENT(final String _uSERSENT) {
        this._uSERSENT = _uSERSENT;
    }

}
