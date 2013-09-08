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

 File: es.tid.corba.TIDNaming.oracle.SQLNamingSpaceDB.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 


package es.tid.corba.TIDNaming.oracle;

import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NamingContextPackage.*;

import java.sql.*;
import javax.sql.*;
import oracle.jdbc.pool.*;

import es.tid.TIDorbj.util.Trace;

/**
 * Interface for acceding to a NamingSpace storage media.
 * The storage media is seen as a row collection of NamingContext Entries.
 * There is an special entry for all the active NamingContext ""."".<p>
 * The NameComponents are added an underscore due to null references in JDBC.
 * The oid is the NamingContext identifier.
 * @author Juan A. C&aacute;ceres
 */
 
public class SQLNamingSpaceDB implements es.tid.corba.TIDNaming.NamingSpaceDB {
  
  private Trace trace;
  
  public final static String UNDERSCORE = "_";
  public final static String EMPTY = "";
  public final static String CONTEXT_TYPE = "C";
  public final static String OBJ_REF_TYPE = "O";
  
  private String resolve_stmt;
  private String bind_stmt;
  private String rebind_stmt;
  private String unbind_stmt;
  private String destroy_stmt;
  private String ctx_exist_stmt; 
  private String ctx_count_stmt; 
  private String list_stmt;

  private  es.tid.corba.TIDNamingAdmin.NamingSpace  space;
  
  private String table_name; 
  
  private oracle.jdbc.pool.OracleConnectionCacheImpl pool;
  
  private int max_connections;
  
  public SQLNamingSpaceDB(es.tid.corba.TIDNamingAdmin.NamingSpace  space, 
                          int max_connections)
  {
    this.space = space; 
    this.max_connections = max_connections;
  
    table_name = space.data.sql_data().table;
    
    init_resolve_stmt(table_name);
    init_bind_stmt(table_name);
    init_rebind_stmt(table_name);
    init_unbind_stmt(table_name);
    init_ctx_exist_stmt(table_name);
    init_ctx_count_stmt(table_name);
    init_destroy_stmt(table_name);
    init_list_stmt(table_name);
  }

  public void setTrace(Trace trace) 
  {
    this.trace = trace;
  }
  
