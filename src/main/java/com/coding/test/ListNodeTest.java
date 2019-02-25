package com.coding.test;

import java.util.Arrays;
import java.util.List;

public class ListNodeTest {

    public static void main(String[] args) {
        LinkedListCreator linkedListCreator = new LinkedListCreator();
        Node node = linkedListCreator.createLinkedList(Arrays.asList(1, 2, 3, 4, 5));
        Node node2 = reverserLinkedList(node);
        linkedListCreator.printList(node2);
    }

    private static Node reverserLinkedList(Node node) {
        //指向空，可以想象成位于第一个节点之前
        Node newNode = null;
        //指向第一个节点
        Node curNode = node;

        //循环中，使用第三变量事先保存curNode的后面一个节点
        while (curNode != null) {
            Node tempNode = curNode.getNext();
            //把curNode反向往前指
            curNode.setNext(newNode);
            //newNode向后移动
            newNode = curNode;
            //curNode 向后移动
            curNode = tempNode;
        }

        return newNode;
    }
}

class LinkedListCreator {
    Node createLinkedList(List<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }
        Node headNode = new Node(list.get(0));
        Node tempNode = createLinkedList(list.subList(1, list.size()));
        headNode.setNext(tempNode);
        return headNode;
    }

    //测试方便的打印函数
    void printList(Node node) {
        while (node != null) {
            System.out.print(node.getValue());
            System.out.print(" ");
            node = node.getNext();
        }
        System.out.println();
    }
}

class Node {

    //链表用于存储值
    private final int value;

    //指向下一个节点  理解为Node next更加恰当
    private Node next;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }

    public int getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}