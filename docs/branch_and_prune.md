# Approche Branch and Prune (BP)

## Introduction

L'approche Branch and Prune (BP) est une méthode algorithmique utilisée pour résoudre le problème de la somme de sous-ensembles (Subset Sum Problem - SSP). Elle consiste à explorer l'espace de recherche de manière systématique tout en éliminant les branches non prometteuses pour réduire le nombre de calculs nécessaires.

## Principe de Fonctionnement

1. **Branching** : À chaque étape, l'algorithme divise le problème en sous-problèmes plus petits en choisissant d'inclure ou non un élément dans le sous-ensemble.

2. **Pruning** : L'algorithme élimine les branches qui ne peuvent pas mener à une solution optimale. Cela se fait en utilisant des critères de coupure basés sur des bornes inférieures et supérieures.

## Avantages

- Réduction significative de l'espace de recherche.
- Peut être très efficace pour des instances spécifiques du problème.

## Inconvénients

- Peut encore être coûteux en temps pour des instances de grande taille.
- La performance dépend fortement de la qualité des critères de coupure.

## Conclusion

L'approche BP est une technique puissante pour résoudre le SSP, surtout lorsqu'elle est combinée avec des heuristiques efficaces pour le pruning.
