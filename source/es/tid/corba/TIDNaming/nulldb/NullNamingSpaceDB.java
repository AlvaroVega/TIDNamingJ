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

 File: es.tid.corba.TIDNaming.nulldb.NullNamingSpaceDB.java
  
 Author/s      : Alvaro Vega Garcia
 Project       : Morfeo
 Rel           : 01.00
 Created       : 20/03/2009
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 


package es.tid.corba.TIDNaming.nulldb;

import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;
import org.omg.CosNaming.NamingContext.*;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.StringTokenizer;

import es.tid.TIDorbj.util.Trace;

/**
 * Interface for acceding to a NamingSpace storage media.
 * This is a dummy implementation of Naming Service using HashTables and no .
 * persistence mechanims.
 * The oid is the NamingContext identifier.
 * @author Alvaro Vega
 */
 
public class NullNamingSpaceDB 
    implements es.tid.corba.TIDNaming.NamingSpaceDB {
  
  private Trace trace;
  
  private  es.tid.corba.TIDNamingAdmin.NamingSpace  space;
  
  private String table_name; 
  
  private int max_connections;

  private java.util.Hashtable m_table_contexts; // HashTable (oids, HashEntry)
  private java.util.Hashtable m_table_objects;  // HashTable (oids, HashEntry)
    /*
       HasEntry = HashTable ( name+kind, ref)
     */

  public NullNamingSpaceDB(es.tid.corba.TIDNamingAdmin.NamingSpace space, 
                           int max_connections)
  {
    this.space = space; 
    this.max_connections = max_connections;
  
    table_name = space.data.null_data().null_url;
   
    m_table_contexts = new java.util.Hashtable();
    m_table_objects = new java.util.Hashtable();
  }

  public void setTrace(Trace trace) 
  {
    this.trace = trace;
  }
  
  public void connect() 
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed
  {
      return;
  }
  
  public void create_space() 
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed
  {
      return;
  }
  
  public void delete()
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed
  {
      return;
  }


  public void close()
  {
      return;
  }
    
  public es.tid.corba.TIDNamingAdmin.NamingSpace getNamingSpace()
  {
      return space;
  }
  
  public String resolve_context(String oid, String name_id, String name_kind) 
    throws NotFound
  {
      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);
      } catch (java.lang.NullPointerException ex ) {
          NotFound nf = new NotFound();
          nf.why = NotFoundReason.missing_node;
          throw nf;
      }

      String string_ref = (String) hash_entry.get(name_id + "." + name_kind);
      if (string_ref == null) {
          NotFound nf = new NotFound();
          nf.why = NotFoundReason.not_context;
          throw nf;
      }

      return string_ref;
  }
  

  public String resolve(String oid, String name_id, String name_kind)
    throws NotFound
  {
      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);

      } catch (java.lang.NullPointerException ex ) {
          NotFound nf = new NotFound();
          nf.why = NotFoundReason.missing_node;
          throw nf;
      }

      // The Name must only corresponded to one of m_naming_context or m_object_refs
      String string_ref = (String) hash_entry.get(name_id + "." + name_kind);
      if (string_ref == null) {
//           hash_entry = (java.util.Hashtable) m_table_objects.get(oid);
//           string_ref = (String) hash_entry.get(name_id + "." + name_kind);
//           if (string_ref == null) {
              NotFound nf = new NotFound();
              nf.why = NotFoundReason.not_object;
              throw nf;
//           } 
      }
      return string_ref;
  }

  public boolean new_context(String oid, String new_oid, String new_ref)
  {
      java.util.Hashtable hash_entry = 
          new java.util.Hashtable();
      try {
          m_table_contexts.put(new_oid, hash_entry); 
      } catch (java.lang.NullPointerException ex ) {
          return false;
      }
      return true;
  }
  
  public void new_root_context(String oid, String ref) 
    throws AlreadyBound
  {
      java.util.Hashtable hash_entry = null;
      
      try {
          hash_entry = (java.util.Hashtable) m_table_contexts.get(oid);

          if (hash_entry != null) 
              throw new AlreadyBound();

      } catch (java.lang.NullPointerException ex ) {
      }

      m_table_contexts.put(oid, new java.util.Hashtable());
      return;
  }

  public void bind(String oid, String name_id, String name_kind, String ref) 
    throws AlreadyBound, CannotProceed
  {
      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);

          if (hash_entry != null) {
              String string_ref = (String) hash_entry.get(name_id + "." + name_kind);
              // Name couldn't be in m_table_contexts table yet
              if (string_ref != null)
                  throw new AlreadyBound();
          }

      } catch (java.lang.NullPointerException ex ) {

          try {
              hash_entry =
                  (java.util.Hashtable) m_table_objects.get(oid);
              
              if (hash_entry != null) {
                  String string_ref = (String) hash_entry.get(name_id + "." + name_kind);
                  // Name couldn't be in m_table_objects table yet
                  if (string_ref != null)
                      throw new AlreadyBound();

                  hash_entry.put(name_id + "." + name_kind, ref);
              }

          } catch (java.lang.NullPointerException ex2 ) {
              throw new CannotProceed();
          }
      }

      return;
  }
  
  public void bind_context(String oid, String name_id, String name_kind, String ref) 
      throws AlreadyBound, CannotProceed
  {
      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);
      } catch (java.lang.NullPointerException ex ) {
          throw new CannotProceed();
      }

      String string_ref = (String) hash_entry.get(name_id + "." + name_kind);
      
      if (string_ref != null)
          throw new AlreadyBound();

      hash_entry.put(name_id + "." + name_kind, ref);
      return;
  }
  
  public void bind_new_context (String oid, String new_oid, String name_id, 
                                String name_kind, String ref) 
    throws AlreadyBound, CannotProceed
  {
      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);
      } catch (java.lang.NullPointerException ex ) {
          throw new  CannotProceed();
      }

      try {
          String string_ref = (String) hash_entry.get(name_id + "." + name_kind);
          
          if (string_ref != null)
              throw new AlreadyBound();

          hash_entry.put(name_id + "." + name_kind, ref); 
          
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(new_oid);

          if (hash_entry == null)
              m_table_contexts.put(new_oid, new java.util.Hashtable());

      } catch (java.lang.NullPointerException ex) {
          throw new CannotProceed();
      }
      return;
  }

  public void rebind(String oid, String name_id, String name_kind, String ref) 
    throws NotFound, CannotProceed
  {
      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);

          if (hash_entry != null) {
              String string_ref = (String) hash_entry.get(name_id + "." + name_kind);
              // Name couldn't be in m_table_contexts table
              if (string_ref != null)
                  throw new CannotProceed();
          }

      } catch (java.lang.NullPointerException ex ) {

          try {
              hash_entry =
                  (java.util.Hashtable) m_table_objects.get(oid);
              
          } catch (java.lang.NullPointerException ex2 ) {
              NotFound nf = new NotFound();
              nf.why = NotFoundReason.not_context;
              throw nf;
          }
      }

      hash_entry.put(name_id + "." + name_kind, ref); 

      return;
  }
  
  public void rebind_context(String oid, String name_id, String name_kind, String ref) 
    throws NotFound, CannotProceed
  {
      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);
      } catch (java.lang.NullPointerException ex ) {
          NotFound nf = new NotFound();
          nf.why = NotFoundReason.missing_node;
          throw nf;
      }

      hash_entry.put(name_id + "." + name_kind, ref); 

      return;
  }

  public void unbind(String oid, String name_id, String name_kind) 
    throws NotFound, CannotProceed
  {
      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);

      } catch (java.lang.NullPointerException ex ) {

          try {
              hash_entry =
              (java.util.Hashtable) m_table_objects.get(oid);
              
          } catch (java.lang.NullPointerException ex2 ) {
              NotFound nf = new NotFound();
              nf.why = NotFoundReason.missing_node;
              throw nf;
          }
      }


      try {
          hash_entry.remove(name_id + "." + name_kind);
      } catch (java.lang.NullPointerException ex ) {
          throw new CannotProceed();
      }

      return;
  }

  public void destroy(String oid) throws NotEmpty
  {
      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);
      } catch (java.lang.NullPointerException ex ) {
          throw new NotEmpty();
      }

      if (hash_entry.size() == 0) {
          m_table_contexts.remove(oid);
      }
      else 
          throw new NotEmpty();

      return;   
  }

  public es.tid.corba.TIDNaming.RowIterator list(String oid) 
  {
      NullRowIterator row_iterator = new NullRowIterator(trace);

      java.util.Hashtable hash_entry = null;
      try {
          hash_entry =
              (java.util.Hashtable) m_table_contexts.get(oid);

          for (Enumeration e = hash_entry.keys(); e.hasMoreElements(); ) {
              
              Binding b = new Binding();
              b.binding_name = new NameComponent[1];
              NameComponent name = new NameComponent();
              
              StringTokenizer key = 
                  new StringTokenizer((String)e.nextElement(), ".", false);
              int ntokens = key.countTokens();
              if (ntokens == 2) {
                  name.id = key.nextToken();
                  name.kind = key.nextToken();
              }
              b.binding_name[0] = name;
              b.binding_type = BindingType.ncontext;
              
              row_iterator.addRow(b);
          }
          return row_iterator;
          
      } catch (java.lang.NullPointerException ex ) {

          try {
              hash_entry =
                  (java.util.Hashtable) m_table_objects.get(oid);
              
              for (Enumeration e = hash_entry.keys(); e.hasMoreElements(); ) {
                  
                  Binding b = new Binding();
                  b.binding_name = new NameComponent[1];
                  NameComponent name = new NameComponent();
                  
                  StringTokenizer key = 
                      new StringTokenizer((String)e.nextElement(), ".", false);
                  int ntokens = key.countTokens();
                  if (ntokens == 2) {
                      name.id = key.nextToken();
                      name.kind = key.nextToken();
                  }
                  b.binding_name[0] = name;
                  b.binding_type = BindingType.nobject;
                  
                  row_iterator.addRow(b);
              }
              
          } catch (java.lang.NullPointerException ex2 ) {
              return row_iterator;
          }
          
      }
      
      return row_iterator;
  }





}
