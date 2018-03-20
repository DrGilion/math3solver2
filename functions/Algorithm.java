package functions;

import datastructures.Expr;
import datastructures.Matrix;
import datastructures.Vektor;

import static java.lang.Math.*;

import java.util.ArrayList;

public class Algorithm {

	public static double calculateAbsoluteError(double exact, double estimate) {
		return abs(estimate - exact);
	}

	public static double calculateRelativeError(double exact, double estimate) {
		return calculateAbsoluteError(exact, estimate) / abs(exact);
	}

	public static double Poisson(int p, int max, double intervall) {
		double lambda = p / intervall;
		double result = 0;
		for (int i = 0; i <= max; ++i) {
			result += pow(E, -2) * ((pow(2, i) / fakultaet(i)));
		}
		return 1 - result;
	}

	public static long fakultaet(int max) {
		long fakultaet = 1;
		for (int zahl = 1; zahl <= max; zahl++) {
			fakultaet = fakultaet * zahl;
		}
		return fakultaet;
	}

	public static double calculateStandardNormalVerteilung(double X, double erwartungswert, double varianz) {
		return StandardnormalVerteilung((X - erwartungswert) / sqrt(varianz), 1000);
	}

	/**
	 * Berechnet die Gaussfunktion (Wahrscheinlichkeitsdichte) mit
	 * Erwartungswert 0 und Varianz 1 für ein gegebenes x.
	 * 
	 * @param x
	 *            Stelle an der der Funktionswert berechnet werden soll
	 * @return Funktionswert an der Stelle x
	 */
	public static double calculateErrorFunction(double x) {
		return 1 / (sqrt(2 * PI)) * exp(-pow(x, 2) / 2);
	}

	/**
	 * Berechnet die Standardnormalverteilung für ein gegebenes x mittels
	 * Integralapproximierung (Simpson-Verfahren).
	 * 
	 * @param x
	 *            Stelle x an der die Standardnormalverteilung berechnet werden
	 *            soll
	 * @param N
	 *            Präzisionsparameter für das Simpson-Verfahren (je höher desto
	 *            genauer das Ergebnis)
	 * @return (Approximierte) Standardnormalverteilung an der Stelle x
	 */
	public static double StandardnormalVerteilung(double x, int N) {
		double limitLinks = -10;
		if (x <= limitLinks)
			return 0;

		double h = abs(limitLinks - x) / N;
		double summe = 0;

		// 1. Schleife in normalVerteilung()
		for (int i = 1; i <= N - 1; i++) {
			double xi = limitLinks + i * h;
			summe += calculateErrorFunction(xi);
		}

		// 2. Schleife in normalVerteilung()
		for (int i = 1; i <= N; i++) {
			double xi_vorher = limitLinks + (i - 1) * h;
			double xi = limitLinks + i * h;
			summe += 2 * calculateErrorFunction((xi_vorher + xi) / 2);
		}

		summe = (h / 3) * (0.5 * calculateErrorFunction(limitLinks) + summe + 0.5 * calculateErrorFunction(x));
		return summe;
	}

	public static double Erwartungswert(int p, double chance) {
		return chance * p;
	}

	public static double Varianz(int wert, double chance) {
		double result = wert * chance * (1 - chance);
		return result;
	}

	public static double Fixpunkt(double start, int iterations, Expr func) {
		double tmp = start;
		for (int i = 0; i < iterations; ++i) {
			tmp = func.value(tmp);
		}
		return tmp;
	}

	public static double BanachFix(double L, double x0, double x1, double iter) {
		return (pow(L, iter) / (1 - L)) * abs(x1 - x0);
	}

	/**
	 * Konvergenz der Fixpunktiteration
	 * 
	 * @param a
	 *            zu berechneder Term
	 * @param limit1
	 *            untere Intervallgrenze
	 * @param limit2
	 *            untere Intervallgrenze
	 * @return ist die Funktion im Intervall konvergenz
	 */
	public static boolean KonvergenzFixpunktiteration(Expr func, double limit1, double limit2) {
		return (func.value(limit1) > 1 && func.value(limit2) > 1);
	}

