package com.example.jonathan_cordeiro_3;

import java.util.Random;

import com.example.jonathan_cordeiro_3.Pizza.Size;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class PaymentActivity extends Activity {

	private static final int MIN = 0;
	private static final int MAX = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);

		Intent intent = getIntent();
		Pizza pizza = intent.getParcelableExtra(MainActivity.PIZZA);

		displayPizzaToppings(pizza);
		displayPizzaSize(pizza);
		displayPizzaPrice(pizza);

	}



	public void displayPizzaToppings(Pizza pizza) {

		// Get a reference to the toppings textview widget
		TextView txtPizzaToppings = (TextView)findViewById(R.id.txtPizzaTopings);

		StringBuilder toppings = new StringBuilder();

		// Loop through the pizza's toppings and append them to the string builder
		// We don't get the last 4 characters because we need to chop off the dollar amount
		// from the string representing the topping
		for (String topping : pizza.getToppings()) {
			//			toppings.append(topping.substring(0, topping.length() - 5) + "\n");
			toppings.append(topping + "\n");
		}

		// Diplay the pizza toppings to the user
		txtPizzaToppings.setText(toppings.toString());




	}

	public void displayPizzaSize(Pizza pizza) {

		Size pizzaSize = pizza.getSize();
		String size;

		if (pizzaSize == Size.SMALL) {
			size = getResources().getString(R.string.small_size);
		}
		else if (pizzaSize == Size.MEDIUM) {
			size = getResources().getString(R.string.medium_size);
		}
		else {
			size = getResources().getString(R.string.large_size);
		}

		// Get a reference the the pizza size textview widget
		TextView txtPizzaWith = (TextView)findViewById(R.id.txtPizzaWith);
		txtPizzaWith.setText(size + " " + getResources().getString(R.string.pizza_with));
	}

	private void displayPizzaPrice(Pizza pizza) {

		// Get a reference to the total price textview widget
		TextView txtTotalPrice = (TextView)findViewById(R.id.txtTotalPrice);

		// Add the price of the pizza to the string resource
		String price = String.format("%s%.2f", getResources().getString(R.string.total_price), pizza.getPrice());

		// Display the total price of the pizza to the user
		txtTotalPrice.setText(price);

	}


	public void OnPayNow(View view) {

		// Get a reference to the radio button widget
		// Since RadioButtons are grouped together, we only need to check one
		RadioButton radCash = (RadioButton)findViewById(R.id.radCash);

		if (radCash.isChecked()) {
			setResult(RESULT_OK);
		}
		else {
			// Paying by credit card = 50% chance of failure
			// Calculate a random number between 1 and 100
			// Even number = success
			// Odd number = failure

			Random r = new Random();
			int randomNum = r.nextInt(MAX - MIN + 1) + MIN;

			// Even number = success!
			if (randomNum % 2 == 0) {
				setResult(RESULT_OK);
			}
			else {
				// Odd number = failure!
				Intent intent = new Intent();
				setResult(RESULT_CANCELED);
			}
		}

		// End PaymentActivity and go back to the MainActivity
		finish();
	}




}
