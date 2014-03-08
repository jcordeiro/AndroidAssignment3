package com.example.jonathan_cordeiro_3;

import java.util.ArrayList;
import java.util.Random;

import com.example.jonathan_cordeiro_3.Pizza.Size;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class PaymentActivity extends Activity {

	private static final int MIN = 0;
	private static final int MAX = 100;
	public static final String ORDER_TOTAL = "order total";
	private static final double HST = 1.13;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);


		Pizza pizza = getPizzaFromIntent();

		double pizzaToppingPrice = calculateToppingPrice(pizza.getSize(), pizza.getToppings());
		double pizzaTotalPrice = calculateTotalPizzaPrice(pizza.getSize(), pizzaToppingPrice);

		displayPizzaToppings(pizza);
		displayPizzaSize(pizza);
		displayPizzaPrice(pizzaTotalPrice);
	}

	// Returns a Pizza object from an Intent
	public Pizza getPizzaFromIntent() {
		Intent intent = getIntent();
		Pizza pizza = intent.getParcelableExtra(MainActivity.PIZZA);

		return pizza;
	}

	// Get the price of all the toppings currently on the pizza
	public double calculateToppingPrice(Size size, ArrayList<String> toppings) {

		double toppingPrice = 0;

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

	private void displayPizzaPrice(double pizzaPrice) {

		// Get a reference to the total price textview widget
		TextView txtTotalPrice = (TextView)findViewById(R.id.txtTotalPrice);

		// Add the price of the pizza to the string resource
		String price = String.format("%s%.2f", getResources().getString(R.string.total_price), pizzaPrice);

		// Display the total price of the pizza to the user
		txtTotalPrice.setText(price);
	}

	public void OnPayNow(View view) {

		// Get a reference to the radio button widget
		// Since RadioButtons are grouped together, we only need to check one
		RadioButton radCash = (RadioButton)findViewById(R.id.radCash);

		Pizza pizza = getPizzaFromIntent();

		double pizzaToppingPrice = calculateToppingPrice(pizza.getSize(), pizza.getToppings());
		double pizzaTotalPrice = calculateTotalPizzaPrice(pizza.getSize(), pizzaToppingPrice);

		Intent intent  = new Intent();
		intent.putExtra(ORDER_TOTAL, pizzaTotalPrice);

		if (radCash.isChecked()) {
			setResult(RESULT_OK, intent);
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
				setResult(RESULT_OK, intent);
			}
			else {
				// Odd number = failure!
				setResult(RESULT_CANCELED);
			}
		}

		// End PaymentActivity and go back to the MainActivity
		finish();
	}

}
