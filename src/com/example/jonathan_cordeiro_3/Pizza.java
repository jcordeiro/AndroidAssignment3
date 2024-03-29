package com.example.jonathan_cordeiro_3;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Pizza implements Parcelable {

	private ArrayList<String> toppings;
	private Size size;

	public enum Size {
		SMALL,
		MEDIUM,
		LARGE
	}

	// Constructor
	public Pizza(ArrayList<String> toppings, Size size) {

		this.toppings = toppings;
		this.size = size;
	}

	public ArrayList<String> getToppings() {
		return toppings;
	}

	public void setToppings(ArrayList<String> toppings) {
		this.toppings = toppings;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	// Writes data to parcel
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(toppings);
		dest.writeSerializable(size);
	}

	// Constructor that reads values from a Parcel
	@SuppressWarnings("unchecked")
	public Pizza(Parcel in){
		this.toppings = (ArrayList<String>) in.readSerializable();
		this.size = (Size) in.readSerializable();
	}

	public static final Parcelable.Creator<Pizza> CREATOR = new
			Parcelable.Creator<Pizza>() {
		public Pizza createFromParcel(Parcel in) {
			return new Pizza(in);
		}

		public Pizza[] newArray(int size) {
			return new Pizza[size];
		}
	};

}
