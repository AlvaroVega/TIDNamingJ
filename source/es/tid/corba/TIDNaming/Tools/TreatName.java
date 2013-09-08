/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDNamingJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 1 $
* Date: $Date: 2009-03-20 11:28:08 +0100 (Fri, 20 Mar 2009) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2009 Telefonica Investigacion y Desarrollo
*     S.A.Unipersonal (Telefonica I+D)
*
* Info about members and contributors of the MORFEO project
* is available at:
*
*   http://www.morfeo-project.org/TIDNamingJ/CREDITS
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*
* If you want to use this software an plan to distribute a
* proprietary application in any way, and you are not licensing and
* distributing your source code under GPL, you probably need to
* purchase a commercial license of the product.  More info about
* licensing options is available at:
*
*   http://www.morfeo-project.org/TIDNamingJ/Licensing
*/

package es.tid.corba.TIDNaming.Tools;

import java.util.*;
import javax.swing.JOptionPane;
import org.omg.CosNaming.*;


public  class TreatName {

    public TreatName() 
    {
    }//constructor
    
    public static String put_escapes(String name)
    {
        
        int position;
        String cnescapes= translate_escapes(name);
        
        position= cnescapes.indexOf("%5c%5c");
        while (position!=-1) { //Sustituir \
            cnescapes=
                cnescapes.substring(0,position)+"\\\\"+cnescapes.substring(position+6);
            position= cnescapes.indexOf("%5c%5c",position);
        }
        position= cnescapes.indexOf("%5c/");
        while (position!=-1) { //Sustituir /
            cnescapes=
                cnescapes.substring(0,position)+"\\<"+cnescapes.substring(position+4);
            position= cnescapes.indexOf("%5c/",position);
        }
        position= cnescapes.indexOf("%5c.");
        while (position!=-1) { //Sustituir .
            cnescapes=
                cnescapes.substring(0,position)+"\\>"+cnescapes.substring(position+4);
            position= cnescapes.indexOf("%5c.",position);
        }
        return cnescapes;
        
    }//put_escapes
    
    
    public static String translate_escapes(String str)
    {
        
 	String strescapes= "";
 	String no_escapes= ";/:?@&=+$,-_.!~*'()";
 	char c;
        
	for (int i=0;i<str.length();i++) {
            c = str.charAt(i);
            if (no_escapes.indexOf(c)==-1 && !Character.isLetterOrDigit(c))
                strescapes= strescapes+"%"+ Integer.toHexString((int)c);
            else
                strescapes= strescapes + c;
	}
	return strescapes;
	
  }//translate_escapes


  public static String resolve_escapes(String str)
  {

 	int position;
 	String strescapes= str;

	position= strescapes.indexOf("\\\\"); // Leido \
	while (position!=-1)
	{
	  	strescapes=strescapes.substring(0,position)+"%5c"+strescapes.substring(position+2);
	  	position= strescapes.indexOf("\\\\",position);
	}
 	position= strescapes.indexOf("\\<"); // Leido /
 	while (position!=-1)
	{
  		strescapes=strescapes.substring(0,position)+"/"+strescapes.substring(position+2);
  		position= strescapes.indexOf("\\<",position);
   	}
 	position= strescapes.indexOf("\\>"); // Leido .
 	while (position!=-1)
	{
  		strescapes=strescapes.substring(0,position)+"."+strescapes.substring(position+2);
  		position= strescapes.indexOf("\\>",position);
 	}
 	return strescapes;
	
  }//resolve_escapes

  public static NameComponent[] to_name(java.lang.String sn)
  {

  	int ntokensn, ntokenscmp;
 	String name= "";
 	String component= "";
 	String id;
 	String kind;

 	name= put_escapes(sn);
 	if (name.indexOf("//")!=-1)
 	{
            JOptionPane.showMessageDialog(null,"The name of the object is not correct",
                                          "name error",JOptionPane.ERROR_MESSAGE);
            return (null);
 	}
 	StringTokenizer stname= new StringTokenizer(name,"/",false);
 	ntokensn= stname.countTokens();
 	org.omg.CosNaming.NameComponent[] n= new
	org.omg.CosNaming.NameComponent[ntokensn];
 	for (int i=0;i<ntokensn;i++)
	{
  		id= "";
  		kind= "";
		component= stname.nextToken();
                StringTokenizer stcomp= new StringTokenizer(component,".",false);
		ntokenscmp= stcomp.countTokens();
		if(ntokenscmp>2)
		{
                    JOptionPane.showMessageDialog(null,"The name of the object is not correct",
                                                  "name error",JOptionPane.ERROR_MESSAGE);
		    return (null);
		}
		if (!(ntokenscmp<=1 && component.charAt(0)=='.'))
			id= resolve_escapes(stcomp.nextToken());
		if (stcomp.countTokens()==1)
			kind= resolve_escapes(stcomp.nextToken());
		n[i]= new NameComponent(id,kind);
 	}
 	return n;
	
  }//to_name




}//class
