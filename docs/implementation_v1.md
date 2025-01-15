# Implémentation V1

## Introduction

L'implémentation V1 est l'une des deux versions de structures de données utilisées pour résoudre le problème de la somme de sous-ensembles (Subset Sum Problem - SSP) dans ce projet. Elle se concentre sur la simplicité et la facilité d'utilisation.

## Caractéristiques

- **Structure de Données** : Utilise une structure de données simple, généralement basée sur des listes ou des tableaux, pour représenter les sous-ensembles.
- **Opérations** : Fournit des opérations de base pour :
  - Ajouter des éléments à un sous-ensemble.
  - Calculer la somme totale des éléments.
  - Déterminer la cardinalité (nombre d'éléments).
  - Vérifier l'égalité entre deux sous-ensembles.

## Avantages

- **Simplicité** : L'implémentation est facile à comprendre et à maintenir, ce qui la rend idéale pour les débutants ou pour des cas d'utilisation simples.
- **Rapidité de développement** : Grâce à sa simplicité, les modifications et les extensions peuvent être réalisées rapidement.

## Inconvénients

- **Performance** : Peut être moins efficace pour des ensembles de grande taille en raison de la simplicité de la structure de données.
- **Fonctionnalités limitées** : Moins optimisée pour des opérations complexes ou des cas d'utilisation avancés.

## Exemples d'Utilisation

```java
SubsetV1 subset = new SubsetV1();
subset.add(3);
subset.add(5);
System.out.println("Sum: " + subset.getSum()); // Affiche 8
```

## Conclusion

L'implémentation V1 est une solution simple et efficace pour des instances de taille modérée du SSP, particulièrement adaptée aux environnements où la simplicité et la rapidité de développement sont prioritaires.
