package model;

import amy.RoomType;

public class RoomPrefrences {
    RoomType preferredType;
    int preferredFloor;
    boolean smokingAllowed;

    public RoomPrefrences(RoomType preferredType,int preferredFloor,boolean smokingAllowed ){
        this.preferredType=preferredType;
        this.preferredFloor=preferredFloor;
        this.smokingAllowed=smokingAllowed;
    }
    public RoomType getPreferredType() {
        return preferredType;
    }

    public void setPreferredType(RoomType preferredType) {
        this.preferredType = preferredType;
    }

    public int getPreferredFloor() {
        return preferredFloor;
    }

    public void setPreferredFloor(int preferredFloor) {
        this.preferredFloor = preferredFloor;
    }

    public boolean isSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }
}
