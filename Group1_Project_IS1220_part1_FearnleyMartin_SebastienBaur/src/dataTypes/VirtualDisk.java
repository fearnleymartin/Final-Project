package dataTypes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import graphImplementation.*;

public class VirtualDisk implements Serializable {

	private int capacity;
	private String name;
	private graphImplementation.Graph graph = new Graph();
	private String path;

	// -----------------------------------------------------------------------------
	// CONSTRUCTORs UNUSED BY THE USER
	// -----------------------------------------------------------------------------

	//needs to raise error !! but breaks load method
	public VirtualDisk(String name, String path, int capacity) {
		this.name = name;
		if (capacity >= 0){
			this.capacity = capacity;
		}
		else{
			System.out.println("You can't have a negative capacity");}
		this.path = path;
		this.graph = new Graph();
	}

	public VirtualDisk() {
		// TODO Auto-generated constructor stub
	}

	// -----------------------------------------------------------------------------
	// LOADING AND SAVING VIRTUAL DISKS
	// -----------------------------------------------------------------------------
	
	// saving using serialization
	public void saveVirtualDisk(){
			try{
				FileOutputStream fos = new FileOutputStream(this.path);
				ObjectOutputStream out = new ObjectOutputStream(fos);
				out.writeObject(this);
				out.close();
				fos.close();
				System.out.println("Your virtual disk is saved in " + this.path);

			}catch(IOException i){
				i.printStackTrace();
			}
	}

