/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDNamingJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 1 $
* Date: $Date: 2009-03-20 11:28:08 +0100 (Fri, 20 Mar 2009) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2009 Telefonica Investigacion y Desarrollo
*     S.A.Unipersonal (Telefonica I+D)
*
* Info about members and contributors of the MORFEO project
* is available at:
*
*   http://www.morfeo-project.org/TIDNamingJ/CREDITS
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*
* If you want to use this software an plan to distribute a
* proprietary application in any way, and you are not licensing and
* distributing your source code under GPL, you probably need to
* purchase a commercial license of the product.  More info about
* licensing options is available at:
*
*   http://www.morfeo-project.org/TIDNamingJ/Licensing
*/

/*

 File: es.tid.corba.TIDNaming.ServiceManager.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming;

import java.io.FileOutputStream;
import java.io.FileInputStream;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

import es.tid.corba.TIDNamingAdmin.*;
import es.tid.corba.TIDNamingAdmin.AgentPackage.*;

import es.tid.TIDorbj.util.Trace;
import es.tid.TIDorbj.util.CircularTraceFile;

/**
 * TIDNaming Service Manager Library Object Implementation.
 * 
 */
 
public class ServiceManager implements AgentOperations {
 
    /** 
     * Service State: there is no space active.
     */
    public final static int NO_SPACES = 0;

    /** 
     * Service State: there is any bad initialized space.
     */
    public final static int BAD_SPACES = 1;

    /** 
     * Service State: the service is ready to start running.
     */
    public final static int CAN_RUN = 2;

    /** 
     * Service State: the service is running.
     */
    public final static int RUNNING = 3;

    /** 
     * Service State: the service has been shutdowned.
     */
    public final static int SHUTDOWNED = 4;
  
  
    /**
     * Active Naming Spaces.
     */
    private java.util.Hashtable spaces;
  
    /**
     * Service ORB.
     */
    private es.tid.TIDorbj.core.TIDORB orb;

    /**
     * Service ORB Root POA.
     */
    private POA rootPOA;
  
    /**
     * Service Log Output.
     */
    private Trace trace;

    /**
     * Close log on exit
     */
  
    private boolean close_trace_on_exit;
  
    /**
     * Service Properties.
     */
    private NamingProperties properties;
  
    /**
     * Naming Spaces configuration file.
     */
    private ConfFile conf_file;

    /**
     * Service status.
     */
    private int service_state;
  
    /**
     * Number of erroneous state Naming Spaces.
     */
    private int space_faults;
  
    /**
     * Serive Fault report.
     */
    private String _fault_reason;
  
    private ServiceManager(es.tid.TIDorbj.core.TIDORB orb) 
    {
        this.orb = orb;
        try {
            rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        } catch (org.omg.CORBA.ORBPackage.InvalidName iv) {
            if(trace != null)
                trace.print(Trace.ERROR,"Cannot find RootPOA in ORB");
        }
   
        spaces = new java.util.Hashtable();
    
        service_state = NO_SPACES;
    }
  
    private ServiceManager(es.tid.TIDorbj.core.TIDORB orb, NamingProperties props)
    {
        this(orb);    
        conf_file = new ConfFile(orb, props.spaces_file);    
        properties = props;
    }
  

    /**
     * There is a new fault in an loaded Naming Space.
     */
    private void faultAdded()
    {
        if(service_state != BAD_SPACES)
            service_state = BAD_SPACES;
      
        space_faults ++;
    
        _fault_reason = null;
    }

    /**
     * One fault in an loaded Naming Space has been solved.
     * @can_run if there is
     */
    private void faultRemoved()
    {
        space_faults--;
    
        if(space_faults == 0) {
            if(spaces.size() > 0)
                service_state = CAN_RUN;
            else
                service_state = NO_SPACES;
        } 
    
        _fault_reason = null;
    }
  
    public void setTrace(Trace trace, boolean close_on_exit)
    {
        this.trace = trace;
        this.close_trace_on_exit = close_on_exit;
    }

