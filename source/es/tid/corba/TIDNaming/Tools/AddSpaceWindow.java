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

package es.tid.corba.TIDNaming.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import es.tid.corba.TIDNamingAdmin.*;
import org.omg.CosNaming.*;


public class AddSpaceWindow extends JFrame {


    NamingSpace space;
    Agent agent;
    org.omg.CORBA.ORB orb;
    NamingSpaceAccessData data;
    NamingContext root_context;
    JLabel jl1 = new JLabel("Name Space to create:");
    JLabel jl2 = new JLabel("User name:");
    JLabel jl3 = new JLabel("Table name:");
    JLabel jl4 = new JLabel("Password:");
    JLabel jl5 = new JLabel("Password:");
    JLabel jl6 = new JLabel("Init sequence:");
    JLabel jl7 = new JLabel("Root context IOR:");
    JTextField jtf_namespace = new JTextField();
    JButton jbaccept = new JButton("Accept");
    JButton jbcancel = new JButton("Cancel");
    JTextField jtf_username = new JTextField();
    JTextField jtf_tablename = new JTextField();
    JPasswordField jpf1 = new JPasswordField();
    JPasswordField jpf2 = new JPasswordField();
    JTextField jtf_initseq = new JTextField();
    JTextField jtf_ior = new JTextField();

    public AddSpaceWindow  (Agent agent, org.omg.CORBA.ORB orb) 
    {
 
  	this.agent = agent;  
	this.orb = orb;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension d = tk.getScreenSize();
        this.setSize(400,325);
        this.setLocation((d.width-400)/2,(d.height-325)/2);
        this.setResizable(false);
        this.setTitle("Add Space Window");
	jl1.setBounds(new Rectangle(14, 27, 181, 21));
	jl2.setBounds(new Rectangle(14, 63, 126, 21));
	jl3.setBounds(new Rectangle(14, 94, 117, 21));
	jl4.setBounds(new Rectangle(14, 123, 75, 21));
	jl5.setBounds(new Rectangle(14, 148, 77, 21));
	jl6.setBounds(new Rectangle(14, 176, 118, 21));
	jl7.setBounds(new Rectangle(14, 210, 161, 21));
        jtf_namespace.setBounds(new Rectangle(190, 32, 145, 19));
        jtf_username.setBounds(new Rectangle(190, 63, 145, 21));
        jtf_tablename.setBounds(new Rectangle(190, 96, 144, 21));
        jpf1.setBounds(new Rectangle(190, 121, 145, 21));
        jpf2.setBounds(new Rectangle(191, 151, 143, 21));
        jtf_initseq.setBounds(new Rectangle(190, 178, 145, 19));
        jtf_ior.setBounds(new Rectangle(190, 210, 145, 19));
        jbaccept.setBounds(new Rectangle(70, 249, 79, 27));
        jbcancel.setBounds(new Rectangle(222, 246, 90, 30));
        this.getContentPane().setLayout(null);
	this.getContentPane().add(jl1);
	this.getContentPane().add(jtf_namespace);
        this.getContentPane().add(jl2);
        this.getContentPane().add(jl3);
	this.getContentPane().add(jl4);
	this.getContentPane().add(jl5);
	this.getContentPane().add(jl6);
        this.getContentPane().add(jl7);
	this.getContentPane().add(jtf_tablename);
	this.getContentPane().add(jtf_username);
	this.getContentPane().add(jpf1);
	this.getContentPane().add(jpf2);
	this.getContentPane().add(jtf_initseq);
	this.getContentPane().add(jtf_ior);
	this.getContentPane().add(jbaccept);
	this.getContentPane().add(jbcancel);
        jbaccept.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    acceptCreate();
                }
            });
        jbcancel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    cancelCreate();
                }
            });
	this.show();
	
    }//constructor

    private void acceptCreate () 
    {
  	String user,passwd, table, seq;
	SQL_AccessData  sql_access;

        try {
            root_context = NamingContextHelper.narrow(orb.string_to_object(jtf_ior.getText()));
            this.space.name = jtf_namespace.getText();
            user = jtf_username.getText();
            table = jtf_tablename.getText();
            seq = jtf_initseq.getText();
            if (String.valueOf(jpf1.getPassword()).equals(String.valueOf(jpf2.getPassword()))) {
                passwd = String.valueOf(jpf1.getPassword());
                sql_access = new SQL_AccessData(user,passwd,table,seq);
                data.sql_data(sql_access);
                agent.add_space(space);
                this.hide();
                this.dispose();
            }
            else
                JOptionPane.showMessageDialog(null,"The passwords don't match","security error",
                                              JOptionPane.ERROR_MESSAGE);
        } catch (es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist ex1) {
            JOptionPane.showMessageDialog(null,"NamingSpaceAlreadyExist Exception has been raised","corba error",
                                          JOptionPane.ERROR_MESSAGE);
        } catch (es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData ex2) {
            JOptionPane.showMessageDialog(null,"InvalidAccesData Exception has been raised","corba error",
                                          JOptionPane.ERROR_MESSAGE);
        } catch (es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed ex3) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised","corba error",
                                          JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.Exception exg) {
            JOptionPane.showMessageDialog(null,exg.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
        }
	
    } //acceptCreate
    
    
    private void cancelCreate () 
    {
        this.hide();
        this.dispose();
    } //cancelCreate

} //class
