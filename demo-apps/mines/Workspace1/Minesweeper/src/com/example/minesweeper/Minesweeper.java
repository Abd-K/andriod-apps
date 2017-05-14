package com.example.minesweeper;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Minesweeper extends Activity {
//public class Minesweeper extends ActionBarActivity {

	private TextView mineDisplay;
	private TextView timerDisplay;
	private ImageButton smilebtn;
	private ImageView timerPic;
	private Button settings, highScores;
	
	private final String selectedRow = "selectedRow";
	private final String selectedCol = "selectedCol";
	private final String selectedMine = "selectedMine";
	private final String selectedTime = "selectedTime";
	private final String selectedSound = "selectedSound";
	SharedPreferences pref;
	int setmine,setcol,setrow;
	private boolean timerState, soundState;
	
	// variables for game settings
	private int numberMines;
	private int boardCol;
	private int boardRow;
	private int boardSize= boardCol*boardRow;
	
	private int flags = 0;
	private int openedBoxes = 0;
	private int timer= 0;
	
	private Board [][] board2D;
	private int [] board1D;
	private int [] board1DText;
	
	private ImageAdapter mineboardAdapter;
	private GridView mineboardGrid;
	
	
	private final int interval = 1000; // 1 Second
	private Handler handler = new Handler();
	final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		 pref = Minesweeper.this.getSharedPreferences("pref", 0);
		 setrow = pref.getInt(selectedRow, 0);
		 setcol = pref.getInt(selectedCol, 0);
		 setmine = pref.getInt(selectedMine, 0);
		
		 boardCol =setcol;
		 boardRow = setrow;
		 numberMines = setmine;
		 boardSize= boardCol*boardRow;
		 
		 timerState = pref.getBoolean(selectedTime, true);
		 soundState = pref.getBoolean(selectedSound, true);
		 
		 board2D  = new Board[boardCol][boardRow];
		 board1D = new int [boardSize];
		 board1DText = new int [boardSize];
		 
		//declares gridview from mine_grid and sets custom adapter ImageAdapter
		//for what should be displayed in the grid
		mineboardGrid = (GridView) findViewById(R.id.mineGrid);
		mineboardAdapter = new ImageAdapter(this,board1D,board1DText);
		mineboardGrid.setAdapter(mineboardAdapter);
		mineboardGrid.setNumColumns(boardCol);
		mineDisplay = (TextView) findViewById(R.id.mineCount);
		timerDisplay = (TextView) findViewById(R.id.time);
		smilebtn = (ImageButton) findViewById(R.id.smiley);
		timerPic = (ImageView) findViewById(R.id.timerPic);
		
		highScores = (Button) findViewById(R.id.highScore);
		settings = (Button) findViewById(R.id.settings);
		
		Typeface displayFont;
		displayFont = Typeface.createFromAsset(getAssets(),"LCDM2U__.TTF");
		mineDisplay.setTypeface(displayFont);
		timerDisplay.setTypeface(displayFont);
		
		newGame();
		
	     OnItemClickListener mineboardListener1 = new OnItemClickListener() {
	         @Override
	         
	         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	 int flagState = 0;
	        	 convert1D2D(position, flagState);
	        	 
	        	 mineboardAdapter.notifyDataSetChanged();
	         }
		};

		OnItemLongClickListener mineboardListener2  = new OnItemLongClickListener() {
	         @Override
	         
	         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
	        	 int flagState = 1;
	        	 
	        	 convert1D2D(position, flagState);
	        	 
	        	 mineboardAdapter.notifyDataSetChanged();
	        	 return true;
	         }
		};
			smilebtn.setOnClickListener(new OnClickListener()		{
				public void onClick(View view)
				{
					
					restartGame();
					mineboardAdapter.notifyDataSetChanged();
				}
			});
					
			highScores.setOnClickListener(new OnClickListener()		{
				public void onClick(View view)
				{
					
					
					mineboardAdapter.notifyDataSetChanged();
				}
			});
			settings.setOnClickListener(new OnClickListener()		{
				public void onClick(View view)
				{
					
					Intent menu = new Intent("android.intent.action.MAINMENU");
					menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 startActivity(menu);
					 onPause();
					 timerState = pref.getBoolean(selectedTime, true);
					 soundState = pref.getBoolean(selectedSound, true);
					mineboardAdapter.notifyDataSetChanged();
				}
			});
		mineboardGrid.setOnItemClickListener(mineboardListener1);
		mineboardGrid.setOnItemLongClickListener(mineboardListener2);
	
	}
	
	/* the section of code below is for game setup, code for rules of the game is below that */
	// sets mine positions at the start, and notifies surrounding cells for area number
	private void newGridMines(int col, int row) {
		
		Random rand = new Random();
		for (int set = 0; set<numberMines;set++){
//			int col =0;
//			int row = 1;
//			board_2D[col][row].Set_Mine();
//			adjacent_number(col, row);
			
//			 col =1;
//			 row = 1;
//			 board_2D[col][row].Set_Mine();
//			adjacent_number(col, row);
//			
//			 col =2;
//			 row = 1;
//			 board_2D[col][row].Set_Mine();
//			adjacent_number(col, row);
			
			// testing correct mine position allocation
//			board_2D[0][0].Set_Mine();
//			board_2D[1][0].Set_Mine();
//			board_2D[col][row].Set_Mine();
			
			
			// inclusive of 0, exclusive of n, therefore no need for -1
			// set-- to check no oversetting same mine location
			// ensure game doesnt start on mine
			int colRandom = rand.nextInt((boardCol));
			int rowRandom = rand.nextInt((boardRow));
			if ((board2D[colRandom][rowRandom].getMine()== false) && (board2D[colRandom][rowRandom] != board2D[col][row])) {
			board2D[colRandom][rowRandom].setMine();
			adjacentNumber(colRandom, rowRandom);
			}
			else {
				set--;
			}
		}
		startTime();
	}
	
	 private void adjacentNumber(int col, int row){
		 addAdjacent((col-1),(row-1));
		 addAdjacent(col-1,row);	
		 addAdjacent(col-1,row+1);
		 addAdjacent(col,row-1);
		 addAdjacent(col,row+1);
		 addAdjacent(col+1,row-1);
		 addAdjacent(col+1,row);
		 addAdjacent(col+1,row+1);
		 
	 }
	 
	 // ensures adjacent cell being added to exists, to prevent adding to a non existing array which causes exception, then adds number to area int
	 private void addAdjacent(int col, int row){
		 if (col <boardCol && col >-1 && row >-1 && row < boardRow && (board2D[col][row]!=null) && (board2D[col][row].getMine()!=true))
		 {
			 board2D[col][row].addArea(); 
		 }	 
	 }
	 
	 private void convert1D2D(int position, int flagState) {
		 int col=0;
		 int row=0;
		 for(int index = 0; index <= position; ) {
			 col = index % (boardCol);
		     row = (index - col) / (boardCol);
		     index++;
		     
		 } 
		 if (board2D[col][row].getClickable() == true){
			 if ((openedBoxes == 0) && flagState ==0){
				 newGridMines(col, row);
			 }
			 if ((flagState == 0) && board2D[col][row].getFlag() == false) {
				 board2D[col][row].setOpen();
				 adjacentEmpty(col, row);
			 }
			 else if ((flagState == 1) && (board2D[col][row].getOpen() == false) && flags <= numberMines && flags >= 0) {
				 if ((board2D[col][row].getFlag() == false) && flags < numberMines){
					 flags++;
					 board2D[col][row].setFlag();
				 }
				 else  if ((board2D[col][row].getFlag() == true) && flags > 0){
					 flags--;
					 board2D[col][row].setFlag();
				 }
			 }
			 displayCell(col, row, position);
		 }
	 }
	 
	 public void convert2D1D(int col, int row){
		 int position = (row * boardCol) + col;
		 openCell(col, row, position);
		 
	 }
	
		private void displayCell(int col, int row, int position){
			if ((board2D[col][row].getOpen() == true) && board2D[col][row].getFlag() == false){
				openCell(col, row, position);
			}
				if (board2D[col][row].getOpen() == false){
					flagCell(col, row, position);
				}
			mineDisplay();
		}
		
	 // method called by many others, its function is to open the array position given.
	private void openCell(int col, int row, int position){
		smilebtn.setBackgroundResource(R.drawable.surprise);
			 if (board2D[col][row].getMine()==true){
				 gameOver( position);
			 }
			 else if (board2D[col][row].getMine()==false) {
				 board1D[position] = 1;
				 board1DText[position] =  board2D[col][row].getArea();
				 openedBoxes++;
				 
				 // measuring game won by number of opened boxes in comparision to number of boxes minus number of mines
				 // better than measuring with number of unopenboxes to number of mines, as its less buggy
				 if (openedBoxes == boardSize - numberMines){
					 gameWon();
				 }
			 }
			 
		
	}
	
	private void flagCell(int col, int row, int position) {
		if ((board2D[col][row].getFlag() == true)) {
			board1D[position] = 4;
		}
		else if ((board2D[col][row].getFlag() == false)){
			board1D[position] = 0;
		}
	}

	 // runs functions for all 8 neighbooring grid cells
	// if the opened cell has no niehgbouring mines, and is not a mine, then it checks its 8 neighbours 
	 private void adjacentEmpty(int col, int row){
		 if ((board2D[col][row].getArea() == 0) && (board2D[col][row].getMine() == false)){
		 openAdjacent((col-1),(row-1));
		 openAdjacent((col-1),row);	
		 openAdjacent((col-1),(row+1));
		 openAdjacent(col,(row-1));
		 openAdjacent(col,(row+1));
		 openAdjacent((col+1),(row-1));
		 openAdjacent((col+1),row);
		 openAdjacent((col+1),(row+1));
		 }
	 }
	 
	 // ensures adjacent cells exists, then setting their value to open if they have no mine, and are not open
	 // it then sets them open, and rerusn the adjacent_empty method with the new niehgbouring value, which will in turn check its niehgbours, till mines are in the area.
	 private void openAdjacent(int col, int row){
		 if (col < boardCol && col >-1 && row >-1 && row < boardRow &&  (board2D[col][row].getMine() == false) && (board2D[col][row].getOpen() == false) && (board2D[col][row].getFlag() == false) )
		 {
			 board2D[col][row].setOpen();
			 convert2D1D(col,row);
			 adjacentEmpty(col,row);
		 }	 
	 }
	 
	 public void gameOver(int position){
		 for (int row =0; row <(boardRow);){
				for(int col =0; col <(boardCol);){
					int pos = (row * boardCol) + col;
					if (board2D[col][row].getMine() == true){
						if (board2D[col][row].getFlag() == false){
							
							board1D[pos] = 2;
						}
					}
					else if ((board2D[col][row].getMine() == false) && (board2D[col][row].getFlag() == true)){
						board1D[pos] = 5;
					}
					
					col++;
				}
				row++;
			}
		 smilebtn.setBackgroundResource(R.drawable.sad);
		 board1D[position] = 3;
		 
		 disableButtons();
		 stopTime();
	 }
	 
	 public void gameWon(){
			for (int r=0;r<(boardRow);){
				for(int c=0;c<(boardCol);){
				
					if (board2D[c][r].getMine() == true){
						int pos = (r * boardCol) + c;
						board1D[pos] = 4;
					}
					c++;
				}
				r++;
			}
			
			smilebtn.setBackgroundResource(R.drawable.cool);
			// pop up message
			
			
			// disable buttons
			disableButtons();
			popDialog("Well done. time:"+" seconds! play again!", 3000, true, false);
			stopTime();
	 }
	 
	 public void disableButtons(){
			for (int row = 0;row < (boardRow);){
				for(int col =0; col <(boardCol);){
				
					board2D[col][row].setClickable();
					col++;
				}
				row++;
			}
	 }

	 public void new2DBoard(){
			/* create objects of class Board for the entire array */
			// arrays must start with position 0, hence increment after
			// Baord_col and Board_Row shouldnt be -1 as the loop stops at the array size.
			// row major order
		 
			for (int row = 0;row < (boardRow);){
				for(int col =0; col <(boardCol);){
				
					board2D[col][row] = new Board();
					col++;
				}
				row++;
			}
		 
	 }
	 public void reset2dBoards(){
			for (int row = 0;row < (boardRow);){
				for(int col =0; col <(boardCol);){
				
					board2D[col][row].resetValues();
					col++;
				}
				row++;
			}
	 }
	 public void reset1dBoards(){
		for (int index = 0; index < boardSize;){
			board1D[index] = 0;
		 	board1DText[index] = 0;
		 	index++;
		}

		 
	 }
	 public void newGame(){

		 mineDisplay();
		 timerDisplay.setText("000"); 
		 stopTime();
		 new2DBoard();
		 popDialog("Click on  smiley to reset", 3000, true, false);
			
	 }
	 public void restartGame(){
		  flags = 0;
		  openedBoxes = 0;
		  smilebtn.setBackgroundResource(R.drawable.smile);
//		  smile = (ImageButton) findViewById(R.id.Smiley);
		  mineDisplay();
		  timerDisplay.setText("000"); 
		  
		  stopTime();
		  reset2dBoards();
		  reset1dBoards();
		  popDialog("Click on  smiley to reset", 3000, true, false);
	 }
	 
	 public void mineDisplay(){
		  int mineCount = numberMines - flags;
		 if (mineCount < 10){
			 mineDisplay.setText("00" + mineCount ); 
		 }
		 else if (mineCount < 100){
			 mineDisplay.setText("0" + mineCount ); 
			 
		 }
		 else if (mineCount > 100){
			 mineDisplay.setText("" + mineCount ); 
			 
		 }
	 }
	 
	 public void startTime(){
		 if (timerState == true)
		 {
		 	timer = 0;
			handler.postDelayed(runnable, interval);
			}
		 else if (timerState == false){
			 timerPic.setBackgroundResource(R.drawable.timer_off);
		 }

	 }
	 public void stopTime(){
		 	timer = 0;
		 	handler.removeCallbacks(runnable);
	 }
	 private Runnable runnable = new Runnable(){
		    public void run() {
		    	if (timer < 10){
		    		timerDisplay.setText("00" + timer); 
		    	}
		    	else if (timer < 100){
		    		timerDisplay.setText("0" + timer); 
		    	}
		    	else if (timer > 100) {
		    		timerDisplay.setText("" + timer); 
		    	}
		    	
		    	if((timer%30 ==0)){
		    		beepSound();
		    	}
		    	handler.postDelayed(this, interval);
		    	timer++;
		    	
		    }
		};
		
	 public void beepSound(){
		if (soundState == true){
	     tg.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 100);
		}
	 }
	 

		public void popDialog(String message, int milliseconds, boolean useSmileImage, boolean useCoolImage)
		{
			// show message
			Toast dialog = Toast.makeText(
					getApplicationContext(),
					message,
					Toast.LENGTH_LONG);

			dialog.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout dialogView = (LinearLayout) dialog.getView();
			ImageView coolImage = new ImageView(getApplicationContext());
			if (useSmileImage)
			{
				coolImage.setImageResource(R.drawable.smile);
			}
			else if (useCoolImage)
			{
				coolImage.setImageResource(R.drawable.cool);
			}
			else
			{
				coolImage.setImageResource(R.drawable.sad);
			}
			dialogView.addView(coolImage, 0);
			dialog.setDuration(milliseconds);
			dialog.show();
		}
		
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}
}