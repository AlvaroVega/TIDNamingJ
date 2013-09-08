//
// SQL_AccessData.java (struct)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

public class SQL_AccessData
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String user;
  public java.lang.String passwd;
  public java.lang.String table;
  public java.lang.String connection_init;

  public SQL_AccessData() {
    user = "";
    passwd = "";
    table = "";
    connection_init = "";
  }

  public SQL_AccessData(java.lang.String user, java.lang.String passwd, java.lang.String table, java.lang.String connection_init) {
    this.user = user;
    this.passwd = passwd;
    this.table = table;
    this.connection_init = connection_init;
  }

}
