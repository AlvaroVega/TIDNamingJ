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


public class BindWindow extends JFrame 
{

    NamingContext context; //contexto donde se va a hacer el bind
    NameComponent [] name;
    org.omg.CORBA.ORB orb;
    JLabel jl1 = new JLabel("Object name: ");
    JTextField jtfobjectname = new JTextField();
    JLabel jl2 = new JLabel("Object IOR:");
    JTextArea jta1 = new JTextArea();
    JButton jbaccept = new JButton("Accept");
    JButton jbcancel = new JButton("Cancel");

    public BindWindow(NamingContext context, org.omg.CORBA.ORB orb) 
    {
  
  	this.context = context;
	this.orb = orb;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension d = tk.getScreenSize();
        this.setSize(390,300);
        this.setLocation((d.width-390)/2,(d.height-300)/2);
        this.setResizable(false);
        this.setTitle("Bind Window");
        jl1.setBounds(new Rectangle(20, 30, 85, 21));
        jl2.setBounds(new Rectangle(20, 65, 85, 21));
        jtfobjectname.setBounds(new Rectangle(110, 30, 200, 21));
        jta1.setLineWrap(true);
        jta1.setBounds(new Rectangle(20, 90, 335, 115));
        jbaccept.setBounds(new Rectangle(65, 225, 79, 27));
        jbcancel.setBounds(new Rectangle(220, 225, 79, 27));
	this.getContentPane().setLayout(null);
        this.getContentPane().add(jta1, null);
        this.getContentPane().add(jbaccept, null);
        this.getContentPane().add(jbcancel, null);
        this.getContentPane().add(jtfobjectname, null);
        this.getContentPane().add(jl1, null);
        this.getContentPane().add(jl2, null);
        jbaccept.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    acceptBind();
                }
            });
        jbcancel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    cancelBind();
                }
            });
	this.show();
	
    }//contructor

    void acceptBind()
    {

        String aux="";
    
	try {
            aux = jtfobjectname.getText();
            name = TreatName.to_name(aux);
            context.bind(name,orb.string_to_object(jta1.getText()));
            this.hide();
            this.dispose();
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST ex0) {
            JOptionPane.showMessageDialog(null,"OBJECT_NOT_EXIST Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1) {
            JOptionPane.showMessageDialog(null,"NotFound Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2)             {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex3) {
            JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.AlreadyBound ex4) {
            JOptionPane.showMessageDialog(null,"AlreadyBound Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.Exception exg) {
            JOptionPane.showMessageDialog(null,exg.getMessage(),"corba error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    } //acceptBind


    void cancelBind() 
    {
        this.hide();
        this.dispose();
    } //cancelBind


} //class
