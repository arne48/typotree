package org.hitzmann.typotree;

/**
 * Created by arne on 19.03.16.
 */
public class State {

    private String substring;
    private int remainingFaults;
    private Node node;
    private Node parent;

    @SuppressWarnings("unused")
	private State(){
    }

    public State(String substring, int remainingFaults, Node node, Node parent) {
        this.substring = substring;
        this.remainingFaults = remainingFaults;
        this.node = node;
        this.parent = parent;
    }

    public String getSubstring() {
        return substring;
    }

    public void setSubstring(String substring) {
        this.substring = substring;
    }

    public int getRemainingFaults() {
        return remainingFaults;
    }

    public void setRemainingFaults(int remainingFaults) {
        this.remainingFaults = remainingFaults;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
