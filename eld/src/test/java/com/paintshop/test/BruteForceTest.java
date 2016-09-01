package com.paintshop.test;

import com.paintshop.problem.Customer;
import com.paintshop.problem.Problem;
import com.paintshop.solution.impl.BruteForceSolver;
import com.paintshop.utils.RandomProblemGenerator;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class tests brute force solution
 */
public class BruteForceTest {
    RandomProblemGenerator randomProblemGenerator = null;
    Problem problem;

    @Before
    public void initializeProblem()
    {
        int limit = 10;
        Random randomParameters = new Random();
        int numberOfColors=randomParameters.nextInt((limit - 1) + 1)+1;
        int numberOfCustomers=randomParameters.nextInt((limit - 1) + 1)+1;

        randomProblemGenerator = new RandomProblemGenerator(numberOfColors,numberOfCustomers);
        problem=randomProblemGenerator.generateProblem();
    }

    @Test
    public void testBruteForceSolver(){
        BruteForceSolver solver =new BruteForceSolver();
        int[] solution=solver.solveProblem(problem);
        assertTrue(solver.filteredStates.size()<=solver.states.size());
        assertEquals(solver.states.size(),(int) ArithmeticUtils.pow(2,problem.numberOfColors));

        //there is a solution
        if(solution[0]!=-2){
            assertTrue(solver.validateProposal(new ArrayList<Customer>(problem.customers),solution));
        }
        else{
            assertFalse(solver.validateProposal(new ArrayList<Customer>(problem.customers),solution));
        }
    }

}
