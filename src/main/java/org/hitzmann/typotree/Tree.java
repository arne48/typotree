package org.hitzmann.typotree;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by arne on 18.03.16.
 */
public class Tree {

	private Node root = new Node();

	public void addWord(String word) {

		if (root.containsChildOfValue(word.charAt(0))) {
			root.getChildOfValue(word.charAt(0)).addChild(word.substring(1, word.length()));
		} else {
			root.addChild(word);
		}

	}

	public void addWordList(Collection<String> words) {
		for (String word : words) {
			addWord(word);
		}
	}

	public boolean checkWord(String word, int maxDistance) {
		// Push root
		Stack<State> queue = new Stack<State>();
		queue.push(new State(word, maxDistance, root, root));

		while (!queue.empty()) {
			State current = queue.pop();

			if (current.getSubstring().length() == 0 && current.getNode().isEndNode()) {
				return true;
			} else if (current.getSubstring().length() == 0 && !current.getNode().isEndNode()) {
				continue;
			} else if (current.getRemainingFaults() < 0) {
				continue;
			} else if (current.getNode().getChildren().isEmpty()
					&& current.getRemainingFaults() < current.getSubstring().length()) {
				continue;
			}

			String newSubstring = current.getSubstring().substring(1, current.getSubstring().length());
			Character firstCharacter = current.getSubstring().charAt(0);
			int newIdx = current.getRemainingFaults() - 1;

			// Wrong Character (Levenshtein edit)
			for (Map.Entry<Character, Node> child : current.getNode().getChildren().entrySet()) {
				if (!child.getKey().equals(firstCharacter)) {
					queue.push(new State(newSubstring, newIdx, child.getValue(), current.getNode()));
				}
			}
			
			// Additional Character (Levenshtein remove)
			queue.push(new State(newSubstring, newIdx, current.getNode(), current.getParent()));

			// Missing Character (Levenshtein add)
			for (Map.Entry<Character, Node> child : current.getNode().getChildren().entrySet()) {
				queue.push(new State(current.getSubstring(), newIdx, child.getValue(), current.getNode()));
			}

			// If available put right character on top of stack to follow this
			// path in a greedy way
			if (current.getNode().containsChildOfValue(firstCharacter)) {
				queue.push(new State(newSubstring, current.getRemainingFaults(),
						current.getNode().getChildOfValue(firstCharacter), current.getNode()));
			}
		}
		return false;
	}

}