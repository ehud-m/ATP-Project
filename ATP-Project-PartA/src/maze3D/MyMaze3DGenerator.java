package maze3D;

import algorithms.mazeGenerators.MyMazeGenerator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;


public class MyMaze3DGenerator extends AMaze3DGenerator{

    private Random rnd;

    public MyMaze3DGenerator() {
        rnd=new Random();
    }
    private Position3D randomEdge(Maze3D maze) {
        Random rand=new Random();
        int edge = rand.nextInt(6);
        int depth,row,col;
        if (edge<=1) { //Top or Bottom edge
            col=rand.nextInt(maze.getCols());
            depth=rand.nextInt(maze.getDepth());
            if (edge==0)
                row=0;
            else
                row= maze.getRows()-1;
        }
        else if (edge <=3){ //Left or Right edge
            row=rand.nextInt(maze.getRows());
            depth=rand.nextInt(maze.getDepth());
            if (edge==2)
                col=0;
            else
                col=maze.getCols()-1;
        }
        else { //Left or Right edge
            row=rand.nextInt(maze.getRows());
            col=rand.nextInt(maze.getCols());
            if (edge==4)
                depth=0;
            else
                depth=maze.getDepth()-1;
        }
        return new Position3D(depth,row,col);
    }
    @Override
    public Maze3D generate(int depth,int row, int col) {
        int stepSize = 2;
        if (depth*row*col>1000000)
            stepSize = 3;
        Maze3D maze = new Maze3D(depth,row,col);
        InitBoard(maze,1);
        Position3D start=randomEdge(maze);
        maze.setPositionValue(start,0);
        maze.setStart(start);
        Cell3D First = new Cell3D(start,GetMyNeibs(maze.getMap(),start,stepSize));
        Stack<Cell3D> stack = new Stack<Cell3D>();
        stack.push(First);
        DFS(maze,stack,stepSize);
        //maze.setGoal(new Position(8,8)); /////#######CHEckkkk
        //fixFrame(maze);
        setGoal(maze);
        return maze;
    }
    private void setGoal(Maze3D maze) {
        Position3D p;
        do {
            p=randomEdge(maze);
        }
        while (maze.getPositionValue(p)!=0 || p.equals(maze.getStartPosition()));
        maze.setGoal(p);
    }


