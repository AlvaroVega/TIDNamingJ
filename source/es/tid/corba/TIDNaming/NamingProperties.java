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

 File: es.tid.corba.TIDNaming.NamingProperties.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming;

import es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

public class NamingProperties {
 
  public final static String spaces_file_name = 
    "es.tid.corba.TIDNaming.spaces_file";
  public String spaces_file;

  public final static String use_space_manager_name = 
    "es.tid.corba.TIDNaming.use_space_manager";
  
  public boolean use_space_manager;

  public final static String space_threads_name = 
    "es.tid.corba.TIDNaming.space_threads";

  public int space_threads;

  public final static String max_space_threads_name = 
    "es.tid.corba.TIDNaming.max_space_threads";
    
  public int max_space_threads;

  public final static String space_connections_name = 
    "es.tid.corba.TIDNaming.space_connections";

  public int space_connections;

  public final static String use_trace_name = 
    "es.tid.corba.TIDNaming.use_trace";
  
  public boolean use_trace;

  public final static String trace_level_name = 
    "es.tid.corba.TIDNaming.trace_level";
  
  public int trace_level;
  
  public final static String trace_file_name = 
    "es.tid.corba.TIDNaming.trace_file";
  public String trace_file;
 

  public NamingProperties()
  {
    use_space_manager = true;
    space_threads = 10;
    max_space_threads = 25;
    space_connections = 5;
    use_trace = false;
    trace_level = 1;
  }
  
  /**
   * Initialize the ORB properties.
   * @param args the arguments vector.
   * @param props the properties.
   */
  public void parse(String[] args, java.util.Properties props)
    throws CannotProceed
  {
    parse_system_properties();    
    
    if(props != null)
      parse(props);
    
     if(args != null)
      parse(args);
      
     if(spaces_file == null)
       throw new CannotProceed(spaces_file_name + " value must be expecified");
  }

  private void parse_system_properties() 
    throws CannotProceed
  {
    SystemProperties system_props = new SystemProperties();
    
    spaces_file = system_props.find_property(spaces_file_name);
    parse_use_trace(system_props.find_property(use_trace_name));
    parse_trace_level(system_props.find_property(trace_level_name));
    trace_file = system_props.find_property(trace_file_name);
    parse_use_space_manager(system_props.find_property(use_space_manager_name));
    parse_space_connections(system_props.find_property(space_connections_name)); 
    parse_space_threads(system_props.find_property(space_threads_name)); 
    parse_max_space_threads(system_props.find_property(max_space_threads_name)); 
  }
  
  private void parse (java.util.Properties props)
    throws CannotProceed
  {
    spaces_file = props.getProperty(spaces_file_name);
    parse_use_trace(props.getProperty(use_trace_name));
    parse_trace_level(props.getProperty(trace_level_name));
    trace_file = props.getProperty(trace_file_name);
    parse_use_space_manager(props.getProperty(use_space_manager_name));
    parse_space_connections(props.getProperty(space_connections_name)); 
    parse_space_threads(props.getProperty(space_threads_name)); 
    parse_max_space_threads(props.getProperty(max_space_threads_name)); 
  }
  
  private void parse(String[] args)  
    throws CannotProceed
  {
    int i = 0;
    String name;
 
      
    while (i < args.length - 1)
    {
      if((args[i] == null) || (args[i].length() == 0) /*||
         !args[i].startsWith("-")*/) {
        i++;
      } else {
         if(args[i]/*.substring(1)*/.equals(spaces_file_name))
          spaces_file = args[++i];        
         else if(args[i]/*.substring(1)*/.equals(use_space_manager_name))
          parse_use_space_manager(args[++i]);
         else if(args[i]/*.substring(1)*/.equals(space_connections_name))
          parse_space_connections(args[++i]);
         else if(args[i]/*.substring(1)*/.equals(space_threads_name))
          parse_space_threads(args[++i]);
         else if(args[i]/*.substring(1)*/.equals(max_space_threads_name))
          parse_max_space_threads(args[++i]);
         else if(args[i]/*.substring(1)*/.equals(trace_file_name))
          trace_file = args[++i];        
         else if(args[i]/*.substring(1)*/.equals(use_trace_name))
          parse_use_trace(args[++i]);
         else if(args[i]/*.substring(1)*/.equals(trace_level_name))
          parse_trace_level(args[++i]);
         else
           i++;
      }

    }
  }
  
