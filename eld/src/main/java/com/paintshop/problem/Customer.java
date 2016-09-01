package com.paintshop.problem;

import java.util.*;

public class Customer {

	public boolean isHappy = false;
	public boolean onlyOneOption = false;
	public Map<Integer, Integer> colorPreferences;
	public Map<Integer, Integer> proposedColorPreferences; //these will be the options that we proposed for each customer


	public Customer(int[] preferences) {
		onlyOneOption = preferences.length == 2 ? true : false;
		colorPreferences = new HashMap<Integer, Integer>(preferences.length / 2);
		proposedColorPreferences = new HashMap<Integer, Integer>(preferences.length / 2);

		for (int i = 0; i < preferences.length - 1; i = i + 2) {
			colorPreferences.put(preferences[i], preferences[i + 1]);
		}
	}

	@Override
	public int hashCode() {
		int multiplier = isHappy ? 2 : 3;
		return colorPreferences.hashCode() * multiplier;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			Customer compareCustomer = (Customer) obj;
			return this.colorPreferences.equals(compareCustomer.colorPreferences) && this.isHappy == compareCustomer.isHappy;
		} catch (ClassCastException e) {
			System.out.print(e.getMessage());
			return false;
		}
	}

	public boolean checkProposedSolution(int[] solution) {

		//solution marked as impossible no need for check
		if(solution[0]==-2){
			return false;
		}
		else{
			int matchingPreferences = (int) colorPreferences.entrySet().stream()
					.filter(colorPreference -> solution[colorPreference.getKey() - 1] == colorPreference.getValue()).count();
			return matchingPreferences > 0 ? true : false;
		}
	}

	@Override
	public String toString() {

		String info=" ";
		Set<Integer>keys=colorPreferences.keySet();

		for(Integer key:keys){
			info=info+key+" "+colorPreferences.get(key)+" ";
		}

		return info;
	}
}
