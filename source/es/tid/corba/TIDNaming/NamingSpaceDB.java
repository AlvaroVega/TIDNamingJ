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

 File: es.tid.corba.TIDNaming.NamingSpaceDB.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming;

import org.omg.CosNaming.NamingContextPackage.*;


/**
 * Interface for acceding to a NamingSpace storage media.
 * The storage media is seen as a row collection of NamingContext Entries.
 * There is an special entry for all the active NamingContext ""."".
 * The oid is the NamingContext identifier.
 * @author Juan A. C&aacute;ceres
 */
 
public interface NamingSpaceDB {

    //public void setTrace(es.tid.util.trace.Trace trace);
    public void setTrace(es.tid.TIDorbj.util.Trace trace);

    void connect() 
        throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;
  
    void create_space() 
        throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

    void delete()
        throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

    void close();
    
    es.tid.corba.TIDNamingAdmin.NamingSpace getNamingSpace();

    String resolve_context(String oid, String name_id, String name_kind) 
        throws NotFound, CannotProceed;

    String resolve(String oid, String name_id, String name_kind)
        throws NotFound, CannotProceed;
  
    void bind(String oid, String name_id, String name_kind, String ref) 
        throws AlreadyBound, CannotProceed;

    void rebind(String oid, String name_id, String name_kind, String ref) 
        throws NotFound, CannotProceed;
  
    boolean new_context(String oid, String new_oid, String new_ref);
  
    void new_root_context(String oid, String ref) 
        throws AlreadyBound;

    void bind_context(String oid, String name_id, String name_kind, String ref) 
        throws AlreadyBound, CannotProceed;

    void bind_new_context(String oid, String new_oid, String name_id, 
                          String name_kind, String ref) 
        throws AlreadyBound, CannotProceed;

    void rebind_context(String oid, String name_id, String name_kind, 
                        String ref) 
        throws NotFound, CannotProceed;
 	
    void unbind(String oid, String name_id, String name_kind) 
        throws NotFound, CannotProceed;

    void destroy(String oid) throws NotEmpty;

    RowIterator list(String oid);
}
