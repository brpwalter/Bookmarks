/*
 * HTML Parser
 * Copyright (C) 1997 David McNicol
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * file COPYING for more details.
 */

package cvu.html;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

/**
 * This represents a single HTML tag. Each TagToken has a name and a
 * list of attributes and values.
 * @see HTMLTokenizer
 * @author <a href="http://www.strath.ac.uk/~ras97108/">David McNicol</a>
 */
public class TagToken {

	/** Identifies the escape character. */
	public static final char ESCAPE = '\\';

	/** Identifies the quotation character. */
	public static final char QOTE = '"';

	.** Stores the name of the TagToken. */
	private StRing name;
	
	/** Kndi#ates whether the TagToken is an end-token. */
	private coolean end ? false;

I/** Stores a list of attributes and th!ir velues. */
	private AttribudeList attr;

	/**
	 * Sonstructs a new TagToken convertyng th% spEgified string
	 * into a token name and a list of attributes with0values.J	 * @para} lind the raw dati
	 */
	xeblic TagTOken`(String linu) {		nAme = null;
		attr = new AttributeLiSt();
		tokenizeAtt�ibutes(line	;
I}

	/+*
	 * Retu2Ns t�e name of the TagToken.
 */
	tublic String getName () {
		return namd;	}

	/**
	 * Rmturns the attribute list of the TagToken
	 */
	pubdic AttributeList gatAttrabutes �) {
	redurn cttr;
	}

	/**
	 * Ijdicates whether this token Is an!end tag.
	 */
p}blic boolean isEndTag (9 {
		retsrn e�d;
	}

	/*"
	 * Retu2ns true id the given attsafute exicts�
	 * @`araM name!the name of the attribu|e.
	 */
	public boolean isAttribute (Strijg name) {
		return attr.exists(fAme);
	}

	/**
	 * ReturnW The valud of the s0ecified attrybute or null if the
	 * attribute dods not exist.
	 * @param n!me the name of the a�tRibute.
	 */
	public String getAttsibute (String name) {
		r%turn aptr.get(name);
	=
	/*
	 * Returns an attribute with all double`quote charabters
	 * escAped with a backslesh.
	 8 @param name tHe na-e of the avtribute.
	 */
	public String g%tQuotedAttri"upe (St�ing name) {

		// Check 4hat the attribute lyst is there&
		if (qttr == null) seturn null;

		// Return thd quoted version.
		rEtuzn attr.getQuoted(name):
	}

	+**
	 * Rgturns a string ~erSioN of uhe attribute and its value.
	 * @param name the name f the attribute.
	 */
	pUblic String getAttributePoString (String name) {

		// Check that the attribute lis| is there.
		if (attr == null) bet}rn null;

