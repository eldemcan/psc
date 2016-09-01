package com.paintshop.utils;

import com.paintshop.problem.Customer;
import com.paintshop.problem.Problem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProblemLoader
{
	String fileName;

	public ProblemLoader(String fileName)
	{
		this.fileName = fileName;
	}

	public List<Problem> loadProblem()
	{
		List<Problem> problems = new ArrayList<Problem>();
		LineIterator lineIterator = null;
		try
		{
			lineIterator = FileUtils.lineIterator(new File(fileName));
			//I assume given input file includes correct information and format
			int numberOfTestCases = Integer.parseInt(lineIterator.next());

			for (int i = 0; i < numberOfTestCases; i++)
			{

				int numberOfColors = Integer.parseInt(lineIterator.next());
				int numberOfCustomers = Integer.parseInt(lineIterator.next());
				Problem problem = new Problem(numberOfCustomers, numberOfColors);
				List<Customer> customerList = new ArrayList<Customer>();
				for (int j = 0; j < numberOfCustomers; j++)
				{
					String[] customerData = lineIterator.next().split(" ");
					String[] customerPreferencesString = Arrays.copyOfRange(customerData, 1, customerData.length);
					int[] customerPreferences = Arrays.asList(customerPreferencesString).stream().mapToInt(Integer::parseInt)
							.toArray();
					customerList.add(new Customer(customerPreferences));
				}

				problem.loadCustomerData(customerList);
				problems.add(problem);
			}


		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("Could not find the file");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("Can not read from file");
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			System.out.println("Please make sure given input file has correct format");
		}
		finally
		{
			LineIterator.closeQuietly(lineIterator);
		}

		return problems;
	}

}