  public void connect() 
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed
  {
    Connection conn = null;
    
    try{
      
      pool = new OracleConnectionCacheImpl();

      es.tid.corba.TIDNamingAdmin.SQL_AccessData sql_data = space.data.sql_data();

      /*
      if(trace != null)
        pool.setLogWriter(trace.getLog());
*/
      pool.setURL(sql_data.connection_init);
      pool.setUser(sql_data.user);
      pool.setPassword(sql_data.passwd);
      pool.setMaxLimit(max_connections);
      pool.setCacheScheme(OracleConnectionCacheImpl.FIXED_WAIT_SCHEME);

      
      //set the statement cache
      
     // pool.setStmtCacheSize(8);
      
      conn = getConnection();
      
      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::connect: connected to ",
                        sql_data.connection_init}; 
        trace.print(Trace.DEBUG,msg);
      }
      
    } catch (SQLException ex) {
      throw new es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed(ex.toString());
    } catch(Throwable th) {
      throw new es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed(th.toString());
    } finally {
      close(trace, conn);
    }
  }
  
  public void create_space() 
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed
  {
    Connection conn = null;
    Statement statement = null;

    try {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("CREATE TABLE ");
      buffer.append(table_name);
      buffer.append(" (OID VARCHAR2(32) NOT NULL, Name_id VARCHAR2(64) NOT NULL, Name_kind VARCHAR2(64) NOT NULL, Binding_type VARCHAR2(1), IOR VARCHAR2(600), PRIMARY KEY(OID, Name_id, Name_kind))");
      
      conn = getConnection();
      
      statement = conn.createStatement();
      
      statement.execute(buffer.toString());
            
      conn.commit();

      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::create_space: created ",
                        table_name,
                        " space table"}; 
        trace.print(Trace.DEBUG,msg);
      }
    } catch (SQLException ex) {   
      
      rollback(conn);
      
      if(ex.getSQLState().equals(SQLMessages.NAME_ALREADY_USED))
        throw new es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed("SpaceName Table already exist");
      else      
        throw new es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed(ex.getSQLState());
    } catch (Throwable th) {

      rollback(conn);

      throw new es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed(th.toString());
    } finally {
      close(statement);
      close(trace, conn);
    }
  }
  
  public void delete()
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed
  {
    Connection conn = null;
    Statement statement = null;
    
    try {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("DROP TABLE ");
      buffer.append(table_name);
      
      conn = getConnection();
      
      statement = conn.createStatement();
      
      statement.execute(buffer.toString());

      conn.commit();
      
      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::delete: \"",
                         table_name,
                        "\" space table deleted"}; 
        trace.print(Trace.DEBUG,msg);
      }

    } catch (Throwable th) {
      rollback(conn);
      throw new es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed(th.toString());
    } finally {
      close(statement);
      close(trace,conn);
    }
  }


  public void close()
  {
    try {
      pool.close();
    } catch(Throwable th) {}
  }
    
  public es.tid.corba.TIDNamingAdmin.NamingSpace getNamingSpace()
  {
    return space;
  }
  
  public String resolve_context(String oid, String name_id, String name_kind) 
    throws NotFound
  {
    return typed_resolve(oid, name_id, name_kind, CONTEXT_TYPE);
  }
  
  private String typed_resolve(String oid, String name_id, String name_kind, String type) 
    throws NotFound
  {
    String ior = null;
    String result_type = null;


    //tells if the NamingContext which the invokation is made exist in the Database
    
    boolean object_exist = false;

    Connection conn = null;
    PreparedStatement statement = null;
    ResultSet result = null;
    try {      

      conn = getConnection();
      
      statement = conn.prepareStatement(resolve_stmt);
      
      statement.setString(1,oid);
      statement.setString(2,UNDERSCORE + name_id);
      statement.setString(3,UNDERSCORE + name_kind);
      
      result = statement.executeQuery();
      
      while(result.next()) {   // iterations 0, 1 or 2   
        if(result.getString(1).equals(UNDERSCORE) && 
           result.getString(2).equals(UNDERSCORE)) {
          object_exist= true;
        } else {
          result_type = result.getString(3);
          ior = result.getString(4);
        }
      }
      
      conn.commit();
      
    } catch(Throwable th) {
      
      rollback(conn);
      
      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::typed_resolve error: ",
                        th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      throw new org.omg.CORBA.TRANSIENT();
      
    } finally {
      close(result);
      close(statement);
      close(trace,conn);
    }
    
    if(!object_exist)
      throw new org.omg.CORBA.OBJECT_NOT_EXIST();
      
    if(ior == null){
      NotFound nf = new NotFound();
      
      if(type.equals(OBJ_REF_TYPE))
        nf.why = NotFoundReason.not_object;
      else
        nf.why = NotFoundReason.missing_node;
      
      throw nf;  
    } else if((type.equals(CONTEXT_TYPE)) && 
              (!result_type.equals(CONTEXT_TYPE))) {
      NotFound nf = new NotFound();      
      nf.why = NotFoundReason.not_context;      
      throw nf;  
    }
    
    return ior;
  }
    

  public String resolve(String oid, String name_id, String name_kind)
    throws NotFound
  {
    String ior = null;

    //tells if the NamingContext which the invokation is made exist in the Database
    
    boolean object_exist = false;

    Connection conn = null;
    ResultSet result = null;
    PreparedStatement statement = null;
    
    try {
      
      conn = getConnection();
      
      statement = conn.prepareStatement(resolve_stmt);
      
      statement.setString(1,oid);
      statement.setString(2,UNDERSCORE + name_id);
      statement.setString(3,UNDERSCORE + name_kind);
      
      result = statement.executeQuery();
      
      while(result.next()) {   // iterations 0, 1 or 2   
        if(result.getString(1).equals(UNDERSCORE) && 
           result.getString(2).equals(UNDERSCORE)) {
          object_exist= true;
        } else {
          //type is in the 3th column and IOR in 4th
          ior = result.getString(4);
        }
      }
      
      conn.commit();
      
    } catch(Throwable th) {
      
      rollback(conn);
      
      if (trace != null) {
       String[] msg = {"SQLNamingSpaceDB::resolve error: ",th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      throw new org.omg.CORBA.TRANSIENT();
      
    } finally {
      close(result);
      close(statement);
      close(trace,conn);
    }
    
    if(!object_exist)
      throw new org.omg.CORBA.OBJECT_NOT_EXIST();
      
    if(ior == null){
      NotFound nf = new NotFound();
      nf.why = NotFoundReason.not_object;
      throw nf;  
    }
    
    return ior;
  }

  public boolean new_context(String oid, String new_oid, String new_ref)
  {
    boolean exist_object = false;
    
    Connection conn =  null;

    try {
      
      conn = getTransactionConnection();
      
      exist_object = existContext(conn, oid);
      
    } catch (org.omg.CORBA.TRANSIENT t) {
     
      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::new_context existContext query error: ",
                        t.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      rollback(conn);
      close(trace, conn);
      
      throw t;
    }
    
    PreparedStatement statement = null;
    
    try {
      
      statement = conn.prepareStatement(bind_stmt);
      
      statement.setString(1,new_oid);
      statement.setString(2,UNDERSCORE);
      statement.setString(3,UNDERSCORE);
      statement.setString(4,CONTEXT_TYPE);
      statement.setString(5,new_ref);
      
      statement.executeUpdate();
      
      conn.commit();

    } catch(Throwable th) {
      
      rollback(conn);
      
      if (trace != null) {
       String[] msg = {"SQLNamingSpaceDB::new_context error: ",th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      return false;
    } finally {
      close(statement);
      close(trace, conn);
    }
    
    return true;
  }
  
  public void new_root_context(String oid, String ref) 
    throws AlreadyBound
  {
    
    Connection conn = null;
    PreparedStatement statement = null;
    
    try {
      
      conn = getConnection();

      statement = conn.prepareStatement(bind_stmt);
      
      statement.setString(1,oid);
      statement.setString(2,UNDERSCORE);
      statement.setString(3,UNDERSCORE);
      statement.setString(4,CONTEXT_TYPE);
      statement.setString(5,ref);
      
      statement.executeUpdate();
      
      conn.commit();
      
    } catch (SQLException ex) {  
      
      rollback(conn);

      while(ex != null) {
        if(ex.getSQLState().equals(SQLMessages.UNIQUE_CONSTRAIN_VIOLATED)) {
          throw new AlreadyBound();
        } else {
          if (trace != null) {
           String[] msg = {"SQLNamingSpaceDB::new_root_context error: ",ex.toString()}; 
            trace.print(Trace.ERROR,msg);
          }
        }
        
        ex = ex.getNextException();
      }
      
      throw new org.omg.CORBA.TRANSIENT();
    } catch(Throwable th) {
      
      rollback(conn);
      
      if (trace != null) {
       String[] msg = {"SQLNamingSpaceDB::new_root_context error: ",th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      throw new org.omg.CORBA.TRANSIENT();
    } finally {
      close(statement);
      close(trace, conn);
    }
  }

  public void bind(String oid, String name_id, String name_kind, String ref) 
    throws AlreadyBound, CannotProceed
  {
    bind(oid, name_id, name_kind, ref, OBJ_REF_TYPE); 
  }
  
  public void bind_context(String oid, String name_id, String name_kind, String ref) 
    throws AlreadyBound, CannotProceed
  {
    bind(oid, name_id, name_kind, ref, CONTEXT_TYPE); 
  }

  
   private void bind(String oid, String name_id, 
                     String name_kind, String ref, String type) 
    throws AlreadyBound, CannotProceed
  {
    boolean exist_object = false;
    boolean cannot_procced = false;
    boolean alread_bound = false;
    
    Connection conn =  null;

    
    try {
      
      conn = getTransactionConnection();
      
      exist_object = existContext(conn, oid);
      
    } catch (org.omg.CORBA.TRANSIENT t) {
      
      rollback(conn);
      
      close(trace, conn);
      
      throw t;
    }
     
    
    PreparedStatement statement = null;
    
    try {
      
      if(exist_object) {
        statement = conn.prepareStatement(bind_stmt);
        
        statement.setString(1,oid);
        statement.setString(2,UNDERSCORE + name_id);
        statement.setString(3,UNDERSCORE + name_kind);
        statement.setString(4,type);
        statement.setString(5,ref);
        
        statement.executeUpdate();
      }
      
      conn.commit();

    } catch (SQLException ex) {
      
      rollback(conn);
      
      while(ex != null) {
        if(ex.getSQLState().equals(SQLMessages.UNIQUE_CONSTRAIN_VIOLATED)) {
          throw new AlreadyBound();
        } else {
          if (trace != null) {
           String[] msg = {"SQLNamingSpaceDB::new_root_context error: ",ex.toString()}; 
            trace.print(Trace.ERROR,msg);
          }
        }
        
        ex = ex.getNextException();
      }
      
      throw new CannotProceed();
      
    } catch(Throwable th) {

      rollback(conn);
      
      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::bind insert statement: error: ",
                         th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      throw new CannotProceed();
      
    } finally {       
      close(statement);
      close(trace, conn);
    }

    if(!exist_object)
      throw new org.omg.CORBA.OBJECT_NOT_EXIST();
  }

  public void bind_new_context (String oid, String new_oid, String name_id, 
                                  String name_kind, String ref) 
    throws AlreadyBound, CannotProceed
  {
    boolean exist_object = false;
    boolean cannot_procced = false;
    boolean alread_bound = false;
    
    Connection conn =  null;

    try {
      
      conn = getTransactionConnection();
      
      exist_object = existContext(conn, oid);
      
    } catch (org.omg.CORBA.TRANSIENT t) {
      
      rollback(conn);
      
      close(trace, conn);
      
      throw t;
    }
     
    
    PreparedStatement statement = null;
    PreparedStatement new_ctx_statement = null;
    
    
    try{
      
      if(exist_object) {
        // new context
        
        new_ctx_statement = conn.prepareStatement(bind_stmt);
        
        new_ctx_statement.setString(1,new_oid);
        new_ctx_statement.setString(2,UNDERSCORE);
        new_ctx_statement.setString(3,UNDERSCORE);
        new_ctx_statement.setString(4,CONTEXT_TYPE);
        new_ctx_statement.setString(5,ref);
        
        statement = conn.prepareStatement(bind_stmt);
  
        statement.setString(1,oid);
        statement.setString(2,UNDERSCORE + name_id);
        statement.setString(3,UNDERSCORE + name_kind);
        statement.setString(4,CONTEXT_TYPE);
        statement.setString(5,ref);

        statement.executeUpdate();
        new_ctx_statement.executeUpdate();
      }
      
      conn.commit();

    } catch (SQLException ex) {
      
      rollback(conn);
      
      while(ex != null) {
        if(ex.getSQLState().equals(SQLMessages.UNIQUE_CONSTRAIN_VIOLATED)) {
          throw new AlreadyBound();
        } else {
          if (trace != null) {
           String[] msg = {"SQLNamingSpaceDB::bind_new_context error: ",ex.toString()}; 
            trace.print(Trace.ERROR,msg);
          }
        }
        
        ex = ex.getNextException();
      }
      
      throw new CannotProceed();
      
    } catch(Throwable th) {

      rollback(conn);
      
      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::bind insert statement: error: ",
                         th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      throw new CannotProceed();
      
    } finally {
      close(new_ctx_statement);
      close(statement);
      close(trace, conn);
    }

    if(!exist_object)
      throw new org.omg.CORBA.OBJECT_NOT_EXIST();
  }

  public void rebind(String oid, String name_id, String name_kind, String ref) 
    throws NotFound, CannotProceed
  {
    rebind(oid, name_id, name_kind, ref, OBJ_REF_TYPE); 
  }
  
  public void rebind_context(String oid, String name_id, String name_kind, String ref) 
    throws NotFound, CannotProceed
  {
    rebind(oid, name_id, name_kind, ref, CONTEXT_TYPE); 
  }


  private void rebind(String oid, String name_id, 
                             String name_kind, String ref, String type) 
    throws NotFound, CannotProceed
  {
    int updated_count = 0;
    boolean exist_object = false;

    Connection conn = null;
    
    try {
      
      conn = getTransactionConnection();
      
      exist_object = existContext(conn, oid);
      
    } catch (org.omg.CORBA.TRANSIENT t) {
      
      rollback(conn);
      
      close(trace, conn);
      
      throw t;
    }

    PreparedStatement statement = null;
    
    try{
      
      if(exist_object) {

        statement = conn.prepareStatement(rebind_stmt);
  
        statement.setString(1,ref);
       
        statement.setString(2,oid);
        statement.setString(3,UNDERSCORE + name_id);
        statement.setString(4,UNDERSCORE + name_kind);
        statement.setString(5,type);
        
        updated_count = statement.executeUpdate();
  
      }      

      conn.commit();

    } catch(Throwable th) {
      
      rollback(conn);
      
      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::rebind error: ",
                        th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      throw new CannotProceed();
      
    } finally {
      close(statement);
      close(trace, conn);
    }
    
    if(!exist_object)
      throw new org.omg.CORBA.OBJECT_NOT_EXIST();

    if(updated_count == 0) {
      NotFound nf = new NotFound();
      nf.why = NotFoundReason.not_object;
      throw nf;  
    }
  }
  
  public void unbind(String oid, String name_id, String name_kind) 
    throws NotFound, CannotProceed
  {
    int deleted_count = 0;
    boolean exist_object = false;

    Connection conn = null;

    try {
      
      conn = getTransactionConnection();
      
      exist_object = existContext(conn, oid);
      
    } catch (org.omg.CORBA.TRANSIENT t) {
      
      rollback(conn);
      
      close(trace, conn);
      
      throw t;
    }

    PreparedStatement statement = null;
    
    try {
      if(exist_object) {
      
        statement = conn.prepareStatement(unbind_stmt);
        
        statement.setString(1,oid);
        statement.setString(2,UNDERSCORE + name_id);
        statement.setString(3,UNDERSCORE + name_kind);
        
        deleted_count = statement.executeUpdate();
      }

      conn.commit();
      
    } catch(Throwable th) {
      rollback(conn);

      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::unbind error, CannotProceed: ",
                        th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      throw new CannotProceed();
    } finally {
      close(statement);
      close(trace, conn);
    }

    if(!exist_object)
      throw new org.omg.CORBA.OBJECT_NOT_EXIST();
      
    if(deleted_count == 0) {
      NotFound nf = new NotFound();
      nf.why = NotFoundReason.not_object;
      throw nf;  
    }
  }

  public void destroy(String oid) throws NotEmpty
  {
    int binding_count = 0;

    Connection conn = null;

    try {
      
      conn = getTransactionConnection();
      
      binding_count = contextCount(conn, oid);
      
    } catch (org.omg.CORBA.TRANSIENT t) {
      
      rollback(conn);
      
      close(trace, conn);
      
      throw t;
    }

    PreparedStatement statement = null;
    
    try {
      
      if(binding_count == 1) {
        
      // there is only the self referenced context entry
        
        statement = conn.prepareStatement(destroy_stmt);
        
        statement.setString(1,oid);
        
        statement.executeUpdate();
      }
     
      conn.commit();

    } catch(Throwable th) {
      rollback(conn);

      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::unbind error, CannotProceed: ",
                        th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      throw new org.omg.CORBA.TRANSIENT();
      
    } finally {
      close(statement);
      close(trace, conn);
    }

    if(binding_count == 0)
      throw new org.omg.CORBA.OBJECT_NOT_EXIST();
      
    if(binding_count > 1) {
      throw new NotEmpty();  
    }
    
  }
  

  public es.tid.corba.TIDNaming.RowIterator list(String oid) 
  {
    boolean exist_object = false;

    SQLRowIterator row_iterator = null;
    
    Connection conn = null;
    
    PreparedStatement statement = null;
    
    ResultSet result = null;

    try {
      
      conn = getConnection();
      
      statement = conn.prepareStatement(list_stmt, 
                                        ResultSet.TYPE_FORWARD_ONLY,
                                        ResultSet.CONCUR_READ_ONLY);
      
      statement.setString(1,oid);
     
      result = statement.executeQuery();
      
      row_iterator = new SQLRowIterator(trace);
      
      String name_id = null;
      String name_kind = null;
      
      while(result.next()) {
       
        name_id = result.getString(1).substring(1);
        
        name_kind = result.getString(2).substring(1);
        
        if(name_id.equals(EMPTY) && name_kind.equals(EMPTY)) {
          exist_object = true;
        } else {
        
          Binding b = new Binding();
          
          b.binding_name = new NameComponent[1];
          
          NameComponent name = new NameComponent();
          
          name.id = name_id;
          
          name.kind = name_kind;
          
          b.binding_name[0] = name;
          
          if(result.getString(3).equals(CONTEXT_TYPE))
            b.binding_type = BindingType.ncontext; 
          else
            b.binding_type = BindingType.nobject;
          
           row_iterator.addRow(b);
        }
      }
      
      conn.commit();

    } catch(Throwable th) {
      
      rollback(conn);
      
      if (trace != null) {
        String[] msg = {"SQLNamingSpaceDB::list error: ",
                        th.toString()}; 
        trace.print(Trace.ERROR,msg);
      }
      
      throw new org.omg.CORBA.TRANSIENT();
    } finally {
      close(result);
      close(statement);
      close(trace, conn);
    }

    if(!exist_object) {
      if(row_iterator != null)
        row_iterator.close();
      throw new org.omg.CORBA.OBJECT_NOT_EXIST();
    }
    
    return row_iterator;
  }

  private int contextCount(Connection conn, String oid) 
    throws org.omg.CORBA.TRANSIENT
  {
    int count;
    
    PreparedStatement statement = null;
    ResultSet result = null;
    
    try {
      statement = conn.prepareStatement(ctx_count_stmt);
        
      statement.setString(1,oid);
        
      result = statement.executeQuery();
        
      result.next();
        
      count = result.getInt(1);
    } catch (SQLException ex) {
      if(trace != null) {
        String[] msg = {"SQLNamingSpaceDB::contextCount error: " +
                         ex.toString()};
        trace.print(Trace.ERROR, msg);
      }
      throw new org.omg.CORBA.TRANSIENT();
    }finally{
      close(result);
      close(statement);
    }
      
    return count;
  }

  private boolean existContext(Connection conn, String oid) 
    throws org.omg.CORBA.TRANSIENT
  {
    boolean exist = false;
    PreparedStatement statement = null;
    ResultSet result = null;
    
    try {
      statement = conn.prepareStatement(ctx_exist_stmt);
        
      statement.setString(1,oid);
        
      result = statement.executeQuery();
        
      result.next();
        
      exist = (result.getInt(1) > 0);

    } catch (SQLException ex) {
      if(trace != null) {
        String[] msg = {"SQLNamingSpaceDB::existContext error: " +
                         ex.toString()};
        trace.print(Trace.ERROR, msg);
      }
      throw new org.omg.CORBA.TRANSIENT();
    }finally{
      close(result);
      close(statement);
    }
      
    return exist;
  }

  private Connection getConnection()
  {
    /*
    if(trace != null) {
      trace.print(Trace.DEEP_DEBUG, "SQLNamingSpaceDB::getConnection");
    }
    */

    try {
      Connection conn = pool.getConnection();
  
      if(conn.getAutoCommit()) {
        conn.setAutoCommit(false);
      }
   
      return conn;
    } catch (SQLException ex) {
      if(trace != null) {
        String[] msg = {"SQLNamingSpaceDB::getConnection error ", ex.toString()};
        trace.print(Trace.ERROR, msg);
      }
      
      throw new org.omg.CORBA.TRANSIENT();
    }
  }

  private Connection getTransactionConnection() 
  {   
   /*   
    if(trace != null) {
      trace.print(Trace.DEEP_DEBUG, "SQLNamingSpaceDB::getTransactionConnection");
    }
   */ 
    try {
       Connection conn = pool.getConnection();
   
      if(conn.getAutoCommit()){
        conn.setAutoCommit(false);
      }
      if(conn.getTransactionIsolation() != Connection.TRANSACTION_SERIALIZABLE) {
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
      }
      
      return conn;
      
    } catch (SQLException ex) {      
      if(trace != null) {
        String[] msg = {"SQLNamingSpaceDB::getTransactionConnection error ", ex.toString()};
        trace.print(Trace.ERROR, msg);
      }
      
      throw new org.omg.CORBA.TRANSIENT();
    }
  }

  private static void close(ResultSet result)
  {
    try {
      if(result != null)
        result.close();
    }catch (Exception ex){}
  }
  
  private static void close(Statement statement)
  {
    try {
      if(statement != null)
        statement.close();
    }catch (Exception ex){}
  }

  private static void close(Trace trace, Connection conn)
  {
    if(trace != null) {
      trace.print(Trace.DEBUG, "SQLNamingSpaceDB::close_connection");
    }

    try {
      if(conn != null) 
        conn.close();
    }catch (Exception ex){}
  }

  private static void rollback(Connection conn)
  {
    try {
      if(conn != null) 
        conn.rollback();
    }catch (Exception ex){}
  }

  private void init_resolve_stmt(String space_name)
  {
    StringBuffer buffer = new StringBuffer();
    
    buffer.append("SELECT Name_id, Name_kind, Binding_type, IOR FROM ");
    buffer.append(space_name);
    buffer.append(" WHERE (OID = ? AND ((Name_id = ? AND Name_kind = ?) OR ");
    buffer.append("(Name_id = \'_\' AND Name_kind = \'_\')))");

    resolve_stmt = buffer.toString();
  }

  
  private void init_bind_stmt(String space_name)
  {  
    StringBuffer buffer = new StringBuffer();

    buffer.append("INSERT INTO ");
    buffer.append(space_name);
    buffer.append(" VALUES (?,?,?,?,?)");
    
    bind_stmt = buffer.toString();

  }
  
  private void init_rebind_stmt(String space_name)
  {  
    StringBuffer buffer = new StringBuffer();

    buffer.append("UPDATE ");
    buffer.append(space_name);
    buffer.append(" SET IOR = ? WHERE OID = ? AND Name_id = ? AND Name_kind = ? AND Binding_type = ?");
    
    rebind_stmt = buffer.toString();
  }
  
  private void init_unbind_stmt(String space_name)
  {  
    StringBuffer buffer = new StringBuffer();

    buffer.append("DELETE ");
    buffer.append(space_name);
    buffer.append(" WHERE OID = ? AND Name_id = ? AND Name_kind = ?");
    
    unbind_stmt = buffer.toString();
  }

    private void init_destroy_stmt(String space_name)
  {  
    StringBuffer buffer = new StringBuffer();

    buffer.append("DELETE ");
    buffer.append(space_name);
    buffer.append(" WHERE OID = ? AND Name_id = \'_\' AND Name_kind = \'_\'");
    
    destroy_stmt = buffer.toString();
  }

  private void init_ctx_exist_stmt(String space_name)
  {
    StringBuffer buffer = new StringBuffer();

    buffer.append("SELECT COUNT(*) AS num FROM ");
    buffer.append(space_name);
    buffer.append(" WHERE OID = ? AND Name_id = \'_\' AND Name_kind = \'_\'");
    
    ctx_exist_stmt = buffer.toString();
  }

  private void init_ctx_count_stmt(String space_name)
  {
    StringBuffer buffer = new StringBuffer();

    buffer.append("SELECT COUNT(*) AS num FROM ");
    buffer.append(space_name);
    buffer.append(" WHERE OID = ?");
    
    ctx_count_stmt = buffer.toString();
  }

  private void init_list_stmt(String space_name)
  {
    StringBuffer buffer = new StringBuffer();
    
    buffer.append("SELECT Name_id, Name_kind, Binding_type, IOR FROM ");
    buffer.append(space_name);
    buffer.append(" WHERE OID = ?");

    list_stmt = buffer.toString();
    
  }
}