	/**
	 * Konvergenz des newtonverfahrens
	 * 
	 * @param funcab1
	 *            erste Ableitung der Funktion
	 * @param funcab2
	 *            zweite Ableitung der Funktion
	 * @return ist das Newtonverfahren für dieses Intervall konvergent?
	 */
	public static boolean KonvergenzNewtonVerfahren(Expr funcab1, Expr funcab2, double limit1, double limit2) {
		return (funcab1.value(limit1) > 0 || funcab2.value(limit1) > 0);

	}

	/**
	 * Berechnung der Determinante einer Matrix
	 * 
	 * @param m
	 *            zu berechnede Matrix
	 * @return Determinannte der Matrix m
	 */
	public static double Det_berechnen(Matrix m) {
		return m.Determinante();

	}

	/**
	 * LU-Zerlegung einer Matrix
	 * 
	 * @param m
	 *            Matrix aus der die LU-Zerlegung berechnet wird
	 * @return LU-Zerlegung der Matrix m
	 */
	public static double[][] LUZerlegung(Matrix m) {
		return m.LU();

	}

	/**
	 * Convience-Methode für alle Normen eines Vektors
	 * 
	 * @param vec
	 *            zu berechneder Vektor
	 * @return ein double-Array mit 3 Werten: 1. Euklidnorm 2. Summennorm 3.
	 *         Maximumsnorm
	 */
	public static double[] Vektornormen(Vektor vec) {
		return new double[] { vec.euklidNorm(), vec.summenNorm(), vec.maximumsNorm() };
	}
	
	public static double VektorBetrag(Vektor vec){
		return vec.Betrag();
	}
	/**
	 * jede Zahl im Vektor wird durch die Laenge des Vektors geteilt. Dieser Vektor ist dann der normierte Vektor.
	 * @param vec
	 * @return
	 */
	
	public static Vektor normierterVektor(Vektor vec){
		return vec.normiert();
	}

	/**
	 * Convience-Methode für alle Normen einer Matrix
	 * 
	 * @param m
	 *            zu berechnede Matrix
	 * @return ein double-Array mit 3 Werten: 1. Zeilensummennorm 2.
	 *         Spaltensummennorm 3. Forbeniusnorm
	 */
	public static double[] Matrixnormen(Matrix m) {
		return new double[] { m.zeilensummenNorm(), m.spaltensummenNorm(), m.frobeniusNorm() };
	}

	/**
	 * Polynom-Interpolation nach Lagrange
	 * 
	 * @param m
	 *            Input für die Berechnung
	 * @param polynomgrad
	 *            Grad des Polynoms
	 * @return
	 */
	public static String[] LagrangePolynomInterpolation(Matrix m, int polynomgrad) {
		String[] result = new String[polynomgrad + 2];
		for (int a = 0; a <= polynomgrad; ++a) {
			double prod = 1;
			for (int b = 0; b <= polynomgrad; ++b) {
				if (a != b)
					prod *= (m.matrix[1][a] - m.matrix[1][b]);
			}
			prod = 1 / prod;
			String tmp = "";
			tmp += a + ".Langrangepoylnom : l" + a + "(x)" + prod + "*";
			for (int b2 = 0; b2 <= polynomgrad; ++b2) {
				if (a != b2) {
					tmp += "(x-" + m.matrix[1][b2] + ")";
				}
			}
			tmp += "\n";
			result[a] = tmp;
		}
		String end = "";
		end += "Vollständiges Interpolationpolynom :\np" + polynomgrad + "(x)= ";
		for (int z = 0; z <= polynomgrad; ++z) {
			end += m.matrix[2][z] + "*l" + z + "(x)";
			if (z != polynomgrad)
				end += "+ ";
		}
		end += "\n";
		result[polynomgrad+1] = end;
		return result;
	}

