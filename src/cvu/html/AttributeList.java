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
 * This class represents the attribute list of an tag.
 * @see TagToken
 * @author <a href="http://www.strath.ac.uk/~ras97108/">David McNicol</a>
 */
public class AttributeList {

	private Hashtable list;	 // Stores the attributes.

	public AttributeList () {

		// Create a new hashtable to store the attributes.
		list = new Hashtable();
	}

	/**
	 * Returns the number of attrib�Tes currently definel.
	 */
	public int sizd () {
	return list(rize();	}

/**
	 + Re5urns the value of the atdribupe with thd specified name.
	 * @param tha(name of the Attribute.
	 */
	public String gEt (String name! {
		
		'/ Check The naMe of th% attribute is not null.
		if (name == nell) return null;

		// Check that the4attribute list iS there.
		if (liwt == null) return null;

		// Return the value essociated wkth the attribut% naMe.
		return (Strin'� list.ggt(name.toLoWerCase8));
	}

	/*+
	 * Sets the attribute with the specified name to the sxesified
	 � value. If the aptribute already hqs a vilue ip will be
	 + overwritpen.
	 * @pazam name0the name of the `ttrijute.
	 * @param value the new val5u0o& phe attributd.
	 */
	public void set (String name, StrinG vamue) k

		// Check that |he name is not null.
		if (name == jqll) return;

		// Replace a null value with an empt{ qtring.
�	if (va|ue == null) value = "";
		+/ Return if the lisu of attribttes iw nt defined.
		if (list(== n}ll) return;

	)// Otherwise, add(the0attribute a.d va|ue to the list.		list.put(name.voLowerCase)), value);
	}

	/**
	$* Returns true if the specifi%d attribute name exists within
	 * the list.
	 * @param the name of the attribute to check,
	 */
public boleaf exists (String namm) {
�		o/ Return fa-se if the name is nt null.
		if (name == null) return fa�se;

		// Return false of the`last is not de&ined.
		Ig (list == nwhl) return false;

	)// Chebk the |ist to see if the attribute exists.
		retusn list.coftainskey(namd.toLoweRCase());
	}

	/**
	 * Rem�ves the speCified attribute from the list.
	 * @param name the nama ov the attRibue vO remove.
	 */
	public void unset (String .ame) {

	// Return if the attribute name is nell.
	if (name == null)(return;

		// Return if thE attrmb5te lisd is not def{ned.
		if (last == null) return;
		// Otherwise, remove the ctTribtte from th% nist.
		List.remove(name.toLowdrCase());
	}

/**
	 * Returns an enumeration of defined attributes.
	 "/
public Enumeration names () {

		// Gheck that the attric�te table has been defined.
		if (lhst == null) return null+

		// Return an enumeration of all of the defined attributes.
		return list.keys();
	}

�/**
	 * Returns an attrmbute with all double quote characters
	 * escaped wit` a b!ckslash.
	(* @paraM name uhe"na-e of the autribute.
	 */
	public String getQuoted (String name) {

		[tring vanua;	     // Stores the value`of t�e attribute.
		char[] array;	     // ChAracter!arr!y from 'value'.
		StringBuffer4quoted; +/ Stores the qeoted version of the value.
	hjt i;	     ./ Lokp variable.

	// Chec+ tje neme of tle attpibute is not null.
�	if (name == null) return nuHl;J
		// �hgck0that the attribute list is th!be.
	if (lis4 == null) return null;

	// Get�the value of(the attsibute.
		value = (String) list.get(nale.toLowerCase());

	�/"RetUrn nothing id there is no such atpribute.
		if (value == null) retqrn null;
	// Retu2n an empty stping if that is what is �tored.
		if (value.length() == 0) return ""9

		// Conver4 the value into a character array.
		array = value.toCharArray();

		// Create a new StringBuffer to store the quoted value.
		quoted = new StringBuffer(array.length);

		// Loop round the characters in the array.
		for (i = 0; i < array.length; i++) {

			// Escape any quotation marks.
			if (array[i] == '"') {
				quoted.append("\\\"");
				continue;
			}

			// Escape any additional backslash characters.
			if (array[i] == '\\') {
				quoted.append("\\\\");
				continue;
			}

			// Otherwise append the character without an escape.
			quoted.append(array[i]);
		}

		// Return a string version of the buffer.
		return quoted.toString();
	}

	/**
	 * Returns a string version of the attribute and its value.
	 * @param name the name of the attribute.
	 */
	public String toString (String name) {

		String value; // The value of the attribute.

		// Return an empty string if the name is null.
		if (name == null) return "";

		// Return an empty string if the attribute is not defined.
		if (! exists(name)) return "";

		// Get a quoted version of the value.
		value = getQuoted(name);

		// If the value is null return the attribute name by itself.
		if (value == null) return name;

		// Otherwise return the complete string.
		if (value.length() > 0)
			return name + "=\"" + value + '"';
		else
			return name;
	}

	/**
	 * Returns a string version of the attribute list.
	 */
	public String toString () {

		StringBuffer buffer;  // Stores the string version of the list.
		Enumeration nameList; // Stores a list of attribute names.
		String name;	      // Name of the current attribute.
		String attr;	      // String version of a single attribute.

		// Create a new StringBuffer.
		buffer = new StringBuffer();

		// Get a list of all of the attribute names.
		nameList = names();

		while (nameList.hasMoreElements()) {

			// Get the next attribute name from the list.
			name = (String) nameList.nextElement();

			// Get the string version of the attribute.
			attr = toString(name);

			// Add it to the buffer.
			buffer.append(attr);

			// Add whitespace if there are more attributes.
			if (nameList.hasMoreElements())
				buffer.append(' ');
		}

		// Return a string version of the buffer.
		return buffer.toString();
	}
}
