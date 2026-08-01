package com.Test;

public class ReverseTraingle {
	
	public static void main(String[] args) {
		System.out.println("Reverse Traingle Program");
		        int rows = 4;
		        
		        for (int i = 1; i <= rows; i++) {		            
		            for (int j = 1; j < i; j++) {		            	
		                System.out.print(" ");
		            }
		        for (int k = i; k <= rows; k++) {
		                System.out.print(k + " ");
		            }	            
		        		System.out.println();
		  
		}

	}
}


