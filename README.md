# SaeApplication
Bienvenue sur le repository git de notre projet SAE pour l'application Android. Ce projet est une 
extension de notre projet principal, SAE, qui permet de gérer les interventions en IME pour les 
éducateurs et les responsables.

# Prérequis
Pour utiliser cette application, vous devez préalablement installer le projet SAE disponible sur 
le repository git [SAE](https://forge.univ-lyon1.fr/p2102597/SAE).

# Installation
Pour installer l'application, vous pouvez télécharger le fichier APK en cliquant sur Télécharger
l'Appli Via l'APK dans le footer du site internet [ici par exemple](https://sae-ime.ovh/)
ou bien cloner ce repository et compiler l'application avec Android Studio.

# Utilisation
Pour l'utilisation de l'application, veuillez vous référer au manuel utilisateur disponible dans 
le dossier "manuel" du repository git SAEApplication. Ce manuel contient toutes les informations 
nécessaires à la prise en main de l'application.

N'oubliez pas de modifier le lien de l'API dans la classe API pour mettre l'adresse IP 
(ou le nom de domaine) de votre serveur où est hébergé le projet SAE.

remplacer
```
private static final String URL_BASE = "https://sae-ime.ovh/api";
```
par :
```
private static final String URL_BASE = "https://<"Votre Domaine ou IP">/api";
```

Nous espérons que cette application vous sera utile. N'hésitez pas à nous contacter pour toute 
question ou remarque.

Bonne utilisation!