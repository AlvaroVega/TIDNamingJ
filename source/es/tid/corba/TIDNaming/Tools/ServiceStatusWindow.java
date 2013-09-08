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


public class ServiceStatusWindow extends JFrame {

    Agent agent;
    JLabel jl1 = new JLabel("Service status:");
    JLabel jl2 = new JLabel("Message");
    JTextField jtf_status = new JTextField();
    JTextArea jta_message = new JTextArea();
    JButton jbaccept = new JButton("Accept");

    public ServiceStatusWindow(Agent agent) 
    {
  
        this.agent = agent;
	Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        this.setSize(390,300);
        this.setLocation((d.width-390)/2,(d.height-300)/2);
        this.setResizable(false);
        this.setTitle("Get Service Status Window");
        jl1.setBounds(new Rectangle(40, 76, 125, 21));
        jl2.setBounds(new Rectangle(40, 120, 65, 21));
        jta_message.setBounds(new Rectangle(95, 116, 225, 112));
        jta_message.setEditable(false);
        jtf_status.setBounds(new Rectangle(163, 77, 154, 23));
        jtf_status.setEditable(false);
        jbaccept.setBounds(new Rectangle(149, 244, 79, 27));
        this.getContentPane().setLayout(null);
        this.getContentPane().add(jl1);
        this.getContentPane().add(jtf_status);
        this.getContentPane().add(jl2);
        this.getContentPane().add(jta_message);
        this.getContentPane().add(jbaccept);
        fill_status();
        jta_message.setText(agent.get_status().msgs);
        jbaccept.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    accept();
                }
            });
	this.show();
	
    }//constructor


    private void fill_status ()
    {

        int valor_estado;

        valor_estado = agent.get_status().state.value();
        if (valor_estado ==0)
            jtf_status.setText("cannot_run");
        else if (valor_estado ==1)
            jtf_status.setText("can_run");
        else if (valor_estado ==2)
            jtf_status.setText("running");
        else if (valor_estado ==3)
            jtf_status.setText("shutdowned");
        else
            jtf_status.setText("NO PUEDE SER");
	  
    } //fill_status
  
  
    private void accept () 
    {
        this.hide();
        this.dispose();
    } //accept


} //class
