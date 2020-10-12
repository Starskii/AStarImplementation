package com.company;

import java.util.LinkedList;

public class Node{
    int x;
    int y;
    Node parent;
    boolean visited = false;
    double costFromStart;
    double costFromEnd;
    double fCost = Double.MAX_VALUE;
    LinkedList<Node> walledNodes = new LinkedList<>();
    LinkedList<Node> adjacentNodes = new LinkedList<>();
    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void calculateFCost(Node sender, Node goal){
        parent = sender;
        if(sender.x == this.x || sender.y == this.y){
            //this is adjacent to sender
            costFromStart = sender.costFromStart+1;
        }else{
            //this is diagonal to sender
            costFromStart = sender.costFromStart+(Math.sqrt(2));
        }
        //Pythagorean Theorem
        double xDif = Math.abs(this.x - goal.x);
        double yDif = Math.abs(this.y - goal.y);
        //Square the values
        xDif = xDif*xDif;
        yDif = yDif*yDif;
        //Cost from end is the sqrt of these two added together
        costFromEnd = Math.sqrt(xDif+yDif);
        fCost = costFromStart + costFromEnd;
    }
    public String printNode(){
        return "[" + this.x + "," + this.y + "] ";
    }
    public static void removeNeighbor(Node current, Node neighbor){
        if(current.adjacentNodes.contains(neighbor))
            current.adjacentNodes.remove(neighbor);
        if(neighbor.adjacentNodes.contains(current))
            neighbor.adjacentNodes.remove(current);

        if(!current.walledNodes.contains(neighbor))
            current.walledNodes.add(neighbor);
        if(!neighbor.walledNodes.contains(current))
            neighbor.walledNodes.add(current);
    }
}