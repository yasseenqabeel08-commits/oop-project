package model;
import HotelRooms.*;

public class RoomPrefrences {
   private RoomType preferredType;
    private int preferredFloor;
    private boolean smokingAllowed;

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

    public String toString() {
        String type  = preferredType  != null ? preferredType.getTypeName() : "Any";
        String floor = preferredFloor != 0    ? "Floor " + preferredFloor   : "Any floor";
        return String.format("Preferences: type=%s | %s | smoking=%s", type, floor, smokingAllowed);
    }
}
