Problem Statement

You own a paint factory. There are N different colors you can mix, and each color can be prepared "matte" or "glossy". So, you can make 2N different types of paint.
Each of your customers has a set of paint types they like, and they will be satisfied if you have at least one of those types prepared. At most one of the types a customer likes will be a "matte".
You want to make N batches of paint, so that:

There is exactly one batch for each color of paint, and it is either matte or glossy.
For each customer, you make at least one paint type that they like.
The minimum possible number of batches are matte (since matte is more expensive to make).
Find whether it is possible to satisfy all your customers given these constraints, and if it is, what
paint types you should make.
If it is possible to satisfy all your customers, there will be only one answer which minimizes the
number of matte batches

Solution 

-To solve this problem I developed an algorithm based on backtrack algorithm
-In addition, I developed another algorithm which brute forces all possibilities to see which one is suitable
(this was created for testing purposes)
-There is a random problem generator which generates random cases, again mainly created for testing purpose
-I made classes's attributes public on purpose of readability
-First I wrote unit tests for brute force method and I test main method using brute force method
-You can run the program with different algorithms
  -  -i <name of the input.txt>  this will read given .txt file and output the solution if any
  -  -r <number of colors> <number of customers> this will use random problem generator and generate one problem with given parameters

-Project can be imported into inteliji IDEA it is a maven project

Enjoy!!!
