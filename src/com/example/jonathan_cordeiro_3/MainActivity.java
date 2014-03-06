package com.example.jonathan_cordeiro_3;

import com.example.jonathan_cordeiro_3.Pizza.Size;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

// TODO: Solve problem with topping prices
// TODO: Put pizza object in intent
// TODO: Finish activity_payment layout
// TODO: Add onActivityResult callback


public class MainActivity extends Activity implements OnCheckedChangeListener {

	private static final int PAYMENT_ACTIVITY = 0;

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

	// Runs when the user clicks the payment button
	public void onPayment(View view) {
		Intent intent = new Intent(MainActivity.this, PaymentActivity.class);

		// TODO: Put extras in Intent

		startActivityForResult(intent, PAYMENT_ACTIVITY);
	}

	// Gets the currently selected pizza size
	public Size getPizzaSize() {

		// Get references to the radioButton widgets
		RadioButton radSmall = (RadioButton)findViewById(R.id.radSmall);
		RadioButton radMedium = (RadioButton)findViewById(R.id.radMedium);

		if (radSmall.isChecked()) {
			return Size.SMALL;
		}
		else if (radMedium.isChecked()) {
			return Size.MEDIUM;
		}
		else {
			return Size.LARGE;
		}

	}

	// Updates the price of the pizza toppings based on currently selected pizza size
	public void updateToppingPrices() {
		// The base price for the toppings
		double cheesePrice = 1;
		double pepperoniPrice = 1.5;
		double sausagePrice = 1.75;
		double baconPrice = 1.25;
		double greenPepperPrice = 1;
		
		// Get the size of the pizza
		Size pizzaSize = getPizzaSize();
		
		// If pizza is medium or large, we increase price of toppings
		// If pizza is small, we use the base price
		if (pizzaSize == Size.MEDIUM) {
			cheesePrice += 0.25;
			pepperoniPrice += 0.25;
			sausagePrice += 0.25;
			baconPrice += 0.25;
			greenPepperPrice += 0.25;
		}
		else if (pizzaSize == Size.LARGE) {
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
