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

 File: es.tid.corba.TIDNaming.TIDNamingAdminHandler.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming; 
  
  
import es.tid.corba.TIDNamingAdmin.*;

public class TIDNamingAdminHandler {
  
    public final static int SQL_ACCESS_DATA_LENGTH = 4;
  
    public static NamingSpaceAccessData parse_sql_access(String[] args, int offset)
        throws Exception
    {
        if(args.length - offset < SQL_ACCESS_DATA_LENGTH)
            throw new Exception("Invalid SQL Access Data argument length");
      
        int i = offset;
    
        SQL_AccessData  sql_access = new SQL_AccessData(args[i++], args[i++], 
                                                        args[i++], args[i++]);
      
        NamingSpaceAccessData data = new NamingSpaceAccessData();
    
        data.sql_data(sql_access);
    
        return data;    
    }
  
    public static NamingSpaceAccessData parse_nulldb_access(String[] args)
        throws Exception
    {
        NULL_AccessData nulldb_access = new NULL_AccessData("");
      
        NamingSpaceAccessData data = new NamingSpaceAccessData();
    
        data.null_data(nulldb_access);
    
        return data;    
    }

    public static String toString(NamingSpace space, org.omg.CORBA.ORB orb)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("NamingSpace: name =");
        buffer.append(space.name);
        buffer.append("\n\troot_context=");
        buffer.append(orb.object_to_string(space.root_context));
        buffer.append("\n\ttype=");
        switch(space.data.discriminator().value()) {
        case NamingSpaceType._SQL:
            buffer.append("SQL (");
            buffer.append(toString(space.data.sql_data()));
            buffer.append(")");
            break;
        case NamingSpaceType._LDAP:
            buffer.append("LDAP ");
            /// IMPLEMENT
            break;
        case NamingSpaceType._FILE:
            buffer.append("FILE ");
            /// IMPLEMENT
            break;
        }
    
        return buffer.toString();
    
    }
    
    public static String toString(SQL_AccessData data)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("user=\""); 
        buffer.append(data.user);
        buffer.append("\" passwd=\"");
        buffer.append(data.passwd);
        buffer.append("\" table=\"");
        buffer.append(data.table);
        buffer.append("\" connection_init=\"");
        buffer.append(data.connection_init);
        buffer.append('\"');
    
        return buffer.toString();
    
    }

}
