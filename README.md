# Tp4-systemes-distribues
Ce projet met en œuvre le modèle **CQRS (Command Query Responsibility Segregation)** pour la gestion des comptes bancaires. Il intègre des événements clés pour la **création, activation, crédit et débit des comptes**, tout en s’appuyant sur un **Event Store** pour tracer toutes les modifications et activités associées.

## Description du Projet

L'approche CQRS est utilisée pour séparer les **opérations de commande** (création, activation, crédit, débit) des **opérations de lecture des données**. Le système exploite un Event Store pour conserver un historique détaillé des événements et refléter l’état actuel des comptes.

## Fonctionnalités Principales

- **Création de Compte** : Permet de créer un compte avec un identifiant unique et un solde initial défini.  
- **Activation de Compte** : Active un compte nouvellement créé.  
- **Crédit de Compte** : Ajoute des fonds au compte existant.  
- **Débit de Compte** : Retire des fonds du compte, sous réserve d’un solde suffisant.

## Démonstration
### 1. Création de Compte
![image](https://github.com/user-attachments/assets/036d8ee7-d8df-48b6-8e45-0569468305fd)
### 2. Affichage des Comptes
![image](https://github.com/user-attachments/assets/b4c3fb4e-be90-4f3f-8b6f-60bcdd6ea959)
### 3. Crédit de Compte
![image](https://github.com/user-attachments/assets/d573f739-361a-4268-8df6-3cf78dbbaada)
### 4. Débit de Compte
![image](https://github.com/user-attachments/assets/b5db39d5-16ec-4167-92f2-9c4e7bfa824e)
### 5. Affichage des évènements
![image](https://github.com/user-attachments/assets/647dfa81-7478-46fa-8515-186a032f641c)

## Conclusion
Ce projet de gestion de comptes basé sur le modèle CQRS illustre l'application des principes de conception orientée événements pour une gestion efficace des comptes. Chaque action effectuée sur un compte génère un événement, lequel est enregistré dans l’Event Store et synchronisé avec la base de données MySQL. Cela garantit un historique complet des opérations tout en maintenant la cohérence des données au sein de l’application.


