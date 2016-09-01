package com.paintshop.problem;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Problem {
    public int numberOfCustomers;
    public int numberOfColors;
    public SortedSet<Customer> customers;
    public int[] state;

    public Problem(int numberOfCustomers, int numberOfColors) {
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfColors = numberOfColors;
        this.customers = new TreeSet<Customer>(((o1, o2) -> {
            int res = o1.colorPreferences.size() >= o2.colorPreferences.size() ? 1 : -1;
            return res;
        }));
        this.state=new int[this.numberOfColors];

    }

    public void loadCustomerData(List<Customer> customerList){
        customerList.forEach(customer -> {
            customers.add(customer);
        });
    }

    @Override
    public String toString() {

        String problemInfo="Number of Customers:"+numberOfCustomers+"\nNumber of Colors:"+numberOfColors+"\n";

        Iterator<Customer> customerIterator=customers.iterator();
        int i=1;
        while(customerIterator.hasNext()){
            problemInfo=problemInfo+"Customer#"+i+" "+customerIterator.next().toString()+"\n";
            i++;
        }

        return problemInfo;
    }
}
