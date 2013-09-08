//
// ServiceStatus.java (struct)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

public class ServiceStatus
   implements org.omg.CORBA.portable.IDLEntity {

  public es.tid.corba.TIDNamingAdmin.ServiceState state;
  public java.lang.String msgs;

  public ServiceStatus() {
    msgs = "";
  }

  public ServiceStatus(es.tid.corba.TIDNamingAdmin.ServiceState state, java.lang.String msgs) {
    this.state = state;
    this.msgs = msgs;
  }

}
