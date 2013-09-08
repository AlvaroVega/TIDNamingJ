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


/*

 File: es.tid.corba.TIDNaming.AgentImpl.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming;

import es.tid.corba.TIDNamingAdmin.*;
import es.tid.corba.TIDNamingAdmin.AgentPackage.*;
import org.omg.CosNaming.NamingContext;

/**
 * Implementation clas of TIDNamingAdmin::Agent. <p>
 * The AgentImpl is a servant that has got a reference to the Server ServiceManager,
 * that really implements the service management operations.
 * @author Juan A. C&aacute;ceres
 * @version 1.0
 * @see es.tid.corba.TIDNamingAdmin.Agent
 * @see ServiceManager
 */
public class AgentImpl extends AgentPOA {
 
  /**
   * The current orb.
   */
  org.omg.CORBA.ORB orb;
  
  /**
   * Agents Service Manager library.
   */
  ServiceManager manager;
  
  /**
   * Constructor.
   */
  public AgentImpl(ServiceManager manager, org.omg.CORBA.ORB orb)
  {
    this.orb = orb;
    this.manager = manager;
  }
    
  public NamingContext new_space(String space_name, NamingSpaceAccessData data)
    throws NamingSpaceAlreadyExist, InvalidAccessData, CannotProceed
  {
    return manager.new_space(space_name, data);
  }

  public NamingContext add_space(NamingSpace space)
    throws NamingSpaceAlreadyExist, InvalidAccessData, CannotProceed
  {
    return manager.add_space(space);
  }

  public void update_space(String space_name, NamingSpaceAccessData data)
    throws NamingSpaceNotExist, InvalidAccessData, CannotProceed
  {
    manager.update_space(space_name, data);
  }

  public NamingSpace get_space(String space_name)
    throws NamingSpaceNotExist
  {
    return manager.get_space(space_name);   
  }

  public  void set_max_threads(java.lang.String space_name, int n)
    throws NamingSpaceNotExist, CannotProceed
  {
    manager.set_max_threads(space_name,n);
  }

  public NamingSpace[] get_spaces()
  {
    return manager.get_spaces();
  }

  public ServiceStatus get_status()
  {
    return manager.get_status();
  }

  public NamingContext get_root_context(String space_name)
    throws NamingSpaceNotExist
  {
    return manager.get_root_context(space_name);
  }

  public void delete_space(String space_name)
    throws NamingSpaceNotExist, CannotProceed
  {
    manager.delete_space(space_name);
  }

  public void remove_space(String space_name)
    throws NamingSpaceNotExist, CannotProceed
  {
    manager.remove_space(space_name);
  }

  public void init_service()
    throws CannotProceed
  {
    manager.init_service();
  }

  public void shutdown_service() throws CannotProceed
  {
    manager.shutdown_service();
    orb.shutdown(false);
  }      
}
