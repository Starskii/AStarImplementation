package com.company;

import java.util.*;

public class Main {
    public static final int MAPSIZE = 16;
    public static class Node{
        int x;
        int y;
        Node parent;
        boolean visited = false;
        double costFromStart;
        double costFromEnd;
        double fCost = Double.MAX_VALUE;
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
    }
    }

    public static void addWalls(Node current, Node... neighbors){
        for(Node e: neighbors){
            Node.removeNeighbor(current,e);
        }
    }
    public static Node[][] generateMap(){
        Node[][] map = new Node[MAPSIZE][MAPSIZE];
        for(int col = 0; col < MAPSIZE; col++){
            for(int row = 0; row < MAPSIZE; row++){
                map[col][row] = new Node(col, row);
            }
        }

        for(int col = 0; col < MAPSIZE; col++){
            for(int row = 0; row < MAPSIZE; row++){
                if(col >= 1 && row >= 1 && col < MAPSIZE-1 && row < MAPSIZE-1){
                    map[col][row].adjacentNodes.add(map[col-1][row-1]);
                    map[col][row].adjacentNodes.add(map[col-1][row]);
                    map[col][row].adjacentNodes.add(map[col-1][row+1]);
                    map[col][row].adjacentNodes.add(map[col+1][row-1]);
                    map[col][row].adjacentNodes.add(map[col+1][row]);
                    map[col][row].adjacentNodes.add(map[col+1][row+1]);
                    map[col][row].adjacentNodes.add(map[col][row+1]);
                    map[col][row].adjacentNodes.add(map[col][row-1]);
                }else{
                    //Special Cases
                    if(col == 0){
                        if(row == 0){
                            map[col][row].adjacentNodes.add(map[col+1][row]);
                            map[col][row].adjacentNodes.add(map[col+1][row+1]);
                            map[col][row].adjacentNodes.add(map[col][row+1]);
                        }else if(row == MAPSIZE-1){
                            map[col][row].adjacentNodes.add(map[col][row-1]);
                            map[col][row].adjacentNodes.add(map[col+1][row-1]);
                            map[col][row].adjacentNodes.add(map[col+1][row]);
                        }else{
                            map[col][row].adjacentNodes.add(map[col][row-1]);
                            map[col][row].adjacentNodes.add(map[col][row+1]);
                            map[col][row].adjacentNodes.add(map[col+1][row-1]);
                            map[col][row].adjacentNodes.add(map[col+1][row]);
                            map[col][row].adjacentNodes.add(map[col+1][row+1]);
                        }
                    }else{
                        if(col == MAPSIZE-1){
                            if(row == 0){
                                map[col][row].adjacentNodes.add(map[col-1][row]);
                                map[col][row].adjacentNodes.add(map[col-1][row+1]);
                                map[col][row].adjacentNodes.add(map[col][row+1]);
                            }else if(row == MAPSIZE-1){
                                map[col][row].adjacentNodes.add(map[col-1][row]);
                                map[col][row].adjacentNodes.add(map[col-1][row-1]);
                                map[col][row].adjacentNodes.add(map[col][row-1]);
                            }else{
                                map[col][row].adjacentNodes.add(map[col-1][row-1]);
                                map[col][row].adjacentNodes.add(map[col-1][row]);
                                map[col][row].adjacentNodes.add(map[col-1][row+1]);
                                map[col][row].adjacentNodes.add(map[col][row-1]);
                                map[col][row].adjacentNodes.add(map[col][row+1]);
                            }
                        }else{
                            if(row == 0){
                                //Row at Min
                                map[col][row].adjacentNodes.add(map[col-1][row]);
                                map[col][row].adjacentNodes.add(map[col-1][row+1]);
                                map[col][row].adjacentNodes.add(map[col][row+1]);
                                map[col][row].adjacentNodes.add(map[col+1][row]);
                                map[col][row].adjacentNodes.add(map[col+1][row+1]);
                            }else{
                                //Row at Max
                                map[col][row].adjacentNodes.add(map[col-1][row-1]);
                                map[col][row].adjacentNodes.add(map[col-1][row]);
                                map[col][row].adjacentNodes.add(map[col][row-1]);
                                map[col][row].adjacentNodes.add(map[col+1][row-1]);
                                map[col][row].adjacentNodes.add(map[col+1][row]);
                            }
                        }
                    }
                }
            }
        }
        //Now we need to add the walls.
        //Row 0
        addWalls(map[7][0],map[8][0],map[8][1]);
        //Row 1
        addWalls(map[3][1],map[4][2]);
        addWalls(map[4][1], map[4][2],map[5][2]);
        addWalls(map[5][1],map[6][1],map[6][2],map[5][2]);
        addWalls(map[7][1],map[8][0],map[8][1],map[8][2]);
        addWalls(map[9][1],map[10][1]);
        addWalls(map[10][1],map[9][1],map[9][2],map[10][2],map[11][2]);
        addWalls(map[11][1],map[10][2],map[11][2]);
        addWalls(map[12][1],map[11][2]);
        //Row 2
        addWalls(map[3][2],map[4][2],map[4][3]);
        addWalls(map[4][2],map[3][2],map[3][3],map[4][1],map[5][1],map[3][1]);
        addWalls(map[5][2],map[4][1],map[5][1]);
        addWalls(map[6][2],map[5][1]);
        addWalls(map[7][2],map[8][1],map[8][2]);
        addWalls(map[8][2],map[7][2],map[7][1]);
        addWalls(map[9][2],map[10][1]);
        addWalls(map[10][2],map[10][1],map[11][1]);
        addWalls(map[11][2],map[10][1],map[11][1],map[12][1],map[12][2],map[12][3]);
        addWalls(map[12][2],map[11][2],map[11][3]);
        //Row 3
        addWalls(map[1][3],map[2][4]);
        addWalls(map[2][3],map[2][4],map[3][4]);
        addWalls(map[3][3],map[2][4],map[3][4],map[4][4],map[4][3],map[4][2]);
        addWalls(map[4][3],map[3][2],map[3][3],map[3][4]);
        addWalls(map[5][3],map[6][4]);
        addWalls(map[6][3],map[6][4]);
        addWalls(map[10][3],map[10][4],map[11][4]);
        addWalls(map[11][3],map[12][3],map[12][4],map[11][4],map[10][4],map[12][2]);
        addWalls(map[12][3],map[11][3],map[11][4],map[11][2],map[12][4],map[13][4]);
        addWalls(map[13][3],map[12][4],map[13][4]);
        addWalls(map[14][3],map[13][4]);
        //Row 4
        addWalls(map[1][4],map[2][5],map[2][4]);
        addWalls(map[2][4],map[1][4],map[1][3],map[2][3],map[3][3],map[1][5]);
        addWalls(map[3][4],map[2][3],map[3][3],map[4][3],map[4][4]);
        addWalls(map[4][4],map[3][4],map[3][3]);
        addWalls(map[5][4],map[6][4],map[6][5]);
        addWalls(map[6][4],map[5][5],map[5][4],map[5][3],map[6][3]);
        addWalls(map[10][4],map[10][3],map[9][3]);
        addWalls(map[11][4],map[10][3],map[11][3],map[12][3]);
        addWalls(map[12][4],map[11][3],map[12][3],map[13][3]);
        addWalls(map[13][4],map[12][3],map[13][3],map[14][3],map[14][4],map[14][5]);
        addWalls(map[14][4],map[13][4],map[13][5]);
        //Row 5
        addWalls(map[1][5],map[2][4],map[2][5],map[2][6]);
        addWalls(map[2][5],map[1][5],map[1][4],map[1][6]);
        addWalls(map[5][5],map[6][4],map[6][5],map[6][6]);
        addWalls(map[6][5],map[5][4],map[5][5],map[5][6]);
        addWalls(map[7][5],map[7][6],map[8][6]);
        addWalls(map[8][5],map[7][6],map[8][6],map[9][6]);
        addWalls(map[9][5],map[8][6],map[9][6],map[10][6]);
        addWalls(map[10][5],map[9][6],map[10][6],map[11][6]);
        addWalls(map[11][5],map[10][6],map[11][6]);
        addWalls(map[12][5],map[14][4],map[14][5]);
        addWalls(map[14][5],map[13][5],map[13][4],map[13][6],map[14][6]);
        //Row 6
        addWalls(map[1][6],map[2][5],map[2][6],map[2][7]);
        addWalls(map[2][6],map[1][5],map[1][6],map[1][7],map[2][7],map[3][7]);
        addWalls(map[3][6],map[2][7],map[3][7]);
        addWalls(map[5][6],map[6][5],map[6][6],map[6][7]);
        addWalls(map[6][6],map[5][5],map[5][6],map[5][7]);
        addWalls(map[7][6],map[7][5],map[8][5]);
        addWalls(map[8][6],map[7][5],map[8][5],map[9][5]);
        addWalls(map[9][6],map[8][5],map[9][5],map[10][5],map[10][6],map[10][7]);
        addWalls(map[10][6],map[9][7],map[9][6],map[9][5],map[10][5],map[11][5]);
        addWalls(map[11][6],map[10][5],map[11][5]);
        addWalls(map[13][6],map[14][5]);
        addWalls(map[14][6],map[14][5]);
        //Row 7
        addWalls(map[1][7],map[2][6],map[2][7]);
        addWalls(map[2][7],map[3][6],map[2][6],map[1][6],map[1][7]);
        addWalls(map[3][7],map[2][6],map[3][6]);
        addWalls(map[5][7],map[6][6],map[6][7]);
        addWalls(map[6][7],map[5][6],map[5][7]);
        addWalls(map[9][7],map[10][6],map[10][7],map[10][8]);
        addWalls(map[10][7],map[9][6],map[9][7],map[9][8]);
        addWalls(map[12][7],map[12][8],map[13][8]);
        addWalls(map[13][7],map[12][8],map[13][8],map[14][8]);
        addWalls(map[14][7],map[13][8],map[14][8],map[15][8]);
        addWalls(map[15][7],map[14][8],map[15][8]);
        //Row 8
        addWalls(map[0][8],map[0][9],map[1][9]);
        addWalls(map[1][8],map[0][9],map[1][9],map[2][9]);
        addWalls(map[2][8],map[1][9],map[2][9],map[3][9]);
        addWalls(map[3][8],map[2][9],map[3][9]);
        addWalls(map[9][8],map[10][8],map[10][7]);
        addWalls(map[10][8],map[9][8],map[9][7]);
        addWalls(map[12][8],map[12][7],map[13][7]);
        addWalls(map[13][8],map[12][7],map[13][7],map[14][7]);
        addWalls(map[14][8],map[13][7],map[14][7],map[15][7]);
        addWalls(map[15][8],map[14][7],map[15][7]);
        //Row 9
        addWalls(map[0][9],map[0][8],map[1][8]);
        addWalls(map[1][9],map[0][8],map[1][8],map[2][8],map[2][9],map[2][10]);
        addWalls(map[2][9],map[1][8],map[2][8],map[3][8],map[1][9],map[1][10]);
        addWalls(map[3][9],map[2][8],map[3][8]);
        addWalls(map[5][9],map[6][9],map[6][10]);
        addWalls(map[6][9],map[5][9],map[5][10],map[6][10],map[7][10]);
        addWalls(map[7][9],map[6][10],map[7][10]);
        addWalls(map[9][9],map[9][10],map[10][10]);
        addWalls(map[10][9],map[9][10],map[10][10],map[11][10]);
        addWalls(map[11][9],map[10][10],map[11][10]);
        addWalls(map[13][9],map[14][10]);
        addWalls(map[14][9],map[14][10]);
        //Row 10
        addWalls(map[1][10],map[2][9],map[2][10],map[2][11]);
        addWalls(map[2][10],map[1][9],map[1][10],map[1][11]);
        addWalls(map[5][10],map[6][9],map[6][10]);
        addWalls(map[6][10],map[5][10],map[5][9],map[6][9],map[7][9]);
        addWalls(map[7][10],map[6][9],map[7][9]);
        addWalls(map[9][10],map[9][9],map[10][9]);
        addWalls(map[10][10],map[9][9],map[10][9],map[11][9]);
        addWalls(map[11][10],map[10][9],map[11][9]);
        addWalls(map[13][10],map[14][10],map[14][11]);
        addWalls(map[14][10],map[13][10],map[13][11],map[13][9],map[14][9]);
        //Row 11
        addWalls(map[1][11],map[2][10],map[2][11]);
        addWalls(map[2][11],map[1][10],map[1][11],map[1][12],map[2][12]);
        addWalls(map[3][11],map[4][11],map[4][12]);
        addWalls(map[4][11],map[3][11],map[3][12]);
        addWalls(map[10][11],map[10][12],map[11][12]);
        addWalls(map[11][11],map[10][12],map[11][12],map[12][12]);
        addWalls(map[12][11],map[11][12],map[12][12],map[13][12]);
        addWalls(map[13][11],map[12][12],map[13][12],map[14][12],map[14][11],map[14][10]);
        addWalls(map[14][11],map[13][11],map[13][10]);
        //Row 12
        addWalls(map[1][12],map[2][11]);
        addWalls(map[2][12],map[2][11]);
        addWalls(map[3][12],map[4][11],map[4][12],map[4][13]);
        addWalls(map[4][12],map[3][11],map[3][12],map[3][13]);
        addWalls(map[5][12],map[6][12],map[6][13]);
        addWalls(map[6][12],map[5][12],map[5][13]);
        addWalls(map[7][12],map[8][12],map[8][13]);
        addWalls(map[8][12],map[7][12],map[7][13]);
        addWalls(map[10][12],map[10][11],map[11][11],map[11][12]);
        addWalls(map[11][12],map[10][12],map[10][11],map[11][11],map[12][11]);
        addWalls(map[12][12],map[11][11],map[12][11],map[13][11]);
        addWalls(map[13][12],map[12][11],map[13][11]);
        addWalls(map[14][12],map[13][11]);
        //Row 13
        addWalls(map[3][13],map[4][12],map[4][13],map[4][14]);
        addWalls(map[4][13],map[3][12],map[3][13],map[3][14],map[4][14],map[5][14]);
        addWalls(map[5][13],map[6][12],map[6][13],map[6][14],map[5][14],map[4][14]);
        addWalls(map[6][13],map[5][12],map[5][13]);
        addWalls(map[7][13],map[8][12],map[8][13],map[8][14]);
        addWalls(map[8][13],map[7][12],map[7][13],map[7][14],map[8][14]);
        //Row 14
        addWalls(map[3][14],map[4][13],map[4][14]);
        addWalls(map[4][14],map[3][14],map[3][13],map[4][13],map[5][13]);
        addWalls(map[5][14],map[4][13],map[5][13]);
        addWalls(map[7][14],map[8][13],map[8][14],map[8][15]);
        addWalls(map[8][14],map[7][15],map[7][14],map[7][13],map[8][13]);
        addWalls(map[10][14],map[11][14],map[11][15]);
        addWalls(map[11][14],map[10][14],map[10][15]);
        //Row 15
        addWalls(map[7][15],map[8][15],map[8][14]);
        addWalls(map[8][15],map[7][15],map[7][14]);
        addWalls(map[10][15],map[11][15],map[11][14]);
        addWalls(map[11][15],map[10][14],map[10][15]);

        //Now we need to remove the center 4 blocks in the middle
        for(int col = 5; col < 10; col++){
            for(int row = 5; row < 10; row++){
                addWalls(map[col][row],map[7][7],map[7][8],map[8][7],map[8][8]);
            }
        }
        return map;
    }
    public static void generateUninformedPath(Node start, Node end){
        Stack<Node> Q = new Stack();
        Q.push(start);
        while(!Q.isEmpty()){
            Node G = Q.pop();
            G.visited = true;
            if(G.equals(end)) {
                while(!G.equals(start)){
                    System.out.print("[" + G.x + "," + G.y + "] ");
                    G = G.parent;
                }
                System.out.print("[" + G.x + "," + G.y + "] ");
            }else{
                for(Node e: G.adjacentNodes){
                    if(!Q.contains(e) && e.visited == false) {
                        Q.push(e);
                        e.parent = G;
                    }
                }
            }
        }
    }
    public static Node getLowestCostNode(LinkedList<Node> list){
        double minCost = Double.MAX_VALUE;
        Node lowestNode = null;
        for(Node e :list){
            if(e.fCost < minCost){
                minCost = e.fCost;
                lowestNode = e;
            }
        }
        return lowestNode;
    }
    public static void generateInformedPath(Node start, Node end){
        LinkedList<Node> open = new LinkedList<>();
        LinkedList<Node> closed = new LinkedList<>();
        open.add(start);
        start.costFromStart = -1;
        start.calculateFCost(start,end);
        boolean pathFound = false;
        while(!pathFound){
            Node current = getLowestCostNode(open);
            open.remove(current);
            closed.add(current);
            if(current.equals(end)){
                //We have our path
                pathFound = true;
                outputPath(end);
            }
            for(Node e:current.adjacentNodes){
                if(!closed.contains(e)){
                    if(open.contains(e)){
                        if(current.costFromStart < e.parent.costFromStart){
                            e.calculateFCost(current, end);
                        }
                    }else{
                        e.calculateFCost(current, end);
                        open.add(e);
                    }
                }
            }
        }
    }
    public static void outputPath(Node e){
        Stack<Node> nodeStack = new Stack<>();
        while(!e.parent.equals(e)){
            nodeStack.push(e);
            e = e.parent;
        }
        nodeStack.push(e.parent);
        while(!nodeStack.isEmpty()){
            System.out.print("[" + nodeStack.peek().x + "," + nodeStack.pop().y + "] ");
        }

    }
    public static void main(String[] args) {
        Node[][] map = generateMap();
        Node start = map[0][0];
        Node end = map[15][15];
        System.out.println("\nPath from 0,0 to 15,15:");
        generateInformedPath(start,end);
        System.out.println();
        map = generateMap();
        start = map[15][15];
        end = map[0][0];
        System.out.println("\nPath from 15,15 to 0,0:");
        generateInformedPath(start,end);
    }
}
