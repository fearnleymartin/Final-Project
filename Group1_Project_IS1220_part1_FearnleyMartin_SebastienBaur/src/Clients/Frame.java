package Clients;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import treeImplementation.Node;
import treeImplementation.NotInTreeException;
import treeImplementation.ParentException;
import dataTypes.VirtualDisk;

public class Frame extends JFrame implements TreeSelectionListener, ActionListener, MouseListener, KeyListener{
	
//	CLUI.crvfs("vfsGUItest",1000);
	
	VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
	VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);
	
	
	
	JTree tree;
	TreePath treepath=null;

	
	
	
	public VirtualDisk getVd() {
		return vd;
	}

	public void setVd(VirtualDisk vd) {
		this.vd = vd;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	protected JEditorPane htmlPane = new JEditorPane();
	private JPanel panLeft = new JPanel();
	private JPanel panUpRight = new JPanel();
	private JPanel panDownRight = new JPanel();
	JScrollPane htmlView = new JScrollPane(htmlPane);
	
	//text fields
	JTextField renameTextField = new JTextField(13);
	JTextField copyTextField = new JTextField(20);
	JTextField createVFSTextField = new JTextField(20); // maybe add two others ones for size and location and name
	JTextField importFileStructureTextField = new JTextField(20); // maybe open a window of navigation AND maybe others text boxes
	JTextField exportVFSTextField = new JTextField(13);
	JTextField findTextField = new JTextField(20);
	
	
	private JButton buttonRename = new JButton("Rename");
	private JButton buttonCopy = new JButton("Copy");
	private JButton buttonPaste = new JButton("Paste");
	private JButton buttonRemoveVFS = new JButton("Remove VFS");
	private JButton buttonRemoveFile = new JButton("Remove file");
	private JButton buttonCreateVFS = new JButton("Create VFS");
	private JButton buttonImport = new JButton("Import file structure"); 
	private JButton buttonExport = new JButton("Export VFS");
	private JButton buttonFind = new JButton("Find");
	private JButton buttonHelp = new JButton("Help");

	
	
	public Frame() throws NotInTreeException{
		tree = TreeUtil.buildTreeFromVd(vd);
		VdcnManagement.getVdList().add(vdcn);
		
		
		JFrame frame = new JFrame();
		this.setTitle("VFS");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
	
		htmlPane.setEditable(false);
		
		// ajout des panneaux aux bonnes positions
		this.getContentPane().add(panLeft, BorderLayout.WEST);
		this.getContentPane().add(panUpRight, BorderLayout.CENTER);
		this.getContentPane().add(panDownRight, BorderLayout.SOUTH);
		
		panLeft.setLayout(new GridLayout(8,2));
		

		panLeft.add(buttonFind);
		panLeft.add(findTextField);
		
		panUpRight.add(tree);
		panDownRight.add(new JLabel("salut"));
		
		panLeft.add(buttonRename);
		panLeft.add(renameTextField);
		panLeft.add(buttonCopy);
		panLeft.add(copyTextField);
		panLeft.add(buttonExport);
		panLeft.add(exportVFSTextField);
		panLeft.add(buttonRemoveVFS);
		panLeft.add(buttonRemoveFile);
		panLeft.add(buttonImport);
		panLeft.add(importFileStructureTextField);
		panLeft.add(htmlPane);
		panUpRight.add(tree);
		panDownRight.add(new JLabel("salut"));
		
		tree.addTreeSelectionListener(new SelectionListener());
		buttonRename.addMouseListener(new RenameButtonListener());
		buttonCopy.addMouseListener(new CopyButtonListener());
		buttonRemoveVFS.addMouseListener(new RemoveVFSButtonListener());
		buttonRemoveFile.addMouseListener(new RemoveFileButtonListener());
		buttonCreateVFS.addMouseListener(new CreateVFSButtonListener());
		buttonImport.addMouseListener(new ImportButtonListener());
		buttonExport.addMouseListener(new ExportButtonListener());
		buttonFind.addMouseListener(new FindButtonListener());
		buttonHelp.addMouseListener(new HelpButtonListener());
		buttonImport.addMouseListener(new ImportButtonListener());
		
		this.setVisible(true);
		

	}

	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
//		treepath = e.getPath();
//		htmlPane.setText( TreeUtil.treePathToString(treepath));
}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class FindButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	class HelpButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ExportButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			String hostpath = exportVFSTextField.getText();
			if (!hostpath.equals("")&& hostpath!=null){
				CLUI.expvfs(vd.getName(), hostpath);
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	class ImportButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (treepath != null){
				panUpRight.remove(tree);
				String parent = TreeUtil.treePathToString(treepath);	
				String hostpath = importFileStructureTextField.getText();
				
				CLUI.impvfs(hostpath,"vdlevel1", parent);
				
				try {
					tree = TreeUtil.buildTreeFromVd(vd);
				} catch (NotInTreeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				panUpRight.add(tree);
				tree.addTreeSelectionListener(new SelectionListener());
				revalidate();	
				repaint();
		}
		else{
			System.out.println("No file/directory selected");
		}

		}
		
	}

	class CopyButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			
		}
			


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class CreateVFSButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	class RemoveVFSButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class RemoveFileButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (treepath != null){
					panUpRight.remove(tree);
					String oldPath = TreeUtil.treePathToString(treepath);	
					
					CLUI.rm("vdlevel1", oldPath);
					try {
						tree = TreeUtil.buildTreeFromVd(vd);
					} catch (NotInTreeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					panUpRight.add(tree);
					tree.addTreeSelectionListener(new SelectionListener());
					revalidate();
					repaint();
			}
			else{
				System.out.println("No file/directory selected");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class MoveButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class RenameButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (treepath != null){
				if ((!renameTextField.getText().equals("")) && (renameTextField.getText()!=null)){
					panUpRight.remove(tree);
					String oldPath = TreeUtil.treePathToString(treepath);	
					String newPath = renameTextField.getText();
					CLUI.mv("vdlevel1", oldPath, newPath);
					try {
						tree = TreeUtil.buildTreeFromVd(vd);
					} catch (NotInTreeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					panUpRight.add(tree);
					tree.addTreeSelectionListener(new SelectionListener());
					revalidate();
					repaint();
				}
				else {System.out.println("Please enter a new name for the file/directory");}
			}
			else{
				System.out.println("No file/directory selected");
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}

	class SelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent arg0) {
			treepath = arg0.getPath();
			htmlPane.setText( TreeUtil.treePathToString(treepath));
		}
		
	}
}
