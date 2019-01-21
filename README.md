# Muse App Android
Cette application a été réalisé dans le cadre de l'IUT lors d'un projet tuteuré. Le but de l'application est d'afficher des captures de l'activité électrique du cerveau (captures réalisées grâce au casque Muse).

# Screenshots de l'application

<img src="screenshot/Screen_8.png" width="400" height="250"> <img src="screenshot/Screen_3.png" width="400" height="250">
<img src="screenshot/Screen_5.png" width="400" height="250"> <img src="screenshot/Screen_7.png" width="400" height="250">
<img src="screenshot/Screen_9.png" width="400" height="250">

# Fonctionnement de l'application

### Ecran d'acceuil
*screenshots 1*

Dans cet écran nous pouvons retrouver la liste de toutes les captures que vous avez réalisé. Vous pouvez ajouter une capture en cliquant sur le bouton "+" en bas à droite de l'écran. Vous pouvez aussi retrouver les détails des anciennes captures en cliquant sur l'une de vos captures.

### Ecran de connexion de l'appareil
*screenshots 2*

Cet écran permet de connecter le casque Muse à l'application.
Allumez le casque puis cliquez sur le bouton "rafraichir", quand le nom du casque apparaît sur l'écran, cliquez sur "suivant" pour poursuivre l'ajout de la capture.

### Ajout d'une capture
*screenshots 3 - 4*

La première vue permet d'ajouter des informations sur la capture que vous allez réaliser, lorsque les détails seront soumis, vous pourrez passer à l'enregistrement des données du casque.
Pour se faire vous devrez cliquer sur le bouton "enregistrer", c'est à ce moment que l'application enregistre les données renvoyées par le casque.
Si vous voulez arreter l'enregistrement, il faut cliquer sur le bouton "Pause", à ce moment là vous pouvez recommencer une capture en cliquant sur le bouton "recommencer", reprendre la capture en cours en cliquant sur le bouton "enregistrer" ou valider la capture en cliquant sur le bouton "valider".

### Details d'une capture enregistrer
*screenshots 5*

Dans cet écran vous pouvez retrouver la visualisation des données par le biais de trois graphiques, vous retrouverez aussi le titre et la description qu'il est possible de modifier. Puis si vous cliquez sur le bouton "Plus d'options" vous pouvez exporter la capture au format CSV et vous pouvez supprimer la capture.


# Structure du projet
Le code est structuré de manière à bien différencier les couches de traitement des données et la présentation.

- data
  - manager : Traitement de la récupération des données et des fichiers JSON
  - repository : Appels de données nécessaires pour le manager
  - model : Classes modèles de l'application, contiennent les propriétés des objets de l'application
  
- presentation
  - presenter : Gestion des erreurs et appels aux méthodes get du repository
  - ui
    - activity : Activités de l'application, List et Detail, récupèrent les données ; implémentent leurs interfaces dans view
    - view : Interfaces des activités
    - adapter : Les classes "adapter" des listes des captures
    - viewholder : Les ViewHolder des adapater pour l'affichage des données dans le layout
