/* ===== HELPER CLASS =====
 * WHY DO WE NEED?
 * ANSWER ->
 *	 contains 
 * 		- only static methods
 * 		- private constructors so that nobody can create an object
 * 		- used for validating
 * 			- user input for productName, categoryName,
 * 				brandName etc. which must not be null   
 * 
 */

package com.ecommerce.util;

import java.util.function.Function;

public final class ValidationUtil {

	
	private ValidationUtil() {}
	
	/* trim the user-input
	 * then will reject if trimmed String is null.blank
	 * will reject text which is longer than maxLength
	 */
	public static String validateAndTrim(
		String value, 
		String label, 
		int maxLength,
		Function<String, ? extends RuntimeException> exceptionFactory
	) {
        // trim: "  Brand  " -> "Brand"
        String trimmed = (value == null) ? "" : value.trim();

        if (trimmed.isEmpty()) {
            throw exceptionFactory.apply(label + " cannot be null or blank!");
        }
        if (trimmed.length() > maxLength) {
            throw exceptionFactory.apply(label + " cannot exceed " + maxLength + " characters!");
        }
        return trimmed;
    }
	
	/* will reject invalid IDs 
	 * 	- IDs having null values
	 * 	- IDs with zero value,
	 * 	- IDs with negative value
	 */
	public static void validateId(
		Long id, 
		String label,
		Function<String, ? extends RuntimeException> exceptionFactory
	) {
        if (id == null || id <= 0) {
            throw exceptionFactory.apply(label + " must be a positive number!");
        }
    }
}
