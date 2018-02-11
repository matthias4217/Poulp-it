package core;

import java.util.Collection;

import content.GameObject;

/* Manage the flow of the game: one instance */

public class GameEngine {
	
	Collection<GameObject> gameObjects;
	
	
	// Initialisation
	/* 
	for each gameObject in gameObjects:
		for each script in gameObject.scripts:
			script.Awake()
	for each gameObject in gameObjects:
		for each script in gameObject.scripts:
			script.Start()
	
	
	
	
	
	
	
	// Each frame
	for each gameObject in gameObjects:
		for each script in gameObject.scripts:
			script.Update()
	for each gameObject in gameObjects:
		for each script in gameObject.scripts:
			script.LateUpdate()
	
	
	
	
	
	*/
}
