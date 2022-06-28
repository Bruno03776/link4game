package com.example.numberguessing;

public class Player {
    private String username; //player's username
    private String password; //player's password
    private int score; //player's score

    //Constructor: set up username, password and score for a new player
    public Player(String username, String password, int score) {
        this.username = username;
        this.password = password;
        this.score = score;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: return username of this Player
    public String getUsername() {
        return username;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: return password of this Player
    public String getPassword() {
        return password;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: return score of this Player
    public int getScore() {
        return score;
    }

    //Requires: new_score>=0
    //Modifies: this, score
    //Effects: change score to new_score
    public void setScore(int new_score) {
        score = new_score;
    }
}
