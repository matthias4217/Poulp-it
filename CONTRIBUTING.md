<<<<<<< HEAD
# Contribution #

## Conventions de code ##

  * We code in english t(-_-t) !
  * Cochez "Generate comments" quand vous créez une nouvelle classe SVP pour qu'on ait les mêmes templates.
  * On ne suit pas toutes les conventions Javadoc, qui sont vraiment trop extrêmes,
  		mais chaque classe, chaque méthode, chaque attribut est documenté...
  * ...sauf si c'est vraiment explicite.
  * LES METHODES SONT DOCUMENTEES AU DESSUS
  * On utilise @Override : l'annotation est à mettre au dessus d'une méthode surchargée afin d'éviter des erreurs
  * Raphaël : je mets "@@@" à un endroit où il y a un truc à faire



## Remarques ##

  * On va utiliser des ImageView, ça a l'air cool.
  * 


  
## Structure du programme ##

  * content : contient le squelette des éléments de jeu (exemples : classe GameObject, classe Player) + 
  		les listes des divers éléments (par exemples, les armes, les collectables, etc)
  * core : tout ce qui fait partie du coeur du programme.
  		On trouve notamment le Launcher, le GameEngine et le GraphicManager
  		* core.exceptions : les exceptions personnalisées qu'on a créé
  		* core.scripts : tous les scripts qui peuvent être associées à des GameObjects 
  		* core.util : toutes les classes utilitaires utilisées (exemple : Vector2)
  * resources : les ressources qui ne sont pas du code. Typiquement les sprites
  
  
  
 
## Git ##

  * branche master : stable, testée ; Pas de modifications directes, uniquement des *pull requests* à *merge*
  * En cas de résolution d'issue, branche «issue-<number>»
  * Pour le développement, créer une nouvelle branche à chaque fois, dont le nom est explicite,
  * puis ouvrir des *pull requests* sur Github pour fusionner à la branche master

  
  
  
  
=======
# Contribution #

## Conventions de code ##

  * We code in english t(-_-t) !
  * Cochez "Generate comments" quand vous créez une nouvelle classe SVP pour qu'on ait les mêmes templates.
  * On ne suit pas les conventions Javadoc, qui sont vraiment trop extrêmes,
  		mais chaque classe, chaque méthode, chaque attribut est documenté... 
  * ...sauf si c'est vraiment explicite.
  * On utilise @Override : l'annotation est à mettre au dessus d'une méthode surchargée afin d'éviter des erreurs
  * Raphaël : je mets "@@@" à un endroit où il y a un truc à faire



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
  * resources : les ressources qui ne sont pas du code. Typiquement les sprites
  
## À propos des éléments de jeu ##

  * GameManager est l'interface implémentée par tout élément faisant gérant un aspect du jeu.
  		Il est updaté chaque frame par le GameEngine.
  * GameObject est la superclasse pour tous les éléments de jeu qui :
  		- ont une position dans l'espace
  		- ont une apparence (méthode void render())
  	À un GameObject est attaché une liste de scripts décrivant sont comportement et étant exécutés chaque frame.
  * Obstacle est la classe qui désigne les éléments n'ayant pas de scripts et étant @@@
  
 
## Git ##

  * branche master : stable, testée ; Pas de modifications directes, uniquement des *pull requests* à *merge*
  * En cas de résolution d'issue, branche «issue-<number>»
  * Pour le développement, créer une nouvelle branche à chaque fois, dont le nom est explicite,
  * puis ouvrir des *pull requests* sur Github pour fusionner à la branche master

>>>>>>> refs/remotes/origin/develop
