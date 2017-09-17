package com.example.calculator;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Calculate extends Activity {

	private TextView Display;
	
	private Button Number_0;
	private Button Number_1;
	private Button Number_2;
	private Button Number_3;
	private Button Number_4;
	private Button Number_5;
	private Button Number_6;
	private Button Number_7;
	private Button Number_8;
	private Button Number_9;
	private Button decimal_point;
	private Button Delete;
	private Button Clear;
	private Button Multiplication;
	private Button Division;
	private Button Addition;
	private Button Subtraction;
	private Button Answer;
	

	private String Current_Value="";
	private String Number1= "";
	private String Number2= "";
	private String display = "";
	private boolean Decimal=false;
	private char Operator;
	private char Unary;
	private char Switcher;
	private String Error="";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		Display = (TextView) findViewById(R.id.Display);
		Display.setText(display);
	
		Number_0 = (Button) findViewById(R.id.Number_0);
		Number_1 = (Button) findViewById(R.id.Number_1);
		Number_2 = (Button) findViewById(R.id.Number_2);
		Number_3 = (Button) findViewById(R.id.Number_3);
		Number_4 = (Button) findViewById(R.id.Number_4);
		Number_5 = (Button) findViewById(R.id.Number_5);
		Number_6 = (Button) findViewById(R.id.Number_6);
		Number_7 = (Button) findViewById(R.id.Number_7);
		Number_8 = (Button) findViewById(R.id.Number_8);
		Number_9 = (Button) findViewById(R.id.Number_9);
		decimal_point = (Button) findViewById(R.id.decimal_point);
		Delete = (Button) findViewById(R.id.Delete);
		Clear = (Button) findViewById(R.id.Clear);
		Multiplication = (Button) findViewById(R.id.Multiplication);
		Division = (Button) findViewById(R.id.Division);
		Addition = (Button) findViewById(R.id.Addition);
		Subtraction = (Button) findViewById(R.id.Subtraction);
		Answer = (Button) findViewById(R.id.Answer);
		
		Number_0.setOnClickListener(onClickListener);
		Number_1.setOnClickListener(onClickListener);
		Number_2.setOnClickListener(onClickListener);
		Number_3.setOnClickListener(onClickListener);
		Number_4.setOnClickListener(onClickListener);
		Number_5.setOnClickListener(onClickListener);
		Number_6.setOnClickListener(onClickListener);
		Number_7.setOnClickListener(onClickListener);	
		Number_8.setOnClickListener(onClickListener);
		Number_9.setOnClickListener(onClickListener);
		decimal_point.setOnClickListener(onClickListener);
		Delete.setOnClickListener(onClickListener);	 
		Clear.setOnClickListener(onClickListener);
		Multiplication.setOnClickListener(onClickListener);
		Division.setOnClickListener(onClickListener);
		Addition.setOnClickListener(onClickListener);
		Subtraction.setOnClickListener(onClickListener);
		Answer.setOnClickListener(onClickListener);
		

}
	/* On click listener for all the keys in this app  */
	
	private OnClickListener onClickListener = new OnClickListener() {
		 public void onClick(View v) {
			 switch(v.getId()){
			 case R.id.Number_0:
	            	Current_Value = "0";
	            	Entry();
	            break;
            case R.id.Number_1:
            	Current_Value = "1";
            	Entry();
            break;
            case R.id.Number_2:
            	Current_Value = "2";
            	Entry();
            break;
            case R.id.Number_3:
            	Current_Value = "3";
            	Entry();
        	break;
            case R.id.Number_4:
            	Current_Value = "4"; 
            	Entry();
        	break;
            case R.id.Number_5:
            	Current_Value = "5";
            	Entry();
        	break;
            case R.id.Number_6:
            	Current_Value = "6";
            	Entry();
        	break;
            case R.id.Number_7:
            	Current_Value = "7";
            	Entry();
        	break;
            case R.id.Number_8:
            	Current_Value = "8";
            	Entry();
        	break;
            case R.id.Number_9:
            	Current_Value = "9";
            	Entry();
        	break;
            case R.id.decimal_point:
            	Decimal();
            	Decimal=true;
        	break;
            case R.id.Addition:
            	if (Unary == '-'){
            		Unary = ' ';
            		Operator = '+';
            	}
            	Operator = '+';
            	Shift_Numbers();
            	Switcher = 'A';
        	break;
            case R.id.Subtraction:
            	Negative();
            	Shift_Numbers();
        	break;
            case R.id.Multiplication:
            	Operator ='*' ; 
            	Shift_Numbers();
            	Switcher = 'M';
            		
        	break;
            case R.id.Division:
            	Operator = '/';
            	Shift_Numbers();
            	Switcher = 'D';
        	break;
            case R.id.Delete:
            	Delete();
        	break;
            case R.id.Clear:
            	Clear();
        	break;
            case R.id.Answer:
            	Answer();
        	break;
				}
			 Update_Display(); 
		 }
		};
		
		// Whenever a digit key is pressed, this method is called, 
		// it adds the inputed value till 15 digits
		public void Entry() {
			if (Number1.length() + Number2.length() <15)
				Number1 = Number1 + Current_Value; 
			else {
				Error = "<15 Digits> "; 
			}
		}
		
		// Resets all variables to clear Entry area
		public void Clear(){
	    	display = "";
	    	Number1 = "";
	    	Number2 = "";
	    	Decimal = false;
	    	Operator=' ';
	    	Unary = ' ';
	    	Error = "";
		}
		
		// This method deletes one string character at a time from entry point, 
		// Till  Number1 is null, the operator will be removed, then Number2
		public void Delete(){
			if(Number1.length()>0)
	    	{
				Number1 =  Number1.substring(0, Number1.length() - 1);
	    	}
			else if (Unary != ' ')
				Unary = ' ';
			else if (Operator != ' ') {
				Operator = ' ';
				Switcher = ' ';
			}
			else if (Number2.length()>0)
			{
				Number2 =  Number2.substring(0, Number2.length() - 1);
			}
		}
		
		// A method for updating display in Entry area
		public void Update_Display(){
			display = (Error + Number2 + Operator + Unary + Number1);
			Display.setText(display);
			Error = "";
		}
		
		// method for adding Decimal point to end of number
		// if there is no number, 0. is placed instead of . only
		public void Decimal() {
			if (Decimal==false)
				if(Number1.length()>0)
					Number1 = Number1 + ".";
				else 
					Number1 = Number1 + "0.";
		}	
		
		/* Method for Switching the First number and Second number as the equation progresses.
		 Before the switch takes place, i will do checks for the Syntax */
		public void Shift_Numbers(){
			// If second Slot is empty 
			// __ 
			if (Number2.length()<1){
				// If both Strings are empty, i.e First entry
				// __ _ __
				if (Number1.length() <1){
					// If the First entry is a Unary number
					// __ - __
					// if First entry isnt Unary
					// __ * __
					if (Unary != '-')
						Unary = ' ';
						Operator = ' ';
				}
				/*  if First Number isnt empty 
				   __ _ X */
				else if (Number1.length() >0){
					/* And If First entry was a minus, indicating negative unary
					   __ - X */
						Unary();
					/* Shift number positions, from Number1 to Number2 Allowing new number entry
					   X _ __ */
					Number2 = Number1;
					Number1 = "";
					Decimal = false;	
				}
			}
			// If Second number exists 
			else if (Number2.length()>0){
				// If Shift is requested after an evaluation nothing should happen besides display Operator

				 if (Number1.length() >0){
					 Unary();
					/* If Second number exists with the First number,  then the equation should proceed to be evaluated
					   X _ X */
					Operator();
			} 
			}
		}
		
		/* This method is called for Differentiating between a Unary minus and a regular minus operator
		   It also ensures an addition and Unary minus arent both available at the same instance */
		public void Negative(){
			if (Number1.length()<1 && Number2.length()<1 )
				Unary = '-';
			else if (Number2.length()>0 && Operator != ' ' )
				if (Operator == '+'){
					Operator = '-';
	    			Switcher = 'S';
				}
				else
				Unary = '-';
			else {
				Operator = '-';
				Switcher = 'S';}
			
		}
		
		/* Method for Unary minus
		   This method is only called to convert the String into negative float, then back to a string */
		public void Unary(){
			if (Unary == '-'){
				Number1 = Float.toString(-(Float.parseFloat(Number1)));
				Unary = ' ';
			}
		}
		
		/* this method is for Evaluating all the operators*/
		public void Operator(){
			switch(Switcher){
		    case 'A':
		    	Number2 = Float.toString(Float.parseFloat(Number2) + Float.parseFloat(Number1));
		    break;
		    case 'S':
		    	Number2 = Float.toString(Float.parseFloat(Number2) - Float.parseFloat(Number1));
		    break;
		    case 'M':
		    	Number2 = Float.toString(Float.parseFloat(Number2) * Float.parseFloat(Number1));
		    break;
		    case 'D':
		    	Number2 = Float.toString(Float.parseFloat(Number2) / Float.parseFloat(Number1));
		    	
		    break;
				}
			Decimal = false;
			Number1 = "";
		}
		
		/* this method is for Evaluation once = button is clicked
		   this is treated differently than Shift_numbers() Operator() for error and syntax checks */
		public void Answer(){
			// if both numbers of the equation exist
			if(Number2.length()>0 && Number1.length()>0 ){
				/* and an operator exists, then do a unary check, to treat negative numbers first
				   then Evaluate the answers */
				if (Operator != ' '){
					Unary();
					Operator();
					Operator= ' ';
				}
				else 
					// if no operator is given, then it gives an error message
					Error = "<Missing Operator> ";
			}
			/* if Number1 exists yet there is no number2
			   then it should return missing input */
			else if (Number1.length()>0 && Number2.length()<1){
				Error = "<Missing Input> ";
			}
			// likewise if number1 is missing while number2 isnt, error should be shown
			else if (Number2.length()>0 && Number1.length()<1){
				Error = "<Missing Input> ";
			}
			else 
				// if no entries are placed at all, error message should be returned
				Error = "<Missing Entry> ";
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		}


