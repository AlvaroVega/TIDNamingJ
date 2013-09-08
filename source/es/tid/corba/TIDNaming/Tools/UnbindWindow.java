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
import org.omg.CosNaming.*;


public class UnbindWindow extends JFrame {

    NamingContext context; //es el contexto en el que esta el elemento a borrar
    NameComponent [] name; //identifica el elemento a borrar
    JLabel jl1 = new JLabel("Element name:");
    JTextField jtfobjectname = new JTextField();
    JButton jbaccept = new JButton("Accept");
    JButton jbcancel = new JButton("Cancel");

    public UnbindWindow(NamingContext context, NameComponent[] name) 
    {
  
	this.context = context;
	this.name = name;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension d = tk.getScreenSize();
        this.setSize(390,175);
        this.setLocation((d.width-390)/2,(d.height-175)/2);
        this.setResizable(false);
        this.setTitle("Unbind Window");
        jl1.setBounds(new Rectangle(30, 40, 100, 21));
        jtfobjectname.setEditable(false);
        jtfobjectname.setBounds(new Rectangle(135, 40, 160, 21));
	jtfobjectname.setText(name[0].id + "." + name[0].kind);
        jbaccept.setBounds(new Rectangle(80, 100, 79, 27));
        jbcancel.setBounds(new Rectangle(220, 100, 79, 27)); 
        this.getContentPane().setLayout(null);
        this.getContentPane().add(jtfobjectname, null);
        this.getContentPane().add(jl1, null);
        this.getContentPane().add(jbaccept, null);
        this.getContentPane().add(jbcancel, null);
        jbaccept.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    acceptUnbind();
                }
            });
        jbcancel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    cancelUnbind();
                }
            });
	this.show();
	
    } //constructor

  
    private void acceptUnbind() 
    {

        try {
            context.unbind(name);
            this.hide();
            this.dispose(); 
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST ex0) {
            JOptionPane.showMessageDialog(null,"OBJECT_NOT_EXIST Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1) {
            JOptionPane.showMessageDialog(null,"NotFound Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex3) {
            JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.Exception exg) {
            JOptionPane.showMessageDialog(null,exg.getMessage(),
                                          "error",JOptionPane.ERROR_MESSAGE);
        }
	
    } //acceptUnbind


    private void cancelUnbind() 
    {
        this.hide();
        this.dispose();
    } //cancelUnbind


}//class
