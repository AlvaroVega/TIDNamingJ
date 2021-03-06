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

 File: es.tid.corba.TIDNaming.DBManager.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming;

import es.tid.corba.TIDNamingAdmin.NamingSpace;
import es.tid.corba.TIDNamingAdmin.NamingSpaceType;
import es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

import es.tid.TIDorbj.util.Trace;

/**
 * NamingSpaceDB creator.
 * @author Juan A. C&aacute;ceres
 * @version 1.0
 */
 
public abstract class DBManager {
  
  /**
   * Creates a naming space Data base accesor for the given NamingSpace.
   * @param space the naming space.
   * @param max_connections maximun  of data base connections opened.
   */
    
  public static NamingSpaceDB getDB(NamingSpace space, int max_connections) throws CannotProceed 
  {
    switch (space.data.discriminator().value())
    {
      case NamingSpaceType._SQL:
          return new es.tid.corba.TIDNaming.oracle.SQLNamingSpaceDB(space, max_connections);
      case NamingSpaceType._NULL:
          return new es.tid.corba.TIDNaming.nulldb.NullNamingSpaceDB(space, max_connections);
      default:
        throw new CannotProceed("Not Implemented");
    }
  }
}
