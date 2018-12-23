import sun.nio.ch.Net;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

class Path {
    private List<String> Directions;
    private Path ParentPath = null;
    private ArrayList<Path> Branches = new ArrayList<>();
    private Path NextPath = null;
    private Set<Room> ProcessedRooms = new HashSet<>();

    Path(List<String> rawInput, Path parentPath) {
        ParentPath = parentPath;
        //Set the front part as directions
        //Set the inner parts as the inner branches
        //Set the ending part as the outer path

        if (rawInput.size() == 0) {
            Directions = new ArrayList<>();
            return;
        }

        int directionEnd = 0;
        int currentBranchStart = 0;
        int currentBranchEnd = 0;
        int afterPathStart = 0;
        int parenthesisCount = 0;
        for (int currentIndex = 0; currentIndex < rawInput.size(); currentIndex++) {
            String character = rawInput.get(currentIndex);
            if (character.equals("(")) {
                parenthesisCount++;
                if (parenthesisCount == 1) {
                    directionEnd = currentIndex;
                    currentBranchStart = currentIndex + 1;
                }
            } else if (character.equals(")")) {
                parenthesisCount--;
                if (parenthesisCount == 0) {
                    currentBranchEnd = currentIndex;
                    Branches.add( new Path(rawInput.subList(currentBranchStart, currentBranchEnd), this));
                    afterPathStart = currentIndex + 1;
                    break;
                }
            } else if (character.equals("|") && parenthesisCount == 1) {
                currentBranchEnd = currentIndex;
                Branches.add( new Path(rawInput.subList(currentBranchStart, currentBranchEnd), this));
                currentBranchStart = currentIndex + 1;
            }
        }

        if (directionEnd == 0) {
            Directions = new ArrayList<>();
        }
        if (Branches.size() == 0) {
            Directions = rawInput;
            return;
        }
        Directions = rawInput.subList(0, directionEnd);
        if (afterPathStart < rawInput.size()) {
            NextPath = new Path(rawInput.subList(afterPathStart, rawInput.size()), parentPath);
        }
    }

    int GetNumberOfPaths() {
        int count = 1;
        for (Path b : Branches) {
            count += b.GetNumberOfPaths();
        }
        if (NextPath != null) {
            count += NextPath.GetNumberOfPaths();
        }
        return count;
    }

    void executeRoom(Room r, AtomicLong completedPathCount) {
        if (ProcessedRooms.contains(r)) {
            return;
        }
        ProcessedRooms.add(r);
        //Run all directions
        for (String direction : Directions) {
            r = r.MakeRoom(direction);
        }
        if (Branches.size() == 0) {
            if (ParentPath != null) {
                ParentPath.finishRoom(r, completedPathCount);
            } else {
                this.finishRoom(r, completedPathCount);
            }
        } else {
            for (Path b : Branches) {
                b.executeRoom(r, completedPathCount);
            }
        }
    }
    private void finishRoom(Room r, AtomicLong completedPathCount) {
        if (NextPath != null) {
            NextPath.executeRoom(r, completedPathCount);
        } else if (ParentPath != null) {
            ParentPath.finishRoom(r, completedPathCount);
        } else {
            completedPathCount.addAndGet(1);
            //System.out.println("Finished paths: " + completedPathCount.get());
        }
    }
}