    /**
     * Service initialitation.
     */
    public static ServiceManager 
        init(es.tid.TIDorbj.core.TIDORB orb, 
             String[] args,
             java.util.Properties props)
        throws CannotProceed
    {
        NamingProperties n_props = new NamingProperties();
    
        n_props.parse(args, props);
    
        ServiceManager manager = new ServiceManager(orb, n_props);
    
        if(n_props.use_trace) {
            try {
                if(n_props.trace_file != null) {
                    
                    // TODO: provide TIDNaming arguments for CircularTraceFile options
                    CircularTraceFile ctf = 
                        new CircularTraceFile(orb.m_conf.DEFAULT_NUM_FILES,
                                              orb.m_conf.DEFAULT_FILE_SIZE,
                                              n_props.trace_file);

                    manager.setTrace(Trace.createTrace(ctf, "TIDNaming",
                                                       n_props.trace_level), true);
                    manager.trace.setIncludeDate(false);                                    
                } else {
                    manager.setTrace(Trace.createTrace(System.err, "TIDNaming",
                                                        n_props.trace_level), false);
                    manager.trace.setIncludeDate(false);                                    
                }
            } catch (java.io.IOException ioe) {
                throw new CannotProceed("Error opening trace: " + ioe.toString());
            }
        }

        manager.read_spaces();    
      
        return manager;
    }
 
    /**
     * Service initialitation.
     */
    public static ServiceManager 
        init(es.tid.TIDorbj.core.TIDORB orb, 
             String[] args,
             java.util.Properties props,
             Trace trace)
        throws CannotProceed
    {
        NamingProperties n_props = new NamingProperties();
    
        n_props.parse(args, props);
    
        ServiceManager manager = new ServiceManager(orb, n_props);
  
        manager.setTrace(trace, false);

        manager.read_spaces();    
      
        return manager;
    }

  
    public synchronized NamingContext 
        new_space(String space_name, NamingSpaceAccessData data)
        throws NamingSpaceAlreadyExist, InvalidAccessData, CannotProceed
    {
        if(service_state == SHUTDOWNED)
            throw new CannotProceed("Naming Service has been shutdowned.");

        if(!conf_file.canWrite())
            throw new CannotProceed("Can not change Service Configuration");
      
        if(spaces.containsKey(space_name))
            throw new NamingSpaceAlreadyExist();


        NamingSpaceServer space_server = new NamingSpaceServer(orb,rootPOA,properties);
        space_server.setTrace(trace);
    
        try {

            space_server.setSpace(new NamingSpace(space_name,null,data), true);

            space_server.setup_root_Context();

            spaces.put(space_name, space_server);
      
            write_spaces();
  
        } catch (CannotProceed cp) {
            if(trace != null) {
                String[] msg = {"ServiceManager::new_space: CannotProceed ", cp.why};
                trace.print(Trace.ERROR,msg);
            }
            try {
                spaces.remove(space_name);
                space_server.delete_space();
            } catch(Exception e) {};
            throw cp;
        }
      
        if(trace != null) {
            String[] msg = {"ServiceManager::new_space NamingSpace \"",
                            space_name,"\" created correctly "};
            trace.print(Trace.USER,msg); 
        }
    
        if(service_state == NO_SPACES)
            service_state = CAN_RUN;
    
        return space_server.getSpace().root_context;     
    }

    public synchronized NamingContext add_space(NamingSpace space)
        throws NamingSpaceAlreadyExist, InvalidAccessData, CannotProceed
    {
        if(service_state == SHUTDOWNED)
            throw new CannotProceed("Naming Service has been shutdowned.");

        if(conf_file == null)
            throw new CannotProceed("Can not change Service Configuration");
      
        return add_space(space, false);
    }

