//
// ServiceState.java (enum)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

public class ServiceState
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _cannot_run = 0;
  public static final ServiceState cannot_run = new ServiceState(_cannot_run);
  public static final int _can_run = 1;
  public static final ServiceState can_run = new ServiceState(_can_run);
  public static final int _running = 2;
  public static final ServiceState running = new ServiceState(_running);
  public static final int _shutdowned = 3;
  public static final ServiceState shutdowned = new ServiceState(_shutdowned);

  public int value() { return _value; }
  public static ServiceState from_int(int value) {
    switch (value) {
      case 0: return cannot_run;
      case 1: return can_run;
      case 2: return running;
      case 3: return shutdowned;
    };
    return null;
  };
  protected ServiceState (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
