package com.paintshop.solution.impl;

import com.paintshop.problem.Customer;
import com.paintshop.problem.Problem;
import com.paintshop.solution.Solver;

import java.util.*;


public class BackTrackSolver extends AbstractProblemSolver implements Solver
{
	@Override
	public int[] solveProblem(Problem problem)
	{
		boolean alternativeExists;
		int[] state = new int[problem.numberOfColors];
		SortedSet<Customer> customers = problem.customers;

		for (Customer currentCustomer : customers)
		{
			List<Customer> previousCustomers = previousCustomers(customers, currentCustomer);

			int[] proposedSolution = proposeSolution(state, currentCustomer);
			boolean res = validateProposal(previousCustomers, proposedSolution);

			if (res)
			{
				state = proposedSolution.clone();
				currentCustomer.isHappy = true;
			}
			else
			{
				alternativeExists = researchAlternative(state, currentCustomer, previousCustomers);
				if (!alternativeExists)
				{
					break;
				}
				else
				{
					currentCustomer.isHappy = true;
				}
			}
		}

		if(!checkAllCustomers(new ArrayList<Customer>(problem.customers))){
			state[0]=-2; //flag it impossible
		}

		return state;
	}


	/*
	 * this method returns previous customers from the set it is like returning subset of a set. (excluding customerTo)
	 */
	public List<Customer> previousCustomers(SortedSet<Customer> customerSet, Customer customerTo)
	{

		List<Customer> customers = new ArrayList<Customer>();
		Iterator<Customer> customerIterator = customerSet.iterator();
		boolean brk = false;

		while (customerIterator.hasNext() && brk == false)
		{
			Customer customer = customerIterator.next();

			if (!customer.equals(customerTo))
			{
				customers.add(customer);
			}
			else
			{
				brk = true;
			}
		}
		return customers;
	}

	public boolean researchAlternative(int[] state, Customer currentCustomer, List<Customer> previousCustomers)
	{

		if (state[0] == -2)
		{
			return false;
		}
		else
		{
			int[] proposedSolution = proposeSolution(state, currentCustomer);
			if (validateProposal(previousCustomers, proposedSolution))
			{
				arrayCopyWithReference(proposedSolution, state);
				return true;
			}
			else if(proposedSolution[0]==-2){
				arrayCopyWithReference(proposedSolution,state);
				return false;
			}
			else
			{
				return (researchAlternative(state, currentCustomer, previousCustomers));
			}
		}
	}


	public int[] proposeSolution(int[] currentState, Customer customer)
	{
		int colorPreference;
		int colorType;
		Optional<Map.Entry<Integer, Integer>> result;
		int[] proposal = currentState.clone();

		//check if customer has any preference with glossy
		result = customer.colorPreferences.entrySet().stream()
				.filter(item -> !customer.proposedColorPreferences.containsKey(item.getKey()) && item.getValue() == 0).findFirst();
		if (result.isPresent())
		{
			colorPreference = result.get().getKey();
			colorType = result.get().getValue();
			proposal[colorPreference - 1] = colorType;
			customer.proposedColorPreferences.put(colorPreference, colorType);
		}
		//check if customer has one  preference with matt
		else
		{
			result = customer.colorPreferences.entrySet().stream()
					.filter(item -> !customer.proposedColorPreferences.containsKey(item.getKey()) && item.getValue() == 1).findFirst();
			if (result.isPresent())
			{
				colorPreference = result.get().getKey();
				colorType = result.get().getValue();
				proposal[colorPreference - 1] = colorType;
				customer.proposedColorPreferences.put(colorPreference, colorType);
			}
			//we exhaust all options for current customer mark the current state with -2
			else
			{
				proposal[0] = -2;
			}
		}

		return proposal;
	}

	public void arrayCopyWithReference(int[] source, int[] target)
	{
		for (int i = 0; i < target.length; i++)
		{
			target[i] = source[i];
		}
	}



}
