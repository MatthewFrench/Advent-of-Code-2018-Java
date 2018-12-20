import java.util.ArrayList;
import java.util.List;

public class Path {
    List<String> Directions;
    Path branch1 = null;
    Path branch2 = null;

    public void executeRoom(Room r) {
        for (String direction : Directions) {
            r = r.MakeRoom(direction);
        }
        if (branch1 != null) {
            branch1.executeRoom(r);
        }
        if (branch2 != null) {
            branch2.executeRoom(r);
        }
    }

    public Path(List<String> s) {
        int currentIndex = 0;
        //Add the first parts to the directions
        currentIndex = s.indexOf("(");
        if (currentIndex == -1) {
            Directions = s;
            return;
        }
        Directions = s.subList(0, currentIndex);
        //Take everything inside the parenthesis and separate it
        int parenthesisCount = 1;
        currentIndex++;
        boolean foundPipe = false;
        int branch1Start = currentIndex;
        int branch1End = currentIndex;
        int branch2Start = currentIndex;
        int branch2End = currentIndex;
        List<String> branch1List = null;
        List<String> branch2List = null;
        for (; currentIndex < s.size(); currentIndex++) {
            String character = s.get(currentIndex);
            if (character.equals("(")) {
                parenthesisCount++;
            } else if (character.equals(")")) {
                parenthesisCount--;
                if (parenthesisCount == 0) {
                    currentIndex++;
                    break;
                }
            } else if (character.equals("|") && parenthesisCount == 1) {
                foundPipe = true;
                branch2Start = currentIndex + 1;
                continue;
            }
            if (parenthesisCount > 0) {
                if (!foundPipe) {
                    branch1End = currentIndex;
                } else {
                    branch2End = currentIndex;
                }
            }
        }
        branch1List = s.subList(branch1Start, branch1End+1);
        if (branch2Start <= branch2End) {
            branch2List = s.subList(branch2Start, branch2End+1);
        }
        if (currentIndex < s.size()-1) {
            List<String> listToAdd = s.subList(currentIndex, s.size());
            ArrayList<String> newBranch1 = new ArrayList<>();
            newBranch1.addAll(branch1List);
            newBranch1.addAll(listToAdd);
            branch1List = newBranch1;
            ArrayList<String> newBranch2 = new ArrayList<>();
            if (branch2List != null) {
                newBranch2.addAll(branch2List);
            }
            newBranch2.addAll(listToAdd);
            branch2List = newBranch2;
        }
        if (branch1List.size() > 0) {
            branch1 = new Path(branch1List);
        }
        if (branch2List != null && branch2List.size() > 0) {
            branch2 = new Path(branch2List);
        }
    }
}
