

package uk.ac.brookes.khaled;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends Activity implements OnClickListener  {

	public Button beg,inter,adv,diff;
	private final String sharedrow = "sharedrow";
	private final String sharedcol = "sharedcol";
	private final String sharedmine = "sharedmine";
	
	 SharedPreferences pref;
	
	@Override
	public void onCreate(Bundle abby) {
		
		// TODO Auto-generated method stub
		super.onCreate(abby);
		setContentView(R.layout.menu);

		beg = (Button) findViewById(R.id.beg);
		inter = (Button) findViewById(R.id.inter);
		adv = (Button) findViewById(R.id.adv);

		 pref = Menu.this.getSharedPreferences("pref", 0);

		 
		Toast display = Toast.makeText(this, "Choose Your preferred game difficulty", Toast.LENGTH_LONG);
		display.show();
	
		beg.setOnClickListener(this);		
		inter.setOnClickListener(this);
		adv.setOnClickListener(this); 
	
	}

	   public void onClick(View v) {
		   
	       Intent openmine = new Intent("uk.ac.brookes.khaled.MINESWEEPER");
	       SharedPreferences.Editor editor1 = pref.edit();

	       switch(v.getId()) {
	           case R.id.beg:
	        	   
	        	   editor1.putInt(sharedcol, 7);
	        	   editor1.putInt(sharedrow, 7);
	        	   editor1.putInt(sharedmine, 9);
	       
	           break;
	           case R.id.inter:    
	        	   
	        	   editor1.putInt(sharedcol, 9);
	        	   editor1.putInt(sharedrow, 9);
	        	   editor1.putInt(sharedmine, 20);
	        	  
	           break;
	           case R.id.adv:
	        	   
	        	   editor1.putInt(sharedcol, 10);
	        	   editor1.putInt(sharedrow, 10 );
	        	   editor1.putInt(sharedmine, 30);
	        
	        	   break;
	       }
			
	       editor1.commit();  
//	       editor1.commit(); 
//	       editor1.commit(); 
//    	   int sharedstate = pref.getInt(sharedmine, 0);
//	       Toast dialog = Toast.makeText(Menu.this, ""+sharedstate, Toast.LENGTH_LONG);
//	       dialog.show();
		startActivity(openmine);
			
	       }

@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}


}
	