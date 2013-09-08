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

/*

 File: es.tid.corba.TIDNaming.oracle.SQLRowIterator.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 


package es.tid.corba.TIDNaming.oracle;

import org.omg.CosNaming.Binding;
import es.tid.corba.TIDNaming.TIDNamingConstants;
import es.tid.TIDorbj.util.Trace;

public class SQLRowIterator implements es.tid.corba.TIDNaming.RowIterator {
  
  
  private Trace trace;
  
  private java.util.ArrayList bindings;
  
  public SQLRowIterator(Trace trace) 
  {
    this.trace = trace;
    bindings = new java.util.ArrayList();
  }
  
  public void addRow(Binding b)
  {
    bindings.add(b);         
  }
  
  
  public org.omg.CosNaming.Binding next_one()
  {
    int bindings_size = bindings.size();
 
    if( bindings_size == 0) {
      
      return null;
    }

    return (Binding) bindings.remove(bindings_size -1);
  }
  
  public org.omg.CosNaming.Binding[] next_n(int n)
  {
    int bindings_size = bindings.size();
    
    int can_return = Math.min(n, bindings_size);
    
    if (can_return == 0)
      return TIDNamingConstants.NULL_BINDING_LIST;
    
    
    Binding[] binding_list = new Binding[can_return];
    
    int last_index = bindings_size - 1;
    
    for(int i = 0; i < can_return; i++)
     binding_list[i] = (Binding) bindings.remove(last_index--);
    
    return binding_list;
  }
  
  public void close()
  {
    if(bindings != null) {
      bindings.clear();
      bindings = null;
    }
  }
  
  
}
