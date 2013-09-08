//
// AgentPOATie.java (tie)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

public class AgentPOATie
 extends AgentPOA
 implements AgentOperations {

  private AgentOperations _delegate;
  public AgentPOATie(AgentOperations delegate) {
    this._delegate = delegate;
  };

  public AgentOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:tid.es/TIDNamingAdmin/Agent:1.0"  };

  public org.omg.CosNaming.NamingContext new_space(java.lang.String space_name, es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist, es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    return this._delegate.new_space(
    space_name, 
    data
    );
  };

  public org.omg.CosNaming.NamingContext add_space(es.tid.corba.TIDNamingAdmin.NamingSpace space)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist, es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    return this._delegate.add_space(
    space
    );
  };

  public void update_space(java.lang.String space_name, es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    this._delegate.update_space(
    space_name, 
    data
    );
  };

  public es.tid.corba.TIDNamingAdmin.NamingSpace get_space(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist {
    return this._delegate.get_space(
    space_name
    );
  };

  public es.tid.corba.TIDNamingAdmin.NamingSpace[] get_spaces() {
    return this._delegate.get_spaces(
    );
  };

  public es.tid.corba.TIDNamingAdmin.ServiceStatus get_status() {
    return this._delegate.get_status(
    );
  };

  public void set_max_threads(java.lang.String space_name, int n)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    this._delegate.set_max_threads(
    space_name, 
    n
    );
  };

  public org.omg.CosNaming.NamingContext get_root_context(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist {
    return this._delegate.get_root_context(
    space_name
    );
  };

  public void delete_space(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    this._delegate.delete_space(
    space_name
    );
  };

  public void remove_space(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    this._delegate.remove_space(
    space_name
    );
  };

  public void init_service()
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    this._delegate.init_service(
    );
  };

  public void shutdown_service()
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    this._delegate.shutdown_service(
    );
  };


}
