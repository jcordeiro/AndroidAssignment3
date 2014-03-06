package com.example.jonathan_cordeiro_3;

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
		
		
		
		
	}
	
	public void displayPizzaToppings(Pizza pizza) {
		
		// Get a reference to the toppings textview widget
		TextView txtPizzaToppings = (TextView)findViewById(R.id.txtPizzaTopings);
		
		StringBuilder toppings = new StringBuilder();
		
		// Loop through the pizza's toppings and append them to the string builder
		for (String topping : pizza.getToppings()) {
			toppings.append(topping + "\n");
		}
			
		// Diplay the pizza toppings to the user
		txtPizzaToppings.setText(toppings.toString());
		
		
		
	}
	
	
	public void OnPayNow(View view) {
		
		
		
	}

	
	
	
}
