package com.example.jonathan_cordeiro_3;

import java.util.ArrayList;

import com.example.jonathan_cordeiro_3.Pizza.Size;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnCheckedChangeListener {

	private static final int PAYMENT_ACTIVITY = 0;
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
		//		double toppingPrice = getToppingPrice(pizzaSize, pizzaToppings);
		//		double pizzaPrice = calculateTotalPizzaPrice(pizzaSize, toppingPrice);

		Pizza pizza = new Pizza(pizzaToppings, pizzaSize);
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

	// Runs when the user returns from PaymentActivity by pressing the Pay Now button
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Get references to the UI widgets we need to update
		Button btnPayment = (Button)findViewById(R.id.btnPayment);
		TextView txtPaymentStatus = (TextView)findViewById(R.id.txtPaymentStatus);
		TextView txtPaymentResult = (TextView)findViewById(R.id.txtPaymentResult);

		// If their payment was successful
		if (resultCode == RESULT_OK) {

			// Get the total price from the PaymentActivity
			double totalPrice = data.getDoubleExtra(PaymentActivity.ORDER_TOTAL, 0);

			txtPaymentStatus.setText(getResources().getString(R.string.payment_accepted));
			txtPaymentStatus.setTextColor(Color.BLACK);

			String totalIs = String.format("%s%.2f %s", 
					getResources().getString(R.string.total_is), totalPrice, getResources().getString(R.string.inc_HST));

			txtPaymentResult.setText(totalIs);
			txtPaymentResult.setVisibility(View.VISIBLE);

			btnPayment.setText(getResources().getString(R.string.new_order));

		}
		else {
			// Payment failed

			txtPaymentStatus.setText(getResources().getString(R.string.payment_not_accepted));
			txtPaymentStatus.setTextColor(Color.RED);
			btnPayment.setText(getResources().getString(R.string.payment));
			txtPaymentResult.setVisibility(View.INVISIBLE);

		}


	}

}
