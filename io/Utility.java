package io;

import java.util.ArrayList;
import java.util.Arrays;

public class Utility {
	
	public static ArrayList<Double> StringToArray(String input){
		ArrayList<Double> result = new ArrayList<>();
		String[] strings = input.split(",");
		Arrays.stream(strings).forEach( tmp -> result.add(Double.parseDouble(tmp)));
		return result;	
	}

}
