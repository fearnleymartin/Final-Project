package Clients;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import treeImplementation.Edge;
import treeImplementation.Node;
import treeImplementation.NotInTreeException;
import treeImplementation.ParentException;
import treeImplementation.Tree;
import dataTypes.VirtualDisk;

public class Frame extends JFrame implements TreeSelectionListener, ActionListener, MouseListener, KeyListener{

	//	CLUI.crvfs("vfsGUItest",1000);

	VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
	VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);


	JTree tree;
	TreePath treepath=null;
	Tree tempTree=null;
	Node tempNode=null;

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

	private JTextField commandLinePrinting = new JTextField(20);
	private JEditorPane commandLineWriting = new JEditorPane();
	//	protected JEditorPane htmlPane = new JEditorPane();
	private JPanel panLeft = new JPanel();
	private JPanel panUpRight = new JPanel();
	private JPanel panDownRight = new JPanel();
	private JTabbedPane tabbedPanUpRight = new JTabbedPane();
	JTextArea htmlView = new JTextArea();

	//text fields
	JTextField renameTextField = new JTextField(13);
	JTextField copyTextField = new JTextField(20);
	JTextField createVFSTextField = new JTextField(20); // maybe add two others ones for size and location and name
	JTextField importFileStructureTextField = new JTextField(20); // maybe open a window of navigation AND maybe others text boxes
	JTextField exportVFSTextField = new JTextField(13);
	JTextField findTextField = new JTextField(20);
	JTextField helpTextField = new JTextField(20);

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
	private JButton buttonCut = new JButton("Cut");



	public Frame() throws NotInTreeException{
		//		this.setResizable(false);
		this.setSize(1000, 500);
		JFrame frame = new JFrame();
		this.setTitle("VFS");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		htmlView.setEditable(false);
		tree = TreeUtil.buildTreeFromVd(vd);
		VdcnManagement.getVdList().add(vdcn);

		// ajout des panneaux aux bonnes positions
		this.getContentPane().add(panLeft, BorderLayout.WEST);
		this.getContentPane().add(tabbedPanUpRight, BorderLayout.CENTER);
		this.getContentPane().add(panDownRight, BorderLayout.SOUTH);

		panLeft.setLayout(new GridLayout(11,2));

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
		panLeft.add(buttonCut);
		panLeft.add(new JPanel());
		panLeft.add(buttonPaste);
		panLeft.add(new JPanel());
		panLeft.add(buttonRemoveFile);
		panLeft.add(new JPanel());
		panLeft.add(buttonRemoveVFS);
		panLeft.add(new JPanel());
		panLeft.add(buttonHelp);
		panLeft.add(helpTextField);

		panUpRight.add(tree);
		tabbedPanUpRight.addTab("vfs1",panUpRight);

		Box b = Box.createVerticalBox();
		b.setPreferredSize(new Dimension(900,100));
		Box lineWriting = Box.createHorizontalBox();
		lineWriting.add(new JLabel("Write here : "));
		lineWriting.add(commandLineWriting);
		b.add(lineWriting);
		Box lineReading = Box.createHorizontalBox();
		lineReading.add(new JLabel("Read here : "));
		JScrollPane htmlContainer = new JScrollPane(htmlView);
		//		htmlContainer.setPreferredSize(new Dimension(1000,100));
		//		htmlView.setPreferredSize(new Dimension(1000,100));
		htmlContainer.setPreferredSize(new Dimension(700,100));
		htmlContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		lineReading.add(htmlContainer);
		b.add(Box.createVerticalStrut(10));
		b.add(lineReading);
		panDownRight.add(b);


		tree.addTreeSelectionListener(new SelectionListener());
		buttonRename.addMouseListener(new RenameButtonListener());
		buttonCopy.addMouseListener(new CopyButtonListener());
		buttonPaste.addMouseListener(new PasteButtonListener());
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
			String str = new String();
			String calledFunction = helpTextField.getText();
			switch (calledFunction){
			case "ls":
				str +="To list the information concerning files and directories contained in absolute position corresponding to pathname (if no pathname argument is given then the current position, i.e. the current directory, is listed) in a VFS named vfsname. The command ls should behave differently, depending on the optional argument arg:\n " ;
				str +="If args=\"\" (i.e. no args is given): in this case ls simply displays the list of names of files and directories contained in the current directory of the VFS.\n " ;
				str +="In this case ls vfs1 -l displays the list of names and dimension of files and directories contained in the current directory of the VFS.\n " ;
				str +="Syntax: ls <vfsname> <args> <pathname> \n " ;
				break;
			case "crvfs":
				str +="To create a new VFS with name vfsname and maximal dimension dim bytes\n " ;
				str +="Syntax: crvfs <vfsname> <dim>\n " ;
				break;
			case "cd":
				str +="Changes the current position in the VFS vfsname to the directory whose absolute name is <pathname>\n " ;
				str +="Notice that if pathname=. the current position is unchanged whether if pathname=..the current position becomes the parent directory of the current one. For example \"cd vfs1 /Pictures/London\" will set the current position of VFS called vfs1 to path \"/Pictures/London\", whereas \"cd vfs1 ..\" will set the current position to the parent directory of the current one hence to \"/Pictures\" if \"cd vfs1 ..\" is executed soon after \"cd vfs1 /Pictures/London\"\n " ;
				str +="Syntax: cd <vfsname> <pathname>\n " ;
				break;
			case "mv":
				str +="To change the name of a file/directory with absolute name oldpath in the new absolute name newpath of the VFS named vfsname.\n " ;
				str +="Syntax: mv <vfsname> <oldpath> <newpath>\n " ;
				break;
			case "cp":
				str +="To copy, within the VFS named vfsname, the content of a file/directory whose absolute name is source path into a target le/directory whose absolute name is targetpath.\n " ;
				str +="cp <vfsname> <sourcepath> <targetpath>\n " ;
				break;
			case "rm":
				str +="To remove a file/directory with absolute name pathname from the VFS named vfsname.\n " ;
				str +="Syntax: rm <vfsname> <pathname>\n " ;
				break;
			case "rmvfs":
				str +="To delete a VFS named vfsname\n " ;
				str +="Syntax: rmvfs <vfsname>\n " ;
				break;
			case "impvfs":
				str +="To import the content of the directory/file corresponding to absolute name hostpath on the host file system into the position vfspath on an existing VFS named vfsname.\n " ;
				str +="Syntax: impvfs <hostpath> <vfsname> <vfspath>\n " ;
				break;
			case "expvfs":
				str +="To export an existing VFS named vfsname into the absolute path named hostpath of the host file system\n " ;
				str +="Syntax: expvfs <vfsname> <hostpath>\n " ;
				break;
			case "free":
				str += "To display the quantity of free/occupied space for VFS named vfsname\n " ;
				str += "Syntax: free <vfsname>\n " ;
				break;
			case "find":
				str +="To search if a file named filename is stored in the VFS named vfsname, shall return the absolute path of the sought le if it is present in the VFS, null otherwise\n " ;
				str +="Syntax: find <vfsname> <filename>\n " ;
				break;
			case "help":
				str +="To display an \"help message\" (similar to that of unix shell terminal) which gives information about how to use the command named command-name. If help is invoked without <command-name> argument then it should display a generic help message about how to use the CLUI (e.g. general syntax of a CLUI command, list of all CLUI commands name).\n " ;
				str +="help <command-name>\n " ;
			case "gen":
				str +="Generates a tree of the current file system\n " ;
				str +="Syntax gen <vfsname>\n " ;
				break;
			case "" : 
				str += "Commands take the following general syntax: command <arg1> <arg2> <arg3> \n";
				str += "The different commands are: ls, cd, mv, cp, rm, crvfs, rmvfs, impvfs, expvfs, free, find, help \n";
				str += "Type help <command> to find out more about a command \n";
			default: 
				str += "";
			}
			htmlView.setText(str);
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
			VirtualDisk vd1 = new VirtualDisk();
			int index = tabbedPanUpRight.getSelectedIndex();
			try {
				vd1 = CLUI.getVdACNFromVfsname(tabbedPanUpRight.getTitleAt(index)).getVd();
			} catch (VirtualDiskDoesntExistException e1) {
				e1.printStackTrace();
			}
			String hostpath = exportVFSTextField.getText();
			if (!hostpath.equals("")&& hostpath!=null){
				CLUI.expvfs(vd1.getName(), hostpath);
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
			if (treepath != null){
				String path = TreeUtil.treePathToString(treepath);
				try {
					tempTree = vd.getSubTree(path);
					tempNode = vd.getNodeFromPath(path);
					commandLinePrinting.setText(path + " has been copied");
				} catch (NotInTreeException e1) {
					System.out.println("path doesn't exists");
					e1.printStackTrace();
				}
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

	class PasteButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (treepath != null){
				String parent = TreeUtil.treePathToString(treepath);
				if (tempTree!=null && tempNode!=null){
					Tree subTreeCopy = null;
					try {
						subTreeCopy = vd.duplicateTree(tempTree);
					} catch (NotInTreeException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					if (subTreeCopy!=null){
						if (vd.getTotalFileSize(subTreeCopy) < vd.queryFreeSpace()){
							//				        	System.out.println(tempNode.toString());
							//				        	System.out.println(parent.toString());

							panUpRight.remove(tree);    

							for (Node n : subTreeCopy.getNodeList()){
								vd.getTree().addNode(n);
								System.out.println("add: " + n.toString());

							}
							for(Edge e : subTreeCopy.getEdgeList()){
								try {

									vd.getTree().addEdge(e);
									System.out.println("add edge: " + e.toString());

								} catch (ParentException e1) {
									e1.printStackTrace();
								} catch (NotInTreeException e1) {
									e1.printStackTrace();
								}
							}

							try {
								Edge edgeToAdd = new Edge(vd.getNodeFromPath(parent),subTreeCopy.getRoot());
								vd.getTree().addEdge(edgeToAdd);
								System.out.println("add edge (parent) " + edgeToAdd.toString());
							} catch (ParentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (NotInTreeException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							try {
								tree = TreeUtil.buildTreeFromVd(vd);
								//								System.out.println("ok");
							} catch (NotInTreeException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							CLUI.ls("vdlevel1", "", "");
							System.out.println(vd.getTree().getNodeList().toString());
							System.out.println(vd.getTree().getEdgeList().toString());
							panUpRight.add(tree);
							tree.addTreeSelectionListener(new SelectionListener());
							revalidate();
							repaint();   
						}
						else{commandLinePrinting.setText("error while copying");}
					}
					else {commandLinePrinting.setText("Not enough available space in virtual disk");}
				}
				else{commandLinePrinting.setText("Please copy something first");}
			}
			else{commandLinePrinting.setText("Please select a place to copy to on the tree");}
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

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
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

		// PEUT ETRE CHANGER LA FACON D'OBTENIR LE DERNIER INDICE EN UTILISANT getVdACNFromVfsname au cas où multithread
				@Override
				public void mouseReleased(MouseEvent e) {
					// ordre des arguments : name capacity
					String enteredText = createVFSTextField.getText();
					List<String> splitEnteredText = CLUI.preTreatment(enteredText);
					if (splitEnteredText.size() != 2){
						htmlView.setText("You must enter first the name and second the capacity of the Vitual Disk !");
					}
					else{
						try{
							CLUI.crvfs(splitEnteredText.get(0), Integer.valueOf(splitEnteredText.get(1)));
							VirtualDisk vd = VdcnManagement.getVdList().get(VdcnManagement.getVdList().size()-1).getVd();
							JPanel vdContent = new JPanel();
							vdContent.setName(splitEnteredText.get(0));
							JTree arbre = new JTree();
							try {
								arbre = TreeUtil.buildTreeFromVd(vd);
								tabbedPanUpRight.addTab(vd.getName(), arbre);
							} catch (NotInTreeException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						catch(NumberFormatException i){
							htmlView.setText("The second argument must be the capacity of the Virtual Disk, of type int");
						}
					}

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
			Component component = tabbedPanUpRight.getSelectedComponent();
			int index = tabbedPanUpRight.getSelectedIndex();
			String nameVFS = tabbedPanUpRight.getTitleAt(index);
			CLUI.rmvfs(nameVFS);
			//			VirtualDisk searchedVD = new VirtualDisk();
			//			for (VdAndCurrentNode vdACN : VdcnManagement.vdList)
			//			{
			//				VirtualDisk vd = vdACN.getVd();
			//				if (vd.getName().equals(name)){
			//					searchedVD = vd;
			//					break;
			//				}
			//			}
			//			VdcnManagement.vdList.remove(searchedVD);
			tabbedPanUpRight.remove(component);
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
			htmlView.setText( TreeUtil.treePathToString(treepath));
		}

	}
}
