# Approche Dynamic Programming (DP)

## Introduction

L'approche Dynamic Programming (DP) est une méthode algorithmique qui résout le problème de la somme de sous-ensembles (Subset Sum Problem - SSP) en utilisant la mémoire pour stocker les résultats intermédiaires et éviter les calculs redondants.

## Principe de Fonctionnement

1. **Tableau de Résultats** : L'algorithme utilise un tableau pour stocker les solutions des sous-problèmes. Chaque entrée du tableau représente la possibilité d'atteindre une somme donnée avec un sous-ensemble des éléments.

2. **Construction Itérative** : L'algorithme remplit le tableau de manière itérative, en ajoutant chaque élément à tous les sous-ensembles possibles déjà calculés.

3. **Solution Optimale** : La solution au problème est trouvée en vérifiant si la somme cible peut être atteinte à la fin du processus.

## Avantages

- Garantit une solution optimale.
- Efficace pour des instances de taille modérée.

## Inconvénients

- Peut consommer beaucoup de mémoire pour des instances de grande taille.
- La complexité temporelle peut être élevée pour des ensembles avec de nombreux éléments.

## Conclusion

L'approche DP est une méthode robuste pour résoudre le SSP, particulièrement adaptée aux problèmes où la mémoire est moins contraignante que le temps de calcul.
