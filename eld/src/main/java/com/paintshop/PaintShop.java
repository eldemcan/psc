package com.paintshop;

import com.paintshop.problem.Problem;
import com.paintshop.solution.Solver;
import com.paintshop.solution.impl.BackTrackSolver;
import com.paintshop.solution.impl.BruteForceSolver;
import com.paintshop.utils.ProblemLoader;
import com.paintshop.utils.RandomProblemGenerator;
import org.apache.commons.math3.util.ArithmeticUtils;

import java.util.List;


/**
 * Created by Can Eldem web page:eldemcando.com
 */
public class PaintShop
{

	public static void main(String[] args) throws IllegalArgumentException
	{
		//load from file
		if (args[0].equalsIgnoreCase("-i"))
		{
			try
			{
				String fileName = args[1];
				ProblemLoader problemLoader = new ProblemLoader(fileName);
				List<Problem> problems = problemLoader.loadProblem();
				Solver solver = new BackTrackSolver();
				int caseNumber = 1;
				for (Problem problem : problems)
				{
					System.out.println("\nProblem statement");
					System.out.println(problem.toString());
					int[] solution = solver.solveProblem(problem);
					printSolution(solution, 1);
					caseNumber++;
				}

			}
			catch (IllegalArgumentException e)
			{
				System.out.println(e.getMessage());
				System.out.println("Please enter correct arguments");
			}
		}
		//use random program generator
		else if (args[0].equalsIgnoreCase("-r"))
		{
			try
			{
				int numberOfColors = Integer.parseInt(args[1]);
				int numberOfCustomers = Integer.parseInt(args[2]);
				RandomProblemGenerator randomProblemGenerator = new RandomProblemGenerator(numberOfColors, numberOfCustomers);
				Problem generatedProblem = randomProblemGenerator.generateProblem();
				System.out.println("\nProblem statement");
				System.out.println(generatedProblem.toString());
				Solver solver = new BruteForceSolver();
				int[] solution = solver.solveProblem(generatedProblem);
				printSolution(solution, 1);
			}
			catch (IllegalArgumentException e)
			{
				System.out.println("Please enter correct arguments");
			}
		}

	}

	//prints the solution as requirement indicates
	public static void printSolution(int[] solution, int caseNumber)
	{

		String solString = "";
		//formating
		if (solution[0] == -2)
		{
			System.out.println("Case #" + caseNumber + ": IMPOSSIBLE");
		}
		else
		{
			solString = "Case #" + caseNumber + ":";
			for (int i : solution)
			{
				solString = solString + " " + i;
			}

			solString = solString + "\n";
			System.out.print(solString);
		}
	}

}
