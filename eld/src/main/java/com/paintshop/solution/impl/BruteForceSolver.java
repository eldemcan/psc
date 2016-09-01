package com.paintshop.solution.impl;

import com.paintshop.problem.Customer;
import com.paintshop.problem.Problem;
import com.paintshop.solution.Solver;

import java.util.*;


/**
 * This is a brute force solver it will generate all possible combinations make a small optimization of all
 * possibilities and try them weather it is a real solution or not. I wrote this one to test BackTrackSolver
 *
 */
public class BruteForceSolver extends AbstractProblemSolver implements Solver
{

	public SortedSet<int[]> states;
	public SortedSet<int[]> filteredStates;


	public BruteForceSolver()
	{
		this.states = new TreeSet<int[]>(((o1, o2) -> {
			int res = Arrays.stream(o1).sum() >= Arrays.stream(o2).sum() ? 1 : -1;
			return res;
		}));

		this.filteredStates = new TreeSet<int[]>(((o1, o2) -> {
			int res = Arrays.stream(o1).sum() >= Arrays.stream(o2).sum() ? 1 : -1;
			return res;
		}));
	}

	public boolean filterWithOneOptions(List<Customer> customers)
	{
		Map<Integer, Integer> oneColorPreference = new HashMap<Integer, Integer>();
		customers.forEach(customer -> {

			if (customer.onlyOneOption)
			{
				int colorPreference = customer.colorPreferences.keySet().iterator().next();
				int colorType = customer.colorPreferences.get(colorPreference);

				//if customers with one options conflict with each other no point to continue
				if (oneColorPreference.containsKey(colorPreference) && oneColorPreference.get(colorPreference) != colorType)
				{
					oneColorPreference.put(-1, -1); //terminate signal
				}

				else if (!oneColorPreference.containsKey(colorPreference))
				{
					oneColorPreference.put(colorPreference, colorType);
				}

			}
		});

		// (all possible options)- (options which does not fit to customer who has one preference)
		if (!oneColorPreference.containsKey(-1))
		{
			//nothing we can filter
			if (oneColorPreference.size() == 0)
			{
				filteredStates=states;
			}
			else
			{
				oneColorPreference.forEach((key, value) -> {
					states.forEach(possibleSolution -> {
						if (possibleSolution[key - 1] == value)
						{
							filteredStates.add(possibleSolution);
						}
					});
				});
			}
			return true;
		}

		/*
		 * terminate program because there are two customers who has one preference and it is conflicting with each other
		 */
		else
		{
			return false;
		}

	}

	public void generateCombinations(int[] soFar, int iterations)
	{
		if (iterations == 0)
		{
			states.add(soFar.clone());
		}
		else
		{
			soFar[iterations - 1] = 0;
			generateCombinations(soFar, iterations - 1);
			soFar[iterations - 1] = 1;
			generateCombinations(soFar, iterations - 1);
		}
	}

	@Override
	public int[] solveProblem(Problem problem)
	{

		int[] solution = new int[problem.numberOfColors];
		generateCombinations(solution, problem.numberOfColors);
		List<Customer> customerList = new ArrayList<Customer>(problem.customers);

		if (!filterWithOneOptions(customerList))
		{
			solution[0] = -2; //flag it impossible
		}
		else
		{

			boolean isFound = false;
			for (int[] state : filteredStates)
			{
				if (validateProposal(customerList, state))
				{
					solution = state;
					isFound = true;
					break;
				}
			}

			if (!isFound)
				solution[0] = -2; //flag it impossible
		}

		return solution;
	}
}
