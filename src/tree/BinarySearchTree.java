package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BinarySearchTree {
    private static class Node {
        Node parent;
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            parent = null;
            left = null;
            right = null;
        }
    }
    Node root;
    int size;

    BinarySearchTree() {
        size = 0;
        root = null;
    }

    public boolean insert(int data) {
        if (root == null) {
            root = new Node(data);
        } else {
            Node parent = null, current = root;
            while (current != null) {
                if (data < current.data) {
                    parent = current;
                    current = current.left;
                } else if (data > current.data) {
                    parent = current;
                    current = current.right;
                } else {
                    // this is equal or not equal to the value
                    return false;
                }
            }
            if (data < parent.data) {
                parent.left = new Node(data);
                parent.left.parent = parent;
            } else {
                parent.right = new Node(data);
                parent.right.parent = parent;
            }
        }
        size++;
        return true;
    }

    private void splice(Node node) {
        Node child, parent;
        if (node.left != null) {
            child = node.left;
        } else {
            child = node.right;
        }
        if (node == root) {
            root = child;
            parent = null;
        } else {
            parent = node.parent;
            if (parent.left == node) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }
        if (child != null) {
            child.parent = parent;
        }
        size--;
    }

    public void remove(int x) {
        Node node = find(x);
        // case 1 or 2 if node is a leaf or there is 1 child
        if (node.left == null || node.right == null) {
            splice(node);
        } else {
            // case 3 where node has 2 children
            // go to the right sub-tree then find the smallest value that has 1 or 0 children
            // smallest value on the right sub-tree is the most left value in that tree
            Node w = node.right;
            // go to left to find smallest node
            while (w.left != null) {
                w = w.left;
            }
            // swap data
            node.data = w.data;
            // remove that w node
            splice(w);
        }
    }

    public Node find(int x) {
        Node node = root;
        while (node != null) {
            if (x < node.data) {
                node = node.left;
            } else if (x > node.data) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    public void preOrderTraversal() {
        // visit node -> left -> right
        System.out.print("Pre-order traversal recursive: ");
        preOrderRecursive(root);
    }

    private void preOrderRecursive(Node node) {
        if (node != null) {
            System.out.print(" " + node.data);
            preOrderRecursive(node.left);
            preOrderRecursive(node.right);
        }
    }

    public void inOrderTraversal() {
        // visit left -> node -> right
        System.out.print("In-order traversal recursive: ");
        inOrderRecursive(root);
    }

    private void inOrderRecursive(Node node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.print(" " + node.data);
            inOrderRecursive(node.right);
        }
    }

    public void postOrderTraversal() {
        // visit left -> right -> node
        System.out.print("Post-order traversal recursive: ");
        postOrderRecursive(root);
    }

    private void postOrderRecursive(Node node) {
        if (node != null) {
            postOrderRecursive(node.left);
            postOrderRecursive(node.right);
            System.out.print(" " + node.data);
        }
    }

    public void breadthFirstTraversal() {
        System.out.print("Breadth-first traversal iterative: ");
        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.print(" " + node.data);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    public int treeHeight(Node node) {
        if (node == null) {
            return -1;
        }
        // get the height of the left
        int leftHeight = treeHeight(node.left);
        // get the height of the right
        int rightHeight = treeHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public boolean isBST() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        inorderCheck(linkedList, root);
        while (linkedList.size() > 2) {
            int tmp = linkedList.pollFirst();
            if (tmp > linkedList.peekFirst()) {
                return false;
            }
        }
        return true;
    }

    private void inorderCheck(LinkedList<Integer> linkedList, Node node) {
        if (node != null) {
            inorderCheck(linkedList, node.left);
            linkedList.add(node.data);
            inorderCheck(linkedList, node.right);
        }
    }

    public void backBone() {
        Node tmp = root;
        while (tmp.right != null) {
            if (tmp.left != null) {
                rightRotate(tmp);
                tmp = tmp.parent;
            } else {
                tmp = tmp.right;
            }
        }
        while (tmp.parent != null) {
            tmp = tmp.parent;
        }
        root = tmp;
    }

    private void rightRotate(Node nodeA) {
        Node parent = nodeA.parent;
        Node nodeB = nodeA.left;

        // node A point to node B right
        nodeA.left = nodeB.right;
        if (nodeB.right != null) {
            // node B right point back to node A
            nodeB.right.parent = nodeA;
        }
        // node B right now point to A
        nodeB.right = nodeA;
        // node A parent is B
        nodeA.parent = nodeB;
        // node b parent is the parent
        nodeB.parent = parent;
        if (parent != null) {
            if (parent.left == nodeA) {
                parent.left = nodeB;
            } else {
                parent.right = nodeB;
            }
        }
    }

    private Node leftRotate(Node nodeA) {
        Node parent = nodeA.parent;
        Node nodeB = nodeA.right;

        // node A point to node B right
        nodeA.right = nodeB.left;
        if (nodeB.left != null) {
            // node B right point back to node A
            nodeB.left.parent = nodeA;
        }
        // node B right now point to A
        nodeB.left = nodeA;
        // node A parent is B
        nodeA.parent = nodeB;
        // node b parent is the parent
        nodeB.parent = parent;
        if (parent != null) {
            if (parent.right == nodeA) {
                parent.right = nodeB;
            } else {
                parent.left = nodeB;
            }
        }
        return nodeB;
    }

    public void toPerfectBalancedTree() {
        int m = (int) (
                Math.pow(2, Math.floor(
                        Math.log10(size + 1) / Math.log10(2)
                )) - 1);
        Node current = root;
        for (int i = 0; i < size - m; i++) {
            if (current != null) {
                current = leftRotate(current);
                current = current.right;
            }
        }
        while (current.parent != null) {
            current = current.parent;
        }
        root = current;
        while (m > 1) {
            m = m / 2;
            current = root;
            for (int i = 0; i < m; i++) {
                if (current != null) {
                    current = leftRotate(current);
                    current = current.right;
                }
            }
            while (current.parent != null) {
                current = current.parent;
            }
            root = current;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        random.setSeed(48);
        BinarySearchTree bst = new BinarySearchTree();
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(20);
            bst.insert(x);
        }

        bst.inOrderTraversal();
        System.out.println();
        bst.preOrderTraversal();
        System.out.println();
        bst.postOrderTraversal();
        System.out.println();
        bst.breadthFirstTraversal();
        System.out.println();

        System.out.println(bst.treeHeight(bst.root));
        System.out.println(bst.isBST());

        bst.backBone();
        bst.breadthFirstTraversal();
        bst.toPerfectBalancedTree();
        System.out.println();
        bst.breadthFirstTraversal();
    }
}
