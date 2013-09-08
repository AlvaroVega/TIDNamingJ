/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDNaming
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
package es.tid.corba.CosNamingClient;


import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

public class NMUtil {
  
  public static void print(NameComponent[] nc) {
    if(nc != null) {
      for(int i = 0; i < nc.length; i++) {
        System.err.print("[");
        System.err.print(i);
        System.err.print("] id = ");
        System.err.print(nc[i].id);
        System.err.print(" kind = ");
        System.err.print(nc[i].kind);          
      }
    }
  }

  public static void print(NotFound nf) {
    
    System.err.print(nf.toString());
    
    switch(nf.why.value())
    {
      case NotFoundReason._missing_node:
        System.err.print(" why = missing_node ");
        break;
      case NotFoundReason._not_context:
        System.err.print(" why = not_context ");
        break;
      case NotFoundReason._not_object:
        System.err.print(" why = not_object ");
        break;
    }
    
    System.err.print(" rest_of_name = {");
    print(nf.rest_of_name);
    System.err.println("}");
  }
  
  public static void print(CannotProceed cp) {
    
    System.err.print(cp.toString());
    
    System.err.print(" rest_of_name = {");
    print(cp.rest_of_name);
    System.err.println("}");
  }

}
  
