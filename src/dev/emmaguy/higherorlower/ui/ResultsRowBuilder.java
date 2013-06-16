package dev.emmaguy.higherorlower.ui;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;

public class ResultsRowBuilder {
    
    private final Context context;
    
    public ResultsRowBuilder(Context context) {
	this.context = context;
    }
    
    public TableRow createRow(String rowName, String rowValue, int colour) {

	TableRow tableRow = new TableRow(context);
	tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

	TextView textView = new TextView(context);
	textView.setText(rowName);
	textView.setTextColor(colour);
	textView.setTextSize(20.0f);
	
	TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0);
	layoutParams.width = 175;
	textView.setLayoutParams(layoutParams);

	TextView textViewValue = new TextView(context);
	textViewValue.setText(rowValue);
	textViewValue.setTextColor(colour);
	textViewValue.setTextSize(20.0f);
	textViewValue.setGravity(Gravity.RIGHT);
	textViewValue.setLayoutParams(new TableRow.LayoutParams(1));

	tableRow.addView(textView);
	tableRow.addView(textViewValue);

	return tableRow;
    }
}
