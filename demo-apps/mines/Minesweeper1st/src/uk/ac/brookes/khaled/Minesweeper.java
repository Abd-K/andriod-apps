package uk.ac.brookes.khaled;



import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class Minesweeper extends Activity
{
	public Button diff;
	
	private TextView mineDisplay;
	private TextView timeDisplay;
	private ImageButton smile, mus;

	private TableLayout gridTable; // layout of the gritable 
	
	private Grid grid[][]; // grid for gridtable	
	
	private int blockSize = 24; // width of each block
	private int blockPad = 2; // padding between blocks
	Menu menu = new Menu();
	Splash splash = new Splash();
	

	protected int Gridrows;
	protected int Gridcolumns;
	SharedPreferences pref;
	private final String sharedmine = "sharedmine";
	private final String sharedrow = "sharedrow";
	private final String sharedcol = "sharedcol";
	int setmine,setcol,setrow;
	protected int Gridmines;
	
	public int istate;
	// time passed timer
	private Handler timer = new Handler();
	private int sectime = 0;

	private boolean StartTime; // check if time started
	private boolean StartMine; // check if mines are made
	private boolean checkFail; // check if player lost the game
	private int minesleft; // int for the number of mines left
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		 pref = Minesweeper.this.getSharedPreferences("pref", 0);
		 setmine = pref.getInt(sharedmine, 0);
		 setrow = pref.getInt(sharedrow, 0);
		 setcol = pref.getInt(sharedcol, 0);
		
		mineDisplay = (TextView) findViewById(R.id.MineCount);
		timeDisplay = (TextView) findViewById(R.id.Time);
		
		// set font style for timer and mine count to LCD style
		Typeface Font;
		Font = Typeface.createFromAsset(getAssets(),"LCDM2U__.TTF");
		mineDisplay.setTypeface(Font);
		timeDisplay.setTypeface(Font);
		
		smile = (ImageButton) findViewById(R.id.Smiley);
		diff = (Button) findViewById(R.id.diff);
		diff.setText("Mode");
		mus = (ImageButton) findViewById(R.id.mus);
		final MediaPlayer intromusic = MediaPlayer.create(Minesweeper.this, R.raw.bmusic);
		 
		intromusic.start();
		intromusic.setLooping(true);
		intromusic.setVolume(1, 1);
		gridTable = (TableLayout)findViewById(R.id.gridTable);
		
		Gridmines=setmine;
		Gridrows = setrow;
		Gridcolumns= setcol;
		NewGame();
//		showDialog(""+setmine+setrow+setcol, 2000, true, false);

		mus.setOnClickListener(new OnClickListener()
		{
//			@Override
			public void onClick(View view)
			{	
				if(istate==1){
				mus.setBackgroundResource(R.drawable.off);
				istate=0;
				intromusic.setVolume(0, 0);
				}
				else {
				mus.setBackgroundResource(R.drawable.on);
				istate=1;
				intromusic.setVolume(1, 1);

				}
				
			}
		});
		
		smile.setOnClickListener(new OnClickListener()
		
		{
//			@Override
			public void onClick(View view)
			{
				emptyGrid();
				
				NewGame();
			}
		});
		diff.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
			intromusic.stop();
			Intent openmine = new Intent("uk.ac.brookes.khaled.MENU");
			startActivity(openmine);
		}
		});
	}

