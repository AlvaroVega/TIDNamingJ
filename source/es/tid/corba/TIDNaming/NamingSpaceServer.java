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

 File: es.tid.corba.TIDNaming.NamingSpaceServer.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming;

import es.tid.TIDorbj.util.Trace;
import es.tid.corba.TIDNamingAdmin.*;
import es.tid.corba.TIDNamingAdmin.AgentPackage.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

class NamingSpaceServer{
  
  /**
   * Service Log Output.
   */
  private Trace trace;
  
  private NamingProperties properties;
  
  private es.tid.TIDorbj.core.TIDORB orb;

  private POA father_poa;

  private es.tid.corba.TIDNamingAdmin.NamingSpace space;
  
  private NamingSpaceDB db;
  
  private org.omg.PortableServer.POA space_POA;
  
  private NamingContextExtImpl space_servant;

  public String fault;
  
  public NamingSpaceServer(es.tid.TIDorbj.core.TIDORB orb, POA father_poa, 
                           NamingProperties properties)
  {
    this.orb = orb;
    this.father_poa = father_poa;
    this.properties = properties;
  }
  
  public void setTrace(Trace trace)
  {
    this.trace = trace;
    if(db != null)
      db.setTrace(trace);

  }

  public boolean db_ready()
  {
    return db != null;
  }
  
  public String getFault()
  { 
    return fault;
  }
  
  public es.tid.corba.TIDNamingAdmin.NamingSpace getSpace()
  {
    return space;
  }

  public void update_space(NamingSpaceAccessData data)
    throws CannotProceed
  {
     if(this.space == null)
      throw new CannotProceed("Empty Naming Space server.");

     if(db != null) {
       db.close();
       db = null;
     }
     
     NamingSpace aux = space;
     aux.data = data;
     
     space = null;

     setSpace(aux,false);    
  }
  
  public void setSpace(NamingSpace space, boolean create_db_space)
    throws CannotProceed
  { 
    if(this.space != null)
      throw new CannotProceed("There is yet a Naming Space in the server");
      
    this.space = space;
     
    db = DBManager.getDB(space, properties.space_connections);
    
    try {
      db.connect();
      db.setTrace(trace);
      if(create_db_space)
        db.create_space();
        
      if(space_POA == null) {
        create_server_poa();
      } else {
        space_servant.setDB(db);
      }
      
      fault = null;
      
    } catch (CannotProceed cp) {
      fault = cp.why;
      db.close();
      db = null;
      throw cp;
    } catch (Exception e) {
      fault = e.toString();
      db.close();
      db = null;
      throw new CannotProceed(fault);
    }
  }
  
  public void delete_space()    
  {
    if(space == null)
      return;
    if(space_POA != null) {
      try {
        space_POA.destroy(true, false);
       } catch (Exception e) {
        if (trace != null) {
         String[] msg = {"NamingSpaceServer::close_space error closing POA ",
                         e.toString()}; 
          trace.print(Trace.ERROR,msg);
        }       
       }
    }
  
    if(db != null){
      try {
        db.delete();
        db.close();
       } catch (Exception e) {
        if (trace != null) {
         String[] msg = {"NamingSpaceServer::delete_space error deleting DB ",
                         e.toString()}; 
          trace.print(Trace.ERROR,msg);
        }
       }
       
      db = null;
    }
      
    space = null;
  }
  
  public void close_space()   
  {
    if(space == null)
      return;
    if(space_POA != null) {
     try { 
      space_POA.destroy(true, false);
     } catch (Exception e) {
      if (trace != null) {
       String[] msg = {"NamingSpaceServer::close_space error closing POA ",
                       e.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
     }
    }
    if(db != null){
      db.close();
      db = null;
    }
    
    space = null;
    db = null;
    space_POA = null;
    space_servant = null;
    fault = null;    

  }

    
  private void create_server_poa()
    throws CannotProceed
  {
    if((space_POA != null) || (db == null))
      throw new CannotProceed("Naming Space not ready");
      
    try {      
      org.omg.CORBA.Policy[] policies = new org.omg.CORBA.Policy[4];
      
      policies[0] = 
        father_poa.create_lifespan_policy(LifespanPolicyValue.PERSISTENT); 
      policies[1] = 
        father_poa.create_id_assignment_policy(IdAssignmentPolicyValue.SYSTEM_ID);
      policies[2] = 
        father_poa.create_servant_retention_policy(ServantRetentionPolicyValue.NON_RETAIN);
      policies[3] = 
        father_poa.create_request_processing_policy(RequestProcessingPolicyValue.USE_DEFAULT_SERVANT);
      
      // create a new POA with its own POAManager?
      if(properties.use_space_manager) {
        space_POA = father_poa.create_POA(space.name,null,policies);
        // sets the threads per manager
        ((es.tid.PortableServer.POAManager) space_POA.the_POAManager()).set_max_threads(properties.space_threads);
      } else {
        space_POA = father_poa.create_POA(space.name,father_poa.the_POAManager(),policies);
      }
      
      org.omg.CORBA.Policy[] iterator_policies = new org.omg.CORBA.Policy[1];

      iterator_policies[0] = 
        father_poa.create_thread_policy(ThreadPolicyValue.SINGLE_THREAD_MODEL); 

      // create the iterator POA
      POA iterators_POA = 
        space_POA.create_POA(space.name + "_iterators", 
                             space_POA.the_POAManager(), iterator_policies);
      
      //create the default servant
      space_servant = new NamingContextExtImpl(orb, space_POA, iterators_POA, db, trace);
        
      space_POA.set_servant(space_servant);
      
    } catch (Throwable excp) {
      throw new CannotProceed(excp.toString());
    }
  }
  
  public void activate() throws CannotProceed
  {    
    if(space_POA == null)
      return;
    try {  
      if(properties.use_space_manager) {
        space_POA.the_POAManager().activate();
      }
    }catch(Throwable th) {
      if (trace != null) {
       String[] msg = {"NamingSpaceServer::activate ",th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      throw new CannotProceed(th.toString());
    }
  }
    
  public void shutdown()
  {    
    if(space_POA == null)
      return;
    try {  
      if(properties.use_space_manager) {
        space_POA.the_POAManager().deactivate(false,false);  
      }
    
      close_space();

    }catch(Throwable th) {
      if (trace != null) {
        String[] msg = {"NamingSpaceServer::shutdown ",th.toString()};
        trace.print(Trace.ERROR, msg);
      }
    }
  }


  public void set_max_threads(int n)throws CannotProceed
  {
    
    if(space_POA == null)
      throw new CannotProceed("Naming Space not ready");

    if((n <= 0) || (n > properties.max_space_threads))
      throw new CannotProceed("Invalid thread number");
      
    if(!properties.use_space_manager) 
      throw new CannotProceed("Bad Operation, "
                              + properties.use_space_manager_name
                              + " must be set to true.");
               
    try {                          
      // sets the threads per manager
      ((es.tid.PortableServer.POAManager) space_POA.the_POAManager()).set_max_threads(n);
    } catch (Exception e) {
      throw new CannotProceed("Bad Operation, " + e.toString());
    }
   
  }

  public void setup_root_Context() throws CannotProceed
  {
    if(space_servant == null)
      throw new CannotProceed("Naming Space not ready");
    try {
      space.root_context = space_servant.global_new_context();
    } catch (Throwable th) {
      throw new CannotProceed(th.toString());
    }
  }

  
}
