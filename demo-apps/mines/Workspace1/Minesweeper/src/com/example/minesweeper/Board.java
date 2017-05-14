package com.example.minesweeper;


public class Board {
	private boolean open = false;
	private boolean mine = false;
	private boolean flagged = false;
	private int areaMines= 0;
	private boolean clickAble = true;
	

	public void setMine(){
		mine=true;
	}
	
	public boolean getMine(){
		return mine;
	}
	
	public void setOpen(){
		open = true;
	}
	
	public boolean getOpen(){
		return open;
	}
	
	public void addArea(){
		areaMines++;
		
	}

	public int getArea(){
		return areaMines;
	}
	
	public void setFlag(){
		if (flagged == false){
		flagged = true;
		}
		else if (flagged == true){
			flagged = false;
		}
	}
	
	public boolean getFlag(){
		return flagged;
	}
	public void setClickable(){
		clickAble = false;
	}
	public boolean getClickable(){
		return clickAble;
	}
	public void resetValues(){
		open = false;
		mine = false;
		flagged = false;
		areaMines = 0;
		clickAble = true;
	}
}
