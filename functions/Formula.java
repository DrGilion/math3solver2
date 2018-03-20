package functions;

import org.scilab.forge.jlatexmath.TeXFormula;

public class Formula {

	private Formula(){}
	
	public static TeXFormula RelativerFehler(){
		
		TeXFormula result = new TeXFormula("e_{rel}=\\frac{l x_{exakt} \\text{ - } x_{approx}l}{l x_{exakt}l}");
		return result;
	}
	
	public static TeXFormula AbsoluterFehler(){
		TeXFormula result = new TeXFormula("e_{abs}=l x_{exakt} \\text{ - } x_{approx}l");
		return result;
	}
}
