package models;

public class Screen {

    private int number;
    private int rows;
    private int cols;

    public Screen(int number, int rows, int cols){
        this.number = number;
        this.rows = rows;
        this.cols = cols;
    }

    public int getNumber(){
        return number;
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public int geScreenDetails(){
        return number + rows + cols;
    }
}
