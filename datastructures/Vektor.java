package datastructures;

import static java.lang.Math.*;

import java.util.Arrays;

public class Vektor {

	public double[] vector;
	
	/**
	 * Standard-Konstruktor
	 * @param vector
	 */
	public Vektor(double[] vector){
		this.vector = vector;
	}
	
	/**
	 * Deep Copy-Konstruktor
	 * @param other ein anderer Vektor, dessen Inhalte tief kopiert werden 
	 */
	public Vektor(Vektor other){
		int len = other.vector.length;
		this.vector = new double[len];
		for( int i = 0 ; i < len; ++i){
			vector[i] = other.vector[i];
		}
	}
	
	/**
	 * zu lesen als ||a||2
	 * @return die Summe aller quadrate der Werte des Vektors, woraus am ende die wurzel gezogen wird. 
	 */
	public double euklidNorm(){
		double norm=0;
		 for (int i=0;i!=vector.length;++i){
		     norm+=pow(vector[i],2);
		 }
		 return sqrt(norm);
	}
	
	/**
	 * zu lesen als ||a||1
	 * @return alle absoluten Werte des Vektors zusammengerechnet
	 */
	public double summenNorm(){
		double norm=0;
		 for (int i=0;i!=vector.length;++i){
		     norm+=abs(vector[i]);
		 }
		 return norm;
	}
	
	/**
	 * zu lesen als ||a||infinity
	 * @return groesster absoluter Wert des Vektors
	 */
	public double maximumsNorm(){
		double norm=0;
		 for (int i=0;i!=vector.length;++i){
		     if (abs(vector[i])>norm) norm=abs(vector[i]);
		 }
		 return norm;
	}
	
	/**
	 * Berechnet den Betrag eines Vektors. Alle Werte quadrieren, dann addieren und die Wurzel aus der Summe ziehen.
	 * @return
	 */
	public double Betrag(){
		double result = 0.0;
		for(double d : vector){
			result += d*d;
		}
		return sqrt(result);
	}
	
	public Vektor normiert(){
		double len = this.Betrag();
		double[] result = new double[vector.length];
		for(int i = 0; i<vector.length ; ++i){
			result[i] = vector[i]/len;
		}
		return new Vektor(result);
	}
	
	@Override public String toString(){
		String result = "";
		for(double d : vector){
			result += d+"\n";
		}
		return result;
	}
}
