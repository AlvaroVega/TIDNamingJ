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

 File: es.tid.corba.TIDNaming.BindingIteratorImpl.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 
 
package es.tid.corba.TIDNaming;

import es.tid.TIDorbj.util.Trace;

/**
 * Implementation class of CosNaming::BindingIterator. <p>
 * The BindingIterator has a RowIterator associated where reads the binding entries.
 * @author Juan A. C&aacute;ceres
 * @version 1.0
 * @see RowIterator
 */
public class BindingIteratorImpl extends org.omg.CosNaming.BindingIteratorPOA {
  
  /**
   * BindingIteratorImpl name.
   */
   
  private String name;
  
  /**
   * Binding Iterator data source.
   */
  private RowIterator iterator;
  
  /**
   * Binding Iterators POA.
   */
  private org.omg.PortableServer.POA my_poa;
  
  /**
   * Log.
   */
  private Trace trace;
  
  /**
   * Constructor.
   */
  public BindingIteratorImpl(RowIterator iterator,
                             org.omg.PortableServer.POA iterator_poa)
  {
    this.iterator = iterator;
    my_poa = iterator_poa;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  
  public void setTrace(Trace trace)
  {
    this.trace = trace;
  }
    

  public boolean next_one(org.omg.CosNaming.BindingHolder b)
  {
    if(iterator == null) {
      b.value = TIDNamingConstants.NULL_BINDING;
      return false;
    }
    
    b.value = iterator.next_one();
    
    if(b.value == null) {
      if(trace != null) {
        String[] msg = {"BindingIterator::next_one: no more bindings in ", name};
        trace.print(Trace.DEEP_DEBUG,msg); 
      }
      iterator.close();
      iterator = null;
      b.value = TIDNamingConstants.NULL_BINDING;
      return false;      
    }
    
    return true;
      
  }

  public boolean next_n(int how_many, org.omg.CosNaming.BindingListHolder bl)
  {
    if(iterator == null) {
      bl.value = TIDNamingConstants.NULL_BINDING_LIST;
      return false;
    }

    bl.value = iterator.next_n(how_many);
    
    if(bl.value.length == 0) {
      if(trace != null) {
        String[] msg = {"BindingIterator::next_n: no more bindings in ", name};
        trace.print(Trace.DEEP_DEBUG,msg); 
      }

      iterator.close();
      iterator = null;
      return false;
    }
    
    return true;
  }

  public void destroy()
  {
    try {
      if(iterator != null)        
        iterator.close();
        
      my_poa.deactivate_object(my_poa.servant_to_id(this));
    } catch (Throwable th) {
      if(trace != null) {
        String[] msg = {"BindingIterator::destroy ", name, 
                        " iterator: ", th.toString()};
        trace.print(Trace.ERROR,msg); 
      }
    }
  }

}
