package Clients;

import java.util.*;

import dataTypes.*;
import graphImplementation.*;

public class CLUI {
	
	//List of virtual disks and their associated current nodes
	private static List<VdAndCurrentNode> vdList = new ArrayList<VdAndCurrentNode>();
	
	//list contents of directory
	public static void ls(String vfsname, String args, String pathname) throws VirtualDiskDoesntExistException, NotInGraphException{
		VdAndCurrentNode vdcn = getVdACNFromVfsname(vfsname);
		VirtualDisk vd = vdcn.getVd();
		Node currentNode = vdcn.getCurrentNode();
		
		//all files & folders
		if (pathname.equals("")){
			switch(args){
			case "":
				List<Node> succ = vd.getAllSuccessors(vd.getPath(currentNode));
				for (Node n : succ){
					if (n instanceof Fichier){
						System.out.println(n.getName()+"    "+"f");
					}
					else{
						System.out.println(n.getName()+"    "+"d");
					}
				}
				break;
			//prints size too
			case "-l":
				List<Node> succ2 = vd.getAllSuccessors(vd.getPath(currentNode));
				for (Node n : succ2){
					if (n instanceof Fichier){
						System.out.println(n.getName()+"    "+((Fichier)n).getSize() + "    "+"f");
					}
					else{
						Graph subGraph = vd.getSubGraph(vd.getPath(n));
						long size = subGraph.getTotalFileSize();
						System.out.println(n.getName()+"    "+ size+"     "+"d");
					}
				}
				break;
				
			}
		}
		//files & folders in pathname
		else{
			switch(args){
			case "":
				List<Node> succ = vd.getAllSuccessors(pathname);
				for (Node n : succ){
					if (n instanceof Fichier){
						System.out.println(n.getName()+"    "+"f");
					}
					else{
						
						System.out.println(n.getName()+"     "+"d");
					}
				}
				break;
			//prints size too
			case "-l":
				List<Node> succ2 = vd.getAllSuccessors(pathname);
				for (Node n : succ2){
//					System.out.println(succ2);
					if (n instanceof Fichier){
						System.out.println(n.getName()+"    "+((Fichier)n).getSize() + "    "+"f");
					}
					else{
						Graph subGraph = vd.getSubGraph(vd.getPath(n));
						long size = subGraph.getTotalFileSize();
						System.out.println(n.getName()+"    "+ size+"     "+"d");
					}
				}
				break;
				
			}
		}
	}
	
	//returns vd and its current node object from the name of a vfs
	public static VdAndCurrentNode getVdACNFromVfsname(String vfsname) throws VirtualDiskDoesntExistException{
		for (VdAndCurrentNode vdcn : vdList){
			if(vdcn.getVd().getName().equals(vfsname)){
				return vdcn;
			}
		}
		throw new VirtualDiskDoesntExistException("No virtual disk called '" + vfsname + "' exists");
	}
	
	//add in the different cases
	//navigate to directory
	public static void cd(String vfsname, String pathname) throws VirtualDiskDoesntExistException, NotInGraphException{
		VdAndCurrentNode vdcn = getVdACNFromVfsname(vfsname);
		Node nodeSpecified = vdcn.getVd().getNodeFromPath(pathname);
		vdcn.setCurrentNode(nodeSpecified);
	}
	
