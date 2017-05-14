package com.example.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	int [] board;
	int [] board_1D_text;
	
	public ImageAdapter(Context c, int[] board, int[] grid_text) {
		context = c;
		this.board = board;
		this.board_1D_text = grid_text;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return board.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView;
		ImageView imageView;
		TextView textView;
		if (convertView == null){
			
			 gridView = new View(context);
			 gridView = inflater.inflate(R.layout.grid, null);
			 textView = (TextView) gridView.findViewById(R.id.grid_text);
			 imageView = (ImageView) gridView.findViewById(R.id.grid_image);

        } else {
        	
            
        	gridView = (View) convertView;
        }
		imageView = (ImageView) gridView.findViewById(R.id.grid_image);
		textView = (TextView) gridView.findViewById(R.id.grid_text);
		textView.setText("");
		textView.setTypeface(null, Typeface.BOLD);
		
			switch(board[position]){
		        case 0: imageView.setImageResource(R.drawable.button_unopen); break;
		        case 1: textView.setText("" +board_1D_text[position]);
			        switch(board_1D_text[position]){
					case 0: textView.setText(""); break;
					case 1: textView.setTextColor(Color.BLUE); break;
					case 2: textView.setTextColor(Color.rgb(0, 200, 0)); break;
					case 3:textView.setTextColor(Color.RED); break;
					case 4: textView.setTextColor(Color.rgb(0, 0, 100)); break;
					case 5: textView.setTextColor(Color.rgb(100, 50, 0)); break;
					case 6:textView.setTextColor(Color.rgb(0, 150, 150)); break;
					case 7: textView.setTextColor(Color.rgb(150, 0, 150)); break;
					case 8: textView.setTextColor(Color.rgb(0, 0, 0)); break;
			        } 
		        imageView.setImageResource(R.drawable.button_empty);break;
		        case 2: imageView.setImageResource(R.drawable.button_mine); break;
		        case 3: imageView.setImageResource(R.drawable.button_explode); break;
		        case 4: imageView.setImageResource(R.drawable.button_flagged); break;
		        case 5: imageView.setImageResource(R.drawable.button_badflag); break;
	}
			return gridView;
		
	}
		
}

