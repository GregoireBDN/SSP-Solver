# SSP Solver

Ce projet implémente deux approches pour résoudre le problème de la somme de sous-ensembles (Subset Sum Problem - SSP) :

- **Branch and Prune (BP)** : Une méthode qui réduit l'espace de recherche en éliminant les branches non prometteuses.
- **Programmation Dynamique (Dynamic Programming)** : Une approche qui utilise la mémoire pour stocker les résultats intermédiaires et éviter les calculs redondants.

Chaque approche est disponible avec deux implémentations différentes (V1 et V2) des structures de données.

## Prérequis

- **Java 17** ou supérieur
- **Maven 3.6** ou supérieur

## Structure du Projet

```
ssp-solver/
├── src/
│ ├── main/java/fr/ssp/
│ │ ├── api/ # Interface Subset
│ │ ├── impl/ # Implémentations V1 et V2
│ │ └── solver/ # Classes de résolution SSP
│ └── test/java/ # Tests unitaires
├── pom.xml # Configuration Maven
└── README.md # Documentation
```

## Installation

1. **Clonez le dépôt :**

   ```bash
   git clone [URL_DU_DEPOT]
   ```

2. **Compilez le projet :**

   ```bash
   mvn clean install
   ```

## Utilisation

Le programme peut être exécuté de deux manières :

1. **Avec une taille n (génère une instance aléatoire) :**

   ```bash
   mvn exec:java -Dexec.mainClass="fr.ssp.SSP" -Dexec.args="15"
   ```

2. **Avec un fichier d'entrée :**

   ```bash
   mvn exec:java -Dexec.mainClass="fr.ssp.SSP" -Dexec.args="chemin/vers/fichier.txt"
   ```

### Format du fichier d'entrée :

```
n        # nombre d'éléments
target   # valeur cible
v1       # premier élément
v2       # deuxième élément
...
vn       # n-ième élément
```

## Comparaison des Implémentations

Le programme compare automatiquement :

- Les performances des deux approches (BP vs DP)
- Les deux implémentations (V1 vs V2)
- La cohérence des résultats entre les différentes versions

## Tests

Pour lancer les tests :

```bash
mvn test
```

## Auteurs

- **Gregoire Bodin** - [Profil GitHub](https://github.com/gregoire-bodin)
