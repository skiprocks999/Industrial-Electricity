package industrialelectricity.core.utils;

import electrodynamics.Electrodynamics;
import net.minecraft.util.Mth;

public class IMathUtils {

	/**
	 * Returns the inverse hyperbolic cosine of a value
	 * 
	 * @param x      : the value in question
	 * @param xShift : the xShift in y = cosh(a * (x + xShift)) / b
	 * @param a      : the "a" in y = cosh(a * (x + xShift)) / b
	 * @param b      : the "b" in y = cosh(a * (x + xShift)) / b
	 * @return
	 */
	public static float acosh(float x, float xShift, float a, float b) {
		if (b <= 0.0F || x <= (b - xShift)) {
			return 0.0F;
		}
		float num = (x + xShift) / b;
		return (float) (Math.log(num + Math.sqrt(num * num - 1.0F)) * a);
	}

	public static float cosh(float x, float xShift, float a, float b) {
		if (a == 0.0F) {
			return 1.0F;
		}
		return (float) (Math.cosh((x + xShift) / a) * b);
	}

	public static float sinh(float x, float xShift, float a, float b) {
		if (a == 0.0F) {
			return 1.0F;
		}

		return (float) (Math.sinh((x + xShift) / a) * b);
	}

	/**
	 * Given a catenary curve between two points of unequal heights, this function will determine the A value via newtonian iteration
	 * 
	 * @param guessA             : the starting point of the iteration
	 * @param horizontalDistance : the horizontal distance between the two cable points
	 * @param verticalDistance   : the vertical distance between the two cable points
	 * @param cableLength        : the length of the cable
	 * @return : the result of the iteration
	 */
	public static float newtIterForA(float guessA, float horizontalDistance, float verticalDistance, float cableLength) {

		int maxIterations = 100;

		float minimumDifference = 1e-6F;

		float currAVal = guessA;

		float prevAVal = guessA;

		float beta = Mth.sqrt(cableLength * cableLength - verticalDistance * verticalDistance);

		float fOfX, fPrimeOfX, dOverA, dOver2A, twoSinhDOver2A;

		int i = 0;

		while (i < maxIterations) {
			
			prevAVal = currAVal;
			
			//Electrodynamics.LOGGER.info("i: " + i  + ", prevA : " + prevAVal + ", curA : " + currAVal);
			
			dOverA = horizontalDistance / currAVal;

			dOver2A = dOverA / 2.0F;

			twoSinhDOver2A = IMathUtils.sinh(dOver2A, 0, 1, 1) * 2.0F;

			fOfX = currAVal * twoSinhDOver2A - beta;

			fPrimeOfX = twoSinhDOver2A - dOverA * IMathUtils.cosh(dOver2A, 0, 1, 1);

			currAVal = currAVal - (fOfX / fPrimeOfX);

			if (currAVal == 0) {
				return 1.0F;
			}

			if (Math.abs(currAVal - prevAVal) <= minimumDifference) {
				//Electrodynamics.LOGGER.info("currA : " + currAVal);
				return currAVal;
			}

			
			
			i++;

		}
		
		//Electrodynamics.LOGGER.info("currA : " + currAVal);
		
		

		return currAVal;

	}

}
