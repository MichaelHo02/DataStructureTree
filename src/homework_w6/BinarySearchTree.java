package homework_w6;

import java.util.Random;

public class BinarySearchTree<T extends Comparable> {
    static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        Node(T data) {
            this.data = data;
            left = right = null;
        }
    }

    Node<T> root;
    int size;

    BinarySearchTree() {
        size = 0;
        root = null;
    }

    public boolean insert(T data) {
        // if empty
        if (root == null) {
            root = new Node<>(data);
        } else {
            Node<T> parent = null, current = root;
            while (current != null) {
                if (data.compareTo(current.data) < 0) {
                    parent = current;
                    current = current.left;
                } else if (data.compareTo(current.data) > 0) {
                    parent = current;
                    current = current.right;
                } else {
                    // if value is equal or not equal to any value
                    return false;
                }
            }
            if (data.compareTo(parent.data) < 0) {
                parent.left = new Node<>(data);
            } else {
                parent.right = new Node<>(data);
            }
        }
        size++;
        return true;
    }

    public void preOrderTraversal() {
        System.out.print("This is the pre-order traversal: ");
        preOrderRecursive(root);
        System.out.println();
    }

    private void preOrderRecursive(Node<T> node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderRecursive(node.left);
            preOrderRecursive(node.right);
        }
    }

    public void inOrderTraversal() {
        System.out.print("This is the in-order traversal: ");
        inOrderRecursive(root);
        System.out.println();
    }

    private void inOrderRecursive(Node<T> node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.print(node.data + " ");
            inOrderRecursive(node.right);
        }
    }

    public void postOrderTraversal() {
        System.out.print("This is the post-order traversal: ");
        postOrderRecursive(root);
        System.out.println();
    }

    private void postOrderRecursive(Node<T> node) {
        if (node != null) {
            postOrderRecursive(node.left);
            postOrderRecursive(node.right);
            System.out.print(node.data + " ");
        }
    }

    public int getTreeHeight() {
        return treeHeight(root);
    }

    private int treeHeight(Node<T> node) {
        if (node == null) {
            return -1;
        }
        // get the height of the left
        int leftHeight = treeHeight(node.left);
        // get the height of the right
        int rightHeight = treeHeight(node.right);
        // calculate the height of the tree
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public boolean isBST() {
        MyQueue<T> queue = new MyQueue<>();
        inOrderCheck(queue, root);
        while (queue.size > 2) {
            T tmp = queue.deQueue();
            if (tmp.compareTo(queue.peek()) > 0) {
                return false;
            }
        }
        return true;
    }

    private void inOrderCheck(MyQueue<T> queue, Node<T> node) {
        if (node != null) {
            inOrderCheck(queue, node.left);
            queue.enQueue(node.data);
            inOrderCheck(queue, node.right);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Random random = new Random();
        random.setSeed(24);
        for (int i = 0; i < 20; i++) {
            int value = random.nextInt(40);
            System.out.println("This is the value: " + value + " " + bst.insert(value));
        }
        bst.preOrderTraversal();
        bst.inOrderTraversal();
        bst.postOrderTraversal();

        System.out.println(bst.getTreeHeight());
        System.out.println(bst.isBST());

    }

    private static class MyQueue<T> {
        static class Node<T> {
            T data;
            Node<T> next;
            Node(T data) {
                this.data = data;
                next = null;
            }
        }

        Node<T> head;
        Node<T> tail;
        int size;

        MyQueue() {
            size = 0;
            head = null;
            tail = null;
        }

        private boolean isEmpty() {
            return size == 0;
        }

        public void enQueue(T data) {
            if (isEmpty()) {
                head = new Node<>(data);
                tail = head;
                size++;
                return;
            }

            tail.next = new Node<>(data);
            tail = tail.next;
            size++;
        }

        public T peek() {
            return head.data;
        }

        public T deQueue() {
            if (isEmpty()) {
                return null;
            }
            T data = head.data;
            head = head.next;
            size--;
            return data;
        }
    }
}