	/**
	 * Polynom-Interpolation nach Newton
	 * 
	 * @param m
	 * @param polynomgrad
	 */
	public static String NewtonPolynomInterpolation(Matrix m, int polynomgrad) {

		polynomgrad++;
		double[] tmp = new double[polynomgrad];
		double[] result = new double[polynomgrad];
		result[0] = m.matrix[2][0];

		for (int i = 0; i != polynomgrad; ++i) {
			tmp[i] = m.matrix[2][i];
		}

		for (int x = 0; x != (polynomgrad-1); ++x) {
			for (int y = 0; y != (polynomgrad - 1 - x); ++y) {
				tmp[y] = ((tmp[y + 1] - tmp[y]) / (m.matrix[1][y + x + 1] - m.matrix[1][y]));
			}
			result[x+1] = tmp[0];
		}
		
		String retval = "";
		retval += "p" + (polynomgrad-1) + "(x)=\n" + result[0] + "+\n";
	    for(int a=1;a!=polynomgrad;++a){  //output
	        retval += result[a] + "* ";
	        for(int b=0;b!=a;++b){
	            retval += "(x-" + m.matrix[1][b] + ")";
	        }
	        if(a!= (polynomgrad-1)) retval += "+";
	        retval += "\n";
	    }

		return retval;

	}

	/**
	 * Integral mit Recheckregel nähern
	 * 
	 * @param func
	 *            benutzter Term
	 * @param N
	 *            Anzahl Integrale
	 * @param limit1
	 *            untere Intervalgrenze
	 * @param limit2
	 *            obere Intervalgrenze
	 */
	public static double IntegralRechteckregel(Expr func, int N, double limit1, double limit2) {
		double h = ((limit2 - limit1) / N);

		double sum = 0;
		for (int i = 0; i < N; ++i) {
			sum += func.value((double) (((limit1 + i * h) + (limit1 + (i + 1) * h)) / 2));
		}
		sum *= h;
		return sum;

	}

	/**
	 * Integral mit Trapezregelregel nähern
	 * 
	 * @param func
	 * @param N
	 * @param limit1
	 * @param limit2
	 * @return
	 */
	public static double IntegralTrapezregel(Expr func, int N, double limit1, double limit2) {
		double h = ((limit2 - limit1) / N);

		double sum = 0;
		for (int i = 1; i <= N - 1; ++i) {
			double tmp = func.value((double) (limit1 + i * h));
			sum += tmp;
		}
		double t = func.value(limit1);
		double u = t / 2;
		sum += u;
		t = func.value(limit2);
		u = t / 2;
		sum += u;
		sum *= h;
		return sum;
	}

	/**
	 * Integral mit Simpsonregel nähern
	 * 
	 * @param func
	 * @param N
	 *            nur mit gerade Anzahl von Intervallen!!!
	 * @param limit1
	 * @param limit2
	 * @return
	 */
	public static double IntegralSimpsonregel(Expr func, int N, double limit1, double limit2) {

		if (N % 2 != 0) {
			return 1 / 0;
		}
		double h = ((limit2 - limit1) / N);

		double sum = 0;
		for (int i = 0; i < (N / 2); ++i) {
			sum += func.value((double) (limit1 + (2 * i) * h));
			sum += (func.value((double) (limit1 + (i * 2 + 1) * h))) * 4;
			sum += func.value((double) (limit1 + (i * 2 + 2) * h));
		}
		sum *= (h / 3);
		return sum;
	}

	/**
	 * Fehler der Rechteckregel berechnen
	 * 
	 * @param func
	 * @param N
	 * @param limit1
	 * @param limit2
	 * @return
	 */
	public static double RechteckregelFehler(Expr funcab2, int N, double limit1, double limit2) {
		double h = ((limit2 - limit1) / N);
		double mx = 0;
		for (double i = limit1; i <= limit2; i += 0.00001) {
			double tmp = abs(funcab2.value((double) i));
			if (tmp > mx)
				mx = tmp;
		}
		double x = ((limit2 - limit1) / 24) * h * h * mx;
		return x;
	}

