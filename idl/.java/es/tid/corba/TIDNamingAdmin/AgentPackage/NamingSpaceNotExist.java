//
// NamingSpaceNotExist.java (exception)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin.AgentPackage;

final public class NamingSpaceNotExist
   extends org.omg.CORBA.UserException {


  public NamingSpaceNotExist() {
    super(NamingSpaceNotExistHelper.id());
  }

  public NamingSpaceNotExist(String reason) {
    super(NamingSpaceNotExistHelper.id()+" "+reason);

  }

}
