package tree;

import java.util.Arrays;

public class Heap<T extends Comparable> {
    private int size;
    private static int MAX_SIZE = 100;
    private T[] items;

    public Heap() {
        size = 0;
        items = (T[]) new Comparable[MAX_SIZE];
    }

    public int size() {
        return size;
    }

    private void swap(int valueX, int valueY) {
        T tmp = items[valueX];
        items[valueX] = items[valueY];
        items[valueY] = tmp;
    }

    public void insert(T data) {
        items[size] = data;
        heapifyUp(size);
        size++;
    }

    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;
        if (parent >= 0 && items[parent].compareTo(items[index]) < 0) {
            swap(index, parent);
            heapifyUp(parent);
        }
    }


    public T remove() {
        if (size == 0) {
            return null;
        }
        T tmp = items[0];
        items[0] = items[--size];
        // optional delete the item
        items[size] = null;
        heapifyDown(0);
        return tmp;
    }

    private void heapifyDown(int index) {
        int root = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        if (left < size && items[left].compareTo(items[root]) > 0) {
            root = left;
        }
        if (right < size && items[right].compareTo(items[root]) > 0) {
            root = right;
        }
        if (root != index) {
            swap(index, root);
            heapifyDown(root);
        }
    }

    @Override
    public String toString() {
        return "Heap{" +
                "size=" + size +
                ", items=" + Arrays.toString(items) +
                '}';
    }

    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<>();
        heap.insert(10);
        System.out.println(heap);
        heap.insert(15);
        System.out.println(heap);
        heap.insert(8);
        System.out.println(heap);
        heap.insert(9);
        System.out.println(heap);
        heap.insert(20);
        System.out.println(heap);
        heap.insert(32);
        System.out.println(heap);

        while (heap.size() > 0) {
            System.out.println(heap.remove());
        }
    }
}
