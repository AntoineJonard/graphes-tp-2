package aStarIsBorn.GUI;

import aStarIsBorn.A_Star;
import aStarIsBorn.A_StarListener;
import aStarIsBorn.Heuristique;
import graphe.Graphe;
import graphe.Sommet;

import java.util.List;

public class A_StarAsync extends Thread implements A_StarListener{

    private A_StarListener listener;
    private A_Star aStar;
    private Graphe g;

    private long sleepTime;

    public A_StarAsync(A_StarListener listener, Graphe g, Heuristique heuristique) {
        super();
        this.listener = listener;
        this.aStar = new A_Star(heuristique);
        aStar.setListener(this);
        this.g = g;
        this.sleepTime = (long) (1/g.getStart().getFlightDistTo(g.getGoal())*1000);
    }

    @Override
    public void run() {
        super.run();

        aStar.resolve(g);

    }

    @Override
    public void onNewCurrent(Sommet current) {
        listener.onNewCurrent(current);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onNewDiscovered(Sommet discovered) {
        listener.onNewDiscovered(discovered);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onResolutionFound(List<Sommet> resolution) {
        listener.onResolutionFound(resolution);
    }
}
