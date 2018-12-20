import java.util.Set;

public class Room {
    Room NorthRoom = null;
    Room SouthRoom = null;
    Room EastRoom = null;
    Room WestRoom = null;
    int Value = 0;

    public int GetLargestRoomValue(Set<Room> searchedRooms) {
        searchedRooms.add(this);
        int value = Value;
        if (NorthRoom != null && !searchedRooms.contains(NorthRoom)) {
            value = Math.max(NorthRoom.GetLargestRoomValue(searchedRooms), value);
        }
        if (SouthRoom != null && !searchedRooms.contains(SouthRoom)) {
            value = Math.max(SouthRoom.GetLargestRoomValue(searchedRooms), value);
        }
        if (EastRoom != null && !searchedRooms.contains(EastRoom)) {
            value = Math.max(EastRoom.GetLargestRoomValue(searchedRooms), value);
        }
        if (WestRoom != null && !searchedRooms.contains(WestRoom)) {
            value = Math.max(WestRoom.GetLargestRoomValue(searchedRooms), value);
        }
        return value;
    }

    public Room MakeRoom(String direction) {
        Room r;
        if (direction.equals("N")) {
            if (NorthRoom == null) {
                NorthRoom = new Room();
                NorthRoom.SouthRoom = this;
                NorthRoom.Value = Value + 1;
            }
            r = NorthRoom;
        } else if (direction.equals("S")) {
            if (SouthRoom == null) {
                SouthRoom = new Room();
                SouthRoom.NorthRoom = this;
                SouthRoom.Value = Value + 1;
            }
            r = SouthRoom;
        } else if (direction.equals("E")) {
            if (EastRoom == null) {
                EastRoom = new Room();
                EastRoom.WestRoom = this;
                EastRoom.Value = Value + 1;
            }
            r = EastRoom;
        } else {
            if (WestRoom == null) {
                WestRoom = new Room();
                WestRoom.EastRoom = this;
                WestRoom.Value = Value + 1;
            }
            r = WestRoom;
        }
        r.Value = Math.min(Value + 1, r.Value);
        return r;
    }
}
