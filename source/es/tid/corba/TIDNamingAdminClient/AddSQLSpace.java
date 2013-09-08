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

 File: es.tid.corba.TIDNamingAdminClient.AddSQLSpace.java
  
 Revisions:
 
 ------------------------------------------------------------------------------ */ 
 
package es.tid.corba.TIDNamingAdminClient;

import java.util.Properties;

import es.tid.corba.TIDNamingAdmin.*;
import es.tid.corba.TIDNamingAdmin.AgentPackage.*;

import es.tid.corba.TIDNaming.TIDNamingAdminHandler;

import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * add_sql_space command. 
 * <p> Copyright 2001 Telef&oacute;nica I+D. Printed in Spain (Europe). All Rights Reserved.
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class AddSQLSpace {
	
    public static void main(String args[])
    {
    if((args.length == 0) || args[0].equals("-help")){
      System.err.println("Usage: add_sql_space -help | (space_name user password table connection_init (NULL|rootContextIOR|rootContextIORFile))");
      return;
    }
   
    if(args.length < 6) { 
      System.err.println("add_sql_space: Bad param number");
      System.err.println("\tin: reads the Agent IOR");
      System.err.println("\tout: the RootContext IOR");      
      System.err.println("Usage: add_sql_space -help | (space_name user password table connection_init (NULL|rootContextIOR|rootContextIORFile))");
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
        System.err.println("Error reading IOR from Standard Input");
        ((es.tid.TIDorbj.core.TIDORB)orb).destroy();
        return;
    }

    NamingContext root_context = null;
    
    if(!args[5].equals("NULL")) {
    
      String root_str = null;
      
      try { // try to read the IOR in a file
        FileReader file = new FileReader(args[5]);
        BufferedReader reader = new BufferedReader(file);
      
        root_str = reader.readLine();
        file.close();
      } catch(IOException ioe) {}
  
            
      if(root_str != null) {          
        root_context = NamingContextHelper.narrow(orb.string_to_object(root_str));
      } else {
        root_context = NamingContextHelper.narrow(orb.string_to_object(args[5]));
      }
    }	
    
    org.omg.CORBA.Object ref = orb.string_to_object(ior_str);
    
    Agent naming_agent = AgentHelper.narrow(ref);
    
    
    try {
        
        NamingSpaceAccessData space_data = 
        TIDNamingAdminHandler.parse_sql_access(args, 1);

      NamingSpace space = new NamingSpace(args[0], root_context, space_data);
      
      NamingContext rootContext = naming_agent.add_space(space);
      
      System.out.println(orb.object_to_string(rootContext));
      System.err.println("add_sql_space: NamingSpace " + args[0] + " added.");

    } catch (CannotProceed cp) {
      System.err.println("add_sql_space error with " + args[0] + " Naming Space: " + cp.why);
    } catch (NamingSpaceAlreadyExist ne) {
      System.err.println("add_sql_space: NamingSpace " + args[0] + " already exists.");
    } catch (Exception e) {
        e.printStackTrace();	
    }
    
    ((es.tid.TIDorbj.core.TIDORB)orb).destroy();
    }
}
