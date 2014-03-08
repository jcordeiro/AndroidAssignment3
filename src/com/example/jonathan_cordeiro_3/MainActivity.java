package com.example.jonathan_cordeiro_3;

import java.util.ArrayList;

import com.example.jonathan_cordeiro_3.Pizza.Size;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

// TODO: Add onActivityResult callback


public class MainActivity extends Activity implements OnCheckedChangeListener {

	private static final int PAYMENT_ACTIVITY = 0;
	private static final double HST = 1.13;
	public static final String PIZZA = "pizza";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		updateToppingPrices();

		// Set the listener on the radio buttons
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
	// creates a pizza objects and passes it to the payment activity
	public void onPayment(View view) {
		ArrayList<String> pizzaToppings = getPizzaToppings();
		Size pizzaSize = getPizzaSize();
		double toppingPrice = getToppingPrice(pizzaSize, pizzaToppings);
		double pizzaPrice = calculateTotalPizzaPrice(pizzaSize, toppingPrice);

		Pizza pizza = new Pizza(pizzaPrice, pizzaToppings, pizzaSize);
		Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
		intent.putExtra(PIZZA, pizza);

		// Start the payment activity
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

	// Gets the toppings currently selected
	public ArrayList<String> getPizzaToppings() {

		// An ArrayList to store all the toppings
		ArrayList<String> pizzaToppings = new ArrayList<String>();

		// Get references to the checkbox widgets
		CheckBox chkCheese = (CheckBox)findViewById(R.id.chkCheese);
		CheckBox chkPepperoni = (CheckBox)findViewById(R.id.chkPeperoni);
		CheckBox chkBacon = (CheckBox)findViewById(R.id.chkBacon);
		CheckBox chkSausage = (CheckBox)findViewById(R.id.chkSausage);
		CheckBox chkPepper = (CheckBox)findViewById(R.id.chkPepper);

		// Store all the checkboxes in an ArrayList so we can iterate over them
		ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
		checkboxes.add(chkCheese);
		checkboxes.add(chkPepperoni);
		checkboxes.add(chkBacon);
		checkboxes.add(chkSausage);
		checkboxes.add(chkPepper);
		
		// Iterate through the checkboxes and if they're checked
		// add that topping to the ArrayList of toppings
		for (CheckBox cb : checkboxes) {
			if (cb.isChecked()) {
				
				// We don't get the last 4 characters because we need to chop off the dollar amount
				// from the string representing the topping
				String topping = cb.getText().toString();
				topping = topping.substring(0, cb.length() - 6);
				
				pizzaToppings.add(topping);
			}
		}

		return pizzaToppings;


	}

	// Get the price of all the toppings currently on the pizza
	public double getToppingPrice(Size size, ArrayList<String> toppings) {

		double toppingPrice = 0;
		
		for (String s: toppings) {
			Log.i("TOPPINGS", s);
		}

		// The base price for the toppings
		double cheesePrice = 1;
		double pepperoniPrice = 1.5;
		double sausagePrice = 1.75;
		double baconPrice = 1.25;
		double greenPepperPrice = 1;

		// If pizza is medium or large, we increase price of toppings
		// If pizza is small, we use the base prices
		if (size == Size.MEDIUM) {
			cheesePrice += 0.25;
			pepperoniPrice += 0.25;
			sausagePrice += 0.25;
			baconPrice += 0.25;
			greenPepperPrice += 0.25;
		}
		else if (size == Size.LARGE) {
			cheesePrice += 0.5;
			pepperoniPrice += 0.5;
			sausagePrice += 0.5;
			baconPrice += 0.5;
			greenPepperPrice += 0.5;
		}

		// Check which toppings are on the pizza an incremement the price
		if (toppings.contains("Cheese")) {
			toppingPrice = toppingPrice + cheesePrice;
		}
		if (toppings.contains("Pepperoni")) {
			toppingPrice = toppingPrice + pepperoniPrice;
		}
		if (toppings.contains("Sausage")) {
			toppingPrice = toppingPrice + sausagePrice;
		}
		if (toppings.contains("Bacon")) {
			toppingPrice = toppingPrice + baconPrice;
		}
		if (toppings.contains("Green Pepper")) {
			toppingPrice = toppingPrice + greenPepperPrice;
		}

		Log.i("TOPPINGS", "TOPPING PRICE: " + toppingPrice);
		
		return toppingPrice;

	}

	// Calculates the total price of the pizza
	public double calculateTotalPizzaPrice(Size size, double toppingPrice) {

		double price;

		if (size == Size.SMALL) {
			price = 8;
		}
		else if (size == Size.MEDIUM) {
			price = 10;
		}
		else {
			// LARGE
			price = 12;
		}

		price = price + toppingPrice;
		price = price * HST;
		return price;

	}

	// Updates the prices displayed for the pizza toppings based on currently selected pizza size
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

		chkCheese.setText(String.format("%s $%.2f",
				getResources().getString(R.string.topping_cheese), cheesePrice));
		chkPepperoni.setText(String.format("%s $%.2f",
				getResources().getString(R.string.topping_pepperoni), pepperoniPrice));
		chkBacon.setText(String.format("%s $%.2f",
				getResources().getString(R.string.topping_bacon), baconPrice));
		chkSausage.setText(String.format("%s $%.2f",
				getResources().getString(R.string.topping_sausage), sausagePrice));
		chkPepper.setText(String.format("%s $%.2f",
				getResources().getString(R.string.topping_green_pepper), greenPepperPrice));
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		updateToppingPrices();
	}

}