  /*  private int fixRows(Maze maze, int[] Line, int flag,int row) {
        Random rand = new Random();
        int counter = 0;
        for (int i = 0; i < maze.getCols(); i++) {
            if (Line[i] == 1)
                counter++;
        }
        if (counter == maze.getCols() || counter == maze.getCols() - 1 ) { /////////////////bug
            counter = 0;
            for (int i = 0; i < maze.getCols(); i++) {
                if (maze.getPositionValue(new Position(row, i)) == 0) {
                    maze.setPositionValue(new Position(0, i), rand.nextInt(2));
                    counter = 1;
                }
            }
            return counter;
        }
        return  0;
    }

    private int fixLeftCols(Maze maze,int changes){
        Random rand = new Random();
        int [][] frame = maze.getMap();
        int counter = 0;
        for (int i = 0;i < maze.getRows(); i++) {
            if (frame[i][0] == 1)
                counter++;

        }
        if (counter == maze.getRows() - 1|| counter == maze.getRows()){
            for (int i = 0;i < maze.getRows(); i++) {
                if (maze.getPositionValue(new Position(i, 1)) == 0)
                    maze.setPositionValue(new Position(i,0),rand.nextInt(2));
            }
            return 1;
        }
        return  0;
    }
    private int fixRightCols(Maze maze,int changes){
        Random rand = new Random();
        int [][] frame = maze.getMap();
        int counter = 0;
        for (int i = 0;i < maze.getRows(); i++) {
            if (frame[i][maze.getCols()-1] == 1)
                counter++;

        }
        if (counter == maze.getRows() - 1 || counter == maze.getRows()){
            for (int i = 0;i < maze.getRows(); i++) {
                if (maze.getPositionValue(new Position(i, maze.getCols()-2)) == 0)
                    maze.setPositionValue(new Position(i,maze.getCols()-1),rand.nextInt(2));
            }
            return 1;
        }
        return  0;

    }

    private void fixFrame (Maze maze){
        int flag = 0;
        int [][] frame = maze.getMap();
        flag += fixRows(maze,frame[0],flag,1);
        flag += fixRows(maze,frame[maze.getRows()-1], flag,maze.getRows()-2);
        flag += fixRightCols(maze,flag);
        flag += fixLeftCols(maze,flag);
    }
*/
    private void DFS(Maze3D maze, Stack<Cell3D> stack,int stepSize){
        //null check
        int [][][] map = maze.getMap();
        int posD,posR,posC,curD,curR,curC;

        while(!stack.isEmpty()){
            Cell3D currentCell = stack.pop();
            if (currentCell.HaveNeighbours()){
                curD=currentCell.getPosition().getDepthIndex();
                curC=currentCell.getPosition().getColumnIndex();
                curR=currentCell.getPosition().getRowIndex();
                LinkedList<Position3D> neighbour = currentCell.GetCellNeighbour();
                for (Position3D pos: neighbour) {
                    posD=pos.getDepthIndex();
                    posR=pos.getRowIndex();
                    posC=pos.getColumnIndex();
               //     while(maze.getPositionValue(pos) != 1 && currentCell.HaveNeighbours())
               //         neighbour = currentCell.GetCellNeighbour();
                    if (map[posD][posR][posC] == 1){
                       // stack.push(currentCell);

                        map[posD][posR][posC]=0;
                        if (stepSize==3) {
                            map[(posD - curD) / 3 * 2 + curD][(posR - curR) / 3 * 2 + curR][(posC - curC) / 3 * 2 + curC] = 0;
                            map[(posD - curD) / 3 + curD][(posR - curR) / 3 + curR][(posC - curC) / 3 + curC] = 0;
                        }/*
                        if (stepSize==4) {
                            map[(posD-curD)/2+curD][(posR-curR)/2+curR][(posC-curC)/2+curC]=0;
                            map[(posD-curD)/4+curD][(posR-curR)/4+curR][(posC-curC)/4+curC]=0;
                            map[(posD-curD)/4*3+curD][(posR-curR)/4*3+curR][(posC-curC)/4*3+curC]=0;
                        }*/
                        else
                            map[(posD-curD)/2+curD][(posR-curR)/2+curR][(posC-curC)/2+curC]=0;
                        /*map[(posD-curD)/2+curD][(posR-curR)/2+curR][(posC-curC)/2+curC]=0;
                        map[(posD-curD)/4+curD][(posR-curR)/4+curR][(posC-curC)/4+curC]=0;
                        map[(posD-curD)/4*3+curD][(posR-curR)/4*3+curR][(posC-curC)/4*3+curC]=0;*/
                       // maze.setPositionValue(pos,0);
                       // maze.setPositionValue(currentCell.getPosition().getBetween(pos),0);
                        stack.push(new Cell3D(pos,GetMyNeibs(map,pos,stepSize)));
                    }
                }
            }
        }
    }

    private LinkedList<Position3D> GetMyNeibs(int[][][] map , Position3D position,int stepSize){
        if (position == null)
            return null;
        int posD,posR,posC;
        posD=position.getDepthIndex();
        posR=position.getRowIndex();
        posC=position.getColumnIndex();
        LinkedList<Position3D> neibs = new LinkedList<Position3D>();
        /*Position3D up = new Position3D( posD,posR-2,posC);
        Position3D down = new Position3D(posD,posR+2,posC);
        Position3D left = new Position3D(posD,posR,posC-2);
        Position3D right = new Position3D(posD,posR,posC+2);
        Position3D in = new Position3D( posD+2,posR,posC);
        Position3D out = new Position3D(posD-2,posR,posC);*/
        if (posD+stepSize<map.length && map[posD+stepSize][posR][posC]==1)
            neibs.add(new Position3D(posD+stepSize,posR,posC));
        if (posD-stepSize>=0 && map[posD-stepSize][posR][posC]==1)
            neibs.add(new Position3D(posD-stepSize,posR,posC));
        if (posR+stepSize<map[0].length  && map[posD][posR+stepSize][posC]==1)
            neibs.add(new Position3D(posD,posR+stepSize,posC));
        if ( posR-stepSize>=0 && map[posD][posR-stepSize][posC]==1)
            neibs.add(new Position3D(posD,posR-stepSize,posC));
        if ( posC+stepSize <map[0][0].length && map[posD][posR][posC+stepSize]==1)
            neibs.add(new Position3D(posD,posR,posC+stepSize));
        if (posC-stepSize >=0 && map[posD][posR][posC-stepSize]==1)
            neibs.add(new Position3D(posD,posR,posC-stepSize));
        Collections.shuffle(neibs,rnd);
        return neibs;





    }
}