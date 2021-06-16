package musicplayer.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AVL {
    public class Node {
        public Song data;
        public Node left, right;
        
        public Node (Song data) {
            this.data = data;
            left = right = null;
        }
    }
    
    public Node root;
    private List<Song> songs;
    
    public void add(Song data) {
        Node newItem = new Node(data);
        if (root == null) {
            root = newItem;
        }
        else {
            root = recursiveInsert(root, newItem);
        }
    }
    
    private Node recursiveInsert(Node current, Node newItem) {
        if (current == null) {
            current = newItem;
            return current;
        }
        else if ((newItem.data).compareTo(current.data) < 0) {
            current.left = recursiveInsert(current.left, newItem);
            current = balancedTree(current);
        }
        else if (((newItem.data).compareTo(current.data) > 0)) {
            current.right = recursiveInsert(current.right, newItem);
            current = balancedTree(current);
        }
        return current;
    }
    private Node balancedTree (Node current) {
        int factor = balancedFactor(current);
        if (factor > 1) {
            if (balancedFactor(current.left) > 0) {
                current = rotateLL(current);
            } else {
                current = rotateLR(current);
            }
        } 
        else if (factor < -1) {
            if (balancedFactor(current.right) > 0) {
                current = rotateRL(current);
            } else {
                current = rotateRR(current);
            }
        }
        return current;
    }
    private int getHeight(Node current) {
        int height = 0;
        if (current != null) {
            int l = getHeight(current.left);
            int r = getHeight(current.right);
            int m = 0;
            if (l > r) {
                m = l;
            } else {
                m = r;
            }
            height = m + 1;
        }
        return height;
    }
    private int balancedFactor (Node current) {
        int l = getHeight(current.left);
        int r = getHeight(current.right);
        int bFactor = l - r;
        return bFactor;
    }
    private Node rotateRR(Node parent) {
        Node pivot = parent.right;
        parent.right = pivot.left;
        pivot.left = parent;
        return pivot;
    }
    private Node rotateLL(Node parent) {
        Node pivot = parent.left;
        parent.left = pivot.right;
        pivot.right = parent;
        return pivot;
    }
    private Node rotateLR(Node parent) {
        Node pivot = parent.left;
        parent.left = rotateRR(pivot);
        return rotateLL(parent);
    }
    private Node rotateRL(Node parent) {
        Node pivot = parent.right;
        parent.right = rotateLL(pivot);
        return rotateRR(parent);
    }
    
    public Song[] display() {
        songs = new ArrayList<>();
        if (root == null) {
            JOptionPane.showMessageDialog(null, "Play List is empty");
        }
        displayTree(root);
        Song[] array = new Song[songs.size()];
        for (int i = 0; i < songs.size(); i++) {
            array[i] = songs.get(i);
        }
        return array;
    }
    private void displayTree(Node current) {
        if (current != null) {
            displayTree(current.left);
            songs.add(current.data);
            displayTree(current.right);
        }
    }
    
    public void delete(Song target) {
        root = remove(root, target);
    }
    public Node remove(Node current, Song target) {
        Node parent;
        if (current == null) {
            JOptionPane.showMessageDialog(null, "There is no " + target + " on the lisf");
        }
        else {
            if (target.compareTo(current.data) < 0) {
                current.left = remove(current.left, target);
                if (balancedFactor(current) == -2) {
                    if (balancedFactor(current.right) <= 0) {
                        current = rotateRR(current);
                    } else {
                        current = rotateRL(current);
                    }
                }
            } 
            else if (target.compareTo(current.data) > 0) {
                current.right = remove(current.right, target);
                if (balancedFactor(current) == 2) {
                    if (balancedFactor(current.left) >= 0) {
                        current = rotateLL(current);
                    } else {
                        current = rotateLR(current);
                    }
                }
            }
            else {
                if (current.right != null) {
                    parent = current.right;
                    while (parent.left != null) {
                        parent = parent.left;
                    }
                    current.data = parent.data;
                    current.right = remove(current.right, parent.data);
                    if (balancedFactor(current) == 2) {
                        if (balancedFactor(current.left) >= 0) {
                            current = rotateLL(current);
                        } else {
                            current = rotateLR(current);
                        }
                    }
                } else {
                    return current.left;
                }
            }
        }
        return current;
    }
    
    public boolean search(Song key) {
        if (key.equals(find(key, root).data)) {
//            JOptionPane.showMessageDialog(null, key + "was found.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Nothing found!");
            return false;
        }
    }
    private Node find (Song key, Node current) {
        if (key.compareTo(current.data) < 0) {
            if (key.compareTo(current.data) == 0) {
                return current;
            } else {}
            if (current.left == null) {
                return current;
            }
            return find(key, current.left);
        } else {
            if (key.compareTo(current.data) == 0) {
                return current;
            } else {}
            if (current.right == null) {
                return current;
            }
            return find(key, current.right);
        }
    }
    public Song next(Song key) {
        if (key.equals(find(key, root).data)) {
            if (find(key, root).right != null) {
                return find(key, root).right.data;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public Song previous(Song key) {
        System.out.println("previous function");
        if (find(key, root).left != null) {
            System.out.println(find(key, root).left.data);
            return find(key, root).left.data;
        } else {
            return null;
        }
    }
}
