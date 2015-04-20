package Clients;

import java.awt.Color; 
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.tree.TreePath;

import treeImplementation.NotInTreeException;
import dataTypes.VirtualDisk;
import Clients.*;

public class Fenetre extends JFrame{

VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);

private JTree tree;
private TreePath treepath;
private JTextField renameTextField = new JTextField(15);
//private JTextField removeFileTextField = new JTextField(20);
//private JTextField removeVFSTextField = new JTextField(20);
private JTextField createVFSTextField = new JTextField(15); // maybe add two others ones for size and location and name
private JTextField importFileStructureTextField = new JTextField(15); // maybe open a window of navigation AND maybe others text boxes
private JTextField exportVFSTextField = new JTextField(15);
private JTextField findTextField = new JTextField(15);

private JTextField commandLinePrinting = new JTextField(40);
private JTextField commandLineWriting = new JTextField(40);

private JButton buttonRename = new JButton("Rename");
private JButton buttonCopy = new JButton("Copy");
private JButton buttonPaste = new JButton("Paste");
private JButton buttonRemoveFile = new JButton("Remove file");
private JButton buttonRemoveVFS = new JButton("Remove VFS");
private JButton buttonCreateVFS = new JButton("Create VFS");
private JButton buttonImport = new JButton("Import file structure"); 
private JButton buttonExport = new JButton("Export VFS");
private JButton buttonFind = new JButton("Find");
private JButton buttonHelp = new JButton("Help");

// ne pas oublier le label qui affiche le free space
protected JEditorPane htmlPane = new JEditorPane();
private JPanel panLeft = new JPanel();
private JTabbedPane tabbedPanUpRight = new JTabbedPane();
private JPanel panUpRight = new JPanel();
private JPanel panDownRight = new JPanel();
//JScrollPane htmlView = new JScrollPane(htmlPane);







public Fenetre() throws NotInTreeException{
	this.setResizable(false);
	JTree tree = TreeUtil.buildTreeFromVd(vd);
	this.setTitle("Ma premi�re fen�tre Java");
	this.setSize(1000, 500);
	this.setLocationRelativeTo(null);               
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//On d�finit le layout � utiliser sur le content pane
	this.setLayout(new BorderLayout());

//	//Instanciation d'un objet JPanel
//	JPanel pan = new JPanel();
//	//D�finition de sa couleur de fond
//	pan.setBackground(Color.ORANGE);        
//	//On pr�vient notre JFrame que notre JPanel sera son content pane
//	this.setContentPane(pan);    

	// ajout des panneaux aux bonnes positions
	this.getContentPane().add(panLeft, BorderLayout.WEST);
	this.getContentPane().add(tabbedPanUpRight, BorderLayout.CENTER);
	this.getContentPane().add(panDownRight, BorderLayout.SOUTH);

	panLeft.setLayout(new GridLayout(10,2));	
	panLeft.add(buttonRename);
	panLeft.add(renameTextField);
	panLeft.add(buttonCreateVFS);
	panLeft.add(createVFSTextField);
	panLeft.add(buttonImport);
	panLeft.add(importFileStructureTextField);
	panLeft.add(buttonExport);
	panLeft.add(exportVFSTextField);
	panLeft.add(buttonFind);
	panLeft.add(findTextField);
	panLeft.add(buttonCopy);
	panLeft.add(new JPanel());
	panLeft.add(buttonPaste);
	panLeft.add(new JPanel());
	panLeft.add(buttonRemoveFile);
	panLeft.add(new JPanel());
	panLeft.add(buttonRemoveVFS);
	panLeft.add(new JPanel());
	panLeft.add(buttonHelp);
	
	panUpRight.add(tree);
	tabbedPanUpRight.addTab("vfs1",panUpRight);
	tabbedPanUpRight.addTab("vfs2",new JLabel("salut"));

	
    Box b = Box.createVerticalBox();
    Box lineWriting = Box.createHorizontalBox();
    lineWriting.add(new JLabel("Write here : "));
    lineWriting.add(commandLineWriting);
    b.add(lineWriting);
    Box lineReading = Box.createHorizontalBox();
    lineReading.add(new JLabel("Read here : "));
    lineReading.add(commandLinePrinting);
    b.add(lineReading);
	panDownRight.add(b);

	
	this.setVisible(true);

}       







}
