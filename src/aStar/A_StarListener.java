package aStar;

import graphe.Sommet;

import java.util.List;

public interface A_StarListener {
    void onNewCurrent(Sommet current);
    void onNewDiscovered(Sommet discovered);
    void onResolutionFound(List<Sommet> resolution);
}
