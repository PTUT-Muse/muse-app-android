# Muse App Android
Cette application a été réalisé dans le cadre de l'IUT lors d'un projet tuteuré. Le but de l'application est d'afficher des captures de l'activité électrique du cerveau (captures réalisées grâce au casque Muse).


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
