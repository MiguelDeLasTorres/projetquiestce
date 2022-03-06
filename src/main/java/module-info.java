module fqsfsqsf {
	exports helloworld;

	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires transitive javafx.fxml;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.databind;
	
	opens helloworld to javafx.graphics, javafx.fxml, 
	com.fasterxml.jackson.databind, com.fasterxml.jackson.annotation, com.fasterxml.jackson.core;
}