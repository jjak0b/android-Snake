package com.game.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;

import com.game.framework.exceptions.ResourceException;
import com.game.snake.MainActivity;

public class IOManager {
	
    private static AssetManager 	assets;
    private static String 			externalStoragePath;
	
    private static InputStream assetRef;
    private static InputStream fileRRef;
    private static OutputStream fileWRef;
    
    static{
    	
        assets = MainActivity.context.getAssets();
        externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
    }
    
    public static InputStream openAsset(String fileName) throws ResourceException  {
    	try {
			assetRef = assets.open(fileName);
		} catch (IOException e) {
			throw new ResourceException("Unable to open asset file "+fileName);
		}
        return assetRef;
    }

    public static  InputStream openRFile(String fileName) throws ResourceException {
        try {
			fileRRef = new FileInputStream(externalStoragePath + fileName);
		} catch (FileNotFoundException e) {
			throw new ResourceException("Unable to open file "+fileName);
		}
        return fileRRef;
    }

    public static  OutputStream openWFile(String fileName)  throws ResourceException {
        try {
			fileWRef= new FileOutputStream(externalStoragePath + fileName);
		} catch (FileNotFoundException e) {
			throw new ResourceException("Unable to open file "+fileName);
		}
        return fileWRef;
    }
    
    public static  void closeAsset(){
    	if(assetRef!=null){
    		try {
				assetRef.close();
			} catch (IOException e) {}
    	}
    }
    
    public static  void closeRFile(){
    	if(fileRRef!=null){
    		try {
				fileRRef.close();
			} catch (IOException e) {}
    	}
    }
    
    public static  void closeWFile(){
    	if(fileWRef!=null){
    		try {
				fileWRef.close();
			} catch (IOException e) {}
    	}
    }
}