	/**
	 * Fehler der Trapezregel
	 * 
	 * @param funcab2
	 * @param N
	 * @param limit1
	 * @param limit2
	 * @return
	 */
	public static double TrapezregelFehler(Expr funcab2, int N, double limit1, double limit2) {
		double h = ((limit2 - limit1) / N);
		double mx = 0;
		for (double i = limit1; i <= limit2; i += 0.00001) {
			double tmp = abs(funcab2.value((double) i));
			if (tmp > mx)
				mx = tmp;
		}

		double x = ((limit2 - limit1) / 12) * h * h * mx;
		return x;
	}

	/**
	 * Fehler der Simpsonregel berechnen
	 * 
	 * @param funcab4
	 * @param N
	 * @param limit1
	 * @param limit2
	 * @return
	 */
	public static double SimpsonregelFehler(Expr funcab4, int N, double limit1, double limit2) {
		double h = ((limit2 - limit1) / N);
		double mx = 0;
		for (double i = limit1; i <= limit2; i += 0.00001) {
			double tmp = abs(funcab4.value((double) i));
			if (tmp > mx)
				mx = tmp;
		}

		double x = ((limit2 - limit1) / 180) * h * h * h * h * mx;
		return x;
	}
	
	/**
	 * Bisektion mit Prezisionsangabe
	 * @param func
	 * @param a
	 * @param b
	 * @param precision
	 * @return
	 */
	public static double Bisektion(Expr func,double a,double b,double precision){
		if(func.value(a)* func.value(b) > 0){
			throw new ArithmeticException("Diese Funktion hat keine Nullstelle");
		}
		
		double x = (a+b)/2;
		
		if(abs(b-a) < precision){
			return x;
		}else{
			if(func.value(a) * func.value(x) < 0 ){
				return Bisektion(func,a,x,precision);
			}else{
				return Bisektion(func,x,b,precision);
			}
		}
	}

	public static double[] Bisektion(double x0, double x1, int iterations, Expr func) {
		double[] result = new double[iterations];
		double x = ((x0 + x1) / 2);
		for (int i = 1; i <= iterations; ++i) {
			result[i] = x;
			if ((func.value(x0)) * (func.value(x)) < 0)
				x1 = x;
			else
				x0 = x;
			x = ((x0 + x1) / 2);
		}
		return result;
	}

	/**
	 * Konvergenz des Bisektionsverfahren
	 * 
	 * @param limit1
	 *            untere Intervallgrenze
	 * @param limit2
	 *            obere Intervallgrenze
	 * @param genauigkeit
	 *            der Berechnung
	 * @return Anzahl an Schritten bis das Bisektionsverfahren auf diesem
	 *         Intervall konvergiert
	 */
	public static int KonvergenzBisektion(double limit1, double limit2, double genauigkeit) {

		double x = limit2 - limit1;
		double n = log(x / genauigkeit);
		return (int) n;

	}

	public static double[] NewtonVerfahren(Expr func, Expr funcab1, double x0, int iterations) {
		double[] result = new double[iterations];
		double xalt, xneu;
		xalt = x0;
		for (int i = 1; i <= iterations; ++i) {
			xneu = xalt - ((func.value(xalt)) / (funcab1.value(xalt)));
			result[i] = xneu;
			xalt = xneu;
		}
		return result;
	}

	public static double[] SekantenVerfahren(Expr func, int iterations, double xalt, double xneu) {
		double[] result = new double[iterations];
		for (int i = 1; i <= iterations; ++i) {
			double tmp = xneu;
			xneu = xalt - ((xneu - xalt) / ((func.value(xneu)) - (func.value(xalt)))) * (func.value(xalt));
			result[i] = xneu;
			xalt = tmp;
		}
		return result;
	}

