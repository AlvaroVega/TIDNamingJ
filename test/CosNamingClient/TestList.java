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

public class TestList {

  public static boolean test(NamingContext context,
                             int list_size,
                             Class expected_exception,
                             String id, boolean print)
  {
    if(print) {
      System.err.print('(');
      System.err.print(id);
      System.err.println(") TestList ");
    }
    
    try {
      
      BindingIteratorHolder iterator_holder = new BindingIteratorHolder();
      
      BindingListHolder list_holder = new BindingListHolder();
      
      if(print) {
        System.err.println("subcontext list:");
      }

      context.list(list_size, list_holder, iterator_holder);
      
      Binding[] list = null; 
      
      boolean exit = false;

      
      int j = 0;
      do {
        list = list_holder.value;
        if(print) {
          for(int i = 0; i < list.length; i++) {
            System.err.print("\t(");
            System.err.print(j++);
            System.err.print(")\t");
  
            System.err.print(list[i].binding_name[0].id);
            System.err.print("\t");
            System.err.print(list[i].binding_name[0].kind);
            System.err.print("\t");
            if(list[i].binding_type == BindingType.nobject)
              System.err.println("object");
            else
              System.err.println("naming context");            
          }
        }
        
        if(iterator_holder.value  == null)
          exit = true;
        else
          exit = !iterator_holder.value.next_n(list_size,list_holder);
          
      } while (!exit);
      
    } catch (Exception e) {
      if((expected_exception != null) && expected_exception.isInstance(e))
      {
        if(print) {
          System.err.print("\t(");
          System.err.print(id);
          System.err.println(") TestList: Expected Exception ");        
          System.err.println(e.toString());
        }
        return true;
      } else {
        if(print) {
          System.err.print("\t(");
          System.err.print(id);
          System.err.println(") TestList: ERROR, unexpected exception ");
        System.err.println(e.toString());
        }
        return false;
      }
    }

    
    if(print) {
      System.err.print("\t(");
      System.err.print(id);
      System.err.println(") TestList: OK!");
    }
    
    return true;
  }

}