    private NamingContext add_space(NamingSpace space, boolean include_anyway)
        throws NamingSpaceAlreadyExist, InvalidAccessData, CannotProceed     
    {
        if(spaces.containsKey(space.name))
            throw new NamingSpaceAlreadyExist();
    
        NamingSpaceServer space_server = new NamingSpaceServer(orb, rootPOA, properties);
        space_server.setTrace(trace);
    
        try {      
            space_server.setSpace(space, false); 
      
            if(space.root_context == null) {
                space_server.setup_root_Context();
                spaces.put(space.name, space_server);
                write_spaces();
            } else {
                spaces.put(space.name, space_server);
            }

        } catch (CannotProceed cp) {      
            if(include_anyway) {
        
                faultAdded();
                spaces.put(space.name, space_server);
     
                if(trace != null) {
                    String[] msg = {"ServiceManager::add_space: CannotProceed ", cp.why};
                    trace.print(Trace.ERROR,msg);
                }
        
                return null;
            } else {
                throw cp;
            }      
        } 

        if(trace != null) {
            String[] msg = {"ServiceManager::add_space NamingSpace \"",
                            space.name,"\" add correctly"};
            trace.print(Trace.USER,msg); 
        }
    
        if(service_state == NO_SPACES)
            service_state = CAN_RUN;

        return space.root_context;     
    }
   
  
    public synchronized void update_space(String space_name, NamingSpaceAccessData data)
        throws NamingSpaceNotExist, InvalidAccessData, CannotProceed
    { 
        if(service_state == SHUTDOWNED)
            throw new CannotProceed("Naming Service has been shutdowned.");

        if(service_state == RUNNING)
            throw new CannotProceed("NamingService running, NamingSpace cannot been updated.");

        if(conf_file == null)
            throw new CannotProceed("Can not change Service Configuration");
    
        NamingSpaceServer server = (NamingSpaceServer) spaces.get(space_name);
            
        if(server == null)
            throw new NamingSpaceNotExist();
      
        server.update_space(data);
    
        write_spaces();

        if(trace != null) {
            String[] msg = {"ServiceManager::update_space NamingSpace \"",
                            space_name,"\" updated correctly"};
            trace.print(Trace.USER,msg); 
        }
    }

    public synchronized NamingSpace get_space(String space_name)
        throws NamingSpaceNotExist
    {
        NamingSpaceServer server = (NamingSpaceServer) spaces.get(space_name);
    
        if(server == null)
            throw new NamingSpaceNotExist();
    
        return server.getSpace();
    }

    public  void set_max_threads(java.lang.String space_name, int n)
        throws NamingSpaceNotExist, CannotProceed
    {    
        NamingSpaceServer server = (NamingSpaceServer) spaces.get(space_name);
    
        if(server == null)
            throw new NamingSpaceNotExist();
      
        server.set_max_threads(n);
    }
      

    public synchronized NamingSpace[] get_spaces()
    {
        int size = spaces.size();

        NamingSpace[] seq = new NamingSpace[size]; 
   
        if(size > 0) {
    
            java.util.Enumeration my_enum = spaces.elements();
    
            int i = 0;
    
            while(my_enum.hasMoreElements()) {
                seq[i++] = ((NamingSpaceServer)my_enum.nextElement()).getSpace();
            }
        }
    
        return seq;
    }
  

    public ServiceStatus get_status()
    {
        switch (service_state) {
        case NO_SPACES:      
            if(_fault_reason == null) {
                build_fault_reason();
            }
            return new ServiceStatus(ServiceState.cannot_run,_fault_reason);
        case BAD_SPACES:
            if(_fault_reason == null) {
                build_fault_reason();
            }
            return new ServiceStatus(ServiceState.cannot_run,_fault_reason);
        case CAN_RUN:
            return new ServiceStatus(ServiceState.can_run,"");
        case RUNNING:
            return new ServiceStatus(ServiceState.running,"");
        default: // case SHUTDOWNED:
            if(_fault_reason == null) {
                build_fault_reason();
            }
            return new ServiceStatus(ServiceState.shutdowned,_fault_reason);
        }
    }


    public synchronized NamingContext get_root_context(String space_name)
        throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist
    {
        return get_space(space_name).root_context;
    }

    public synchronized void delete_space(String space_name)
        throws NamingSpaceNotExist, CannotProceed
    {
        if(service_state == SHUTDOWNED)
            throw new CannotProceed("Naming Service has been shutdowned.");

        if(service_state == RUNNING)
            throw new CannotProceed("NamingService running, NamingSpace cannot been updated.");
      
        if(conf_file == null)
            throw new CannotProceed("Can not change Service Configuration");

        NamingSpaceServer server = (NamingSpaceServer) spaces.get(space_name);
    
        if(server == null)
            throw new NamingSpaceNotExist();
  
        try{
            spaces.remove(space_name);
            write_spaces();
      
            if(trace != null) {
                String[] msg = {"ServiceManager::delete_space NamingSpace \"",
                                space_name,"\" deleted correctly"};
                trace.print(Trace.USER,msg); 
            }
        } catch(CannotProceed cp) { //cannot write, roolback     
            spaces.put(space_name, server);
            throw cp;
        }

        if(server.getFault() == null)
            faultRemoved();
      
        server.delete_space();
    
        server.shutdown();
    }

