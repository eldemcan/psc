package com.paintshop.solution.impl;

import com.paintshop.problem.Customer;

import java.util.List;


public abstract class AbstractProblemSolver
{

	public boolean validateProposal(List<Customer> customers, int[] proposal)
	{
		return customers.stream().allMatch(customer -> customer.checkProposedSolution(proposal) == true);
	}

	public boolean checkAllCustomers(List<Customer> customers)
	{
		return customers.stream().allMatch(customer -> customer.isHappy == true);
	}


}
