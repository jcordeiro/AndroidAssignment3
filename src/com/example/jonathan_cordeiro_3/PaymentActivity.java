package com.example.jonathan_cordeiro_3;

import com.example.jonathan_cordeiro_3.Pizza.Size;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class PaymentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);

		Intent intent = getIntent();
		Pizza pizza = intent.getParcelableExtra("pizza");

		displayPizzaToppings(pizza);
		displayPizzaSize(pizza);

	}

	public void displayPizzaToppings(Pizza pizza) {

		// Get a reference to the toppings textview widget
		TextView txtPizzaToppings = (TextView)findViewById(R.id.txtPizzaTopings);

		StringBuilder toppings = new StringBuilder();

		// Loop through the pizza's toppings and append them to the string builder
		// We don't get the last 4 characters because we need to chop off the dollar amount
		// from the string representing the topping
		for (String topping : pizza.getToppings()) {
			toppings.append(topping.substring(0, topping.length() - 5) + "\n");
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


	public void OnPayNow(View view) {



	}




}
