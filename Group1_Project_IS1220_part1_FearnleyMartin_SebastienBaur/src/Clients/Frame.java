package Clients;

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
import dataTypes.VirtualDisk;

public class Frame extends JFrame implements TreeSelectionListener, ActionListener, MouseListener, KeyListener{
	
//	CLUI.crvfs("vfsGUItest",1000);
	
	VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
	VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);
	
	
	
	JTree tree;
	TreePath treepath=null;
	JTextField textField = new JTextField(20);
	
	
	
	
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
	
	
	private JButton buttonRename = new JButton("Rename");
	private JButton buttonCopy = new JButton("Copy");
	private JButton buttonMove = new JButton("Move");
	private JButton buttonRemoveVFS = new JButton("Remove VFS");
	
	
	
	
	public Frame() throws NotInTreeException{
		tree = TreeUtil.buildTreeFromVd(vd);
		VdcnManagement.getVdList().add(vdcn);
		
		
		JFrame frame = new JFrame();
		this.setTitle("VFS");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(0,2));
	
		htmlPane.setEditable(false);
		
		this.getContentPane().add(panLeft);
		this.getContentPane().add(panUpRight);
		panLeft.add(textField);
		panLeft.add(buttonRename);
		panLeft.add(buttonCopy);
		panLeft.add(buttonMove);
		panLeft.add(buttonRemoveVFS);
		panLeft.add(htmlPane);
		panUpRight.add(tree);
		panDownRight.add(new JLabel("salut"));
		
		tree.addTreeSelectionListener(new SelectionListener());
		textField.addKeyListener(this);
		buttonRename.addMouseListener(new RenameButtonListener());
		
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
	
	
	
	class CopyButtonListener implements MouseListener{

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
				if ((!textField.getText().equals("")) || (textField.getText()==null)){
					panUpRight.remove(tree);
					String oldPath = TreeUtil.treePathToString(treepath);	
					String newPath = textField.getText();
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
