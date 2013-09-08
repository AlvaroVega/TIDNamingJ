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

public class TestBind {
  
  public static boolean test(NamingContext context, NameComponent[] name,
                             org.omg.CORBA.Object obj,
                             Class expected_exception,
                             String id, boolean print)
  {
    if(print) {
      System.err.print('(');
      System.err.print(id);
      System.err.print(") TestBind ");
      NMUtil.print(name);
      System.err.print('\n');
    }
    
    try {
      
      context.bind(name,obj);
      
    } catch (NotFound nf) {
      if((expected_exception != null) && 
          expected_exception.isInstance(nf)) {

        if(print) {
          System.err.print("\t(");
          System.err.print(id);
          System.err.print(") TestBind: Expected Exception ");
          NMUtil.print(nf);
          System.err.print('\n');
        }
        
        return true;
      } else {
       if(print) {
          System.err.print("\t(");
          System.err.print(id);
          System.err.print(") TestBind: ERROR, unexpected exception ");
          NMUtil.print(nf);
          System.err.print('\n');
       }
        return false;
      }
    } catch (AlreadyBound ab) {
      if((expected_exception != null) && expected_exception.isInstance(ab))
      {
         if(print) {
            System.err.print("\t(");
            System.err.print(id);
            System.err.print(") TestBind: Expected Exception ");
            System.err.println(ab.toString());
         }
        return true;
      } else {
        if(print) {
          System.err.print("\t(");
          System.err.print(id);
          System.err.print(") TestBind: ERROR, unexpected exception ");
          System.err.println(ab.toString());
        }
        return false;
      }
    } catch (CannotProceed cp) {
      if((expected_exception != null) && 
          expected_exception.isInstance(cp)) {
        if(print) {
          System.err.print("\t(");
          System.err.print(id);
          System.err.print(") TestBind: Expected Exception ");        
          NMUtil.print(cp);
          System.err.print('\n');
        }
        return true;
      } else {        
        if(print) {
          System.err.print("\t(");
          System.err.print(id);
          System.err.print(") TestBind: ERROR, unexpected exception ");
          NMUtil.print(cp);
          System.err.print('\n');
        }
        return false;
      }
    } catch (Exception e) {
      if((expected_exception != null) && expected_exception.isInstance(e))
      {
        if(print) {
          System.err.print("\t(");
          System.err.print(id);
          System.err.print(") TestBind: Expected Exception ");        
          System.err.println(e.toString());
        }
        return true;
      } else {
        if(print) {
          System.err.print("\t(");
          System.err.print(id);
          System.err.print(") TestBind: ERROR, unexpected exception ");
        System.err.println(e.toString());
        }
        return false;
      }
    }

    
    if(print) {
      System.err.print("\t(");
      System.err.print(id);
      System.err.println(") TestBind: OK!!");
    }
    
    return true;
  }

  public static boolean test_loop(NamingContext context,
                                  int iterations,
                                  Class expected_exception,
                                  String id, boolean print)
  {
    boolean ok = true;
    NameComponent[] name = {new NameComponent(id + "_hola","")};
   
    long  start = System.currentTimeMillis();                      
    System.out.println("Init binding loop: ");
                            
    for(int i = 0 ; i< iterations; i++) {
      
      name[0].kind = Integer.toString(i);
  
       if(!test(context, name, null, expected_exception, id, print))
         ok = false;
    }
    
    System.out.println("Binding average time: " +((System.currentTimeMillis() - start)/iterations));
    
    return ok;
  }

  
}
