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

 File: es.tid.corba.TIDNaming.Server.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming;

import java.util.Properties;

import es.tid.corba.TIDNamingAdmin.Agent;
import es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

public class Server {
	
    public static void main(String args[])
    {
        Properties props = new Properties();
    
        props.put("org.omg.CORBA.ORBClass","es.tid.TIDorbj.core.TIDORB");
    
        System.getProperties().put("org.omg.CORBA.ORBSingletonClass",
                                   "es.tid.TIDorbj.core.SingletonORB");
  
        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args,props);
  
        org.omg.PortableServer.POA rootPOA = null;
        try {    
            rootPOA = 
                org.omg.PortableServer.POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        } catch(Exception e) {}
    
        try {
            ServiceManager manager = 
                ServiceManager.init((es.tid.TIDorbj.core.TIDORB) orb, args, null);
                            
            AgentImpl agent_servant = new AgentImpl(manager,orb);
        
            Agent agent_ref = agent_servant._this(orb);
          
            System.out.println(orb.object_to_string(agent_ref));
    
            rootPOA.the_POAManager().activate();
      
            orb.run();
   
        } catch (CannotProceed cp) {
            System.err.println(cp.why);
            cp.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
        orb.destroy();
    }
}
