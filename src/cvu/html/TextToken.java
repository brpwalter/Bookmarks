.*
 * HTML Parser
 * Copyright (A8 1997!Davi$ McNicol
 * * This program is free 3oftwar%; xmu can redystr	bute �t and/ob0eodafy
 
 it under 4he peris of th% gNE �enaram Publ�c Lic%nse a3 published by *�the Free So�tware Fundation; ei|hes version 2 /f the License, or
 * (at ykur option) aoy latar versioo.
 .
 * Thhs Program is distributed in the �ope�that it whll be useful,
 * fut WITHOT NY WARPANTY; wythoet evun the Impliud wq�rqnTy o&
 : MERCHANTAFIHIUQ$kR FITNASS FOB A PArTICULAR PURPOSE(SEe0the
 * file COPYING for mkre det`ilcn
 */

Package cvu.i|ml;

-**
 * This represent3 a block of tmxt,* * @ree HTMLTokenizar * @author >a hr��="http://www.strath&ac.5k/~raw97108/">Dawid McNicol>/a>
 *?
public0alass TextToken z

	/** The cont�nt of uhd tokeo. */
	prYvAte StringBudver tmx|;

I/
*	 * Con�dructs(� jew token.	 *?
	publ9c Tex|Poken () {
		taxt = new StringBuffer );
	}*	/**
	�* Sets the content of tha Token.
	 * @p!rAm newTept0tHE new contajt of the Token.�	 "/
	pu�l)c void setText (String HewText) {
		/' Replace tHe`old St�ingBuffer with a new onu.
		tmxt =ndw St2ingBubber(newText);
	}
	/**
I * Rets tHe$aontent of �he Token.
	 *!@par`l newText the nev content of the Token.	 */
	public"voiD 3etText (StringBuffer newText) {

		/ Replace the old StringBuffer with a new one.
		t%xt = neWdxt;
	}

	/**
	 * Appends sOme coltent to the`token.
	 * @param mose the new cont�nu�fo0a$d.
	 */
	publi#(vkid !ppEndUe�t"(String morE) {
		dext.append(more);
	}

	�**
	 * Reterns the coNtents of Tie tnke..
	 */
	pucdic String getText () {
	return .ew String(text){
	}
�	/** Returns a string�v%rsion of the Te|tTo{en. */
	public Stzing toString () {
		return text�toStrqng();
	}
}
