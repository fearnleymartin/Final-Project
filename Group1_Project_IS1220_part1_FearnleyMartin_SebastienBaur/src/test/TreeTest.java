package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import treeImplementation.*;
import dataTypes.Directory;
import dataTypes.Fichier;



public class TreeTest {

       Tree g1 = new Tree();
       Tree g2 = new Tree();
       Directory d1 = new Directory("d1");
       Directory d2 = new Directory("d2");
       Directory d3 = new Directory("d3");
       Directory d4 = new Directory("d4");
       Directory d5 = new Directory("d5");
       Directory d6 = new Directory("d6");
       Fichier f1 = new Fichier("f1");
       Fichier f2 = new Fichier("f2");
       Fichier f3 = new Fichier("f3");
       Fichier f4 = new Fichier("f4");
       Fichier f5 = new Fichier("f5");
       Fichier f6 = new Fichier("f6");
       Edge e1 = new Edge(d1,d2);
       Edge e2 = new Edge(d1,f1);
       Edge e3 = new Edge(d1,d3);
       Edge e4 = new Edge(d3,d5);
       Edge e5 = new Edge(d5,f3);
       Edge e6 = new Edge(d5,f4);
       Edge e7 = new Edge(d2,f2);
       Edge e8 = new Edge(d2,d4);
       Edge e9 = new Edge(d4,d6);
       Edge e10 = new Edge(d4,f6);
       Edge e11 = new Edge(d4,f5);

       
       
       
       @Before
       public void setUp() throws Exception {
       }

       @After
       public void tearDown() throws Exception {
       }


             
             

