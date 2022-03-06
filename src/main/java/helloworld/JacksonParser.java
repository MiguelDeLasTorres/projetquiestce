package helloworld;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class JacksonParser {
	//lis les bytes du fichier
	public Object ChargerPersonnage(String datapath)  throws IOException {

		byte[] jsonData = Files.readAllBytes(Paths.get(datapath));


		//create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

		//convert json string to object
		Personnage[] c = objectMapper.readValue(jsonData, Personnage[].class);
		return c;
	}	
	
	public Object ChargerAttributs(String datapath)  throws IOException {

		byte[] jsonData = Files.readAllBytes(Paths.get(datapath));//lis les bytes du fichier
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);//crée une instance d'objectmapper


		Attribut[] c = objectMapper.readValue(jsonData, Attribut[].class);//converti JSON String en object
		return c;
	}
	
	public Object ChargerPartie(String datapath)  throws IOException {

		byte[] jsonData = Files.readAllBytes(Paths.get(datapath));//lis les bytes du fichier
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);//crée une instance d'objectmapper


		Game c = objectMapper.readValue(jsonData, Game.class);//converti JSON String en object
		return c;
	}
	
	public void EnregistrerPersonnages(String datapath, Object o) throws IOException {		

		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.writeValue(new File(datapath), o);

	}	
	
	public void EnregistrerAttributs(String datapath, Object o) throws IOException {		

		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.writeValue(new File(datapath), o);
	}
	
	public void EnregistrerPartie(String datapath, Object o) throws IOException {		

		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.writeValue(new File(datapath), o);
	}


}


