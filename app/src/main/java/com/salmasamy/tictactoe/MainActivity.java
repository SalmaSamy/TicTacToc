package com.salmasamy.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int turn = 1;
    int[] vis = new int[10];
    boolean gameFinished = false;
    int[][] winPos = {{1,2,3},{4,5,6},{7,8,9},{1,4,7},{2,5,8},{3,6,9},{1,5,9},{3,5,7}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView winner = (TextView) findViewById(R.id.winner);
        winner.animate().alpha(0).setDuration(0);
    }


    public void showMove(View view) {
        ImageView img = (ImageView) view;
        if(!wrongMove(view)){
            if(turn == 1)
                img.setImageResource(R.drawable.xx);
            else
                img.setImageResource(R.drawable.oo);
            checkWinner();
        }
    }

    private boolean wrongMove(View view) {
        ImageView img = (ImageView) view;
        int cell = Integer.parseInt(img.getTag().toString());
        if(vis[cell] != 0 || gameFinished){
            wrongCell();
            return true;
        }
        else{
            vis[cell] = turn;
            return false;
        }
    }

    private void wrongCell() {
        Toast toast = new Toast(this);
        if(gameFinished)
            toast.makeText(this, "Game over, click reset to start new one", Toast.LENGTH_LONG).show();
        else
            toast.makeText(this, "already selected cell", Toast.LENGTH_SHORT).show();
    }

    private void checkWinner() {
        for(int[] wPos : winPos){
            if(vis[wPos[0]] != 0 && vis[wPos[0]] == vis[wPos[1]] && vis[wPos[0]] == vis[wPos[2]])
                winner(vis[wPos[0]]);
        }
        if(!gameFinished && !draw())
            changePlayerTurn();
    }

    private boolean draw() {
        for(int i=1 ; i<10 ; i++){
            if(vis[i] == 0) return false;
        }
        gameFinished = true;
        wrongCell();
        return true;
    }

    private void winner(int win) {
        TextView winner = (TextView) findViewById(R.id.winner);
        if(win == 1)
            winner.setText("Player 1 Wins");
        else
            winner.setText("Player 2 wins");
        winner.animate().alpha(1).rotation(360).setDuration(1000);
        gameFinished = true;
    }

    private void changePlayerTurn() {
        TextView turnText = (TextView) findViewById(R.id.turn);
        if(turn == 1){
            turnText.setText("Player 2 turn ");
            turn = 2;
        }
        else{
            turnText.setText("Player 1 turn ");
            turn = 1;
        }
    }

    public void reset(View view) {
        turn = 1;
        gameFinished = false;
        for(int i=0 ; i<10 ; i++) vis[i] = 0;

        TextView winner = (TextView) findViewById(R.id.winner);
        winner.setText("");
        winner.animate().alpha(0);
        TextView turn = (TextView) findViewById(R.id.turn);
        turn.setText("Player 1 turn ");

        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid);
        for(int i=0 ; i<9 ; i++){
            ImageView img = (ImageView) gridLayout.getChildAt(i);
            img.setImageResource(R.drawable.ww);
        }

    }
}
