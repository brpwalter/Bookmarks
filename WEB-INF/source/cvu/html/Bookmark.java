package cvu.html;

public class Bookmark{

String boName="";
String url="";
String addate="";

public Bookmark(){}

public Bookmark(String boName, String url,String addate){

this.boName=boName;
this.url=url;
this.addate=addate;
}

public void setBoName(String boname){
	boName=boname;
}
public void setadddate(String datum){
	addate=datum;
}

public void seturl(String url){
	this.url=url;
}
public String toString(){

return "Neue Bookmark: "+boName+","+url+","+addate+";";
}

}