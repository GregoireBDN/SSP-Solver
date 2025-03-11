# Projet Subset Sum Problem (SSP)

## Description du Problème

Le problème de la somme de sous-ensembles (Subset Sum Problem ou SSP) est un problème classique d'informatique : étant donné un ensemble d'entiers et une valeur cible, trouver un ou plusieurs sous-ensembles dont la somme est égale à la valeur cible.

## Structure du Projet

```mermaid
classDiagram
    class SSP {
        -long target
        -long[] original
        +SSP(int n)
        +SSP(String filename)
        +SSP(long[] values, long target)
        +long totalSum()
        +Subset bp(SubsetFactory factory)
        +Subset dynprog(SubsetFactory factory)
    }

    class Subset {
        <<interface>>
        +long getSum()
        +int getCardinality()
        +boolean satisfiesBounds(long lb, long ub)
        +void clone(Subset other)
        +void encapsulate(Subset other)
        +String show()
    }

    class SubsetFactory {
        <<enum>>
        V1
        V2
        +Subset createSubset()
        +Subset createSubset(long value)
        +Subset createSubset(Subset other, long value)
    }

    class SubsetV1 {
        -Set<HashSet<Long>> set
        -long sum
        +SubsetV1()
        +SubsetV1(long value)
        +SubsetV1(SubsetV1 subset, long value)
    }

    class SubsetV2 {
        -Set<SubsetV2> set
        -long sum
        -int cardinality
        +SubsetV2()
        +SubsetV2(long value)
        +SubsetV2(SubsetV2 subset, long value)
    }

    class SSPSolver {
        <<abstract>>
        #long target
        #long[] original
        #SubsetFactory factory
        +abstract Subset solve()
    }

    class BranchAndPruneSolver {
        +Subset solve()
        -void bpRec(int i, Subset partial, long total, Subset solutions)
    }

    class DynamicProgrammingSolver {
        +Subset solve()
    }

    Subset <|.. SubsetV1
    Subset <|.. SubsetV2
    SSPSolver <|-- BranchAndPruneSolver
    SSPSolver <|-- DynamicProgrammingSolver
    SSP --> SubsetFactory
    SubsetFactory --> SubsetV1
    SubsetFactory --> SubsetV2
    SSP --> SSPSolver
```

## Organisation des Packages

```mermaid
graph TD
    A[fr.ssp] --> B[SSP.java]
    A --> C[api]
    C --> D[Subset.java]
    A --> E[impl]
    E --> F[SubsetFactory.java]
    E --> G[SubsetV1.java]
    E --> H[SubsetV2.java]
    A --> I[solver]
    I --> J[SSPSolver.java]
    I --> K[BranchAndPruneSolver.java]
    I --> L[DynamicProgrammingSolver.java]
```

## Implémentations de Subset

Le projet propose deux implémentations différentes de l'interface `Subset` :

### SubsetV1

- Basée sur `Set<HashSet<Long>>`
- Chaque HashSet représente un sous-ensemble d'entiers dont la somme est égale à la cible
- Simple à comprendre et à manipuler

| Structure            | Description                                        |
| -------------------- | -------------------------------------------------- |
| `SubsetV1`           | Classe principale contenant un ensemble de HashSet |
| `Set<HashSet<Long>>` | Collection de sous-ensembles d'entiers             |
| `HashSet<Long> #1`   | Premier sous-ensemble (ex: {1, 2, 5})              |
| `HashSet<Long> #2`   | Deuxième sous-ensemble (ex: {3, 5})                |
| `HashSet<Long> #n`   | Autres sous-ensembles possibles                    |

### SubsetV2

- Structure récursive utilisant `Set<SubsetV2>`
- Chaque sous-ensemble contient des références à d'autres sous-ensembles
- Plus complexe mais potentiellement plus efficace pour certaines opérations

```mermaid
graph TD
    A[SubsetV2] --> B["Set<SubsetV2>"]
    B --> C["SubsetV2 #1"]
    B --> D["SubsetV2 #2"]
    C --> E["Set<SubsetV2>"]
    D --> F["Set<SubsetV2>"]
    E --> G["SubsetV2 #3"]
    F --> H["SubsetV2 #4"]
    F --> I["SubsetV2 #5"]
```

## Algorithmes de Résolution

### Branch and Prune (bp)

```mermaid
graph TD
    A[Début] --> B[Créer Subset vide]
    B --> C[Calculer somme totale]
    C --> D[Appeler bpRec avec i=0]
    D --> E{partial.sum = target?}
    E -- Oui --> F[Ajouter aux solutions]
    E -- Non --> G{i = taille tableau?}
    G -- Oui --> H[Retourner]
    G -- Non --> I[total -= original i]
    I --> J[Appel récursif sans original i]
    I --> K[Appel récursif avec original i]
    J --> L[Retourner solutions]
    K --> L
```

