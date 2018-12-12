import java.util.*;

public class Main {

    public static void main(String[] args) {
        part1();
        part2();
    }
    public static void part1() {
        //Part 1
        String originalInput = Input.get();
        System.out.println("Starting");
        String[] splitInput = originalInput.split("\n");

        HashMap<String, Node> nodes = new HashMap<>();

        //Step P must be finished before step O can begin
        for (String piece: splitInput) {
            String firstStep = piece.substring(piece.indexOf(" ") + 1, piece.indexOf(" ") + 2);
            String secondStep = piece.substring(piece.lastIndexOf(" can begin") - 1, piece.lastIndexOf(" can begin"));
            System.out.println(firstStep + " -> " + secondStep);
            Node firstNode = null;
            Node secondNode = null;
            if (nodes.containsKey(firstStep)) {
                firstNode = nodes.get(firstStep);
            } else {
                firstNode = new Node(firstStep);
                nodes.put(firstStep, firstNode);
            }
            if (nodes.containsKey(secondStep)) {
                secondNode = nodes.get(secondStep);
            } else {
                secondNode = new Node(secondStep);
                nodes.put(secondStep, secondNode);
            }
            firstNode.FinishNodesAfter.add(secondNode);
            secondNode.FinishNodesBefore.add(firstNode);
        }

        boolean finishingNodes = true;
        int step = 0;
        while (finishingNodes) {
            finishingNodes = false;
            step++;
            ArrayList<Node> availableNodes = new ArrayList<>();
            for (Map.Entry<String, Node> e : nodes.entrySet()) {
                Node n = e.getValue();
                if (!n.IsFinished && !n.HasUnfinishedNodesBefore() && !availableNodes.contains(n)) {
                    availableNodes.add(n);
                }
            }

            availableNodes.sort((node1, node2) -> {
                return node1.Letter.compareTo(node2.Letter);
            });

            if (availableNodes.size() > 0) {
                Node firstNode = availableNodes.get(0);
                finishingNodes = true;
                firstNode.IsFinished = true;
                firstNode.Step = step;
            }
        }

        System.out.println("Finished nodes");

        HashSet<Node> nodeSet = new HashSet<>();
        for (Map.Entry<String, Node> e: nodes.entrySet()) {
            nodeSet.add(e.getValue());
            if (!e.getValue().IsFinished) {
                System.out.println("Node not finished!");
            } else {
                System.out.println("Node " + e.getValue().Letter + " finished in step " + e.getValue().Step);
            }
        }
        Node[] arr = nodeSet.toArray(new Node[0]);
        Arrays.sort(arr, (node1, node2) -> {
            if (node1.Step < node2.Step) {
                return -1;
            } else if (node1.Step > node2.Step) {
                return 1;
            } else {
                return node1.Letter.compareTo(node2.Letter);
            }
        });
        String order = "";
        for (Node n : arr) {
            order += n.Letter;
        }
        System.out.println(order);
    }


    public static void part2() {
        //Part 1
        String originalInput = Input.get();
        System.out.println("Starting");
        String[] splitInput = originalInput.split("\n");

        HashMap<String, Node2> nodes = new HashMap<>();

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        //Each step takes second corresponding to letter
        //60 seconds + letter
        //a = 1, b = 2, c = 3

        //Need duration it would take all steps
        //5 workers


        //Step P must be finished before step O can begin
        for (String piece: splitInput) {
            String firstStep = piece.substring(piece.indexOf(" ") + 1, piece.indexOf(" ") + 2);
            String secondStep = piece.substring(piece.lastIndexOf(" can begin") - 1, piece.lastIndexOf(" can begin"));
            System.out.println(firstStep + " -> " + secondStep);
            Node2 firstNode = null;
            Node2 secondNode = null;
            if (nodes.containsKey(firstStep)) {
                firstNode = nodes.get(firstStep);
            } else {
                firstNode = new Node2(firstStep);
                firstNode.TotalSeconds = 60 + alphabet.indexOf(firstStep) + 1;
                firstNode.SecondsLeft = firstNode.TotalSeconds;
                nodes.put(firstStep, firstNode);
            }
            if (nodes.containsKey(secondStep)) {
                secondNode = nodes.get(secondStep);
            } else {
                secondNode = new Node2(secondStep);
                secondNode.TotalSeconds = 60 + alphabet.indexOf(secondStep) + 1;
                secondNode.SecondsLeft = secondNode.TotalSeconds;
                nodes.put(secondStep, secondNode);
            }
            firstNode.FinishNodesAfter.add(secondNode);
            secondNode.FinishNodesBefore.add(firstNode);
        }



        boolean finishingNodes = true;
        int step = 0;
        int totalWorkers = 5;
        ArrayList<Node2> nodesInProgress = new ArrayList<>();
        String finishedNodes = "";
        while (finishingNodes || nodesInProgress.size() > 0) {
            finishingNodes = false;

            for (Node2 node : nodesInProgress) {
                if (node.SecondsLeft == 0) {
                    node.IsFinished = true;
                    node.Step = step;
                    finishingNodes = true;
                    finishedNodes += node.Letter;
                }
                node.SecondsLeft--;
            }
            nodesInProgress.removeIf((n)->n.IsFinished);


            ArrayList<Node2> availableNodes = new ArrayList<>();
            for (Map.Entry<String, Node2> e : nodes.entrySet()) {
                Node2 n = e.getValue();
                if (!n.IsFinished && !n.HasUnfinishedNodesBefore() && !availableNodes.contains(n) && !nodesInProgress.contains(n)) {
                    availableNodes.add(n);
                }
            }

            availableNodes.sort((node1, node2) -> {
                return node1.Letter.compareTo(node2.Letter);
            });

            while (availableNodes.size() > 0 && nodesInProgress.size() < totalWorkers) {
                Node2 firstNode = availableNodes.get(0);
                availableNodes.remove(0);
                nodesInProgress.add(firstNode);
                finishingNodes = true;
                firstNode.SecondsLeft--;
            }

            String output = step + " - ";
            for (int i = 0; i < totalWorkers; i++) {
                if (nodesInProgress.size() > i) {
                    output += nodesInProgress.get(i).Letter + " - ";
                } else {
                    output += ". - ";
                }
            }
            output += finishedNodes;
            System.out.println(output);

            step++;
        }


        System.out.println("Finished nodes");

        HashSet<Node2> nodeSet = new HashSet<>();
        for (Map.Entry<String, Node2> e: nodes.entrySet()) {
            nodeSet.add(e.getValue());
            if (!e.getValue().IsFinished) {
                System.out.println("Node not finished!");
            } else {
                System.out.println("Node " + e.getValue().Letter + " finished in step " + e.getValue().Step);
            }
        }
        Node2[] arr = nodeSet.toArray(new Node2[0]);
        Arrays.sort(arr, (node1, node2) -> {
            if (node1.Step < node2.Step) {
                return -1;
            } else if (node1.Step > node2.Step) {
                return 1;
            } else {
                return node1.Letter.compareTo(node2.Letter);
            }
        });
        String order = "";
        for (Node2 n : arr) {
            order += n.Letter;
        }
        System.out.println(order);

    }
}
