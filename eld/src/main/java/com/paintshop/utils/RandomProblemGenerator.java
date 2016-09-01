package com.paintshop.utils;

import com.paintshop.problem.Customer;
import com.paintshop.problem.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *  This class generates random color preferences for given number of colors and number of customers
 */
public class RandomProblemGenerator
{

	public int numberOfColors;
	public int numberOfCustomers;


	public RandomProblemGenerator(int numberOfColors, int numberOfCustomers)
	{
		this.numberOfColors = numberOfColors;
		this.numberOfCustomers = numberOfCustomers;
	}

	public Problem generateProblem()
	{
		Problem generatedProblem = new Problem(numberOfCustomers, numberOfColors);
		generatedProblem.loadCustomerData(generateCustomerList());
		return generatedProblem;
	}

	public List<Customer> generateCustomerList()
	{
		List<Customer> customerList = new ArrayList<Customer>(numberOfCustomers);

		for (int i = 0; i < numberOfCustomers; i++)
		{
			int[] generatedPreferences = generatePreference();

			customerList.add(new Customer(generatedPreferences));
		}

		return customerList;
	}


	public int[] generatePreference()
	{
		Random randomizer = new Random();
		int[] customerPreferences = new int[(randomizer.nextInt((numberOfColors - 1) + 1) + 1) * 2]; //color and type customer has to have at least one color preference
		boolean includeMatt = randomizer.nextBoolean(); // we have at most one matt it will be decided according to boolean value

		for (int i = 0; i < customerPreferences.length - 1; i = i + 2)
		{
			int colorPreference = randomizer.nextInt((numberOfColors - 1) + 1) + 1;

			boolean contains = isContainNumber(customerPreferences, colorPreference);
			while (contains)
			{
				colorPreference = randomizer.nextInt((numberOfColors - 1) + 1) + 1;
				contains = isContainNumber(customerPreferences, colorPreference);
			}

			customerPreferences[i] = colorPreference;
		}

		// this part chooses a random location from customerPreference array and set color type to matt
		if (includeMatt)
		{
			int[] mattLocations = IntStream.iterate(1, i -> i + 2).limit(customerPreferences.length / 2).toArray();
			int mattIndex = randomizer.nextInt(mattLocations.length);
			customerPreferences[mattLocations[mattIndex]] = 1;
		}

		return customerPreferences;
	}


	/*
	 * I had to wrap lamda expression with function because lamda does not accept simple variables if it is not final. It
	 * is something related with object being referenced or not I suppose
	 */
	public boolean isContainNumber(int[] customerPreferences, int number)
	{
		return IntStream.of(customerPreferences).anyMatch(item -> item == number);
	}

}
