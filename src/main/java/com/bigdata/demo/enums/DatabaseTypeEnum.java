package com.bigdata.demo.enums;

public enum DatabaseTypeEnum {

    BIGDATA("bigdata"), monitor("monitor");

    private String datasouceType;

    DatabaseTypeEnum(String datasouceType) {
        this.datasouceType = datasouceType;
    }

    public String getDatasouceType() {
        return datasouceType;
    }

    public void setDatasouceType(String datasouceType) {
        this.datasouceType = datasouceType;
    }
}
