package com.ytu.sad.persistence.enums;

public enum PositionEnum {
    GK,
    CB, LB, RB,
    CDM, CM, CAM,
    LW, RW, ST;

    public String getName(){
        return this.name();
    }
}