	//to create a new virtual disk
	//what if vd already exists with this name ?
	//limit maximum size to stop user doing anything stupid
	public static void crvfs(String vfsname, int dim) throws NotInGraphException{
		
		VirtualDisk vd = VirtualDisk.createVirtualDisk(vfsname, "Virtual Disks/"+ vfsname+ ".ser", dim);
		VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);
		vdList.add(vdcn);
	}
	
	//check this does what it is meant to
	public static void mv (String vfsname, String oldpath, String newpath) throws VirtualDiskDoesntExistException, NotInGraphException{
		VdAndCurrentNode vdcn= getVdACNFromVfsname(vfsname);
		String[] str= newpath.split("/");
		String newName = str[str.length-1];		
		vdcn.getVd().rename(oldpath, newName);
	}

	//doesn't work
	public static void cp(String vfsname, String sourcepath,String targetpath) throws VirtualDiskDoesntExistException, NotInGraphException, NotADirectoryException, NoAvailableSpaceException{
		VdAndCurrentNode vdcn= getVdACNFromVfsname(vfsname);
		VirtualDisk vd = vdcn.getVd();
		//change targetpath into parent path
		String[] str= targetpath.split("/");
		String parentPath = "";
		for (int i=0; i<str.length-1;i++){
			parentPath = parentPath+str[i]+"/";
		}
//		parentPath = parentPath+str[str.length-1];
		System.out.println(parentPath);
		vd.copy(sourcepath, parentPath);
	}

	public static void impvfs(String hostpath,String vfsname, String vfspath) throws NoAvailableSpaceException, NotInGraphException, NotADirectoryException{
		VdAndCurrentNode vdcn = null;
		try {
			vdcn = getVdACNFromVfsname(vfsname);
			VirtualDisk vd = vdcn.getVd();
			vd.importFileStructure(hostpath, vfspath);
		} catch (VirtualDiskDoesntExistException e) {
			System.out.println(vfsname +" doesn't exist");
			e.printStackTrace();
		}
		
	}
	
	//unused
	public static String invertedCommaRemover(String str){
		if ((str.startsWith("\""))&& (str.endsWith("\""))){
			System.out.println("hey");
			return str.replace("\"","");
		}
		else{
			return str;
		}
	}

	
	//cuts up the string argument recieved from the user input into an array of string arguments
	//if path name includes spaces, then the path name must be put inside inverted commas
	public static List<String> preTreatment(String str){
		List<String> list = new ArrayList<String>();
		int strLen = str.length();
		//compteur to iterate through each character of the string
		int compteur = 0;
		//for remembering where the last space was encountered
		int lastSpace = 0;
		//for remembering where the last inverted comma was encountered
		int lastComma = 0;
		//is true if the compteur is between inverted commas
		boolean inInvertedCommas=false;
		//is true if the inverted commas have just been closed
		boolean precededByInvertedCommas=false;
		while (compteur < strLen){
			//tests for a space followed by an inverted comma, i.e. the beginning of an argument in inverted commas
			if (Character.toString((str.charAt(compteur))).equals(" ")&&Character.toString((str.charAt(compteur+1))).equals("\"")){
//				System.out.println("space comma");
				if (!inInvertedCommas&&!precededByInvertedCommas){
//					System.out.println(str.substring(lastSpace, compteur));
					list.add(str.substring(lastSpace, compteur));
					lastSpace = compteur+1;
				}
				lastComma=compteur+2;
				compteur=compteur+2;
				inInvertedCommas=true;
				precededByInvertedCommas=false;
				}
			//tests for the end of a string in inverted commas
			if (Character.toString((str.charAt(compteur))).equals("\"")){
				
//				System.out.println("comma");
				list.add(str.substring(lastComma, compteur));
//				System.out.println(str.substring(lastComma, compteur));
				lastComma = compteur+1;
				lastSpace = compteur+2;
				compteur++;
				inInvertedCommas=false;
				precededByInvertedCommas=true;
			}
			//tests for a space, i.e. athe start of a new argument, unless in inverted commas
			else if(Character.toString((str.charAt(compteur))).equals(" ")){
				if (!inInvertedCommas&&!precededByInvertedCommas){
//					System.out.println(str.substring(lastSpace, compteur));
					list.add(str.substring(lastSpace, compteur));
					lastSpace = compteur+1;
					compteur++;
					precededByInvertedCommas=false;
				}
				else{
					compteur ++;
					precededByInvertedCommas=false;
				}
			}
			//if none of these characters are encountered, we continue to iterate through the string
			else{
				compteur ++;
			}
		}
		return list;
	}
	
 	public static void understand(String str) throws NumberFormatException, NotInGraphException, VirtualDiskDoesntExistException{
		//pre-treat string
 		List<String> strList = preTreatment(str);
 		//create an array from list returned
 		String[] strs =  new String[strList.size()];
		for (int i = 0; i<strList.size();i++){
			strs[i]=strList.get(i);
		}
		//check for first argument
		switch (strs[0]){
		case "ls":
			if (strs.length==2){
				ls(strs[1],"","");
			}
			else if(strs.length==3){
				if(strs[2].equals("-1")){
					ls(strs[1],"-1","");
				}
				else{ls(strs[1],"",strs[2]);
			}
			}
			else{
				ls(strs[1],strs[2],strs[3]);
			}
			break;
		case "crvfs":
			crvfs(strs[1],Integer.parseInt(strs[2]));
			break;
		case "cd":
			cd(strs[1],strs[2]);
			break;
		case "mv":
			
		case "cp":
		
		case "rm":
			
		case "rmvfs":
			
		case "impvfs":
			
		case "expvfs":
		
		case "free":
			
		case "find":
			
		case "help":
		
		case "gen":
			new GenerateTree(getVdACNFromVfsname(strs[1]).getVd());
			break;
		}
		}
	
	public static void main(String[] args) throws NotInGraphException, VirtualDiskDoesntExistException, NoAvailableSpaceException, NotADirectoryException{
		//create vfs1
		crvfs("vfs1",1000);
		//create vfs2
//		crvfs("vfs2",1000);
//		System.out.println(vdList.get(0).getVd());
//		System.out.println(vdList.get(0).getCurrentNode());
		//acces vfs1 and vfs2
//		VdAndCurrentNode vfs1 = getVdACNFromVfsname("vfs1");
//		VdAndCurrentNode vfs2 = getVdACNFromVfsname("vfs2");
//		System.out.println(vfs1.toString());
		//import level 1
		impvfs("eval/Host/level 1","vfs1","Home");
//		GenerateTree gt = new GenerateTree(getVdACNFromVfsname("vfs1").getVd());
//		System.out.println(vfs1.getVd().getGraph().getNodeList());
//		//navigate to level 2
//		cd("vfs1","Home/level 1/level 2");
//		System.out.println(vfs1.getCurrentNode());
//		ls("vfs1","-1","");
		//rename level 1 to level 1 renamed
//		mv("vfs1","Home/level 1","Home/level 1 renamed");
//		gt.UpdateTree();
//		ls("vfs1","","Home");
//		//put test text.txt in level 2 bis
//		cp("vfs1","Home/level 1 renamed/level 2/test text.txt","Home/level 1 renamed/level 2 bis/test text.txt");
//		//display trees
//		GenerateTree gt = new GenerateTree(vfs1.getVd());
//		new GenerateTree(vfs2.getVd());
		
		System.out.println("What would you like to do ? ");
		
		
		Scanner scan = new Scanner(System.in);
		while (true){
		String str = scan.nextLine();
		
		List<String> tab = (preTreatment(str));
//		for (String s : tab){System.out.println("element: " +s);}
		understand(str);
		}
		
	}
}

