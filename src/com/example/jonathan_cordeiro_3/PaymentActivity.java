package com.example.jonathan_cordeiro_3;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class PaymentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		
		Intent intent = getIntent();
		Pizza pizza = intent.getParcelableExtra("pizza");
		
		
		
		
	}

	
	
	
}
