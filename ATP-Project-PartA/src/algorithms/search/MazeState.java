package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.Objects;

public class MazeState extends AState{

    private Position p;



    public MazeState(Position p,int positionValue) {
        if (p == null)
            throw new NullPointerException("position is null");
        this.p = p;
        this.positionValue = positionValue;
        this.father = null;
    }

    public Position getPosition() {
        return p;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p);
    }

    public boolean equals(Object other) {
        if (other == null)
            throw new NullPointerException("null Object");
        if (!(other instanceof MazeState))
            return false;
        return ((MazeState)other).getPosition().equals(p);
    }

    public String toString() {
        return "{"+(p.getRowIndex())+","+(p.getColumnIndex())+"}";
    }



}