	// loading using deserialization
	public static VirtualDisk loadVirtualDisk(String computerPath) {
		VirtualDisk vd =  new VirtualDisk("name",computerPath,100);
		try{
			FileInputStream fileIn = new FileInputStream(computerPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			vd = (VirtualDisk) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i){
			i.printStackTrace();
		}catch(ClassNotFoundException c){
			System.out.println("VirtualDisk class not found");
			c.printStackTrace();
		}
		return vd;
	}

	// -----------------------------------------------------------------------------
	// FILE STRUCTURE MANAGEMENT
	// -----------------------------------------------------------------------------

	// delete the file lying at the indicated path if it is a file, and the directory as well as all his sons if it's a directory
	public void deleteAll(String path) throws NotInGraphException{
		Node n = this.getNodeFromPath(path);
		this.getGraph().deleteAll(n);
	}

	//adds a directory to the graph from a Directory object
	public void addDirectory(String parentLocation, Directory directory) throws NotADirectoryException{
		try {
			if (this.getNodeFromPath(parentLocation) instanceof Directory){
				Directory parent = (Directory) this.getNodeFromPath(parentLocation);
				this.getGraph().addNode(directory);
				this.getGraph().addEdge(new Edge(parent,directory));
			}
			else
				throw new NotADirectoryException();
		} catch (NotInGraphException e) {
			e.printStackTrace();
		} 
	}

	//adds a node to the graph from a Fichier object
	public void addFile(String parentLocation, Fichier fichier) throws NotADirectoryException, NoAvailableSpaceException{
        try{
               if (this.getNodeFromPath(parentLocation) instanceof Directory){
                      if( fichier.getSize() < this.queryFreeSpace()){
                             Directory parent = (Directory) this.getNodeFromPath(parentLocation);
                             this.graph.addNode(fichier);
                             this.graph.addEdge(new Edge(parent,fichier));
                      }
                      else
                             throw new NoAvailableSpaceException("there is not enough space in the disk");
               }
               else
                      throw new NotADirectoryException();
        } catch (NotInGraphException e) {
               e.printStackTrace();
        } 
  }



	public void rename(String filePath, String newName) throws NotInGraphException{
		Node node = this.getNodeFromPath(filePath);
		node.setName(newName);
	}

	// -----------------------------------------------------------------------------
	// VIRTUAL DISK MANAGEMENT
	// -----------------------------------------------------------------------------

	
	// delete a virtual disk from your computer
	public void deleteVirtualDisk(){
		File f = new File(this.getPath());
		f.delete();
	}

	// the user should use this static method to create a virtual disk. This method creates a base folder called home
	public static VirtualDisk createVirtualDisk(String name, String path, int capacity){
		VirtualDisk vd = new VirtualDisk(name,path,capacity);
		vd.getGraph().addNode(new Directory("Home"));
		vd.saveVirtualDisk();
		return vd;
	}



	// -----------------------------------------------------------------------------
	// IMPORT/EXPORT FILE STRUCTURES
	// -----------------------------------------------------------------------------

	//for importing a new file structure into the existing structure form the computer(a file or a directory and all its sons)
	//throws error if no space in vd, or if the parent is not in the graph
	public void importFileStructure(String path, String parentPath) throws NoAvailableSpaceException, NotInGraphException, NotADirectoryException{
		if (this.getNodeFromPath(parentPath) instanceof Directory){
			Directory parent = (Directory) this.getNodeFromPath(parentPath);
			File currentFile = new File(path);
			long availableSpace = this.queryFreeSpace();
			if (currentFile.isFile()){ // if it's a file we instantiate a Fichier object
				Fichier f = new Fichier(currentFile.getName());
				f.importFile(path);
				f.setSize(currentFile.length());
				if (f.getSize()<availableSpace){
					this.graph.addNode(f);
					this.graph.addEdge(new Edge(parent, f));
				}
				else{
					throw new NoAvailableSpaceException("There is no space left on disk to add the file: " + f.getName());
				}
			}
			if (currentFile.isDirectory()){ // if it's a Directory we instantiate a Directory Object
				Directory d = new Directory(currentFile.getName());
				String filename = d.getName();
				this.graph.addNode(d);
				this.graph.addEdge(new Edge(parent,d));
				String[] sonsPaths = currentFile.list();
				for (String sonPath : sonsPaths){
					this.importFileStructure(path + "/" + sonPath, this.getPath(d));
					//make sure to update path name
				}
			}
		}
		else
			throw new NotADirectoryException("the indicated path isn't a directory");
	}


	//for exporting a file specified by path from the virtual disk to the computer at a place specified by computerPath
	// computerPath is the place where it'll be exported, name the one where it's currently saved within the vd
	//make sure to specify the filename in the computerPath !!
	public void exportFile(String computerPath, String filename){ 
		Fichier f = new Fichier("fichier");
		try{
			f = (Fichier) this.getNodeFromPath(filename);
//			System.out.println(f.getName());
			f.exportFile(computerPath);
		}
		catch (NotInGraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// exports Directory as specified by path (filename) to folder specified by computerPath
	// NB : it also exports all what's contained in the specified directory
	public void exportDirectory(String computerPath, String filename) throws NotInGraphException{
		Node n = this.getNodeFromPath(filename);
		//get node name (ie name of file/folder)
		String tempFileName= n.getName();

		if (n instanceof Fichier){
			this.exportFile(computerPath+"/"+tempFileName, filename);
		}
		else if (n instanceof Directory){
			new File(computerPath+"/"+tempFileName).mkdir();
			List<Node> suc = this.getGraph().getSuccessors(n);
			for ( Node node : suc){
				exportDirectory(computerPath+"/"+tempFileName,this.getPath(node));
			}
		}
	}


	// -----------------------------------------------------------------------------
	// MOVING AND COPYING
	// -----------------------------------------------------------------------------

	//Internal moving of hierarchies
	public void move(String nodeMovedPath, String parentPath) throws NotInGraphException, NotADirectoryException{
		Node n = this.getNodeFromPath(nodeMovedPath);
		if (this.getNodeFromPath(parentPath) instanceof Directory){
			Directory parent = (Directory) this.getNodeFromPath(parentPath);
			this.getGraph().move(n, parent);
		}
		else
			throw new NotADirectoryException();
	}
	
	// copying a file hierarchy from one folder to another inside the virtual disk
	//raises exception if there is no available space to recreate extra files
    public void copy(String nodeToBeCopied, String parent) throws NotInGraphException, NotADirectoryException, NoAvailableSpaceException{
        Graph subGraph = this.getSubGraph(nodeToBeCopied);
        Graph subGraphCopy = new Graph();
        subGraphCopy.setNodeList(subGraph.getNodeList());
        subGraphCopy.setEdgeList(subGraph.getEdgeList());
        if (subGraphCopy.getTotalFileSize() < this.queryFreeSpace()){
               for (Node n : subGraphCopy.getNodeList()){
                      this.getGraph().addNode(n);
               }
               for(Edge e : subGraphCopy.getEdgeList()){
                      this.getGraph().addEdge(e);
               }
               this.getGraph().addEdge(new Edge(this.getNodeFromPath(parent),this.getNodeFromPath(nodeToBeCopied)));
        }
        else
               throw new NoAvailableSpaceException("there is not enough space in the disk");
  }

	// -----------------------------------------------------------------------------
	// PATH MANAGEMENT
	// -----------------------------------------------------------------------------

	//returns file path of a node
	public String getPath(Node n) throws NotInGraphException{
		if (this.getGraph().contains(n)){
			List<Node> nodes = new ArrayList<Node>();
			nodes = this.getGraph().getListOfPredecessors(n);
			String str = new String();
			for (int i = 0; i < nodes.size(); i++)
			{
				str = nodes.get(i).getName() + "/" + str;
			}
			return str + n.getName();
		}
		else
			throw new NotInGraphException("node " + n + " isn't part of the graph");
	}


	//gets the Node object (File or Folder) from path name	
	public Node getNodeFromPath(String path) throws NotInGraphException{
		String[] directories = path.split("/");
		//gets Home node
		Node Home = this.getGraph().getNodeList().get(0);
		// Initialises current node with Home, the base of the file hierarchy
		Node currentNode = Home;
		for (int i=0; i< directories.length-1; i++){
			//gets list of successors of the current node
			List<Node> suc = this.getGraph().getSuccessors(currentNode);
			//goes through the successors to see if the next directory specified by path is included, throw error if not found
			boolean hasChanged = false;
			for (Node node : suc){
				if (directories[i+1].equals(node.getName())&&(this.getGraph().containsEdge(currentNode, node))){
					currentNode=node;
					hasChanged = true;
					break;
				}
			}
			if (!hasChanged){
				throw new NotInGraphException();
			}
		}

		return currentNode;
	}

	//---------------------------------------------------------------------------------
	// SEARCH (INCLUDES SUBGRAPH AND GETALLSUCCESSORS)
	//---------------------------------------------------------------------------------

	//search function, returns list of files from string name (gives back a list because multiple files may have the same name)
	public List<Node> search (String name) throws NotInGraphException{
		List<Node> res = new ArrayList<Node>();
		for (Node n : this.getGraph().getNodeList()){
			if((n.getName().equals(name))&& (n instanceof Fichier)){
				res.add((Fichier)n);
			}
			else if((n.getName().equals(name))&& (n instanceof Directory)){
				res.add((Directory)n);
			}
		}
		if (res.isEmpty()){
			throw new NotInGraphException();
		}
		return res;
	}

	//returns a list of all files and folders in a given directory
	public List<Node> getAllSuccessors(String path) throws NotInGraphException{
		Node node = this.getNodeFromPath(path);
		Successors succ = new Successors();
		succ.aux(node);
		return succ.nodes;
	}

	//returns the sub graph of a graph starting from the node specified by path
	public Graph getSubGraph(String path) throws NotInGraphException{
		Node node = this.getNodeFromPath(path);
		Successors succ = new Successors();
		succ.aux2(node);
		Graph g = new Graph();
		g.setNodeList(succ.nodes);
		g.setEdgeList(succ.edges);
		return g;
	}

	// nested class used for the getAllSuccessors function, we needed to make aux a recursive function, but have a fixed variable to store the results (res)
	private class Successors{
		List<Node> nodes = new ArrayList<Node>();
		List<Edge> edges = new ArrayList<Edge>();
		
		public void aux(Node node) throws NotInGraphException{
			//Node node = VirtualDisk.this.getNodeFromPath(path); 
			if(node instanceof Fichier){
				this.nodes.add(node);
			}
			else{
				List<Node> succ = VirtualDisk.this.getGraph().getSuccessors(node);
				this.nodes.add(node);
				for (Node n: succ){
					aux(n);
				}
			}      
		}      
		
		public void aux2(Node node) throws NotInGraphException{
			//Node node = VirtualDisk.this.getNodeFromPath(path); 
			if(node instanceof Fichier){
				this.nodes.add(node);
			}
			else{
				List<Node> succ = VirtualDisk.this.getGraph().getSuccessors(node);
				this.nodes.add(node);
				for (Node n: succ){
					this.edges.add(VirtualDisk.this.getGraph().getEdgeFromNodes(node, n));
					aux2(n);
				}
			}      
		}
	}

	//for searching in specific directory 
	public List<Node> search (String name, String parent) throws NotInGraphException{
		List<Node> hierarchy = this.getAllSuccessors(parent);
		List<Node> res = new ArrayList<Node>();
		for (Node n : hierarchy){
			if((n.getName().equals(name))&& (n instanceof Fichier)){
				res.add((Fichier)n);
			}
			else if((n.getName().equals(name))&& (n instanceof Directory)){
				res.add((Directory)n);
			}
		}
		if (res.isEmpty()){
			throw new NotInGraphException();
		}
		return res;
	}

	//---------------------------------------------------------------------------------
	// GETTERS AND SETTERS AND EQUALS METHOD
	//---------------------------------------------------------------------------------

	// query free space left in virtual disk
		public long queryFreeSpace(){
			long totalSize = this.getTotalFileSize();
			return (this.capacity - totalSize);
		}
		
		public long getTotalFileSize() {
			long totalSize = 0;
			for (Node n : this.getGraph().getNodeList()){
				if (n instanceof Fichier){
					Fichier f = (Fichier) n;
					totalSize = totalSize + f.getSize();
				}
			}
			return totalSize;
		}

	//---------------------------------------------------------------------------------
	// GETTERS AND SETTERS AND EQUALS METHOD
	//---------------------------------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VirtualDisk other = (VirtualDisk) obj;
		if (capacity != other.capacity)
			return false;
		if (graph == null) {
			if (other.graph != null)
				return false;
		} else if (!graph.equals(other.graph))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	public int getCapacity() {
		return capacity;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public graphImplementation.Graph getGraph() {
		return graph;
	}

	public void setGraph(graphImplementation.Graph graph) {
		this.graph = graph;
	}


}