  /**
   * Parse the use_space_manager boolean value.
   * @param str the value representation.
   * @pre Values must be "true" or "false".
   * @exception es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed if the string does not contains a valid value.
   */
  private void parse_use_space_manager(String str)
    throws CannotProceed
  {
    if(str == null)
      return;
  
    use_space_manager = parse_boolean(use_space_manager_name, str);
  }

  /**
   * Parse the use trace boolean value.
   * @param str the value representation.
   * @pre Values must be "true" or "false".
   * @exception es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed if the string does not contains a valid value.
   */
  private void parse_use_trace(String str)
    throws CannotProceed
  {
    if(str == null)
      return;
  
    use_trace = parse_boolean(use_trace_name, str);
  }
  
  /**
   * Parse the trace_level value.
   * @param str the value digits.
   * @pre Values must be >= 0 and  <= 4.
   * @exception es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed if the string does not contains a valid value.
   */  
  public void parse_trace_level(String str)
    throws CannotProceed
  {
    if(str == null)
      return;
  
    int value = parse_int(trace_level_name, str);
    
    if((value < 0) || (value > 4)) 
      throw new CannotProceed("Invalid property value, " + space_connections_name + ": invalid value " + value + 
                           " (values must be between 0 and 4)");
                           
    trace_level = value;
    
  }

  /**
   * Parse the max_connections value.
   * @param str the value digits.
   * @pre Values must be > 0.
   * @exception es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed if the string does not contains a valid value.
   */  
  public void parse_space_connections(String str)
    throws CannotProceed
  {
    if(str == null)
      return;
  
    int value = parse_int(space_connections_name, str);
    
    if(value < 1) 
      throw new CannotProceed("Invalid property value, " + space_connections_name + ": invalid value " + value + 
                           " (minimum connections 1)");
                           
    space_connections = value;
    
  }

  /**
   * Parse the space_threads value.
   * @param str the value digits.
   * @pre Values must be > 0.
   * @exception es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed if the string does not contains a valid value.
   */  
  public void parse_space_threads(String str)
    throws CannotProceed
  {
    if(str == null)
      return;
  
    int value = parse_int(space_threads_name, str);
    
    if(value < 1) 
      throw new CannotProceed(space_threads_name + ": invalid value " + value + 
                           " (minimum 1)");
                           
    space_threads = value;
    
  }

  /**
   * Parse the max_space_threads value.
   * @param str the value digits.
   * @pre Values must be > 0.
   * @exception es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed if the string does not contains a valid value.
   */  
  public void parse_max_space_threads(String str)
    throws CannotProceed
  {
    if(str == null)
      return;
  
    int value = parse_int(max_space_threads_name, str);
    
    if(value < 1) 
      throw new CannotProceed(max_space_threads_name + ": invalid value " + value + 
                           " (minimum 1)");
                           
    max_space_threads = value;
    
  }

  /**
   * Parse the an int value.
   * @param str the value digits.
   * @exception es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed if the string does not contains a valid number.
   */
  protected static int parse_int(String prop_name, String str)
    throws CannotProceed
  {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException num) {
      throw new CannotProceed(prop_name + ": int value expected.");      
    }
  }
  
  /**
   * Parse the a boolean value.
   * @param str the value representation: "true" or "false".
   * @exception es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed   if the string does not contains a valid value.
   */
  protected static boolean parse_boolean(String prop_name, String str) 
    throws CannotProceed
  {
    if(str.equals("true"))
      return true;
    else if(str.equals("false"))
      return false;
    else
      throw new CannotProceed(prop_name + ": boolean value expected.");      
  }

}
