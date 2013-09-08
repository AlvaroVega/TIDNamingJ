//
// NamingSpaceAlreadyExist.java (exception)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin.AgentPackage;

final public class NamingSpaceAlreadyExist
   extends org.omg.CORBA.UserException {


  public NamingSpaceAlreadyExist() {
    super(NamingSpaceAlreadyExistHelper.id());
  }

  public NamingSpaceAlreadyExist(String reason) {
    super(NamingSpaceAlreadyExistHelper.id()+" "+reason);

  }

}
