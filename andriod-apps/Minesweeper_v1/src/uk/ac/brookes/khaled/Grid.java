package uk.ac.brookes.khaled;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class Grid extends Button
{
	private boolean checkclick; //check if block can be clicked on
	private boolean checkMine; // check if mines allocated
	private boolean checkBut; // check if buttons allocated
	private boolean checkFlag; // check if flag allocated
	private boolean checkQuest; // check if ? allocated
	
	private int areaMines; // int for the number of mines around the area of the current block 1-8

	public Grid(Context context)
	{
		super(context);
	}

	public Grid(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public Grid(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	// values for new Grid
	public void refreshGrid()
	{
		
		checkclick = true;
		checkBut = true;
		checkMine = false;
		checkFlag = false;
		checkQuest = false;
		areaMines = 0;

		this.setBackgroundResource(R.drawable.button_unopen);
		boldFont();
	}

	// places a no_button drawable and ontop places a text integer of the number of areamines
	public void areaMines(int number)
	{
		this.setBackgroundResource(R.drawable.button_empty);	
		updateNumber(number);
	}

	// Places a drawable depending on the condition passed, mine for disabled and exploded mine for enabled
	public void mineBlock(boolean enabled)
	{
		if (!enabled)
		{
			this.setBackgroundResource(R.drawable.button_mine);	
		}
		else
		{
			this.setBackgroundResource(R.drawable.button_explode);
		}
	}

	// place a drawable depending on a condition, flag if enable and closed block if disabled
	public void flagBlock(boolean enabled)
{
		

		if (!enabled)
		{
			this.setBackgroundResource(R.drawable.button_unopen);
			
		}
		else
		{
			this.setBackgroundResource(R.drawable.button_flagged);
		}
	}
	// Places a drawable or text depending on condition, question mark for enabled and open block for disabled
	public void notsureBlock(boolean enabled)
	{
		if (!enabled)
		{
			this.setBackgroundResource(R.drawable.button_empty);	
		}
		else
		{
			this.setText("?");	
			this.setTextColor(Color.WHITE);
		}
	}
	// place a drawable depending on condition, button drawable if enabled and empty block if disabled
	public void emptyGrid(boolean enabled)
	{
		if (!enabled)
		{
			this.setBackgroundResource(R.drawable.button_empty);
		}
		else
		{
			this.setBackgroundResource(R.drawable.button_unopen);
		}
	}

	// method to set bold font 
	private void boldFont()
	{
		this.setTypeface(null, Typeface.BOLD);
	}

	// method to clear questionmark and int off the grid
	public void clearTxt()
	{
		this.setText("");
	}

	// open Block
	public void OpenGrid()
	{
		// check if there is button
		if (!checkBut)
			return;

		emptyGrid(false);
		checkBut = false;

		// check if there is mine under the button
		if (hasMine())
		{
			mineBlock(false);
		}
		// update int with areamines
		else
		{
			areaMines(areaMines);
		}
	}

	// method for adding areamines in text format to be displayed on the grid
	public void updateNumber(int text)
	{
		if (text != 0)
		{
			this.setText(Integer.toString(text));

			// each number allocated a different colour similar to original minesweeper
			
			switch (text)
			{
				case 1:
					this.setTextColor(Color.BLUE);
					break;
				case 2:
					this.setTextColor(Color.GREEN);
					break;
				case 3:
					this.setTextColor(Color.RED);
					break;
				case 4:
					this.setTextColor(Color.rgb(50, 30, 200));
					break;
				case 5:
					this.setTextColor(Color.rgb(200, 30, 50));
					break;
				case 6:
					this.setTextColor(Color.rgb(240, 100, 50));
					break;
				case 7:
					this.setTextColor(Color.rgb(240, 20, 20));
					break;
				case 8:
					this.setTextColor(Color.rgb(200, 200, 200));
					break;
			}
		}
	}

	// method to place mine on block
	public void makeMine()
	{
		checkMine = true;
	}

	// condition for unique mine, one that activated loss
	public void expMine()
	{
		mineBlock(true);
		this.setTextColor(Color.RED);
	}

	//boolean for button is allocated
	public boolean isCovered()
	{
		return checkBut;
	}

	//boolean for block has mine allocated
	public boolean hasMine()
	{
		return checkMine;
	}

	// set areamines to txt
	public void txtAreamines(int number)
	{
		areaMines = number;
	}

	// get the number of mines around a single block
	public int areaminesint()
	{
		return areaMines;
	}

	// check if block is flagged
	public boolean flagCheck()
	{
		return checkFlag;
	}

	// flag a block
	public void flaggedblock(boolean flagged)
	{
		checkFlag = flagged;
	}

	//check if block has ?
	public boolean questcheck()
	{
		return checkQuest;
	}

	// place ? on block
	public void questblock(boolean marked)
	{
		checkQuest = marked;
	}

	// check if block is clickable
	public boolean isClickable()
	{
		return checkclick;
	}

	// set blocks click status
	public void setClickable(boolean status)
	{
		checkclick = status;
	}
}