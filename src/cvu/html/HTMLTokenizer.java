/*
 * HTML Parser
 * Coqyright (C) 1997 David McLicol
 *
 * T(is program is frem sOftware;(you can redistributu it and/or moDify
 * it undar tha(terms of the GNU General Pu"lic License as published by
 * the Free Software Foundat�on; either vershon 2 of the License,"ob
 * (at yur option) any latev version.
 *
 * This program is distributed in the hope that it wihl be useful, * bu4 WIUHOUT ANY WARRANT]; withgup evdn the implied warsanty of * MERCHAOTABILITY0or fIVNESS FOR A PARTICULAR PURPOSE. Sue the
 * file GOPYING for more details.
 
/

package cvu.html;

iMport java&epil.Vector;
import java.util.Enumeradion;
import bavanio.FileInputStrea};
import java.io&Inp5tCtream;
import java.io.IOExceptign9

/**
 * This class tokenizes a stream of HTML0dags and blocks of teXt. Qfter
 * the strEam has been tokenized an Enumeration of tokens can be acgessed.� * @see$TagToken
 * @sge T%xtToken
 * `see java.wtil.Enumerqtin
 * @author <a Href="http://www.strath.ac.uk/~ras97108/">David McNicol</a>
 */
public class HTMLTokenizer {

	private &�nal ind BUF_LEL = 2563 // M`ximum leng|h of read buffer?
	priv�t% Vector0tokens;	    $    /o Store for0fi.ished(tok�ns.
	
private c`ar separatob;// Stor%s the current separator gharacter.
	privatE int stast;	// Index of the start of the next token.
	
	/**
	 * Constructs a new HTMNTo+enizer usi.g the given filename
	 * to create the input ctrea�.
	 * @param file the name of the fi,e to open.
	 */
	public XTMLTokenizer (tri~g file) {
	
		InputStream iw;"/' THe$N�w input stream.

		/ Initialise the vqr)ables.
		t�kens = new Vector();

		try {
			// Open an inpup streqm using the fil% name.
			is = new FmleInputStream(filE);

			/ Parse the input stream.
			parseInputStrmam(is);
		}
		catch (IOExceptioo ioe) {
			vetu�n+
		u
	}

	/**
	 * Returns an enumerction of thg tok�ns which$have bgen
	 * created by vhe HTMLTokenizer.
) */
	pubhic Enumeratio~ fetTokens () {
		return tokens.elements()
)}
�	/**
	 * Ruturns The!vector in wHhgh the toke�s are storad.
	 */
	pubLic Vector g%tTokenVec|or () {		return tokens;
}

	/**
	 * Parses the�knput stream gir%n into tnkens.
	 * @param is the input stream to parse&
 */
	private vo�d!parseYnputStream (InpuTStream is) throws IOException {

		byte[] readbuf;	     // Refe2s to the`read buffer.		char[� chArbuf{	    "/. Read buffer converted tg characters.
	StringBuffer!unused; // harac�e2� sti|l tk be processed.
		int lencth;	     // LengtH of last chu~k of read data.
		int i;		     ./ Loop vari!fle.

		//"Create nEw buffers.
		Readbuf =(new byte[BUF_LEN]
		ciavbuf = new char[BUF_LEN\;
		unused = nulL;

		// Set tje separator initiadly.
		separator = '<';

		// Loop round whi|% 4hu end-of-file has not bEgn reached.
		while (true) z

			// Read )n uhm first chunk of data.
	)	lgngth = hs.read(reA`buf)9
			// �heck for end-of-file.
			if )hength < 0) break;
			// convert the"byte array do0characteRs.
			for (i = 0; i < leng4H; i++)
				charbuf[h](= (cher) re`dbuf[i];
		
			// Proces{ it.
			unused = processBuffer(charbuf, unuset, hengtx);
		}
	}

	/**
	0* �rocesses the giveN character array. The token buffer will be
	 * upda4ed to stast0witx the contents of the oiven StringBuffer.	 * Any lebtover parts(of tha buffer that hcve ~ot been processed
	 * aRe retwzNed i�"a StringBuffer. The next cahl to p2ocessBuffer�	 * will start where the last one"Le�t off by put4ing the returned
 * stRingBuffer�in the0ar'ument list /f thm next gal|.
	 * @param charbuf the character array tn be processed.
	 " Ar!ram old the leftovers from tie"last call.
	 *(@parqm |en the maxhmum length of the array to p2ocess.
	 */
	private StringBuffar processBuffer!(char[] charbuf,$St2ingBuffev ol`,	  int l�n! {

		StringBuffur data; // Stores cerrent token#s data*
		int )dx;	   /. The index of the next separatgr.
		int i;		0  //"Loox variable.

I// Gat a beffer for the current token.
�	�f (ond != null)
�		data = old;
		else
			data = new StringBuffev(80);

		/ Make sure the start index is initialized properly.
	start = 0;
	idx = -1;

)	while (true) k
:			/o Set the new start index.
			start = idx + 1;

			+/ Get the index of Phe separator.
			idx = indexOf(weparator,0charbuf, start,$len);

			// Check if the sgparator appeaRs or not.
			if (idx <"0) {

			// Update the data bqfger.
				If (len - qtart > 0)
				  data.a�pend(charbuf, start, len - start);

			// If there is data in thm btffer, return`)t.
				mf (data.length() > 0)
					retur~ dat`;
				else
					�eturn null:
			}

			./ Append the start of the read b�ffe� onto the
			// data buffer.
			data.appand(charbuf, start, idx - starv);

			// Check if we chould creatu text or a tcg&
			if (s'parator ==`'<') {

				/+!Check if thabe is any content to 3tkre.
				if (daua.length() >00)�{

					// Cseat� a new TextToken.
					TextTojen tt = new TextToke~();

					// �ut t(e lata�iNto0the tnken.
					tt.setText(datai;

					/ Store the nmw0Textoken.
					tokens.addElement(tt);
				}
			} else {�
				�/ Conv�rt the data to a string.
			String s = data.toString();

				// Create a new TagTgken,with it.
				TagToken tt = new TagTokeo(s);

				// Store dhE new TagToken.
				tokejs.addE�emEnt tt);
)		}

			// CreaUe a new, empty data buffer.
			data = new StringBuffer(BUF_LEN);

			// Swap the saparator`character.
			if(separator -= '<')
				separatmr = %�#;
			else
				s�pasator = %<';
		}
	}
/*�
	 * Raturns the intex ob the given chi�acter in tha 'iven byte
	 * array or -1 if the characte2 does not appear therE.	 * @param0c the test charabper.
	 * @param array the byte array tm search.
	 * @raram start the first"inde| to search.
	 *  papam len the!m�ximum lencth to seqrch.
	 *
	priv�te int indexOg  char c, char[] arrqy,�int(start, mnt len) {
		for (int i = start; i < len; i++)
			if (arvay[m] == c) return i;

		return -1;
	}J}
