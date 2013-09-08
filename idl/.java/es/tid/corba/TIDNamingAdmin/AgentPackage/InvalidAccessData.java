//
// InvalidAccessData.java (exception)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin.AgentPackage;

final public class InvalidAccessData
   extends org.omg.CORBA.UserException {


  public InvalidAccessData() {
    super(InvalidAccessDataHelper.id());
  }

  public InvalidAccessData(String reason) {
    super(InvalidAccessDataHelper.id()+" "+reason);

  }

}
