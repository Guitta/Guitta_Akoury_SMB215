# Guitta_Akoury_SMB215
Notre projet, développé par Hayat Bourgi et moi, est composé de 4 applications : 

1- GestionDesBiens : une application web développée sous NetBeans, cette application gère les biens d'une société ou d'une organisation, le CNAM par exemple. 
Cette application traite la traçabilité interne et externe, la place de chaque bien est visible pour l'administrateur/utilisateur du système: la traçabilité interne : Centre, salle, personnel. La traçabilité externe : traçabilité du bien en cours du transport, d'un centre à l'autre par exemple.

L'application est sécurisée par la configuration d'un realm JDBC (l'authentification dans GlassFish)faisant référence à la base données MySQL contenant les tables d'authentification des utilisateurs. Des services web (RESTful Web Services) sont créés. A partir de ces services les opérations du CRUD sont programmées pour accéder aux différentes tables de la base de données à partir des services web et non pas des entités (Pages from entity classes). Les rapports sont générés en pdf en utilisant le "jspdf" plugin. Un QRCode est généré par item, pour référencer un item unique. 

2- SetLocation : une application android développée sous android studio, pour pouvoir accomplir la traçabilité externe (traçabilité du transport), cette application est développée pour pouvoir envoyer les coordonnées GPS, latitude et longitude, d'un téléphone doté du système d'exploitation android (celui du chauffeur par exemple) pour les sauvegarder dans une table "Location" située dans une application "GPSLocator" créée sous parse.com en vue de les partager avec l'application web "GestionDesBiens" pour positionner le bien en transport sur une carte géographique (maplocation.html) et avec l'application android "MyMap" pour le même but, que "maplocation.html" mais en version mobile. 

3- MyMap : une application android développée sous android studio, cette application permet de positionner l'item sur une carte géographique (map) sur un mobile android en se servant des coordonnées déjà enregistrées sous parse.com en vue d'assurer la traçabilité externe du bien.

4- GestionDesBiens : une application android  développée sous eclipse qui a les mêmes fonctions que l'application web qui porte le même nom (application 1) mais en version mobile.
