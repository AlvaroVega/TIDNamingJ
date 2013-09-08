//
// AgentPOA.java (skeleton)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

abstract public class AgentPOA
 extends org.omg.PortableServer.DynamicImplementation
 implements AgentOperations {

  public Agent _this() {
    return AgentHelper.narrow(super._this_object());
  };

  public Agent _this(org.omg.CORBA.ORB orb) {
    return AgentHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:tid.es/TIDNamingAdmin/Agent:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("new_space", new Integer(0));
    _methods.put("add_space", new Integer(1));
    _methods.put("update_space", new Integer(2));
    _methods.put("get_space", new Integer(3));
    _methods.put("get_spaces", new Integer(4));
    _methods.put("get_status", new Integer(5));
    _methods.put("set_max_threads", new Integer(6));
    _methods.put("get_root_context", new Integer(7));
    _methods.put("delete_space", new Integer(8));
    _methods.put("remove_space", new Integer(9));
    _methods.put("init_service", new Integer(10));
    _methods.put("shutdown_service", new Integer(11));
  }

  public void invoke(org.omg.CORBA.ServerRequest _request) {
    java.lang.Object _method = _methods.get(_request.operation());
    if (_method == null) {
      throw new org.omg.CORBA.BAD_OPERATION(_request.operation());
    }
    int _method_id = ((java.lang.Integer)_method).intValue();
    switch(_method_id) {
    case 0: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $space_name = _orb().create_any();
      $space_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("space_name", $space_name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $data = _orb().create_any();
      $data.type(es.tid.corba.TIDNamingAdmin.NamingSpaceAccessDataHelper.type());
      _params.add_value("data", $data, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String space_name;
      space_name = $space_name.extract_string();
      es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data;
      data = es.tid.corba.TIDNamingAdmin.NamingSpaceAccessDataHelper.extract($data);
      org.omg.CosNaming.NamingContext _result = this.new_space(space_name, data);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CosNaming.NamingContextHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExistHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 1: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $space = _orb().create_any();
      $space.type(es.tid.corba.TIDNamingAdmin.NamingSpaceHelper.type());
      _params.add_value("space", $space, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      es.tid.corba.TIDNamingAdmin.NamingSpace space;
      space = es.tid.corba.TIDNamingAdmin.NamingSpaceHelper.extract($space);
      org.omg.CosNaming.NamingContext _result = this.add_space(space);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CosNaming.NamingContextHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExistHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 2: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $space_name = _orb().create_any();
      $space_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("space_name", $space_name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $data = _orb().create_any();
      $data.type(es.tid.corba.TIDNamingAdmin.NamingSpaceAccessDataHelper.type());
      _params.add_value("data", $data, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String space_name;
      space_name = $space_name.extract_string();
      es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data;
      data = es.tid.corba.TIDNamingAdmin.NamingSpaceAccessDataHelper.extract($data);
      this.update_space(space_name, data);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 3: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $space_name = _orb().create_any();
      $space_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("space_name", $space_name, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String space_name;
      space_name = $space_name.extract_string();
      es.tid.corba.TIDNamingAdmin.NamingSpace _result = this.get_space(space_name);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      es.tid.corba.TIDNamingAdmin.NamingSpaceHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 4: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      es.tid.corba.TIDNamingAdmin.NamingSpace[] _result = this.get_spaces();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      es.tid.corba.TIDNamingAdmin.NamingSpaceSeqHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 5: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      es.tid.corba.TIDNamingAdmin.ServiceStatus _result = this.get_status();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      es.tid.corba.TIDNamingAdmin.ServiceStatusHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 6: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $space_name = _orb().create_any();
      $space_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("space_name", $space_name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $n = _orb().create_any();
      $n.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong));
      _params.add_value("n", $n, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String space_name;
      space_name = $space_name.extract_string();
      int n;
      n = $n.extract_ulong();
      this.set_max_threads(space_name, n);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 7: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $space_name = _orb().create_any();
      $space_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("space_name", $space_name, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String space_name;
      space_name = $space_name.extract_string();
      org.omg.CosNaming.NamingContext _result = this.get_root_context(space_name);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CosNaming.NamingContextHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 8: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $space_name = _orb().create_any();
      $space_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("space_name", $space_name, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String space_name;
      space_name = $space_name.extract_string();
      this.delete_space(space_name);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 9: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $space_name = _orb().create_any();
      $space_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("space_name", $space_name, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String space_name;
      space_name = $space_name.extract_string();
      this.remove_space(space_name);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 10: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      this.init_service();
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 11: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      this.shutdown_service();
      } catch(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    }
  }
}
