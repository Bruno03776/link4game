package com.example.numberguessing;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;

public class Controller {
    public int player; //player who makes the next move, 0 for player1, 1 for player2
    public boolean stop; //a boolean value for checking whether the game is over
    public Label score1; //a label for showing player1's score
    public Label score2; //a label for showing player2's score
    public Button btnNewGame; //a button for starting a new game
    public ChoiceBox choicePlayer; //a choice box for choosing which player's score to save
    public TextField username; //a text field for entering the username of player
    public TextField password; //a text field for entering the password of player
    public Button btnSave; //a button for saving the score of a player
    public Button btnShow; //a button for showing the total score of a player
    public Label infoText; //a label for showing messages to the players
    public int player1; //player1's current score
    public int player2; //player2's current score
    public Box[][] boxes; //boxes on the board
    public ArrayList<Player> players; //all players stored in the file

    //Requires: nothing
    //Modifies: this, boxes, score1, score2, choicePlayer, players
    //Effects: initialize the game settings
    public void initialize() throws IOException {
        //create a board of boxes
        Board board = new Board();
        boxes = board.getBoxes();
        //set up the initial score for player1 and player2
        score1.setText("0");
        score2.setText("0");
        //set up the choicebox
        ArrayList<String> names = new ArrayList<>();
        names.add("Player1");
        names.add("Player2");
        choicePlayer.setItems(FXCollections.observableArrayList(names));
        choicePlayer.setValue("Player1");

        // read all players from the file "scores.txt"
        FileWriter fw = new FileWriter("scores.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.close();
        players = new ArrayList<>();
        FileReader fr = new FileReader("scores.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line=br.readLine()) != null) {
            String username = line;
            String password = br.readLine();
            int score = Integer.parseInt(br.readLine());
            Player p = new Player(username, password, score);
            players.add(p);
        }
    }

    //Requires: nothing
    //Modifies: this, player1, player2, player
    //Effects: change the color of a box when it is clicked
    public void check(MouseEvent mouseEvent) {
        //clear the infoText
        infoText.setText("");

        //the color of a box can change only if the game is not over yet
        if(!stop) {
            //get the row and column of the clicked box on the board
            String id = mouseEvent.getPickResult().getIntersectedNode().getId();
            int row = Integer.parseInt(id.substring(5, 6));
            int col = Integer.parseInt(id.substring(6, 7));
            Box box = boxes[row][col];
            int status = box.getStatus();
            //a box can change color only if its status is 0 (empty)
            if (status == 0) {
                Rectangle current = (Rectangle) mouseEvent.getSource();
                //color the box red for player1 or blue for player2
                if (player % 2 == 0) {
                    current.setFill(Color.RED);
                    box.setStatus(1);
                } else {
                    current.setFill(Color.BLUE);
                    box.setStatus(2);
                }
                //check if the game is over
                if (win(row, col, box.getStatus())) {
                    stop = true;
                    //check which player wins
                    if(box.getStatus()==1) {
                        player1++;
                        score1.setText(player1+"");
                    }
                    else {
                        player2++;
                        score2.setText(player2+"");
                    }
                } else {
                    player++;
                }
            }
        }
    }

    //Requires: 0<=row<=9, 0<=col<=9, status in {0, 1}
    //Modifies: nothing
    //Effects: return a boolean value indicating whether the current game is over
    public boolean win(int row, int col, int status) {
        // check if current row contains 4 continuous boxes with the same status
        for(int i=0; i<7; i++) {
            int count = 0;
            for(int j=i; j<i+4; j++) {
                if(boxes[row][j].getStatus()==status) {
                    count++;
                }
                else {
                    break;
                }
            }
            if(count==4) {
                return true;
            }
        }

        // check if current col contains 4 continuous boxes with the same status
        for(int i=0; i<7; i++) {
            int count = 0;
            for(int j=i; j<i+4; j++) {
                if(boxes[j][col].getStatus()==status) {
                    count++;
                }
                else {
                    break;
                }
            }
            if(count==4) {
                return true;
            }
        }

        // check if current left-top to right-bottom diagonal contains 4 continuous boxes with the same status
        int i = row;
        int j = col;
        if(i<j) {
            j = j - i;
            i = 0;
        }
        else {
            i = i - j;
            j = 0;
        }
        while(i<7 && j<7) {
            int count = 0;
            for(int k=0; k<4; k++) {
                if(boxes[i+k][j+k].getStatus()==status) {
                    count++;
                }
                else {
                    break;
                }
            }
            if(count==4) {
                return true;
            }
            i++;
            j++;
        }

        // check if current right-top to left-bottom diagonal contains 4 continuous boxes with the same status
        i = row;
        j = col;
        if(i<9-j) {
            j = j + i;
            i = 0;
        }
        else {
            i = i - (9-j);
            j = 9;
        }
        while(i<7 && j>2) {
            int count = 0;
            for(int k=0; k<4; k++) {
                if(boxes[i+k][j-k].getStatus()==status) {
                    count++;
                }
                else {
                    break;
                }
            }
            if(count==4) {
                return true;
            }
            i++;
            j--;
        }
        return false;
    }

    //Requires: btnNewGame is clicked
    //Modifies: this, boxes, stop, player
    //Effects: reset the status of all boxes on the board and allow the player who lost the last game to move first
    public void newGame(ActionEvent actionEvent) {
        //reset the status of all boxes
        for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                boxes[i][j].setStatus(0);
                Rectangle rec = (Rectangle) Main.scene.getRoot().lookup("#board"+i+j);
                rec.setFill(Color.WHITE);
            }
        }
        stop = false;
        //the player who lost the last game will be the player to make the first move
        player = (player+1) % 2;
    }

    //Requires: btnSave is clicked
    //Modifies: this, players, score1, score2
    //Effects: save the score of a player to the "scores.txt" file
    public void save(ActionEvent actionEvent) throws IOException {
        // clear the infoText
        infoText.setText("");
        // get username and password from the text fields
        String name = username.getText().trim();
        String code = password.getText().trim();
        // username and password cannot be empty
        if(name.length()==0 || code.length()==0) {
            infoText.setText("Error: User name and password cannot be empty!");
        }
        else {
            //check if username already exists
            Player current = null;
            boolean error = false;
            for(Player p : players) {
                if(p.getUsername().equals(name)) {
                    //check if username and password match
                    if(p.getPassword().equals(code)) {
                        current = p;
                    }
                    else {
                        infoText.setText("Error: User name and password do not match!");
                        error = true;
                    }
                }
            }
            //save the indicated player's score
            if(!error) {
                //get the score of the player and reset the player's current score
                int score = Integer.parseInt(score1.getText());
                if(choicePlayer.getSelectionModel().getSelectedIndex()==1) {
                    score = Integer.parseInt(score2.getText());
                    score2.setText("0");
                }
                else {
                    score1.setText("0");
                }
                //create a new player if the player is not saved in the file yet
                if(current==null) {
                    current = new Player(name, code, score);
                    players.add(current);
                }
                else {
                    current.setScore(current.getScore()+score);
                }
                //write the updated score to the "scores.txt" file
                FileWriter fw = new FileWriter("scores.txt");
                BufferedWriter bw = new BufferedWriter(fw);
                for(Player p : players) {
                    bw.write(p.getUsername());
                    bw.newLine();
                    bw.write(p.getPassword());
                    bw.newLine();
                    bw.write(p.getScore()+"");
                    bw.newLine();
                }
                bw.close();
                // inform the score has been saved
                infoText.setText("Score for user " + name + " has been updated!");
            }
        }
    }

    //Requires: btnShow is clicked
    //Modifies: nothing
    //Effects: show the total score of a player
    public void show(ActionEvent actionEvent) {
        // clear the infoText
        infoText.setText("");
        // get username and password from the text fields
        String name = username.getText().trim();
        String code = password.getText().trim();
        //check if username or password is empty
        if(name.length()==0 || code.length()==0) {
            infoText.setText("Error: User name and password cannot be empty!");
        }
        else {
            //check if the username exists
            boolean found = false;
            boolean error = false;
            for (Player p : players) {
                if (p.getUsername().equals(name)) {
                    //check if username and password match
                    if (p.getPassword().equals(code)) {
                        found = true;
                        infoText.setText("Score for user " + name + " is " + p.getScore() + "!");
                    } else {
                        infoText.setText("Error: User name and password do not match!");
                        error = true;
                    }
                }
            }
            if (!error && !found) {
                infoText.setText("Error: User " + name + " cannot be found!");
            }
        }
    }
}