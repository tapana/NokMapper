package org.droidplanner.android.widgets.SeekBarWithText;

import org.droidplanner.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekBarWithText extends LinearLayout implements OnSeekBarChangeListener,OnClickListener {

	public interface OnTextSeekBarChangedListener {
		public void onSeekBarChanged();
	}

	private Button plusButton;
	private Button minusButton;
	private TextView textView;
	private SeekBar seekBar;
	private double min = 0;
	private double inc = 1;
	private String title = "";
	private String unit = "";
	private String formatString = "%2.1f";
	private OnTextSeekBarChangedListener listner;

	public SeekBarWithText(Context context) {
		this(context, null);
	}

	public SeekBarWithText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SeekBarWithText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		createViews(context, attrs);		
	}

	private void setFormat(String string) {
		if (string != null) {
			formatString = string;
			invalidate();
		}
	}
	
	
	private void createViews(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SeekBarWithText, 0, 0);

		try {
			setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			setOrientation(VERTICAL);
			textView = new TextView(context);
			seekBar = new SeekBar(context);
			seekBar.setLayoutParams(new LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			seekBar.setOnSeekBarChangeListener(this);
			addView(textView);
						
			LinearLayout lh  = new LinearLayout(context);			
			minusButton = new Button(context);
			minusButton.setText("-");
			minusButton.setOnClickListener(this);
			
			lh.addView(minusButton);
			
			plusButton = new Button(context);
			plusButton.setText("+");			
			plusButton.setOnClickListener(this);
			lh.addView(plusButton);
			lh.addView(seekBar);	
			
			addView(lh);

			setTitle(a.getString(R.styleable.SeekBarWithText_title));
			setUnit(a.getString(R.styleable.SeekBarWithText_unit));
			setMinMaxInc(a.getFloat(R.styleable.SeekBarWithText_min, 0),
					a.getFloat(R.styleable.SeekBarWithText_max, 100),
					a.getFloat(R.styleable.SeekBarWithText_inc, 1));
			setFormat(a.getString(R.styleable.SeekBarWithText_formatString));
		} finally {
			a.recycle();
		}
	}
	

	public void setMinMaxInc(double min, double max, double inc) {
		this.min = min;
		this.inc = inc;
		seekBar.setMax((int) ((max - min) / inc));
	}

	public void setUnit(String unit) {
		if (unit != null) {
			this.unit = unit;
			invalidate();
		}
	}

	public void setTitle(CharSequence text) {
		if (text != null) {
			title = text.toString();
			updateTitle();
		}
	}

	private void updateTitle() {
		textView.setText(String.format("%s\t" + formatString + " %s", title, getValue(), unit));
	}

	public double getValue() {
		return (seekBar.getProgress() * inc + min);
	}

	public void setValue(double value) {
		seekBar.setProgress((int) ((value - min) / inc));
	}

	public void setAbsValue(double value) {
		if (value < 0)
			value *= -1.0;
		seekBar.setProgress((int) ((value - min) / inc));
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		updateTitle();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (listner != null) {
			listner.onSeekBarChanged();
		}
	}

	public void setOnChangedListener(OnTextSeekBarChangedListener listner) {
		this.listner = listner;
	}

	@Override
	public void onClick(View v) {
		Button b = (Button) v;
		if( b.getText().toString().compareTo("-") == 0){			
			setValue(getValue()-1);		
		}else{
			setValue(getValue()+1);				
		}
		if(listner != null){
			listner.onSeekBarChanged();
		}
		
	}

}
