package com.example.numberguessing;

public class Board {
    private Box[][] boxes; // 2D array of Boxes

    //Constructor: create a size 10 by 10 Board of Boxes
    public Board() {
        boxes = new Box[10][10];
        for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                boxes[i][j] = new Box();
            }
        }
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: return the boxes in this Board
    public Box[][] getBoxes() {
        return boxes;
    }
}
