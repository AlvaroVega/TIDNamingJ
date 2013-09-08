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

/* -----------------------------------------------------------------------------

 File: es.tid.corba.TIDNamingAdminClient.RemoveSpace.java
  
 Revisions:
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNamingAdminClient;

import java.util.Properties;

import es.tid.corba.TIDNamingAdmin.*;
import es.tid.corba.TIDNamingAdmin.AgentPackage.*;

import es.tid.corba.TIDNaming.TIDNamingAdminHandler;

import org.omg.CosNaming.NamingContext;

/**
 * remove_space command. 
 * <p> Copyright 2001 Telef&oacute;nica I+D. Printed in Spain (Europe). All Rights Reserved.
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

 public class RemoveSpace {
	
     public static void main(String args[])
     {
    if(args.length < 1) {
      System.err.println("remove_space: Bad param number");
      System.err.println("Usage: remove_space space_name | -help");
      return;
    }
    
    if(args[0].equals("-help")){
      System.err.println("Removes a NamingSpace in server");
      System.err.println("\tin: reads the Agent IOR");
      System.err.println("Usage: remove_space space_name | -help");
      return;
    }
 
    
    Properties props = new Properties();
    
    props.put("org.omg.CORBA.ORBClass","es.tid.TIDorbj.core.TIDORB");
    
    System.getProperties().put("org.omg.CORBA.ORBSingletonClass","es.tid.TIDorbj.core.SingletonORB");
		
    org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args,props);
    
    String ior_str;
    
    try {
        java.io.LineNumberReader r = 
            new java.io.LineNumberReader (new java.io.InputStreamReader(System.in));
        /*
          java.io.LineNumberReader r = 
          new java.io.LineNumberReader (new java.io.FileReader("agent.ior"));
        */
        ior_str = r.readLine();
        r.close();
    }
    catch (java.io.IOException e1) {
        System.err.println("remove_space: Error reading IOR from Standard Input");
        ((es.tid.TIDorbj.core.TIDORB)orb).destroy();
        return;
    }
    
    
    org.omg.CORBA.Object ref = orb.string_to_object(ior_str);
    
    Agent naming_agent = AgentHelper.narrow(ref);
		
    
    try {
        
        naming_agent.remove_space(args[0]);
        
        System.err.println("remove_space: NamingSpace " + args[0] + " removed.");
        
    } catch (CannotProceed cp) {
        System.err.println("remove_space: Error deleting NamingSpace " + args[0] + ": " + cp.why);
    } catch (NamingSpaceNotExist ne) {
        System.err.println("remove_space: NamingSpace " + args[0] + " does not exist.");
    } catch (Exception e) {
        e.printStackTrace();	
    }
    
    ((es.tid.TIDorbj.core.TIDORB)orb).destroy();
     }
}
