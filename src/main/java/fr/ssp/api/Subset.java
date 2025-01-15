package fr.ssp.api;

/**
 * Interface définissant les opérations sur un sous-ensemble pour le problème
 * SSP.
 */
public interface Subset {

  /**
   * Retourne la somme des éléments du sous-ensemble.
   * 
   * @return la somme des éléments
   */
  long getSum();

  /**
   * Retourne la cardinalité (nombre d'éléments) du sous-ensemble.
   * 
   * @return le nombre d'éléments
   */
  int getCardinality();

  /**
   * Vérifie si la somme du sous-ensemble est comprise entre les bornes données.
   * 
   * @param lb borne inférieure
   * @param ub borne supérieure
   * @return true si la somme est comprise entre lb et ub inclus
   */
  boolean satisfiesBounds(long lb, long ub);

  /**
   * Vérifie si le sous-ensemble est égal à un autre sous-ensemble.
   * 
   * @param other le sous-ensemble à vérifier
   * @return true si le sous-ensemble est égal à other, false sinon
   */
  boolean equals(Object o);

  /**
   * Retourne le code de hachage du sous-ensemble.
   * 
   * @return le code de hachage
   */
  int hashCode();

  /**
   * Retourne une copie du sous-ensemble.
   * 
   * @return une copie du sous-ensemble
   */
  void clone(Subset other) throws IllegalArgumentException;

  /**
   * Encapsule le sous-ensemble avec un autre sous-ensemble.
   * 
   * @param other le sous-ensemble à encapsuler
   */
  void encapsulate(Subset other);

  /**
   * Retourne une représentation en chaîne de caractères du sous-ensemble.
   * 
   * @return une chaîne de caractères représentant le sous-ensemble
   */
  String toString();

  /**
   * Retourne une représentation en chaîne de caractères du sous-ensemble.
   * 
   * @return une chaîne de caractères représentant le sous-ensemble
   */
  String show();
}
