package com.example.numberguessing;

public class Box {
    private int status; // 0 means empty, 1 means red, 2 means blue

    //Constructor: set default status to 0
    public Box() {
        status = 0;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: return status of this Box
    public int getStatus() {
        return status;
    }

    //Requires: new_status in {0, 1, 2}
    //Modifies: this, status
    //Effects: change status to new_status
    public void setStatus(int new_status) {
        status = new_status;
    }
}