    public void remove_space(String space_name)
        throws NamingSpaceNotExist, CannotProceed
    {
        if(service_state == SHUTDOWNED)
            throw new CannotProceed("Naming Service has been shutdowned.");

        if(service_state == RUNNING)
            throw new CannotProceed("NamingService running, NamingSpace cannot been updated.");

        if(conf_file == null)
            throw new CannotProceed("Can not change Service Configuration");

        NamingSpaceServer server = (NamingSpaceServer) spaces.get(space_name);
    
        if(server == null)
            throw new NamingSpaceNotExist();

        try{
            spaces.remove(space_name);
            write_spaces();
   
            if(trace != null) {
                String[] msg = {"ServiceManager::remove_space NamingSpace \"",
                                space_name,"\" removed correctly"};
                trace.print(Trace.USER,msg); 
            }
        } catch(CannotProceed cp) { //cannot write, roolback     
            spaces.put(space_name, server);
            throw cp;
        }

        server.close_space();
    
        if(server.getFault() == null)
            faultRemoved();

    }
  
    public synchronized void init_service() throws CannotProceed
    {
        if(service_state != CAN_RUN) {
            if(_fault_reason == null) {
                build_fault_reason();
            }
      
            throw new CannotProceed(_fault_reason);
        }
    
        NamingSpaceServer[] server_list = get_servers();
    
        for (int i = 0; i < server_list.length; i++)
            server_list[i].activate();

        service_state = RUNNING;
    }
     
  
    public synchronized void shutdown_service()  throws CannotProceed  
    {
        if(service_state == RUNNING) {
      
            NamingSpaceServer[] server_list = get_servers();
      
            for (int i = 0; i < server_list.length; i++)
                server_list[i].shutdown();
        } else if(service_state == SHUTDOWNED) {
            throw new CannotProceed("Naming Service shutdowned.");
        }

        service_state = SHUTDOWNED;
    
        if(trace != null) {
            trace.print(Trace.ERROR,"ServiceManager::shutdown_service");
            if(close_trace_on_exit) {
                trace.close();
                trace = null;
            }
        }

    }
  
  
    private void write_spaces() throws CannotProceed
    {
        conf_file.write_spaces(get_spaces());
    }
  
    private void read_spaces() 
        throws CannotProceed
    {
        NamingSpace[] space_list = conf_file.read_spaces();
    
        for (int i = 0; i < space_list.length; i++) {
            try {
                add_space(space_list[i], true);
            } catch(Exception ex) {/*unreachable*/}
        }
    }
  
    private NamingSpaceServer[] get_servers()
    {
        int size = spaces.size();

        NamingSpaceServer[] seq = new NamingSpaceServer[size]; 
   
        if(size > 0) {
    
            java.util.Enumeration my_enum = spaces.elements();
    
            int i = 0;
    
            while(my_enum.hasMoreElements()) {
                seq[i++] = (NamingSpaceServer)my_enum.nextElement();
            }
        }
    
        return seq;
    }

    private void build_fault_reason()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("TIDNaming Service cannot start due to:\n");
        switch (service_state) {
        case NO_SPACES:
            buffer.append("\tNo Naming Space active\n");
            break;
        case BAD_SPACES: 
            buffer.append("\tInvalid Naming Spaces:\n");
  
            java.util.Enumeration my_enum = spaces.elements();
        
            NamingSpaceServer server = null;
            String fault = null;
        
            while(my_enum.hasMoreElements()) {
                server = (NamingSpaceServer)my_enum.nextElement();
                fault = server.getFault();
                if(fault != null) {
                    buffer.append("\t\tNamingSpace: ");
                    buffer.append(server.getSpace().name);
                    buffer.append(" has error ");
                    buffer.append(fault);
                    buffer.append('\n');
                }          
            }
            break;
        case RUNNING:
            buffer.append("\tNaming Service still Running\n");
            break;
        case SHUTDOWNED:
            buffer.append("\tNaming Service shutdowned\n");
            break;
        }

        _fault_reason = buffer.toString();
    }

}
