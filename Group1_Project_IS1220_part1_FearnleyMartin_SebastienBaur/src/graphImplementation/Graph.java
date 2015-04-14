package graphImplementation;
import java.io.Serializable;
import java.util.*;

import graphImplementation.*;
import dataTypes.*;

//using generic types that refer to the generic types of the nodes and edges
public class Graph implements Serializable{


	private  List<Node> nodeList = new ArrayList<Node>();
	private  List<Edge> edgeList = new ArrayList<Edge>();


	//---------------------------------------------------------------------------------
	// GETTERS AND SETTERS, TOSTRING, AND EQUALS METHOD
	//---------------------------------------------------------------------------------

	public List<Node> getNodeList() {
		return nodeList;
	}

	@Override
	public String toString() {
		return "Graph [nodeList=" + nodeList + ", edgeList=" + edgeList + "]";
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public List<Edge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(List<Edge> edgeList) {
		this.edgeList = edgeList;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((edgeList == null) ? 0 : edgeList.hashCode());
		result = prime * result
				+ ((nodeList == null) ? 0 : nodeList.hashCode());
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
		Graph other = (Graph) obj;
		HashSet<Node> thisNodesSet = new HashSet<Node>(this.getNodeList());
		HashSet<Edge> thisEdgesSet = new HashSet<Edge>(this.getEdgeList()); 
		HashSet<Node> otherNodesSet = new HashSet<Node>(other.getNodeList());
		HashSet<Edge> otherEdgesSet = new HashSet<Edge>(other.getEdgeList());
		if (edgeList == null) {
			if (other.edgeList != null)
				return false;
		} else if (!thisEdgesSet.equals(otherEdgesSet))
			return false;
		if (nodeList == null) {
			if (other.nodeList != null)
				return false;
		} else if (!thisNodesSet.equals(otherNodesSet))
			return false;
		return true;
	}
	
	
	
	// -----------------------------------------------------------------------------
	// NODE/EDGE MANAGEMENT
	// -----------------------------------------------------------------------------

	public void addNode(Node n){
		this.nodeList.add(n);
	}
	
	public void addEdge(Edge e) throws NotInGraphException{
		if (this.nodeList.contains(e.getStartNode()) && this.nodeList.contains(e.getEndNode()))
			this.edgeList.add(e);
		else
			throw new NotInGraphException("startNode or endNode of your edge isn't part of the nodeList of the graph");
	}

	// this method is used by the VirtualDisk method deleteAll(String path)
	public void deleteAllAux(Node n) throws NotInGraphException{
		if(this.getNodeList().contains(n)){
			this.getNodeList().remove(n);
			// the edges linking the node to its successors/predecessor have to be removed too
			try{
				this.getEdgeList().remove(this.getEdgeFromNodes(this.getPredecessor(n), n));}
			finally{
				List<Node> successors = new ArrayList<Node>();
				successors = this.getSuccessors(n);
				for (int i = 0; i<successors.size();i++){
					this.getEdgeList().remove(this.getEdgeFromNodes(n, successors.get(i)));
				}
			}
		}
		else 
			throw new NotInGraphException("Node "+n+" not in graph");
	}

	// deletes a Node and all its successors
	public void deleteAll(Node n) throws NotInGraphException{
		while (this.getNodeList().contains(n)){
			if (this.getSuccessors(n).size() == 0)
				this.deleteLeaf(n);
			else
				this.deleteAll(this.getSuccessors(n).get(0));
		}
	}

	// delete a Node which has no successors
	public void deleteLeaf(Node n) throws NotInGraphException{
		if (this.getSuccessors(n).size() == 0){
			this.getNodeList().remove(n);
			this.getEdgeList().remove(this.getEdgeFromNodes(this.getPredecessor(n), n));
		}
	}

	public void deleteEdge(Edge e) throws NotInGraphException{
		if (this.getEdgeList().contains(e)){
			this.getEdgeList().remove(e);
		}
		else
			throw new NotInGraphException("Edge " +e+ "is not in the graph");
	}

	public int nodeCount(){
		return nodeList.size();
	}

	public int edgeCount(){
		return getEdgeList().size();
	}


	// -----------------------------------------------------------------------------
	// SUCCESSORS/PREDECESSORS MANAGEMENT
	// -----------------------------------------------------------------------------
	public List<Node> getSuccessors(Node n){	
		List<Node> res = new ArrayList<Node>();
			for (Edge e : edgeList){
				if (e.getStartNode().equals(n)){
					res.add(e.getEndNode());
				}
			}
		return res;
	}

	public Node getPredecessor(Node n) throws NotInGraphException{
		for (Edge e : edgeList){
			if (e.getEndNode().equals(n))
				return (e.getStartNode());
		}
		throw new NotInGraphException("the node" + n + "isn't part of the graph");
	}

	//return list of predecessors of a node
	public List<Node> getListOfPredecessors(Node n){
		List<Node> res = new ArrayList<Node>();
		try{
			Node prec=this.getPredecessor(n);
			res.add(prec);
			res.addAll(getListOfPredecessors(prec));
		} 
		catch(NotInGraphException e) {
			return new ArrayList<Node>();
		}
		return res;
	}
	
	// -----------------------------------------------------------------------------
	// CONTAINS
	// -----------------------------------------------------------------------------

	// verifies whether the Node n is part of the graph or not
	public boolean contains(Node n) {
		if (this.nodeCount()==1)
			return true;
		else if (this.nodeCount()==0)
			return false;
		else 
			return (this.getListOfPredecessors(n).size() != 0 || this.getSuccessors(n).size() != 0);
	}


	// -----------------------------------------------------------------------------
	// GRAPH SIZE
	// -----------------------------------------------------------------------------

	//return the total file size of all files contained in graph
	public long getTotalFileSize() {
		long totalSize = 0;
		for (Node n : this.getNodeList()){
			if (n instanceof Fichier){
				Fichier f = (Fichier) n;
				totalSize = totalSize + f.getSize();
			}
		}
		return totalSize;
	}

	// -----------------------------------------------------------------------------
	// GRAPH REARRANGING (INTERNAL MOVING)
	// -----------------------------------------------------------------------------

	// get edge from nodes
	public Edge getEdgeFromNodes(Node start, Node end) throws NotInGraphException{
		for (Edge e : this.getEdgeList()){
			if ((e.getStartNode().equals(start))&&(e.getEndNode().equals(end))){
				return e;
			}	
		}
		throw new NotInGraphException();
	}


	//check graph contains edge
	public boolean containsEdge(Node start, Node end){
		for (Edge e : this.getEdgeList()){
			if ((e.getStartNode().equals(start)) && (e.getEndNode().equals(end))){
				return true;
			}	
		}
		return false;
	}


	//moving a file hierarchy from one Folder to another
	public void move(Node n, Directory parent ) throws NotInGraphException{
		Node prec = this.getPredecessor(n);
		Edge edgeToDelete = getEdgeFromNodes(prec,n);
		this.deleteEdge(edgeToDelete);
		Edge edgeToAdd = new Edge(parent,n);
		this.addEdge(edgeToAdd);
	}


}