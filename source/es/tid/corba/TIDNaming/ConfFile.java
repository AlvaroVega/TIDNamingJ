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

 File: es.tid.corba.TIDNaming.ConfFile.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming;

import org.omg.CORBA.Any;
import org.omg.IOP.Codec;
import org.omg.IOP.CodecFactory;
import org.omg.IOP.CodecFactoryHelper;
import org.omg.IOP.Encoding;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;

import es.tid.corba.TIDNamingAdmin.*;
import es.tid.corba.TIDNamingAdmin.AgentPackage.*;


/**
 * TIDNaming Service Configuration File Management class.
 * @author Juan A. C&aacute;ceres
 * @version 1.0
 */
class ConfFile {
  
  /**
   * Configuration File.
   */
  private java.io.File file;
  
  /**
   * Service ORB.
   */   
  private es.tid.TIDorbj.core.TIDORB orb;
  
  /**
   * Any to byte[] Codec.
   */  
  private Codec codec;
  
  /**
   * The configuration file can be written.
   */
  private boolean canWrite;
  
  /**
   * The configuration file can be read.
   */
  private boolean canRead;
  
  /**
   * Constructor.
   * @param orb the Server ORB.
   * @param path configuration file path.
   */
   
  public ConfFile(es.tid.TIDorbj.core.TIDORB orb, String path)
  {
    this.orb = orb;
    file = new java.io.File(path);
  }
  
  /**
   * Gets the Codec reference from the ORB.
   */
  private void init_codec() throws CannotProceed
  {
    try {
    CodecFactory factory = 
      CodecFactoryHelper.narrow(orb.resolve_initial_references("CodecFactory"));
    
    Encoding coding = 
      new Encoding(org.omg.IOP.ENCODING_CDR_ENCAPS.value,(byte)1,(byte)2);
    
    codec = factory.create_codec(coding);
    } catch (Throwable th) {
      throw new CannotProceed(th.toString());
    }
  }
  
  /**
   * Reads the NamingSpace configuration data.
   */
  public NamingSpace[] read_spaces() throws CannotProceed 
  {      
    if(!file.exists())
      return TIDNamingConstants.NULL_NAMING_SPACE_LIST;
    
    if(!file.canRead())
      throw new CannotProceed("Can read config file: " + file.getAbsolutePath());
     
    if(codec == null)
      init_codec();
      
    NamingSpace[] space_list = null;

    try {
      
      BufferedReader reader = new BufferedReader((new FileReader(file)));
      
      String str_data = reader.readLine();
      
      reader.close();
     
      if (str_data != null) {
        byte[] buffer = 
          es.tid.TIDorbj.util.Base64Codec.decode(str_data);
        
        Any any = codec.decode_value(buffer,NamingSpaceSeqHelper.type());
        
        space_list = NamingSpaceSeqHelper.extract(any);
      }      
    } catch (Throwable th) {
      throw new CannotProceed(th.toString());
    }
    
    if (space_list == null)
      throw new CannotProceed("Cannot read Configuration file");
    
    return space_list;    
  }

  /**
   * Writes the NamingSpace configuration data.
   */
  public void write_spaces(NamingSpace[] spaces) throws CannotProceed
  {
        
    if(codec == null)
      init_codec();

    try{
      
      Any any = orb.create_any();
     
      NamingSpaceSeqHelper.insert(any, spaces);

      byte[] buffer = codec.encode_value(any);

//      File temp_file = File.createTempFile("ncfg","tmp");

      PrintWriter writer = new PrintWriter(new FileWriter(file));

      String str_buff = es.tid.TIDorbj.util.Base64Codec.encode(buffer);
      
      writer.println(str_buff);
     
      writer.close();
      
   //   temp_file.renameTo(file);
      
    } catch(java.io.IOException ioe) {
      StringBuffer buffer = new StringBuffer();
      buffer.append("Cannot write config file ");
      buffer.append(file.getAbsolutePath());
      buffer.append(" due to ");
      buffer.append(ioe.toString());
      throw new CannotProceed(buffer.toString());
    } catch (Throwable th) {
      th.printStackTrace();
      throw new CannotProceed(th.toString());
    }
  }

  
  public boolean canWrite()
  {
    if(!file.exists()) {
      try{      
        FileWriter file_output = new FileWriter(file);
        file_output.close();
        return true;
      } catch(java.io.IOException ioe) {
        return false;
      }
    }     
    return file.canWrite();  
  }
}
