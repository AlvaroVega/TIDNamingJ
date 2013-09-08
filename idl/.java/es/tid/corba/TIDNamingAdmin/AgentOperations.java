//
// Agent.java (interfaceOperations)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

public interface AgentOperations {

  org.omg.CosNaming.NamingContext new_space(java.lang.String space_name, es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist, es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

  org.omg.CosNaming.NamingContext add_space(es.tid.corba.TIDNamingAdmin.NamingSpace space)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist, es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

  void update_space(java.lang.String space_name, es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

  es.tid.corba.TIDNamingAdmin.NamingSpace get_space(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist;

  es.tid.corba.TIDNamingAdmin.NamingSpace[] get_spaces();

  es.tid.corba.TIDNamingAdmin.ServiceStatus get_status();

  void set_max_threads(java.lang.String space_name, int n)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

  org.omg.CosNaming.NamingContext get_root_context(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist;

  void delete_space(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

  void remove_space(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

  void init_service()
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;

  void shutdown_service()
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed;


}
