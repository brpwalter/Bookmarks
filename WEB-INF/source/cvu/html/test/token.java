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

package cvu.html.test;

import cvu.html.*;
import java.util.Enumeration;

class token {
	public static void main (String[] args) {

		boolean folder=false;
		boolean bookmark=false;
		Folder f=null;
		Bookmark b=null;
		if (args.length < 1) return;

		HTMLTokenizer ht = new HTMLTokenizer(args[0]);
		AttributeList a1=new AttributeList ();
		Enumeration e2=a1.names();
		

		Enumeration e = ht.getTokens();

		while (e.hasMoreElements()){
			Object o=e.nextElement();
			if ((o) instanceof TextToken){ TextToken tt=new TextToken(); System.out.print("*tt*"); if (folder) f.setFoName(tt.getText()); else if (bookmark) b.setBoName(tt.getText()); }
			else if ((o) instanceof TagToken){ 
				System.out.print("@tagt@"); TagToken tagtoken=new TagToken(o.toString()); System.out.println ("ßß "+o.toString()+" ßß"); 
			System.out.println ("2ßß2 "+tagtoken.getName()+" 2ßß2");
			if ((tagtoken.getName()).equals("<h3")) {
							folder=true;
							f=new Folder();
							System.out.println ("(Ö~H3~#)"); folder=true;
							AttributeList al4=tagtoken.getAttributes ();
							Enumeration e3=al4.names();
							while(e3.hasMoreElements()){
								Object o3=e3.nextElement();
								String value3=al4.get(o3.toString());
								if ((o3.toString()).equals ("add_date")) f.setadddate(value3);
								
								System.out.println(" ## "+o3.toString()+" ##"+value3+" ## ");
							}
						//	if ((o) instanceof TextToken){ System.out.print("*tt*");TextToken tt=new TextToken(); f.setFoName(tt.getText());}
						System.out.println("=======");
						System.out.println(f);
						System.out.println("========");
						folder=false;
			}
			if ((tagtoken.getName()).equals("<a")) {
				b=new Bookmark();
				bookmark=true;
				System.out.println ("(Ö~a~#)"); 
				AttributeList al6=tagtoken.getAttributes ();
				Enumeration e4=al6.names();
							while(e4.hasMoreElements()){
								Object o4=e4.nextElement();
								String value4=al6.get(o4.toString());
								if ((o4.toString()).equals ("add_date")) f.setadddate(value4);
								if ((o4.toString()).equals ("HREF")) b.seturl(value4);
								
								System.out.println(" ## "+o4.toString()+" ##"+value4+" ## ");
								
							}
							//if ((o) instanceof TextToken){ System.out.print("*tt*");TextToken tt=new TextToken(); b.setFoName(tt.getText());}
							System.out.println("=======");
						System.out.println(b);
						System.out.println("========");
							bookmark=false;
			}
			if ((o) instanceof AttributeList){ System.out.print("++al++");} }
			System.out.println(o);
			}
			//Bookmark.addBookmark(bmName,url,int rating, int accessright, int coId,int foId);
			//addFolder(foName,1,1,1,userId);
			System.out.println("================================");
			
	}
}