/* start of the game set grid */
	private void NewGame()
	{
	
		GridMines();
		EndShowMines();
		
		minesleft = Gridmines;
		updateMineCountDisplay();
		checkFail = false;
	
		sectime = 0;
		popDialog("Click on  smiley to reset", 3000, true, false);
	}

	private void EndShowMines()
	{
		
		for (int row = 1; row < Gridrows + 1; row++)
		{
			TableRow tableRow = new TableRow(this);  
			tableRow.setLayoutParams(new LayoutParams((blockSize + 1 * blockPad) * Gridcolumns, blockSize + 1 * blockPad));

			for (int column = 1; column < Gridcolumns + 1; column++)
			{
				grid[row][column].setLayoutParams(new LayoutParams(  
						blockSize + 1 * blockPad,  
						blockSize + 1 * blockPad)); 
				grid[row][column].setPadding(blockPad, blockPad, blockPad, blockPad);
				tableRow.addView(grid[row][column]);
			}
			gridTable.addView(tableRow,new TableLayout.LayoutParams(  
					(blockSize + 1 * blockPad) * Gridcolumns, blockSize + 1 * blockPad));  
		}
	}
	// method to clear/reset the game
	private void emptyGrid()
	{
		stopTimer(); // stop the timer
		timeDisplay.setText("000"); // reset the timer
		mineDisplay.setText("000"); // reset the mine count
		smile.setBackgroundResource(R.drawable.smile);
		gridTable.removeAllViews();
		
		// set all variables to support end of game
		checkFail = false;
		StartTime = false;
		StartMine = false;	
		minesleft = 0;
	}

	// method to set mines on grid
	private void GridMines()
	{
		grid = new Grid[Gridrows + 2][Gridcolumns + 2];

		for (int row = 0; row < Gridrows + 2; row++)
		{
			for (int column = 0; column < Gridcolumns + 2; column++)
			{	
				grid[row][column] = new Grid(this);
				grid[row][column].refreshGrid();


				final int currentRow = row;
				final int currentColumn = column;

				grid[row][column].setOnClickListener(new OnClickListener()
				{
					
//					@Override
					public void onClick(View view)
					{
						// prevent grid from interacting on clicks when game is over
						if (checkFail==false){
						// begin timing
						if (!StartTime)
						{
							StartMine = true;
							setMines(currentRow, currentColumn);
							startTimer();
							StartTime = true;
						}
						
						if (!grid[currentRow][currentColumn].flagCheck())
						{
							// open all grid that have no mines nearby
							massOpen(currentRow, currentColumn);
							
							// mine clicked
							if (grid[currentRow][currentColumn].hasMine())
							{
								// game over
								gameOver(currentRow,currentColumn);
							}

							// check game won
							if (checkGameWin())
							{
								// run game won method
								gamwWon();
							}
						}
					}
					}});

				// click listnerer for alternative operations (long clicks)
				grid[row][column].setOnLongClickListener(new OnLongClickListener()
				{
					public boolean onLongClick(View view)
					{
						// simulate a left-right (middle) click
						// if it is a long click on an opened mine then
						// open all surrounding grid
						if (!grid[currentRow][currentColumn].isCovered() && (grid[currentRow][currentColumn].areaminesint() > 0) && !checkFail)
						{
							int nearbyFlaggedGrids = 0;
							for (int previousRow = -1; previousRow < 2; previousRow++)
							{
								for (int previousColumn = -1; previousColumn < 2; previousColumn++)
								{
									if (grid[currentRow + previousRow][currentColumn + previousColumn].flagCheck())
									{
										nearbyFlaggedGrids++;
									}
								}
							}

							// if flagged block count is equal to nearby mine count
							// then open nearby grid
							if (nearbyFlaggedGrids == grid[currentRow][currentColumn].areaminesint())
							{
								for (int previousRow = -1; previousRow < 2; previousRow++)
								{
									for (int previousColumn = -1; previousColumn < 2; previousColumn++)
									{
										// don't open flagged grid
										if (!grid[currentRow + previousRow][currentColumn + previousColumn].flagCheck())
										{
											// open grid till we get numbered block
											massOpen(currentRow + previousRow, currentColumn + previousColumn);

											// did we clicked a mine
											if (grid[currentRow + previousRow][currentColumn + previousColumn].hasMine())
											{
												// oops game over
												gameOver(currentRow + previousRow, currentColumn + previousColumn);
											}

											// did we win the game
											if (checkGameWin())
											{
												// mark game as win
												gamwWon();
											}
										}
									}
								}
							}

							// as we no longer want to judge this gesture so return
							// not returning from here will actually trigger other action
							// which can be marking as a flag or question mark or blank
							return true;
						}

						// if clicked block is enabled, clickable or flagged
						if (grid[currentRow][currentColumn].isClickable() && 
								(grid[currentRow][currentColumn].isEnabled() || grid[currentRow][currentColumn].flagCheck()))
						{

							// for long clicks set:
							// 1. empty grid to flagged
							// 2. flagged to question mark
							// 3. question mark to blank

							// case 1. set blank block to flagged
							if (!grid[currentRow][currentColumn].flagCheck() && !grid[currentRow][currentColumn].questcheck())
							{
								grid[currentRow][currentColumn].emptyGrid(false);
								grid[currentRow][currentColumn].flagBlock(true);
								grid[currentRow][currentColumn].flaggedblock(true);
								minesleft--; //reduce mine count
								updateMineCountDisplay();
							}
							// case 2. set flagged to question mark
							else if (!grid[currentRow][currentColumn].questcheck())
							{
								grid[currentRow][currentColumn].emptyGrid(true);
								grid[currentRow][currentColumn].notsureBlock(true);
								grid[currentRow][currentColumn].flaggedblock(false);
								grid[currentRow][currentColumn].questblock(true);
								minesleft++; // increase mine count
								updateMineCountDisplay();
							}
							// case 3. change to blank square
							else
							{
								grid[currentRow][currentColumn].emptyGrid(true);
								grid[currentRow][currentColumn].clearTxt();
								grid[currentRow][currentColumn].questblock(false);
								// if it is flagged then increment mine count
								if (grid[currentRow][currentColumn].flagCheck())
								{
									minesleft++; // increase mine count
									updateMineCountDisplay();
								}
								// remove flagged status
								grid[currentRow][currentColumn].flaggedblock(false);
							}
							
							updateMineCountDisplay(); // update mine display
						}

						return true;
					}
				});
			}
		}
	}

	private boolean checkGameWin()
	{
		for (int row = 1; row < Gridrows + 1; row++)
		{
			for (int column = 1; column < Gridcolumns + 1; column++)
			{
				if (!grid[row][column].hasMine() && grid[row][column].isCovered())
				{
					return false;
				}
			}
		}
		return true;
	}

	private void updateMineCountDisplay()
	{
		if (minesleft < 0)
		{
			mineDisplay.setText(Integer.toString(minesleft));
		}
		else if (minesleft < 10)
		{
			mineDisplay.setText("00" + Integer.toString(minesleft));
		}
		else if (minesleft < 100)
		{
			mineDisplay.setText("0" + Integer.toString(minesleft));
		}
		else
		{
			mineDisplay.setText(Integer.toString(minesleft));
		}
	}

	private void gamwWon()
	{
		
		stopTimer();
		StartTime = false;
		checkFail = true;
		minesleft = 0; //set mine count to 0

		//set icon to cool dude
		smile.setBackgroundResource(R.drawable.cool);

		updateMineCountDisplay(); // update mine count

		// disable all buttons
		// set flagged all un-flagged grid
		for (int row = 1; row < Gridrows + 1; row++)
		{
			for (int column = 1; column < Gridcolumns + 1; column++)
			{
				grid[row][column].setClickable(false);
				if (grid[row][column].hasMine())
				{
					grid[row][column].emptyGrid(false);
					grid[row][column].flagBlock(true);
				}
			}
		}
		// winning message
		if ((sectime <150 && Gridmines ==9) ||(sectime <350 && Gridmines ==20) )
		{
			popDialog("Well done, try a harder mode. time:" + Integer.toString(sectime) + " seconds!", 3000, true, false);
		}
				
		else  
			popDialog("Well done. time:" + Integer.toString(sectime) + " seconds! play again!", 3000, true, false);
	}

	private void gameOver(int currentRow, int currentColumn)
	{
		checkFail = true; // mark game as over
		stopTimer(); // stop timer
		StartTime = false;
		smile.setBackgroundResource(R.drawable.sad);

		// show the mines and mark the exploded mine
		// disable all grid
		for (int row = 1; row < Gridrows + 1; row++)
		{
			for (int column = 1; column < Gridcolumns + 1; column++)
			{
				
				grid[row][column].setClickable(false);

				// block has mine and is not flagged
				if (grid[row][column].hasMine() && !grid[row][column].flagCheck())
				{
					// set mine icon
					grid[row][column].mineBlock(false);
					
				}

				// block is flagged and doesn't not have mine
				if (!grid[row][column].hasMine() && grid[row][column].flagCheck())
				{
					// set flag icon
			
					grid[row][column].flagBlock(false);
				}

				// block is flagged
				if (grid[row][column].flagCheck())
				{
					// disable the block

					grid[row][column].setClickable(false);
				}
			}
		}

		// trigger mine
		grid[currentRow][currentColumn].expMine();
	
		// Fail message
		if (Gridmines/2 < minesleft && Gridmines !=9 )
		{
			popDialog("You failed after tried for" + Integer.toString(sectime) + " seconds! try again or choose an easier mode!", 3000, false, false);
		}
		else if (Gridmines/2  > minesleft)
			popDialog("Half way there after" + Integer.toString(sectime) + " seconds! Try again!", 3000, false, false);
		
		else  
			popDialog("You failed after tried for" + Integer.toString(sectime) + " seconds! try again!", 3000, false, false);
			
	}


	private void setMines(int currentRow, int currentColumn)
	{
		// set mines excluding the location where user clicked
		Random rand = new Random();
		int mineRow, mineColumn;

		for (int row = 0; row < Gridmines; row++)
		{
			mineRow = rand.nextInt(Gridcolumns);
			mineColumn = rand.nextInt(Gridrows);
			if ((mineRow + 1 != currentColumn) || (mineColumn + 1 != currentRow))
			{
				if (grid[mineColumn + 1][mineRow + 1].hasMine())
				{
					row--; // mine is already there, don't repeat for same block
				}
				// plant mine at this location
				grid[mineColumn + 1][mineRow + 1].makeMine();
			}
			// exclude the user clicked location
			else
			{
				row--;
			}
		}

		int nearByMineCount;

		// count number of mines in surrounding grid 
		for (int row = 0; row < Gridrows + 2; row++)
		{
			for (int column = 0; column < Gridcolumns + 2; column++)
			{
				// for each block find nearby mine count
				nearByMineCount = 0;
				if ((row != 0) && (row != (Gridrows + 1)) && (column != 0) && (column != (Gridcolumns + 1)))
				{
					// check in all nearby grid
					for (int previousRow = -1; previousRow < 2; previousRow++)
					{
						for (int previousColumn = -1; previousColumn < 2; previousColumn++)
						{
							if (grid[row + previousRow][column + previousColumn].hasMine())
							{
								// a mine was found so increment the counter
								nearByMineCount++;
							}
						}
					}

					grid[row][column].txtAreamines(nearByMineCount);
				}
				// for side rows (0th and last row/column)
				// set count as 9 and mark it as opened
				else
				{
					grid[row][column].txtAreamines(9);
					grid[row][column].OpenGrid();
				}
			}
		}
	}

	private void massOpen(int rowClicked, int columnClicked)
	{
		// don't open flagged or mined rows
		if (grid[rowClicked][columnClicked].hasMine() || grid[rowClicked][columnClicked].flagCheck())
		{
			return;
		}

		// open clicked block
		grid[rowClicked][columnClicked].OpenGrid();

		// if clicked block have nearby mines then don't open further
		if (grid[rowClicked][columnClicked].areaminesint() != 0 )
		{
			return;
		}

		// open next 3 rows and 3 columns recursively
		for (int row = 0; row < 3; row++)
		{
			for (int column = 0; column < 3; column++)
			{
				// check all the above checked conditions
				// if met then open subsequent grid
				if (grid[rowClicked + row - 1][columnClicked + column - 1].isCovered()
						&& (rowClicked + row - 1 > 0) && (columnClicked + column - 1 > 0)
						&& (rowClicked + row - 1 < Gridrows + 1) && (columnClicked + column - 1 < Gridcolumns + 1))
				{
					massOpen(rowClicked + row - 1, columnClicked + column - 1 );
				} 
			}
		}
		return;
	}

	public void startTimer()
	{
		if (sectime == 0) 
		{
			timer.removeCallbacks(updateTimeElasped);
			// tell timer to run call back after 1 second
			timer.postDelayed(updateTimeElasped, 1000);
		}
	}

	public void stopTimer()
	{
		// disable call backs
		timer.removeCallbacks(updateTimeElasped);
	}

	// timer call back when timer is ticked
	private Runnable updateTimeElasped = new Runnable()
	{
		public void run()
		{
			long currentMilliseconds = System.currentTimeMillis();
			++sectime;

			if (sectime < 10)
			{
				timeDisplay.setText("00" + Integer.toString(sectime));
			}
			else if (sectime < 100)
			{
				timeDisplay.setText("0" + Integer.toString(sectime));
			}
			else
			{
				timeDisplay.setText(Integer.toString(sectime));
			}
			
			// add notification
			timer.postAtTime(this, currentMilliseconds);
			// notify to call back after 1 seconds
			// basically to remain in the timer loop
			timer.postDelayed(updateTimeElasped, 1000);
		}
	};
	
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
