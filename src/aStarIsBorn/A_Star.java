package aStarIsBorn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.BiFunction;

import graphe.Graphe;
import graphe.Sommet;

/**
 * Jackson Maine (Bradley Cooper) se produit dans des concerts qui se vendent bien – tout en ayant des acouphènes assez fréquents et des addictions à l'alcool et à la drogue qu'il cache au public. Son principal soutien et manager n'est autre que son demi-frère aîné Bobby (Sam Elliott) qui s'occupe de lui. Ally Campana (Lady Gaga) est une jeune autrice-compositrice qui travaille comme serveuse avec son ami Ramon (Anthony Ramos), tout en chantant dans un bar de drag queens. Après un concert au Coachella Festival, Jackson arrive dans ce même bar pour boire un verre et découvre Ally qui chante La Vie en rose. Impressionné par son talent, il partage un verre avec elle. Ally lui révèle qu'elle n'a jamais poursuivi de carrière professionnelle car les gens de l'industrie lui ont trop souvent dit qu'elle avait un nez trop grand et qu'elle n'arriverait jamais à rien. Jackson lui avoue trouver cela séduisant et lui propose d'écrire des chansons ensemble. Elle le ramène chez elle, où elle vit avec son père veuf, Lorenzo (Andrew Dice Clay), qui dirige un service de chauffeurs avec ses amis. Jackson demande à Ally de venir à son concert le soir même, mais elle refuse malgré l'insistance de Lorenzo. Elle change finalement d'avis et emmène Ramon avec elle. Jackson demande à Ally de chanter avec lui sur scène. Après hésitation, elle cède et finit par être adulée sur les réseaux sociaux grâce à son interprétation de Shallow.
 * Jackson et Ally partent sur les routes, chantent ensemble à plusieurs concerts et commencent à se lier. Après l'évanouissement de Jackson à cause de son alcoolisme, Bobby dit à Ally qu'elle doit être très prudente avec lui. Jackson emmène Ally en Arizona voir une ferme qu'il a achetée pour Bobby et où son père est enterré, mais découvre que Bobby a vendu le terrain et que le corps de leur père a été emporté dans une tornade. Furieux de sa trahison, Jackson frappe Bobby, qui décide de démissionner de son poste de manager.
 * À la fin d'un concert, Ally rencontre Rez (Rafi Gavron), un producteur qui lui propose un contrat après l'avoir entendu chanter Always Remember Us This Way. Malgré le fait qu'il soit visiblement contrarié, Jackson soutient sa décision et aide Ally à traverser les premières épreuves de sa notoriété. Pendant un de ces concerts, Rez réprimande Ally sur sa décision d'annuler le numéro de danse initialement prévu et lui suggère de se teindre les cheveux en blond platine, ce qu'elle refuse. Supposé venir au concert, Jackson, ivre, s'évanouit en centre-ville et est aidé par Noodles (Dave Chappelle), un de ses amis d'enfance qui l'autorise à rester temporairement chez lui. Ally le rejoint et lui avoue avoir de plus en plus de mal à supporter ses addictions. Jackson lui fait alors sa demande en mariage en créant une bague avec un bout de corde de guitare, et ils se marient le jour même.
 * Pendant qu'Ally se produit dans l'émission Saturday Night Live, Bobby arrive et se réconcilie avec Jackson. Plus tard, Jackson exprime sa réprobation face à une des chansons d'Ally, qui perd en authenticité et se transforme petit à petit en produit commercial. La traitant de « laide », ils se disputent puis essaient rapidement de laisser le problème derrière eux. Sous l'emprise de l'alcool et des opiacés, Jackson se produit aux Grammy Awards avec un hommage à Roy Orbison. Ally gagne le Grammy de la meilleure nouvelle artiste. Pendant qu'elle prononce son discours, Jackson arrive sur scène au même moment mais s'humilie en s'urinant dessus et en perdant connaissance. Lorenzo emmène Jackson dans les coulisses, le réprimande et le met sous une douche pour le faire dessoûler. À la suite de cet incident, Jackson part en cure de désintoxication.
 * Ally lui rend visite et Jackson s'excuse pour son comportement. Avant le retour de Jackson chez lui à la fin de sa cure, Ally propose à Rez de faire une tournée commune avec son mari, désormais sobre et remis sur pied. Mais le manager refuse catégoriquement. Hors d'elle, Ally lui dit qu'elle préfère annuler la tournée. Rez se rend à leur domicile et dit en privé à Jackson qu'il finira par replonger tôt ou tard et qu'il pourrait ruiner la carrière d'Ally à cause de ses addictions, mais qu'elle n'osera jamais lui dire. Celle-ci ment à Jackson et lui explique qu'elle a annulé sa tournée européenne pour enregistrer son deuxième album. Lorsqu'elle part pour son dernier show, elle lui demande de la rejoindre sur scène pour chanter ensemble. Il accepte. Mais après le départ d'Ally, Jackson prend des pilules et se suicide en se pendant, comme il avait tenté de le faire durant sa jeunesse.
 * Ally demeure inconsolable malgré les efforts de Lorenzo et de Ramon pour l'aider. Bobby lui explique que la mort de Jackson n'est pas sa faute mais celle de Jackson lui-même. Elle s'approprie une chanson que Jackson avait écrite mais jamais présentée en public, I'll Never Love Again1, et décide de la chanter à une cérémonie de commémoration, au Shrine Auditorium, où elle se présente elle-même comme Ally Maine. Elle se remémore le moment où Jackson la lui avait présentée chez eux et regarde vers le haut, une larme à l'œil.
 */