       @Test
       public void testDeleteAll() throws ParentException {     
             g1.addNode(d1);     g1.addNode(d2);     g1.addNode(d3);     g1.addNode(d4);       g1.addNode(d5);     g1.addNode(d6);     g1.addNode(f1);
             g1.addNode(f2);     g1.addNode(f3);     g1.addNode(f4);     g1.addNode(f5);       g1.addNode(f6);
             
             g2.addNode(d1); g2.addNode(f1);   g2.addNode(d3);     g2.addNode(d5);       g2.addNode(f3);     g2.addNode(f4);            
             try {
                    g1.addEdge(e1);     g1.addEdge(e2);     g1.addEdge(e3);     g1.addEdge(e4);       g1.addEdge(e5);     g1.addEdge(e6);
                    g1.addEdge(e7);     g1.addEdge(e8);     g1.addEdge(e9);       g1.addEdge(e10);g1.addEdge(e11);               
                    
                    g2.addEdge(e2);     g2.addEdge(e3);     g2.addEdge(e4);     g2.addEdge(e5);       g2.addEdge(e6);            
                    
                    g1.deleteAll(d2);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             assertEquals(g1,g2);       
       }

       
       @Test
       public void testDeleteLeaf() throws ParentException {
             g1.addNode(d1);     g1.addNode(d2);     g1.addNode(d3);     g1.addNode(d4);       g1.addNode(d5);     g1.addNode(d6);     g1.addNode(f1);
             g1.addNode(f2);     g1.addNode(f3);     g1.addNode(f4);     g1.addNode(f5);       g1.addNode(f6);
             
             g2.addNode(d1);     g2.addNode(d2);     g2.addNode(d3);     g2.addNode(d4);       g2.addNode(d5);     g2.addNode(d6);     g2.addNode(f1);
             g2.addNode(f2);     g2.addNode(f3);     g2.addNode(f4);     g2.addNode(f5);
             
             try {
                    g1.addEdge(e1);     g1.addEdge(e2);     g1.addEdge(e3);     g1.addEdge(e4);       g1.addEdge(e5);     g1.addEdge(e6);
                    g1.addEdge(e7);     g1.addEdge(e8);     g1.addEdge(e9);     g1.addEdge(e10); g1.addEdge(e11);
                    
                    g2.addEdge(e1);     g2.addEdge(e2);     g2.addEdge(e3);     g2.addEdge(e4);       g2.addEdge(e5);     g2.addEdge(e6);
                    g2.addEdge(e7);     g2.addEdge(e8);     g2.addEdge(e9);     g2.addEdge(e11);
                    
                    g1.deleteLeaf(f6);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             assertEquals(g1,g2);
       }

       
       @Test
       public void testGetSuccessors() throws ParentException {
             g1.addNode(d1);     g1.addNode(d2);     g1.addNode(d3);     g1.addNode(d4);       g1.addNode(d5);     g1.addNode(d6);     g1.addNode(f1);
             g1.addNode(f2);     g1.addNode(f3);     g1.addNode(f4);     g1.addNode(f5);       g1.addNode(f6);
             try {
                    g1.addEdge(e1);     g1.addEdge(e2);     g1.addEdge(e3);     g1.addEdge(e4);       g1.addEdge(e5);     g1.addEdge(e6);
                    g1.addEdge(e7);     g1.addEdge(e8);     g1.addEdge(e9);     g1.addEdge(e10); g1.addEdge(e11);
                    
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             List<Node> nodes = new ArrayList<Node>();
             nodes.add(f6); nodes.add(f5); nodes.add(d6);
             // the order is unimportant so that we transform the lists into sets to compare the two
             HashSet<Node> nodesSet = new HashSet<Node>(nodes);
             HashSet<Node> expected = new HashSet<Node>(g1.getSuccessors(d4));
             assertEquals(expected,nodesSet);
       }
       

       @Test
       public void testGetPredecessor() throws NotInTreeException, ParentException {
             g1.addNode(d1);     g1.addNode(d2);     g1.addNode(d3);     g1.addNode(d4);       g1.addNode(d5);     g1.addNode(d6);     g1.addNode(f1);
             g1.addNode(f2);     g1.addNode(f3);     g1.addNode(f4);     g1.addNode(f5);       g1.addNode(f6);
             try {
                    g1.addEdge(e1);     g1.addEdge(e2);     g1.addEdge(e3);     g1.addEdge(e4);       g1.addEdge(e5);     g1.addEdge(e6);
                    g1.addEdge(e7);     g1.addEdge(e8);     g1.addEdge(e9);     g1.addEdge(e10); g1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             Directory d = new Directory("d4");
             assertEquals(d,g1.getPredecessor(f6));
       }

       
       @Test
       public void testGetListOfPredecessors() throws ParentException {
             g1.addNode(d1);     g1.addNode(d2);     g1.addNode(d3);     g1.addNode(d4);       g1.addNode(d5);     g1.addNode(d6);     g1.addNode(f1);
             g1.addNode(f2);     g1.addNode(f3);     g1.addNode(f4);     g1.addNode(f5);       g1.addNode(f6);
             try {
                    g1.addEdge(e1);     g1.addEdge(e2);     g1.addEdge(e3);     g1.addEdge(e4);       g1.addEdge(e5);     g1.addEdge(e6);
                    g1.addEdge(e7);     g1.addEdge(e8);     g1.addEdge(e9);     g1.addEdge(e10); g1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             List<Node> nodes = new ArrayList<Node>();
             nodes.add(d4);
             nodes.add(d2);
             nodes.add(d1);
             assertEquals(nodes, g1.getListOfPredecessors(f6));
       }

       
       @Test
       public void testContains() throws ParentException {
             g1.addNode(d1);     g1.addNode(d2);     g1.addNode(d3);     g1.addNode(d4);       g1.addNode(d5);     g1.addNode(d6);     g1.addNode(f1);
             g1.addNode(f2);     g1.addNode(f3);     g1.addNode(f4);     g1.addNode(f5);       g1.addNode(f6);
             try {
                    g1.addEdge(e1);     g1.addEdge(e2);     g1.addEdge(e3);     g1.addEdge(e4);       g1.addEdge(e5);     g1.addEdge(e6);
                    g1.addEdge(e7);     g1.addEdge(e8);     g1.addEdge(e9);     g1.addEdge(e10); g1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             // remark : it doesn't work for d1 since it has no predecessor
             // this problem is avoided in the virtual disk since every graph has a base folder called "Home"
             // that way, the graph of the VD doesn't contain the Home folder the way we defined it, but it
             // contains all its sons
             assertTrue(g1.contains(d2));
       }

       
       @Test
       public void testGetEdgeFromNodes() throws ParentException {
             g1.addNode(d1);     g1.addNode(d2);     g1.addNode(d3);     g1.addNode(d4);       g1.addNode(d5);     g1.addNode(d6);     g1.addNode(f1);
             g1.addNode(f2);     g1.addNode(f3);     g1.addNode(f4);     g1.addNode(f5);       g1.addNode(f6);
             try {
                    g1.addEdge(e1);     g1.addEdge(e2);     g1.addEdge(e3);     g1.addEdge(e4);       g1.addEdge(e5);     g1.addEdge(e6);
                    g1.addEdge(e7);     g1.addEdge(e8);     g1.addEdge(e9);     g1.addEdge(e10); g1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             Edge e = new Edge(d3,d5);
             assertEquals(e, e4);
       }

       
       @Test
       public void testContainsEdge() throws ParentException {
             g1.addNode(d1);     g1.addNode(d2);     g1.addNode(d3);     g1.addNode(d4);       g1.addNode(d5);     g1.addNode(d6);     g1.addNode(f1);
             g1.addNode(f2);     g1.addNode(f3);     g1.addNode(f4);     g1.addNode(f5);       g1.addNode(f6);
             try {
                    g1.addEdge(e1);     g1.addEdge(e2);     g1.addEdge(e3);     g1.addEdge(e4);       g1.addEdge(e5);     g1.addEdge(e6);
                    g1.addEdge(e7);     g1.addEdge(e8);     g1.addEdge(e9);     g1.addEdge(e10); g1.addEdge(e11);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             assertTrue(g1.containsEdge(d1,d2));
       }

       
       @Test
       public void testMove() throws ParentException {
             g1.addNode(d1);     g1.addNode(d2);     g1.addNode(d3);     g1.addNode(d4);       g1.addNode(d5);     g1.addNode(d6);     g1.addNode(f1);
             g1.addNode(f2);     g1.addNode(f3);     g1.addNode(f4);     g1.addNode(f5);       g1.addNode(f6);
             
             g2.addNode(d1);     g2.addNode(d2);     g2.addNode(d3);     g2.addNode(d4);       g2.addNode(d5);     g2.addNode(d6);     g2.addNode(f1);
             g2.addNode(f2);     g2.addNode(f3);     g2.addNode(f4);     g2.addNode(f5);       g2.addNode(f6); g2.addNode(d4); g2.addNode(d6);
             g2.addNode(f6); g2.addNode(f5);
             try {
                    g1.addEdge(e1);     g1.addEdge(e2);     g1.addEdge(e3);     g1.addEdge(e4);       g1.addEdge(e5);     g1.addEdge(e6);
                    g1.addEdge(e7);     g1.addEdge(e8);     g1.addEdge(e9);     g1.addEdge(e10); g1.addEdge(e11);
                    
                    g2.addEdge(e1);     g2.addEdge(e2);     g2.addEdge(e3);     g2.addEdge(e4);       g2.addEdge(e5);     g2.addEdge(e6);
                    g2.addEdge(e7);     g2.addEdge(e9);     g2.addEdge(e10); g2.addEdge(e11);
                    g2.addEdge(new Edge(d5,d4)); 
                    
                    g1.move(d4, d5);
             } catch (NotInTreeException e) {
                    e.printStackTrace();
             }
             assertEquals(g1,g2);
       }

}

