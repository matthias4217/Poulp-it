# Contribution #

## Conventions de code ##

  * We code in english t(-_-t) !
  * Cochez "Generate comments" quand vous créez une nouvelle classe SVP pour qu'on ait les mêmes templates.
  * On ne suit pas les conventions Javadoc, qui sont vraiment trop extrêmes,
  		mais chaque classe, chaque méthode, chaque attribut est documenté... 
  * ...sauf si c'est vraiment explicite.
  * Les méthodes sont documentées au dessus
  * On utilise @Override : l'annotation est à mettre en en-tête de toute méthode surchargée



## Remarques ##

  * On va utiliser des ImageView, ça a l'air cool.
  * 


  
## Structure du programme ##

  * content : contient le squelette des éléments de jeu (exemples : classe GameObject, classe Player) + 
  		les listes des divers éléments (par exemples, les armes, les collectables, etc)
  * core : tout ce qui fait partie du coeur du programme.
  		On trouve notamment le Launcher, le GameEngine et le GraphicManager
                * Launcher : point d'entrée du jeu, exécuté au départ
  		* core.exceptions : les exceptions personnalisées qu'on a créé
  		* core.scripts : tous les scripts qui peuvent être associées à des GameObjects
  		* core.util : toutes les classes utilitaires utilisées (exemple : Vector2)
  * resources : les ressources qui ne sont pas du code ; typiquement les sprites et les audios


## !!! À propos du fonctionnement global !!! ##

  * Le Launcher initialise le programme, la fenêtre et crée une instance de GameEngine et de GraphicManager
  	Un Animation Timer se charge d'exécuter le GameEngine à chaque frame et
  		d'afficher ce qu'il faut à l'écran par le GraphicManager.
  * À chaque frame, le GameEngine est updaté, c'est-à-dire qu'il applique les effets de tous les GameManager,
    	puis qu'il update les GameObjects.  

  * GameManager est l'interface implémentée par tout élément faisant gérant un aspect du jeu (exemple, gestionnaire de score)
  * GameObject est la superclasse pour tous les éléments de jeu qui :
  		- ont une position dans l'espace
  		- ont une apparence (méthode void render())
  	À un GameObject est attaché une liste de scripts caractérisant son comportement.
  	Par défaut, l'update d'un GameObject signifie que tous ses scripts sont updatés, mais il est possible de personnaliser cela en surchargeant cette méthode.
  * Un script est un objet héritant de la classe MonoBehaviour. Il possède notamment les méthodes start() et update().
  

## Textures ##

  * Pour créer un thème de tiles :
    * Créer un dossier ayant le nom de votre thème dans src/resources/tiles
    * Ajouter les tiles avec les mêmes noms que le pack de base `simplegrey`, sans en oublier (sinon, bug du jeu)
    * Dans le champs `theme:` du fichier de level, y mettre le nom de votre pack

 
## Git ##

  * branche master : stable, testée ; Pas de modifications directes, uniquement des *pull requests* à *merge*
  * En cas de résolution d'issue, branche «issue-<number>»
  * Pour le développement, créer une nouvelle branche à chaque fois, dont le nom est explicite,
  * puis ouvrir des *pull requests* sur Github pour fusionner à la branche master

  
  