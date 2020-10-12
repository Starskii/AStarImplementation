package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame implements KeyListener {
    JFrame f = new JFrame();
    JPanel jp;
    Node[][] maze;
    int size = 1000;
    Node start;
    Node end;

    public GUI(Node[][] maze) {
        f.setTitle("Maze");
        f.setSize(size+50, size+50);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        jp = new GPanel();
        f.add(jp);
        f.setVisible(true);
        f.addKeyListener(this);

        this.maze = maze;
    }
    public void update(){
        maze = Main.generateMap();
        start = maze[Main.generateRandomLocation()][Main.generateRandomLocation()];
        end = maze[Main.generateRandomLocation()][Main.generateRandomLocation()];
        try {
            Main.generateInformedPath(start, end);
        }catch(Exception e) {
            System.out.println("No path found");
        }
        jp.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        update();
        System.out.println("Key Pressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    class GPanel extends JPanel {
        public GPanel() {
            f.setPreferredSize(new Dimension(size, size));
            f.setBackground(Color.GRAY);
        }

        @Override
        public void paintComponent(Graphics g) {
            int width = (size/maze.length);
            int height = (size/maze.length);
            for(int row = 0; row < maze.length; row++){
                for(int col = 0; col < maze.length; col++){
                    if(maze[col][row].visited)
                        if(col == start.x && row == start.y)
                            g.setColor(Color.BLUE);
                        else if(col == end.x && row == end.y)
                            g.setColor(Color.RED);
                        else
                            g.setColor(Color.GREEN);
                    else
                        g.setColor(Color.WHITE);
                    int xLoc = (size/maze.length)*col;
                    int yLoc = (size/maze.length)*row;
                    g.fillRect(xLoc, yLoc, width-1,height-1);
                    g.setColor(Color.BLACK);
                    g.drawString(col + "," +row,xLoc+(size/maze.length/10), yLoc+height-((size/maze.length)/10));
                }
            }

            LinkedList<Node> nodeList = new LinkedList<>();
            for(int i = 0; i < maze.length; i++){
                for(int j = 0; j < maze.length; j++){
                    nodeList.add(maze[i][j]);
                }
            }
            g.setColor(Color.BLACK);

            for(Node current: nodeList){
                for(Node walled: current.walledNodes){
                    int dy = getPixelPosition(current.y) - getPixelPosition(walled.y);
                    int dx = getPixelPosition(current.x) - getPixelPosition(walled.x);
                    int x1, x2, y1, y2;
                    if(dx == 0){
                        //Up Down
                        x1 = getPixelPosition(current.x);
                        y1 = getPixelPosition(current.y)+height;
                        x2 = getPixelPosition(walled.x)+width;
                        y2 = getPixelPosition(walled.y);
                        if(y1 == y2) {
                            g.drawLine(x1, y1, x2, y2);
                        }

                    }else if(dy == 0){
                        //Left Right
                        x1 = getPixelPosition(current.x)+width;
                        y1 = getPixelPosition(current.y);
                        x2 = getPixelPosition(walled.x);
                        y2 = getPixelPosition(walled.y)+height;
                        if(x1 == x2) {
                            g.drawLine(x1, y1, x2, y2);
                        }
                    }
                }
            }
        }
        public int getPixelPosition(int pos){
            pos = ((size/maze.length)*pos);
            return pos;
        }
    }
}