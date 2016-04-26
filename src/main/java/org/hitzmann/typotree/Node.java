package org.hitzmann.typotree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arne on 18.03.16.
 */
public class Node {

    private Map<Character, Node> children = new HashMap<Character, Node>();

    private boolean endNode;

    public Node() {
        this.endNode = false;
    }

    public Node(boolean endNode) {
        this.endNode = endNode;
    }

    public Map<Character, Node> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, Node> children) {
        this.children = children;
    }

    public void addChild(String substring) {

        //Is max depth already reached by length of word? If not go deeper into the rabbit hole.
        if (substring.length() > 1) {
            if (!containsChildOfValue(substring.charAt(0))) {
                this.children.put(substring.charAt(0), new Node());
            }
            getChildOfValue(substring.charAt(0)).addChild(substring.substring(1, substring.length()));

            //If just one character is left it just has to be decided if the leaf already exists
            //or a new one has to be created. In any case the leaf will be set to an endpoint.
        } else {
            if (!containsChildOfValue(substring.charAt(0))) {
                this.children.put(substring.charAt(0), new Node(true));
            } else{
                getChildOfValue(substring.charAt(0)).setEndNode(true);
            }
        }
    }

    public boolean containsChildOfValue(Character value) {
        return children.containsKey(value);
    }

    public Node getChildOfValue(Character value) {
        return children.get(value);
    }

    public boolean isEndNode() {
        return endNode;
    }

    public void setEndNode(boolean endNode) {
        this.endNode = endNode;
    }
}
