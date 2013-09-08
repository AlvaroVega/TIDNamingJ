//
// InvalidName.java (exception)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming.NamingContextPackage;

final public class InvalidName
   extends org.omg.CORBA.UserException {


  public InvalidName() {
    super(InvalidNameHelper.id());
  }

  public InvalidName(String reason) {
    super(InvalidNameHelper.id()+" "+reason);

  }

}
