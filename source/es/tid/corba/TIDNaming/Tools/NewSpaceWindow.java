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


public class NewSpaceWindow extends JFrame {

    String spacename;
    Agent agent;
    NamingSpaceAccessData data = new NamingSpaceAccessData();
    JLabel jl1 = new JLabel("Name Space to create: ");
    JLabel jl2 = new JLabel("User name: ");
    JLabel jl3 = new JLabel("Table name: ");
    JLabel jl4 = new JLabel("Password: ");
    JLabel jl5 = new JLabel("Password: ");
    JLabel jl6 = new JLabel("Init sequence: ");
    JButton jbaccept = new JButton("Accept");
    JButton jbcancel = new JButton("Cancel");
    JTextField jtf_namespace = new JTextField();
    JTextField jtf_username = new JTextField();
    JTextField jtf_tablename = new JTextField();
    JTextField jtf_initseq = new JTextField();
    JPasswordField jpf1 = new JPasswordField();
    JPasswordField jpf2 = new JPasswordField();


    public NewSpaceWindow(Agent agent) 
    {

	this.agent = agent;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension d = tk.getScreenSize();
        this.setSize(400,325);
        this.setLocation((d.width-400)/2,(d.height-325)/2);
        this.setResizable(false);
        this.setTitle("Create New Space Window");
        jl1.setBounds(new Rectangle(13, 27, 181, 21));
        jl2.setBounds(new Rectangle(13, 63, 126, 21));
        jl3.setBounds(new Rectangle(13, 99, 117, 21));
        jl4.setBounds(new Rectangle(13, 135, 75, 21));
        jl5.setBounds(new Rectangle(13, 171, 77, 21));
        jl6.setBounds(new Rectangle(13, 207, 118, 21));
        jtf_namespace.setBounds(new Rectangle(155, 27, 180, 21));
        jtf_username.setBounds(new Rectangle(155, 63, 180, 21));
        jtf_tablename.setBounds(new Rectangle(155, 99, 180, 21));
        jpf1.setBounds(new Rectangle(155, 135, 180, 21));
        jpf2.setBounds(new Rectangle(155, 171, 180, 21));
        jtf_initseq.setBounds(new Rectangle(155, 207, 180, 21));
        jbaccept.setBounds(new Rectangle(83, 245, 79, 27));
        jbcancel.setBounds(new Rectangle(200, 245, 79, 27));
	this.getContentPane().setLayout(null);
        this.getContentPane().add(jl1);
        this.getContentPane().add(jtf_namespace);
        this.getContentPane().add(jl2);
        this.getContentPane().add(jl3);
        this.getContentPane().add(jl4);
        this.getContentPane().add(jtf_tablename);
        this.getContentPane().add(jtf_username);
        this.getContentPane().add(jpf1);
        this.getContentPane().add(jl5);
        this.getContentPane().add(jpf2);
        this.getContentPane().add(jl6);
        this.getContentPane().add(jtf_initseq);
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


    private void acceptCreate () {
  
        String user,passwd, table, seq;
        SQL_AccessData  sql_access;
      
        try 
            {
                this.spacename = jtf_namespace.getText();
                user = jtf_username.getText();
                table = jtf_tablename.getText();
                seq = jtf_initseq.getText(); //Mirar los driver para rellenarlo
                if (String.valueOf(jpf1.getPassword()).equals(String.valueOf(jpf2.getPassword())))
                    {
                        passwd = String.valueOf(jpf1.getPassword());	  
                        sql_access = new SQL_AccessData(user,passwd,table,seq);
                        data.sql_data(sql_access);
                        agent.new_space(spacename, data);
                        this.hide();
                        this.dispose();
                    }
                else
                    JOptionPane.showMessageDialog(null,"The passwords don't match","security error",JOptionPane.ERROR_MESSAGE);
            }
        catch (es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceAlreadyExist ex1)
            {
		JOptionPane.showMessageDialog(null,"NamingSpaceAlreadyExist Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
	catch (es.tid.corba.TIDNamingAdmin.AgentPackage.InvalidAccessData ex2)
            {
		JOptionPane.showMessageDialog(null,"InvalidAccesData Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
  	catch (es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed ex3)
            {
		JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
		
            }
	catch (java.lang.Exception exg)
            {
                JOptionPane.showMessageDialog(null,exg.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
            }
    
    } //acceptCreate


    private void cancelCreate () {

        this.hide();
        this.dispose();
	
    } //cancelCreate

} //class
