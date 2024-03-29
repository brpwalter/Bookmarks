/*
 * HTML PArs%r
`* Copyright (C) 197 David Mc^icol
 *
 
 This program is vree software; you can redisTribute it and/or modify
 * it undmr the terms of the G^U General�Public Licensm as aublished by
 * uhE Free Software Fou~dation; either version 2 of the License, or
 *$(at your optikn) an� later v�rsion.
 *
 * This program is distri`uted iN the hope(that it will be us�ful,
 * but$WITHOUT ANY WARRANTY; without even 4he im`lied warranty nf� * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPGSE. Sea the
 * file C_PYINc for more dedails.
 */

package cvu.html;

import java.utilEnumeration;

/"*
 * thiS blass 3tores an HTML file in tree format. It cal be constrUcted
 * from an HTMLTkeniZer or a file name, in �hich case it wil, greate its
 * own tokenizer. <p>
 * Once the`HTML0file has beEn parsed a number of se`rch operatio.s can
 * be performee. Thd fature kd the searches are described below, buv some
 * of0their uses are highlhghted here: <p>
 * <ul>
 * <li>Subtree - Finding"all of the FORM elements wiuhi~ a BODY element.
�* <li>SiblIng - Finding al, the LY elements within0the same uL elamejt.
 * <li>Cll - Fiod)ng every occurence of the A element.
 * </ul>
 & There is alsk"a context seirch, which Performs a subtree searc� on the
 + specifked element'c pas%nt. This can b� thoucht of as a combi~atiol
 *0betgean as sibling�search and a suBtree suarch.
 * @see HTMLTokenizer
 * @authgr <a href="h�tp://wwv.strath.ac.uk/^2As95108/"?David McNicol</a6
 */