	/**
	 * Regula-Falsi-Verfahren
	 * @param func
	 * @param iterations
	 * @param xalt
	 * @param xneu
	 * @return
	 */
	public static double[] RegulaFalsi(Expr func, int iterations, double xalt, double xneu) {
		double[] result = new double[iterations];
		for (int i = 1; i <= iterations; ++i) {
			double tmp = xneu;
			xneu = xalt - ((xneu - xalt) / ((func.value(xneu)) - (func.value(xalt)))) * (func.value(xalt));
			result[i] = xneu;
			xalt = tmp;
		}
		return result;
	}

	public static double[][] JacobiVerfahren(Matrix m, Vektor x, Vektor start, int iterations) {
		double[][] result = new double[iterations][3];
		double x1, x2, x3;

		for (int i = 0; i < iterations; ++i) {
			x1 = ((-1 * m.matrix[0][1] * start.vector[1]) / m.matrix[0][0])
					+ ((-1 * m.matrix[0][2] * start.vector[2]) / m.matrix[0][0]) + (x.vector[0] / m.matrix[0][0]);
			result[i][0] = x1;
			x2 = ((-1 * m.matrix[1][0] * start.vector[0]) / m.matrix[1][1])
					+ ((-1 * m.matrix[1][2] * start.vector[2]) / m.matrix[1][1]) + (x.vector[1] / m.matrix[1][1]);
			result[i][1] = x2;
			x3 = ((-1 * m.matrix[2][0] * start.vector[0]) / m.matrix[2][2])
					+ ((-1 * m.matrix[2][1] * start.vector[1]) / m.matrix[2][2]) + (x.vector[2] / m.matrix[2][2]);
			result[i][2] = x3;
			start.vector[0] = x1;
			start.vector[1] = x2;
			start.vector[2] = x3;

		}
		return result;
	}

	public static boolean KonvergenzJacobi(Matrix m) {
		return m.Zeilensummenkriterium();
	}
	
	public static double[][] GaussSeidelVerfahren(Matrix m, Vektor x, Vektor start, int iterations) {
		double[][] result = new double[iterations][3];
		double x1, x2, x3;

		for (int i = 0; i < iterations; ++i) {
			x1 = ((-1 * m.matrix[0][1] * start.vector[1]) / m.matrix[0][0])
					+ ((-1 * m.matrix[0][2] * start.vector[2]) / m.matrix[0][0]) + (x.vector[0] / m.matrix[0][0]);
			result[i][0] = x1;
			start.vector[0] = x1;
			x2 = ((-1 * m.matrix[1][0] * start.vector[0]) / m.matrix[1][1])
					+ ((-1 * m.matrix[1][2] * start.vector[2]) / m.matrix[1][1]) + (x.vector[1] / m.matrix[1][1]);
			result[i][1] = x2;
			start.vector[1] = x2;
			x3 = ((-1 * m.matrix[2][0] * start.vector[0]) / m.matrix[2][2])
					+ ((-1 * m.matrix[2][1] * start.vector[1]) / m.matrix[2][2]) + (x.vector[2] / m.matrix[2][2]);
			result[i][2] = x3;
			start.vector[2] = x3;

		}
		return result;
	}

	/**
	 * Bestimmung der Konvergenz des Gauss-Seidel-Verfahrens
	 * 
	 * @param m
	 * @return ist das Gauss-Seidel-Verfahren bei dieser Matrix konvergent?
	 */
	public static boolean KonvergenzGaussSeidel(Matrix m) {
		return m.Zeilensummenkriterium();
	}

	/**
	 * Arithmetisches Mittel berechnen
	 * 
	 * @param data
	 *            Wertemenge
	 * @return arithmetisches Mittel
	 */
	public static double ArithmetischesMittel(ArrayList<Double> data) {
		double result = 0;
		for(double d : data){
			result += d;
		}
		return result/data.size();
	}
	
