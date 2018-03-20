package datastructures;

import static java.lang.Math.*;

/**
 * Matrix-Klasse, die viele mathematische Funktionen bietet.
 * 
 * @author Michael
 *
 */
public class Matrix {
	public double[][] matrix;
	private int height = 0;
	private int width = 0;

	/**
	 * empty Constructor
	 */
	public Matrix(){
		
	}
	
	/**
	 * Standard-Konstruktor
	 * @param matrix
	 */
	public Matrix(double[][] matrix) {
		this.matrix = matrix;
		height = matrix.length;
		width = matrix[0].length;
	}

	/**
	 * Deep Copy-Konstruktor
	 * @param other eine andere Matrix, deren Inhalte tief kopiert werden 
	 */
	public Matrix(Matrix other) {
		height = other.matrix.length;
		width = other.matrix[0].length;
		this.matrix = new double[height][width];
		for (int i = 0; i < height; ++i) {
			for (int j = 0; i < width; ++i) {
				matrix[i][j] = other.matrix[i][j];
			}
		}
	}
	
	public int rank(){
		return new Jama.Matrix(matrix).rank();
	}

	public Matrix inverse() {
		double[][] result = new double[height][width]; 
		try{
		result = new Jama.Matrix(matrix).inverse().getArray();
		}catch (Exception ex) {
			return null;
		}
		
		return new Matrix(result);
	}
	
	public boolean regulaer(){
		return false;
	}

	/**
	 * zu lesen als ||A||F
	 * @return die Wurzel aus den zusammengerechneten Quadraten aller Werte.
	 */
	public double frobeniusNorm() {
		double norm = 0;
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				norm += pow(matrix[i][j], 2);
			}
		}
		return sqrt(norm);
	}

	/**
	 * zu lesen als ||A||infinity
	 * @return die groesste mit absoluten Werten zusammengerechnete Zeile einer Matrix
	 */
	public double zeilensummenNorm() {
		double norm = 0;
		for (int i = 0; i < height; ++i) {
			double tmp = 0;
			for (int j = 0; j < width; ++j) {
				tmp += abs(matrix[i][j]);
			}
			if (tmp > norm)
				norm = tmp;
		}
		return norm;
	}

	/**
	 * zu lesen als ||A||1
	 * @return die groesste mit absoluten Werten zusammengerechnete Spalte einer Matrix
	 */
	public double spaltensummenNorm() {
		double norm = 0;
		for (int i = 0; i < width; ++i) {
			double tmp = 0;
			for (int j = 0; j < height; ++j) {
				tmp += abs(matrix[j][i]);
			}
			if (tmp > norm)
				norm = tmp;
		}
		return norm;
	}

	/**
	 * LU-Zerlegung einer Matrix
	 * @return
	 */
	public double[][] LU() {
		Matrix result = new Matrix(this);
		/*
		 * if(height!=width){ out<<"nope! Matrix muss quadratisch sein!\n";
		 * return out; }
		 * 
		 * for(int i=0;i<(width-1);++i){ //rechnen
		 * 
		 * for(int x=i+1;x<width;++x){
		 * result.matrix[x][i]=result.matrix[x][i]/result.matrix[i][i]; for(int
		 * y=i+1;y<width;++y){
		 * result.matrix[x][y]=result.matrix[x][y]-(result.matrix[x][i]*result.
		 * matrix[i][y]); } } }
		 * 
		 * Matrix result2(result);
		 * 
		 * //Matrizen dem LU-Format anpassen for(int i=0;i<width;++i){ for(int
		 * j=i;j<width;++j){ if (j==i) result.matrix[i][j]=1; else
		 * result.matrix[i][j]=0; } }
		 * 
		 * for(int i=0;i<width;++i){ for(int j=0;j<i;++j){
		 * result2.matrix[i][j]=0; } }
		 * 
		 * out<<"\nL=\n"<<result<<"U=\n"<<result2<<endl; return out;
		 */
		return result.matrix;
	}

	public boolean Zeilensummenkriterium() {
		for (int i = 0; i < height; ++i) {
			double tmp = 0;
			for (int j = 0; j < width; ++j) {
				tmp += abs(matrix[i][j]);
			}
			tmp -= abs(matrix[i][i]);
			if (tmp >= matrix[i][i])
				return false;
		}
		return true;
	}

	public double Determinante() {
		if (height != width) {
			return sqrt(-1);
		}
		if (height == 1) {
			return matrix[0][0];
		}
		if (height == 2) {
			return (matrix[0][0] * matrix[1][1]) - (matrix[1][0] * matrix[0][1]);
		}
		if (height == 3) {
			return (matrix[0][0] * matrix[1][1] * matrix[2][2]) + (matrix[0][1] * matrix[1][2] * matrix[2][0])
					+ (matrix[0][2] * matrix[1][0] * matrix[2][1]) - (matrix[0][2] * matrix[1][1] * matrix[2][0])
					- (matrix[0][1] * matrix[1][0] * matrix[2][2]) - (matrix[0][0] * matrix[1][2] * matrix[2][1]);
		}
		return 1;
	}
	
	public Matrix Produkt(Matrix other){
		Matrix result;
		if(this.width != other.height) return null;
		double[][] mat = new double[this.height][other.width];
		for(int x = 0 ; x<this.height; ++x){
			for( int y = 0; y<other.width; ++y){
				double value = 0;
				for(int i = 0; i<this.width; ++i){
					value += (this.matrix[x][i]*other.matrix[i][y]);
				}
				mat[x][y] = value;
			}
		}
		result = new Matrix(mat);
		return result;
	}
	
	
	@Override public String toString(){
		String result = "";
		for(double[] arr : matrix){
			for(double d : arr){
				result += d+"\t\t";
			}
			result+= "\n";
		}
		return result;
	}

}