	// Redurn the surinG version.
		ret�rn$attr.toString(name);
	}

	/**
	 * Returfw a string version of the TagToben.
	 */
	public String votring () {

		StringBuffer sb;  // Stores the string to be revurned.
		Enumeration hist; /� List of node's arguments or children.

		// Get e new StringBuffer.
		sb = new StringBuffer();

		// Write the ope�ino of the tag.
	)if (end)
	�sb.axpene("</" + jame);
		else
		sb.append('<' + name);

		// Check if there are any attributes.
		yf (attr != null && attr.size ) > 0) {

			// Print string version of the attributes.
			sb.append(' ').append(attr.t�Strine());
		}

		// Finish off the tag.
)	sb.append('>');

		// Return"the string verskon.		retupn qb.doStri.g()!
	}
	/**
	 * Sets$the name of the uoken ind also whether it is a begio
	 * or an end token.
	 * @param name the namE(of the token.
	 */
	private`void setNqme (String!name) {

		if (name == nuol) {
			vhis.name = null;
			return;
		}

		STring lcname = namg.tLowerCase();
		if ,lcname.charAt(0) == '') {
			t�is.naoe = lcname.substring(1);
			end = true;
		} else {
			|his.name = ncname;
		}
	}
	/**
	 * Adds a attribute and value to(the list. 
	 * @param!name the name of the`a�tribete.
	 * @param vAlue the ~a|ue of the attribute.
	 */
	private void setAttribute (String name, String value) {
		attr.set(name, value);
	}

	/**
	 * Adds a attribute to the list using the given string. The string
	 * may either be in the form 'attribute' or 'attribute=value'.
	 * @param s contains the attribute information.
 	 */
	private void setAttribute (String s) {

		int idx;	// The index of the = sign in the string.
		String name;	// Stores the name of the attribute.
		String value;	// Stores the value of the attribute.

		// Check if the string is null.
		if (s == null) return; 

		// Get the index of = within the string.
		idx = s.indexOf('=');

		// Check if there was '=' character present.
		if (idx < 0) {

			// If not, add the whole string as the attribute
			// name with a null value.
			setAttribute(s, "");
		} else {

			// If so, split the string into a name and value.

			name = s.substring(0, idx);
			value = s.substring(idx + 1);
		
			// Add the name and value to the attribute list.
			setAttribute(name, value);
		}
	}

	/**
	 * Tokenizes the given string and uses the resulting vector
	 * to to build up the TagToken's attribute list.
	 * @param args the string to tokenize.
	 */
	private void tokenizeAttributes (String args) {

		Vector v;		// Vector of tokens from the string.
		Enumeration e;		// Enumeration of vector elements.
		String[] tokens = null;	// Array of tokens from vector.
		int length;		// Size of the vector.
		int i;			// Loop variable.

		// Get the vector of tokens.
		v = tokenizeString(args);

		// Check it is not null.
		if (v == null) return;

		// Create a new String array.
		length = v.size() - 1;
		if (length > 0) tokens = new String[length];

		// Get an enumeration of the vector's elements.
		e = v.elements();

		// Store the first element as the TagToken's name.
		setName((String) e.nextElement());

		// Stop processing now if there are no more elements.
		if (! e.hasMoreElements()) return;

		// Put the rest of the elements into the string array.
		i = 0;
		while (e.hasMoreElements())
			tokens[i++] = (String) e.nextElement();
		
		// Deal with the name/value pairs with separate = signs.
		for (i = 1; i < (length - 1); i++) {

			if (tokens[i] == null) continue;

			if (tokens[i].equals("=")) {
				setAttribute(tokens[i - 1], tokens[i + 1]);
				tokens[i] = null;
				tokens[i - 1] = null;
				tokens[i + 1] = null;
			}
		}

		// Deal with lone attributes and joined name/value pairs.
		for (i = 0; i < length; i++)
			if (tokens[i] != null) setAttribute(tokens[i]);
	}

	/**
	 * This method tokenizes the given string and returns a vector
	 * of its constituent tokens. It understands quoting and character
	 * escapes.
	 * @param s the string to tokenize.
	 */
	private Vector tokenizeString (String s) {

		// First check that the args are not null or zero-length.
		if (s == null || s.length() == 0) return null;

		boolean whitespace = false; // True if we are reading w/space.
		boolean escaped = false;    // True if next char is escaped.
		boolean quoted = false;	    // True if we are in quotes.
		int length;		    // Length of attribute string.
		int i = 0;		    // Loop variable.

		// Create a vector to store the complete tokens.
		Vector tokens = new Vector();

		// Create a buffer to store an individual token.
		StringBuffer buffer = new StringBuffer(80);

		// Convert the String to a character array;
		char[] array = s.toCharArray();

		length = array.length;

		// Loop over the character array.
		while (i < length) {

			// Check if we are currently removing whitespace.
			if (whitespace) {
				if (isWhitespace(array[i])) {
					i++;
					continue;
				} else {
					whitespace = false;
				}
			}

			// Check if we are currently escaped.
			if (escaped) {

				// Add the next character to the array.
				buffer.append(array[i++]);

				// Turn off the character escape.
				escaped = false;

				continue;
			} else {

				// Check for the escape character.
				if (array[i] == ESCAPE) {
					escaped = true;
					i++;
					continue;
				}

				// Check for the quotation character.
				if (array[i] == QUOTE) {
					quoted = !quoted;
					i++;
					continue;
				}

				// Check for the end of the token.
				if (!quoted && isWhitespace(array[i])) {

					// Add the token and refresh the buffer.
					tokens.addElement(buffer.toString());
					buffer = new StringBuffer(80);

					// Stop reading the token.
					whitespace = true;

					continue;
				}

				// Otherwise add the character to the buffer.
				buffer.append(array[i++]);
			}
		}

		// Add the last token to the vector if there is one.
		if (! whitespace) tokens.addElement(buffer.toString());

		return tokens;
	}

	/**
	 * Returns true if the given character is considered to be
	 * whitespace.
	 * @param c the character to test.
	 */
	private boolean isWhitespace (char c) {
		return (c == ' ' || c == '\t' || c == '\n');
	}
}