	/**
	 * Geometrisches Mittel berechnen
	 * @param input
	 * @return geometrische Mittel
	 */
	public static double GeometrischesMittel(ArrayList<Double> input){
		double sum = 1.0;
		for(double d : input){
			sum*= d;
		}
		sum = pow(sum,1.0/input.size());
		return sum;
	}
	
	/**
	 * Modalwert berechnen
	 * @param input
	 * @return
	 */
	public static double Modalwert(ArrayList<Double> input){
		input.sort((o1,o2) -> Double.compare(o1, o2));
		int max = 0;
		double mod= 0.0;
		int count = 0;
		double current= Double.MIN_VALUE;
		for(double d : input){
			if(d == current){
				count++;
				if(count>max){
					max = count;
					mod = d;
				}
			}else{
				count = 0;
				current = d;
			}
		}
		return mod;
	}

	/**
	 * Mittelwert eines anwachsenden Datensatzes
	 * 
	 * @return neuer Mittelwert
	 */
	public static double AnwachsendMittelwert(int size, double mittel1, double mittel2) {
		double tmp = size * mittel1;
		double tmp2 = mittel2 * (size + 1);
		return tmp2 - tmp;
	}

	/**
	 * Berechnung des Medians eines Datensatzes
	 * Spezialfall des p-Quantils mit anteil von 0.5
	 * @param input
	 *            der datensatz
	 * @return Median aus allen Daten
	 */
	public static double Median(ArrayList<Double> input) {
		return p_quantil(input,0.5);
	}
	
	/**
	 * 
	 * @param input
	 * @param area
	 * @return
	 */
	public static double p_quantil(ArrayList<Double> input,double area){
		if(area <0.0 || area >1.0) throw new IllegalArgumentException();
		int len = input.size();
		input.sort((o1,o2) -> Double.compare(o1, o2));
		return input.get((int) (len * area));
	}
	
	/**
	 * Die Spannweite (engl. range) ist definiert als die Differenz
	 * zwischen dem größten und dem kleinsten beobachteten Wert
	 * 
	 * @param input
	 * @return
	 */
	public static double Spannweite(ArrayList<Double> input){
		double min = input.get(0);
		double max = input.get(0);
		for( double d : input){
			if(d < min) min = d;
			if(d > max) max = d;
		}
		return max - min;
	}
	
	public static double EmpirischeVarianz(ArrayList<Double> input){
		double mittel = ArithmetischesMittel(input);
		double sum = 0.0;
		for(double d : input){
			sum += pow(d-mittel,2);
		}
		return (1.0/(input.size()-1)) * sum;
	}
	
	public static double EmpirischeStandardAbweichung(ArrayList<Double> input){
		return sqrt(EmpirischeVarianz(input));
	}
	
	public static double Variationskoeffizient(ArrayList<Double> input){
		return EmpirischeStandardAbweichung(input)/ArithmetischesMittel(input);
	}
	
	public static double InterquartilAbstand(ArrayList<Double> input){
		return p_quantil(input, 0.75) - p_quantil(input, 0.25);
	}

	/**
	 * Wahrscheinlichkeitsdichte einer gleichverteilten Zufallsvariable
	 * 
	 * @param limit1
	 *            unteres Limit des Intervalls
	 * @param limit2
	 *            oberes Limit des Intervalls
	 * @return wahrscheinlichkietsdichte des Intervalls
	 */
	public static double WdichteGleichverteilt(double limit1, double limit2) {
		return /* 1 durch */limit2 - limit1;
	}

	/**
	 * Wahrscheinlichkeit einer normalverteilten Zufallsvariable. In der
	 * folgenden Tabelle den richtigen Wert ablesen:
	 * 
	 * @see <a href=
	 *      "http://de.wikipedia.org/wiki/Tabelle_Standardnormalverteilung">link
	 *      </a>
	 */
	public static double Normalverteilung(double erw, double varianz, double X) {

		return ((X - erw) / sqrt(varianz));
	}

}