### Programmation Dynamique (dynprog)

```mermaid
graph TD
    A[Début] --> B[Créer Map Sums]
    B --> C[Ajouter Subset vide avec clé 0]
    C --> D[Calculer somme totale]
    D --> E[Pour chaque élément i]
    E --> F[Créer nouvelles listes]
    F --> G[total -= original i]
    G --> H[Pour chaque somme existante]
    H --> I[Créer nouveau Subset]
    I --> J[Ajouter à newSets]
    J --> K[Pour chaque nouveau Subset]
    K --> L{Sums contient somme?}
    L -- Oui --> M[Encapsuler]
    L -- Non --> N{Satisfait les bornes?}
    N -- Oui --> O[Ajouter à Sums]
    N -- Non --> P[Ignorer]
    M --> E
    O --> E
    P --> E
    E --> Q[Retourner Sums.get target]
```

## Comparaison des Approches

### Structures de données

| Aspect         | SubsetV1                    | SubsetV2                        |
| -------------- | --------------------------- | ------------------------------- |
| Représentation | Simple et directe           | Récursive et complexe           |
| Opérations     | Directes sur les ensembles  | Via des références              |
| Mémoire        | Potentiellement gourmande   | Plus efficace                   |
| Débogage       | Facile                      | Plus complexe                   |
| Performance    | Bonne pour petits ensembles | Meilleure pour grands ensembles |

### Algorithmes

| Aspect      | Branch and Prune               | Programmation Dynamique             |
| ----------- | ------------------------------ | ----------------------------------- |
| Complexité  | O(2^n) dans le pire cas        | O(n \* somme_totale)                |
| Efficacité  | Bonne pour petites instances   | Stable pour instances moyennes      |
| Mémoire     | Moins gourmand                 | Peut nécessiter beaucoup de mémoire |
| Cas d'usage | Solutions proches de la racine | Problèmes généraux                  |

## Utilisation

```java
// Créer une instance SSP (taille 10)
SSP ssp = new SSP(10);

// Résoudre avec Branch and Prune en utilisant SubsetV1
Subset solBP1 = ssp.bp(SubsetFactory.V1);

// Résoudre avec programmation dynamique en utilisant SubsetV2
Subset solDP2 = ssp.dynprog(SubsetFactory.V2);

// Afficher les résultats
System.out.println(solBP1.show());
System.out.println(solDP2.show());

// Comparer les résultats
System.out.println("Les solutions sont identiques : " + solBP1.equals(solDP2));
```

## Exécution du Programme Principal

```mermaid
sequenceDiagram
    participant Main
    participant SSP
    participant BranchAndPruneSolver
    participant DynamicProgrammingSolver
    participant SubsetV1
    participant SubsetV2

    Main->>SSP: new SSP(10)
    Main->>SSP: bp(SubsetFactory.V1)
    SSP->>BranchAndPruneSolver: solve()
    BranchAndPruneSolver->>SubsetV1: createSubset()
    BranchAndPruneSolver->>BranchAndPruneSolver: bpRec()
    BranchAndPruneSolver-->>SSP: Solution
    SSP-->>Main: solBP1

    Main->>SSP: dynprog(SubsetFactory.V2)
    SSP->>DynamicProgrammingSolver: solve()
    DynamicProgrammingSolver->>SubsetV2: createSubset()
    DynamicProgrammingSolver-->>SSP: Solution
    SSP-->>Main: solDP2

    Main->>Main: Comparer solBP1 et solDP2
```

## Analyse Comparative

### Avantages de SubsetV1

- Représentation plus simple et intuitive
- Opérations directes sur les ensembles d'entiers
- Facilité de débogage et compréhension

### Avantages de SubsetV2

- Structure récursive qui peut être plus efficace en mémoire
- Meilleure performance pour certaines opérations comme `contains` et `encapsulate`
- Potentiellement plus adaptée aux grands ensembles de données

### Comparaison des algorithmes

- Branch and Prune:
  - Plus efficace pour les petites instances ou celles où la solution est proche de la racine de l'arbre de recherche
  - Peut être très inefficace pour les grandes instances
- Programmation Dynamique:
  - Plus stable en termes de performance
  - Efficace pour des instances de taille moyenne
  - Peut nécessiter beaucoup de mémoire pour de grandes valeurs cibles

## Extensions Possibles

- Implémentation parallèle des algorithmes
- Optimisations supplémentaires (heuristiques, structures de données plus efficaces)
- Interface graphique pour visualiser les solutions et comparer les performances
- Adaptation pour traiter des instances très grandes avec des techniques d'approximation

## Conclusion

Ce projet démontre deux approches différentes pour résoudre le problème de la somme de sous-ensembles, ainsi que deux implémentations distinctes de la structure de données pour représenter les sous-ensembles. La comparaison des performances montre que chaque approche a ses avantages selon le contexte d'utilisation.
