/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.commonenemy;

import java.util.ArrayList;
import jgh.javagraph.Graph;
import jgh.javagraph.WeightedEdge;
import jgh.javagraph.algorithms.AStar;
import jgh.javagraph.generation.NodeGeneration;

/**
 *
 * @author sande
 */
public class AStarAlgorithm {
    public void test() {
        // A -> B -> F should be shortest path from A to F.
        NodeGeneration.BasicNode b1 = new NodeGeneration.BasicNode("A");
        NodeGeneration.BasicNode b2 = new NodeGeneration.BasicNode("B");
        NodeGeneration.BasicNode b3 = new NodeGeneration.BasicNode("C");
        NodeGeneration.BasicNode b4 = new NodeGeneration.BasicNode("D");
        NodeGeneration.BasicNode b5 = new NodeGeneration.BasicNode("E");
        NodeGeneration.BasicNode b6 = new NodeGeneration.BasicNode("F");

        WeightedEdge<NodeGeneration.BasicNode> eAB = new WeightedEdge<>(b1,b2, 3);
        WeightedEdge<NodeGeneration.BasicNode> eAC = new WeightedEdge<>(b1,b3, 6);
        WeightedEdge<NodeGeneration.BasicNode> eCD = new WeightedEdge<>(b3,b4, 1);
        WeightedEdge<NodeGeneration.BasicNode> eDE = new WeightedEdge<>(b4,b5, 10);
        WeightedEdge<NodeGeneration.BasicNode> eAF = new WeightedEdge<>(b1,b6, 12);
        WeightedEdge<NodeGeneration.BasicNode> eBF = new WeightedEdge<>(b2,b6, 3.5f);
        WeightedEdge<NodeGeneration.BasicNode> eDF = new WeightedEdge<>(b4,b6, 6.5f);

        ArrayList<WeightedEdge<NodeGeneration.BasicNode>> list = new ArrayList<>();
        list.add(eAB);
        list.add(eAC);
        list.add(eCD);
        list.add(eDE);
        list.add(eAF);
        list.add(eBF);
        list.add(eDF);

        Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>> g = new Graph<NodeGeneration.BasicNode, WeightedEdge<NodeGeneration.BasicNode>>(list);
        ArrayList<NodeGeneration.BasicNode> path = AStar.findMinPath(g, b1, b6, new AStar.IAStarHeuristic<NodeGeneration.BasicNode>() {
            @Override
            public float getHeuristic(NodeGeneration.BasicNode t, NodeGeneration.BasicNode goal) {
                return 15;
            }
        });
    }
}