public class A_Star {
	
	Heuristique heuristique;

    public A_Star(Heuristique heuristique) {
		super();
		this.heuristique = heuristique;
	}

	void resolve(Graphe g){

        Map<Sommet,AStarSommetInfo> mappedSommets = new HashMap<>();

        for (Sommet s : g.allSommets()){
            AStarSommetInfo sommetInfo = new AStarSommetInfo();

            if (s == g.getStart()){
                sommetInfo.minDist = 0;
                sommetInfo.hDist = heuristique.heuristique.apply(s,g.getGoal());
            }

            mappedSommets.put(s, sommetInfo);
        }

        PriorityQueue<Sommet> discoveredNodes = new PriorityQueue<>(
                Comparator.comparingDouble(sommet -> mappedSommets.get(sommet).hDist)
        );

        discoveredNodes.add(g.getStart());

        while (!discoveredNodes.isEmpty()){
            Sommet current = discoveredNodes.poll();

            if (current == g.getGoal())
                return;

            for (Sommet adjacent : current.getAdjacents()){
                double newMinDist = mappedSommets.get(current).minDist + current.getFlightDistTo(adjacent);
                if (Double.compare(newMinDist, mappedSommets.get(adjacent).minDist) < 0 ){
                    mappedSommets.get(adjacent).from = current;
                    mappedSommets.get(adjacent).minDist = newMinDist;
                    mappedSommets.get(adjacent).hDist = newMinDist + heuristique.heuristique.apply(adjacent,g.getGoal());

                    if (!discoveredNodes.contains(adjacent)){
                        discoveredNodes.add(adjacent);
                    }
                }
            }
        }
    }

    List<Sommet> getPath(Map<Sommet,AStarSommetInfo> infos, Sommet current){
        List<Sommet> path = new ArrayList<>();
        path.add(current);
        while (current != null){
            current = infos.get(current).from;
            path.add(current);
        }
        return path;
    }


    static class AStarSommetInfo implements Comparable<AStarSommetInfo>{
        Sommet from;
        double minDist;
        double hDist;

        public AStarSommetInfo() {
            this.from = null;
            minDist = Double.MAX_VALUE;
            hDist = Double.MAX_VALUE;
        }

        @Override
        public int compareTo(AStarSommetInfo o) {
            return Double.compare(this.hDist,o.hDist);
        }
    }
   
}

