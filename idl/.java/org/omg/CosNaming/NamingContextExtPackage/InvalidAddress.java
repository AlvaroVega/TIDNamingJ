//
// InvalidAddress.java (exception)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming.NamingContextExtPackage;

final public class InvalidAddress
   extends org.omg.CORBA.UserException {


  public InvalidAddress() {
    super(InvalidAddressHelper.id());
  }

  public InvalidAddress(String reason) {
    super(InvalidAddressHelper.id()+" "+reason);

  }

}
