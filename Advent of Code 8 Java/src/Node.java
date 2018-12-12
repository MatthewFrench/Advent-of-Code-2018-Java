import java.util.ArrayList;

public class Node {
    public int NumberOfChildNodes;
    public int NumberOfMetadataEntries;
    public ArrayList<Node> ChildNodes = new ArrayList<>();
    public ArrayList<Integer> MetadataEntries = new ArrayList<>();
    public Node() {
    }
    public int Generate(Integer[] input, int index) {
        //Number of child nodes
        NumberOfChildNodes = input[index];
        index++;
        //Number of metadata entries
        NumberOfMetadataEntries = input[index];
        index++;
        //Child nodes
        for (int i = 0; i < NumberOfChildNodes; i++) {
            Node n = new Node();
            index = n.Generate(input, index);
            ChildNodes.add(n);
        }
        //Metadata entries
        for (int i = 0; i < NumberOfMetadataEntries; i++) {
            MetadataEntries.add(input[index]);
            index++;
        }

        return index;
    }
    public int SumOfMetadata() {
        int value = 0;
        for (int entry : MetadataEntries) {
            value += entry;
        }
        for (Node n : ChildNodes) {
            value += n.SumOfMetadata();
        }
        return value;
    }

    public int SumOfMetadataByChildNodes() {
        int value = 0;
        if (ChildNodes.size() == 0) {
            for (int entry : MetadataEntries) {
                value += entry;
            }
        } else {
            for (int entry : MetadataEntries) {
                if (ChildNodes.size() >= entry) {
                    value += ChildNodes.get(entry-1).SumOfMetadataByChildNodes();
                }
            }
        }
        return value;
    }
}
