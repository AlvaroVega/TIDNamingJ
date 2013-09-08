//
// _AgentStub.java (stub)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

public class _AgentStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements Agent {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:tid.es/TIDNamingAdmin/Agent:1.0"  };

  public org.omg.CosNaming.NamingContext new_space(java.lang.String space_name, es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist, es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    org.omg.CORBA.Request _request = this._request("new_space");
    _request.set_return_type(org.omg.CosNaming.NamingContextHelper.type());
    org.omg.CORBA.Any $space_name = _request.add_named_in_arg("space_name");
    $space_name.insert_string(space_name);
    org.omg.CORBA.Any $data = _request.add_named_in_arg("data");
    es.tid.corba.TIDNamingAdmin.NamingSpaceAccessDataHelper.insert($data,data);
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExistHelper.type());
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.type());
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExistHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExistHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CosNaming.NamingContext _result;
    _result = org.omg.CosNaming.NamingContextHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CosNaming.NamingContext add_space(es.tid.corba.TIDNamingAdmin.NamingSpace space)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist, es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    org.omg.CORBA.Request _request = this._request("add_space");
    _request.set_return_type(org.omg.CosNaming.NamingContextHelper.type());
    org.omg.CORBA.Any $space = _request.add_named_in_arg("space");
    es.tid.corba.TIDNamingAdmin.NamingSpaceHelper.insert($space,space);
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExistHelper.type());
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.type());
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExistHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExistHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CosNaming.NamingContext _result;
    _result = org.omg.CosNaming.NamingContextHelper.extract(_request.return_value());
    return _result;
  }

  public void update_space(java.lang.String space_name, es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    org.omg.CORBA.Request _request = this._request("update_space");
    org.omg.CORBA.Any $space_name = _request.add_named_in_arg("space_name");
    $space_name.insert_string(space_name);
    org.omg.CORBA.Any $data = _request.add_named_in_arg("data");
    es.tid.corba.TIDNamingAdmin.NamingSpaceAccessDataHelper.insert($data,data);
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type());
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.type());
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessDataHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public es.tid.corba.TIDNamingAdmin.NamingSpace get_space(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist {
    org.omg.CORBA.Request _request = this._request("get_space");
    _request.set_return_type(es.tid.corba.TIDNamingAdmin.NamingSpaceHelper.type());
    org.omg.CORBA.Any $space_name = _request.add_named_in_arg("space_name");
    $space_name.insert_string(space_name);
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    es.tid.corba.TIDNamingAdmin.NamingSpace _result;
    _result = es.tid.corba.TIDNamingAdmin.NamingSpaceHelper.extract(_request.return_value());
    return _result;
  }

  public es.tid.corba.TIDNamingAdmin.NamingSpace[] get_spaces() {
    org.omg.CORBA.Request _request = this._request("get_spaces");
    _request.set_return_type(es.tid.corba.TIDNamingAdmin.NamingSpaceSeqHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    es.tid.corba.TIDNamingAdmin.NamingSpace[] _result;
    _result = es.tid.corba.TIDNamingAdmin.NamingSpaceSeqHelper.extract(_request.return_value());
    return _result;
  }

  public es.tid.corba.TIDNamingAdmin.ServiceStatus get_status() {
    org.omg.CORBA.Request _request = this._request("get_status");
    _request.set_return_type(es.tid.corba.TIDNamingAdmin.ServiceStatusHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    es.tid.corba.TIDNamingAdmin.ServiceStatus _result;
    _result = es.tid.corba.TIDNamingAdmin.ServiceStatusHelper.extract(_request.return_value());
    return _result;
  }

  public void set_max_threads(java.lang.String space_name, int n)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    org.omg.CORBA.Request _request = this._request("set_max_threads");
    org.omg.CORBA.Any $space_name = _request.add_named_in_arg("space_name");
    $space_name.insert_string(space_name);
    org.omg.CORBA.Any $n = _request.add_named_in_arg("n");
    $n.insert_ulong(n);
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type());
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public org.omg.CosNaming.NamingContext get_root_context(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist {
    org.omg.CORBA.Request _request = this._request("get_root_context");
    _request.set_return_type(org.omg.CosNaming.NamingContextHelper.type());
    org.omg.CORBA.Any $space_name = _request.add_named_in_arg("space_name");
    $space_name.insert_string(space_name);
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CosNaming.NamingContext _result;
    _result = org.omg.CosNaming.NamingContextHelper.extract(_request.return_value());
    return _result;
  }

  public void delete_space(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    org.omg.CORBA.Request _request = this._request("delete_space");
    org.omg.CORBA.Any $space_name = _request.add_named_in_arg("space_name");
    $space_name.insert_string(space_name);
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type());
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public void remove_space(java.lang.String space_name)
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist, es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    org.omg.CORBA.Request _request = this._request("remove_space");
    org.omg.CORBA.Any $space_name = _request.add_named_in_arg("space_name");
    $space_name.insert_string(space_name);
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type());
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExistHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public void init_service()
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    org.omg.CORBA.Request _request = this._request("init_service");
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public void shutdown_service()
    throws es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed {
    org.omg.CORBA.Request _request = this._request("shutdown_service");
    _request.exceptions().add(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type())) {
          throw es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }


}
