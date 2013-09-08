//
// CannotProceed.java (exception)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin.AgentPackage;

final public class CannotProceed
   extends org.omg.CORBA.UserException {

  public java.lang.String why;

  public CannotProceed() {
    super(CannotProceedHelper.id());
  }

  public CannotProceed(java.lang.String _why) {
    super(CannotProceedHelper.id());

    this.why = _why;
  }

  public CannotProceed(String reason, java.lang.String _why) {
    super(CannotProceedHelper.id()+" "+reason);

    this.why = _why;
  }

}
