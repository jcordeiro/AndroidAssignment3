package com.example.jonathan_cordeiro_3;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity implements OnCheckedChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		updateToppingPrices();

		RadioGroup grpSize = (RadioGroup) findViewById(R.id.grpSize);
		grpSize.setOnCheckedChangeListener(this);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onPayment(View view) {


	}

	// Updates the price of the pizza toppings based on currently selected pizza size
	public void updateToppingPrices() {
		// The base price for the toppings
		double cheesePrice = 1;
		double pepperoniPrice = 1.5;
		double sausagePrice = 1.75;
		double baconPrice = 1.25;
		double greenPepperPrice = 1;

		// Get references to the radioButton widgets
		RadioButton radSmall = (RadioButton)findViewById(R.id.radSmall);
		RadioButton radMedium = (RadioButton)findViewById(R.id.radMedium);
		RadioButton radLarge = (RadioButton)findViewById(R.id.radLarge);

		// If pizza is medium or large, we increase price of toppings
		// If pizza is small, we use the base price
		if (radMedium.isChecked()) {
			cheesePrice += 0.25;
			pepperoniPrice += 0.25;
			sausagePrice += 0.25;
			baconPrice += 0.25;
			greenPepperPrice += 0.25;
		}
		else if (radLarge.isChecked()) {
			cheesePrice += 0.5;
			pepperoniPrice += 0.5;
			sausagePrice += 0.5;
			baconPrice += 0.5;
			greenPepperPrice += 0.5;
		}

		// Get references to the checkbox widgets
		CheckBox chkCheese = (CheckBox)findViewById(R.id.chkCheese);
		CheckBox chkPepperoni = (CheckBox)findViewById(R.id.chkPeperoni);
		CheckBox chkBacon = (CheckBox)findViewById(R.id.chkBacon);
		CheckBox chkSausage = (CheckBox)findViewById(R.id.chkSausage);
		CheckBox chkPepper = (CheckBox)findViewById(R.id.chkPepper);

		// Display the appropriate price for the toppings
		//				chkCheese.setText(String.format("%s%.2f", chkCheese.getText().toString(), cheesePrice));

		chkCheese.setText(String.format("%s%.2f",
				getResources().getText(R.string.topping_cheese), cheesePrice));
		chkPepperoni.setText(String.format("%s%.2f",
				getResources().getText(R.string.topping_pepperoni), pepperoniPrice));
		chkBacon.setText(String.format("%s%.2f",
				getResources().getText(R.string.topping_bacon), baconPrice));
		chkSausage.setText(String.format("%s%.2f",
				getResources().getText(R.string.topping_sausage), sausagePrice));
		chkPepper.setText(String.format("%s%.2f",
				getResources().getText(R.string.topping_green_pepper), greenPepperPrice));
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		updateToppingPrices();
	}

}
