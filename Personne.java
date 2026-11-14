package jeux2014;

public class Personne {
	private String nom;
	private int age;
	private int taille;
	
	public Personne(String nom,int age,int taille) {
		this.nom=nom;
		this.age=age;
		this.taille=taille;
	}
	public int getAge() {
		return this.age;
	}
	
}

  interface ConditionAcces{
	 	public boolean accesPossible(Personne p);
}
 
  class ConditionAge implements ConditionAcces {
	    private int ageMin;
	    
	    public ConditionAge(int ageMin) {
	        this.ageMin = ageMin;
	    }

	    public int getAgeMin() {
	        return ageMin;
	    }
	    
	    public boolean accesPossible(Personne p) {
	        return p.getAge() > ageMin;
	    }
	}
   class AccesInterditException extends Exception {
	    public AccesInterditException(String message) {
	        super(message);
	    }
	}
    class Attraction {
	    private String nom;
	    private float cout;               
	    private ConditionAge condition;   
	    private int nbPersonnes = 0;      
	    private float recette = 0;        

	    public Attraction(String nom, float cout, ConditionAge condition) {
	        this.nom = nom;
	        this.cout = cout;
	        this.condition = condition;
	    }

	    public String getNom() {
	        return nom;
	    }

	    public float getRecette() {
	        return recette;
	    }

	    public void affiche() {
	        System.out.println("Attraction : " + nom);
	        System.out.println("Coût : " + cout + " DT");
	        System.out.println("Age minimal : " + condition.getAgeMin());
	        System.out.println("Nombre de personnes : " + nbPersonnes);
	        System.out.println("Recette : " + recette + " DT");
	    }

	    public void utilise(Personne p) throws AccesInterditException {
	        
	        if (!condition.accesPossible(p)) {
	            throw new AccesInterditException(
	                "Accès interdit à l'attraction " + nom);
	        }
	        nbPersonnes++;        
	        recette += cout;      
	    }
	}
     class ParcAttraction {
        private Attraction[] lesAttractions; 
        private String nom;
        private int nb_attraction;           
        public int taille;                   

        
        public ParcAttraction(String nom, int taille) {
            this.nom = nom;
            this.taille = taille;
            this.lesAttractions = new Attraction[taille]; 
            this.nb_attraction = 0;                       
        }

        
        public void ajouteAttraction(Attraction att) {
            if (nb_attraction < taille) {
                lesAttractions[nb_attraction] = att; 
                nb_attraction++;                     
            } else {
                System.out.println("Erreur : le parc est plein, impossible d'ajouter "
                                   + att.getNom());
            }
        }

        
        public float getRecette() {
            float total = 0;
            
            for (int i = 0; i < nb_attraction; i++) {
                total += lesAttractions[i].getRecette();
            }
            return total;
        }

        
        public void affiche() {
            System.out.println("Parc : " + nom);
            for (int i = 0; i < nb_attraction; i++) {
                lesAttractions[i].affiche();
                System.out.println("-----------------");
            }
        }
    }
      class Main {
    	    public static void main(String[] args) {
    	        ParcAttraction parc = new ParcAttraction("Carthage Land", 2);
    	        parc.ajouteAttraction(new Attraction("Grande roue", 5.0f, new ConditionAge(10)));

    	        Personne enfant = new Personne("Ali", 7, 120);
    	        Personne adulte = new Personne("Sami", 25, 180);

    	        
    	      
    	        Attraction roue = new Attraction("Montagnes russes", 8.0f, new ConditionAge(12));
    	        parc.ajouteAttraction(roue);
    	        try {
    	            roue.utilise(adulte);   
    	            roue.utilise(enfant);  
    	        } catch (AccesInterditException e) {
    	            System.out.println(e.getMessage());
    	        }
    	        parc.affiche();
    	        System.out.println("Recette totale : " + parc.getRecette());
    	    }
    	}
