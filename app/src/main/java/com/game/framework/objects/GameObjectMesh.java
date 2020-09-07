package com.game.framework.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.game.framework.gl.GameImage;

public class GameObjectMesh {

	private final int VERTEX_SIZE = (2+2)*4;	
	private float x;
	private float y;
	private int width;
	private int height;
	
	private FloatBuffer verticesBuffer;
	private short[] 	triangles={0,1,2,2,3,0};
	private ShortBuffer trianglesBuffer;
	
	public GameObjectMesh(float x,float y,int width,int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		setIndices();
		setVertices();
	}

	private void setIndices(){
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangles.length*2);
		byteBuffer.order(ByteOrder.nativeOrder());
		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
		shortBuffer.put(triangles);
		shortBuffer.flip();
		trianglesBuffer=shortBuffer;
	}
	
	public short[] getTriangles() {
		return triangles;
	}

	private void setVertices(){
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4*VERTEX_SIZE);
		byteBuffer.order(ByteOrder.nativeOrder());
	    verticesBuffer = byteBuffer.asFloatBuffer();
		verticesBuffer.put(new float[]{	x-width/2,y+height/2,0,0,
             							x+width/2,y+height/2,0,0,
             							x+width/2,y-height/2,0,0,
             							x-width/2,y-height/2,0,0});
		verticesBuffer.flip();
	}
	
	public void mapTextureUV(GameImage gameImage){
		float uTopLeft=gameImage.getU1();
		float vTopLeft=gameImage.getV1();
		float uBottomRight=gameImage.getU2();
		float vBottomRight=gameImage.getV2();
		verticesBuffer.position(2);
		verticesBuffer.put(uTopLeft);
		verticesBuffer.position(3);
		verticesBuffer.put(vTopLeft);
		verticesBuffer.position(6);
		verticesBuffer.put(uBottomRight);
		verticesBuffer.position(7);
		verticesBuffer.put(vTopLeft);
		verticesBuffer.position(10);
		verticesBuffer.put(uBottomRight);
		verticesBuffer.position(11);
		verticesBuffer.put(vBottomRight);
		verticesBuffer.position(14);
		verticesBuffer.put(uTopLeft);
		verticesBuffer.position(15);
		verticesBuffer.put(vBottomRight);
	}
	
	
	public void rebuildMesh(){
		setIndices();
		setVertices();
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		float xPlus=x+width/2;
		float xMinus=x-width/2;
		verticesBuffer.position(0);
		verticesBuffer.put(xMinus);
		verticesBuffer.position(4);
		verticesBuffer.put(xPlus);
		verticesBuffer.position(8);
		verticesBuffer.put(xPlus);
		verticesBuffer.position(12);
		verticesBuffer.put(xMinus);
		verticesBuffer.position(0);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		float yPlus=y+height/2;
		float yMinus=y-height/2;
		verticesBuffer.position(1);
		verticesBuffer.put(yPlus);
		verticesBuffer.position(5);
		verticesBuffer.put(yPlus);
		verticesBuffer.position(9);
		verticesBuffer.put(yMinus);
		verticesBuffer.position(13);
		verticesBuffer.put(yMinus);
		verticesBuffer.position(0);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public FloatBuffer getVerticesBuffer() {
		return verticesBuffer;
	}

	public ShortBuffer getTrianglesBuffer() {
		return trianglesBuffer;
	}
	
	
}
