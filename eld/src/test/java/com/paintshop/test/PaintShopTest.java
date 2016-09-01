package com.paintshop.test;

import com.paintshop.problem.Customer;
import com.paintshop.problem.Problem;
import com.paintshop.solution.impl.BackTrackSolver;
import com.paintshop.solution.impl.BruteForceSolver;
import com.paintshop.utils.RandomProblemGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static junit.framework.TestCase.assertTrue;


public class PaintShopTest
{
	RandomProblemGenerator randomProblemGenerator = null;
	Problem problem;

	@Before
	public void initializeProblem()
	{
		int limit = 10;
		Random randomParameters = new Random();
		int numberOfColors = randomParameters.nextInt((limit - 1) + 1) + 1;
		int numberOfCustomers = randomParameters.nextInt((limit - 1) + 1) + 1;

		randomProblemGenerator = new RandomProblemGenerator(numberOfColors, numberOfCustomers);
		problem = randomProblemGenerator.generateProblem();

	}

	@Test
	public void testRandomDataGeneretor()
	{
		assertTrue(problem != null);
		assertTrue(problem.customers.size() > 0);
		assertTrue(problem.numberOfColors > 0);
		assertTrue(problem.numberOfCustomers > 0);
		problem.customers.stream().forEach(customer -> {
			assertTrue(customer.colorPreferences.size() > 0);
			});

		problem.customers.stream().forEach(
				customer -> {
					int matt = (int) customer.colorPreferences.entrySet().stream()
							.filter((colorPreference) -> colorPreference.getValue() == 1).count();
					assertTrue(matt == 1 || matt==0); //each customer should have at most one matte color
				});
	}

	/*
	 *  Using brute force  method test the backtrack  method
	 */
	@Test
	public void testBackTrackSolver()
	{
		BruteForceSolver bruteForceSolver=new BruteForceSolver();
		BackTrackSolver backTrackSolver=new BackTrackSolver();

		System.out.println(problem.toString());

		int[] bruteForceSolution = bruteForceSolver.solveProblem(problem);
		int[] backTrackSolution = backTrackSolver.solveProblem(problem);

		System.out.println(Arrays.toString(bruteForceSolution));
		System.out.println(Arrays.toString(backTrackSolution));

		//if brute force method could not find the
		if (bruteForceSolution[0]==-2) {
			assertTrue(backTrackSolution[0]==-2);
		}
		else{
			assertTrue(Arrays.equals(bruteForceSolution,backTrackSolution));
			assertTrue(backTrackSolver.checkAllCustomers(new ArrayList<Customer>(problem.customers)));
		}
	}

}