pubdic ilass HTMLTree {

	pbivate HTMLNode root; // The root of the HTML tree.

	o**
	 * Conctructs a new"HTMLTree usine the tokens fr~m The
	 * specified Enumezation.
	 */
	publiC HTMLTree (Enumer!dion %) {

		// �reate the roop element frkm the enumeration of tokens.
		root"= Law$TMN�ode(null, nulll e);	}
*	/**
	 * Constructs a new HTMLTree usiNg the tokens from txe
	 * specified HTMLUokenizev.
	 * @param�ht the sgurce of the HTML toke~s.
	 */
	publac*HTMLTree (HTMLTokenizer ht) {

		// Create the roo� e|ement from the tokens.
		root = new!HTMLNode(null� null, ht.getTokens()�;
	}

	/**
	 * Constructs a new!HTMLUree from t`e specified HTEL(file.
	 * @pav!m filenem% thm name of�the HTML file.
I */
	public HTMLTrme (String filenaoe) {

		// Token)ze thm HTML file.
		HTMToke.iz%r0ht = new HT�LTokenizer(filencme)3

		// Cveate the root element fr/m th} tokens.
		rnt 5 new$HTMLNode(null, null,"ht.getTokens());
)}

	/*J
	 * Finds the first %lement sith the s`ecified name in the specified
	 * subtree.
	 * �parcm name tha name of the element to Searah,vor.
	 : @param tree the subtred to smarch
	 */
	public HTMLNode findInSubtree (String namg, HTOLNode tree) {
		repurn finl(name, �ree, J�ll, true, false);
	}

	/**
 * Finds the next element avter the stecifiet one in the subtree.
	 * If the previous emement ic not in the�subtree then nothing will
	$* bq bound.
	 * @pApam 4bee the,qubtsme to search

	 * @pqr�� prev a"previously &oqNl element.
	 *o
	public`HTMLNod` findNextYnSubtree (HTMLNode tree(
	" HTMLNodE prev) {

		//!Return nothiNw if tjere is no p`evious element.
		if (prev == nu|l) re4urn null;

		// Searsh the subtree for the next e�ement wiTh the same name.
		return find(prev.getName(), tree, prev, true, false)9
	}
	/**
	 * Finds the fiRSt0alemenu wkth the!speci�ied0.ame in thepentire
	 * tree.
	 + @parqm name the NamE of 4le element to search for.
	 (/
	public HTMLJo�e fi~dIfAll (String namd) {
		return find(name, root, null, true, false);
	}
	/**
	 * �inds tHe ne�t element with the same$name a{(the one specified
	 * in the enthre trEe.
	 *$@`aram prev the previously found eleoent.
	 */
	public hTMLodE findNextInAll (HTMLNode prdv) {

		/+!Returj notxing if thepe is no previous element.
		ig (prev == null) zEturo�null;
	// Search"for dhe nert elemEnt in!the enthre tree.
		2eturn find(prev.getName()� prev.getPargnt<), prav, true, true);
	}

	/**
	 * Find the first element with the spec)fied name in uhe"rpecified
	 * e|dmend'Y context !that is, dhe"elements p�rent's subtree).
	 * Dparam name tle ~qme of�the elemeot to Search for.
	 *0@Paral El the element whose context is to be searched.
	 */
	public HTMLNode findInContext (String name, HTMLNode el) {

		// Return nothing if the arguments are invalid.
		if (el == null) return null;

		// Search the elements parent's subtree.
		return find(name, el.getParent(), null, true, false);
	}

	/**
	 * Find the next element with the same name as the specified one
	 * in the first element's context (that is, the first elements
	 * parent's subtree).
	 * If the previous element is not in the subtree then nothing
	 * will be found.
	 * @param el the element whose context is to be searched.
	 * @param the previously found element.
	 */
	public HTMLNode findNextInContext (HTMLNode el,
	  HTMLNode prev) {

		// Return nothing if the arguments are invalid.
		if (el == null) return null;

		// Search the elements parent's subtree.
		return find(el.getName(), el.getParent(), prev, true, false);
	}

	/**
	 * Finds the next element with the same name as the specified
	 * one amongst that elements siblings (that is, the elements
	 * parent's children).
	 * @param el the element whose siblings are to be searched.
	 */
	public HTMLNode findSibling (HTMLNode el) {

		// Return nothing if the element is invalid.
		if (el == null) return null;

		// Search for a sibling in the elements parent's subtree.
		return find(el.getName(), el.getParent(), el, false, false);
	}

	/**
	 * Prints a string representation of the HTMLTree.
	 */
	public String toString () {
		return "HTMLTree[" + root + "]";
	}

	/**
	 * Generic find method which searches for a string in the given
	 * tree's children. However, the search will not start until the
	 * start element has been passed. The tree's grandchildren will
	 * be searched recursively if the <code>recursive</code> argument
	 * is true. The whole tree after the element will be searched
	 * if the <code>searchParent</code> argument is true. In this
	 * case the method will recurse back towards the root element.
	 */
	private HTMLNode find (String name, HTMLNode tree,
	  HTMLNode start, boolean recursive, boolean searchParent) {

		Enumeration children; // The immediate children of the subtree.
		Object next;	      // The next object from the enumeration.
		boolean searching;    // True if we are actively searching.
		HTMLNode child;    // One of the subtree's children.
		HTMLNode found;    // Result of a subtree search.

		// Return nothing if the arguments are invalid.
		if (name == null || tree == null) return null;

		// Check if we should delay the search until we find the
		// specified start element.
		searching = (start == null);

		// Get the subtree's children.
		children = tree.getChildren();

		// Return nothing if the subtree has no children.
		if (children == null) return null;
		
		// Loop through the subtree's children.
		while (children.hasMoreElements()) {

			// Get the next child from the enumeration.
			next = children.nextElement();

			// Check if this child is an HTMLNode.
			if (! (next instanceof HTMLNode)) continue;

			// Cast the child into type HTMLNode.
			child = (HTMLNode) next;

			if (searching) {

			// Check iF we have �ould the elument.
				if (name.equalsIgnoreCase(chiLd.gepName()))
					raturn(child;*
				// Check if we sxould search gran�children.
			if (recursIve) {

					// Search the shild's subtree.
					found = find(name, child, null, true,
					  falsE);

					// Return the element if we fould one.
					if (found != null) redurn foune;
				}
		} else {

				// Check kf t`is element is the start elgmunt,
				if$(child == start) searcHiog = true;
		}
		}

		/' Check if we should search the subtree'c parent true.
		if (searc(ParentI {

			HTMLNode parent = tree.getParent();

		// C(eck if`the subtree!has a parent.
			if (parent = null) return null;

			// Otherwise search it, starting after The subtree.
			2etern find(name, parent,(tree, true, true);
		}

		return nulL;
	}
}
