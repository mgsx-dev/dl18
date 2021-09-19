package net.mgsx.dl18.springs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;

import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class SpringLoader {

	public SpringModel load(String path){
		
		SpringModel m = new SpringModel();
		
		SceneAsset asset = new GLTFLoader().load(Gdx.files.internal(path));
		Model model = asset.scene.model;
		
		for(Node modelNode : model.nodes){
			
			for(NodePart part : modelNode.parts){
				MeshPart mp = part.meshPart;
				
				int indexCount = mp.size;
				short[] indices = new short[indexCount];
				int vertexCount = mp.mesh.getNumVertices();
				int stride = mp.mesh.getVertexSize() / 4;
				float[] vertices = new float[vertexCount * stride];
				mp.mesh.getIndices(indices);
				mp.mesh.getVertices(vertices);
				
				IntMap<SpringNode> nodeMap = new IntMap<SpringNode>(vertexCount);
				
				SpringGraph graph = new SpringGraph();
				
				for(int i=0 ; i<indexCount ; i+=2){
					int iA = indices[i] & 0xFFFF;
					int iB = indices[i+1] & 0xFFFF;
					
					SpringNode nodeA = nodeMap.get(iA); if(nodeA == null) nodeMap.put(iA, nodeA = new SpringNode());
					SpringNode nodeB = nodeMap.get(iB); if(nodeB == null) nodeMap.put(iB, nodeB = new SpringNode());
					
					SpringEdge edge = new SpringEdge();
					edge.nodeA = nodeA;
					edge.nodeB = nodeB;
					
					graph.edges.add(edge);
				}
				
				int posOffset = mp.mesh.getVertexAttribute(Usage.Position).offset / 4;
				for(Entry<SpringNode> entry : nodeMap){
					int index = entry.key;
					SpringNode node = entry.value;
					int posIndex = index * stride + posOffset;
					
					node.originalPosition.set(vertices[posIndex], vertices[posIndex+1], vertices[posIndex+2]);
					node.position.set(node.originalPosition);
					
					graph.nodes.add(node);
				}
				
				m.graphs.add(graph);
				
			}
			
		}
		
		return m;
		
	}
}
