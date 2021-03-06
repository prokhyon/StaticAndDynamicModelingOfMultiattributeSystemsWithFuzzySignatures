package net.prokhyon.modularfuzzy.common;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.util.UUID;

public class CommonUtils {

	public static Object deepClone(Object object) {

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static StringProperty initializeUUIDPropertyFromString(String uuid){

		StringProperty fuzzySetUUIDProp = null;
		try{
			String uuidStr = UUID.fromString(uuid).toString();
			fuzzySetUUIDProp = new SimpleStringProperty(uuidStr);
		} catch (Exception exception){
			fuzzySetUUIDProp = new SimpleStringProperty(UUID.randomUUID().toString());
		}
		return fuzzySetUUIDProp;
	}

	public static String getCanonicalPathFromFile(File file){
		String filePath;
		try {
			filePath = file.getCanonicalPath();
			return filePath;
		} catch (IOException e) {
			e.printStackTrace();
		}
		filePath = file.getAbsolutePath();
		return filePath;
	}

}
