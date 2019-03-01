	public void calculTemps(ArrayList<Element> elements, ArrayList<ChaineDeProduction> chaines, Double[] niveau) {
		ArrayList<ChaineDeProduction> chaines_independantes = new ArrayList<>();
		ArrayList<ChaineDeProduction> chaines_entrees_limites = new ArrayList<>();
		HashMap<Element, Double> elements_entrees_des_chaines_limitees = new HashMap<>();
		//Liste des chaines se disputant une meme entree
		LinkedHashSet<ChaineDeProduction> chaine_disputant_meme_entree = new LinkedHashSet<>();
		for(ChaineDeProduction c: chaines) {
			boolean achetable = true;
			//Si un des élements en entrées n'a pas de prix d'achat, alors on met achetable à false et on ajoute la chaine courantes
			//dans la liste des chaines dont les elements en entrees sont en quantités limités
			for (Element eltsEntrees : c.getEntree().keySet()) {
				if (eltsEntrees.getAchat() == 0) {
					achetable = false;
				}
			}
			if(achetable) {
				chaines_independantes.add(c);
			}
			else {
				chaines_entrees_limites.add(c);
			}
		}
		
		//Pour chaque chaines dont les élements en entrées sont en quantités limités, on sépare ceux qui se disputent les meme chaines
		boolean entree_commun = true;
		for(ChaineDeProduction c1: chaines_entrees_limites) {
			for(ChaineDeProduction c2: chaines_entrees_limites) {
				for (Element e1: c1.getEntree().keySet()) {
					for (Element e2: c2.getEntree().keySet()) {
						if ((e1.getCode() == e2.getCode()) && (c1.getCode() != c2.getCode())) {
							chaine_disputant_meme_entree.add(c1);
							chaine_disputant_meme_entree.add(c2);
						}
					}
				}
			}
		}
		
		for (ChaineDeProduction c: chaines_independantes) {
			System.out.println("Chaine demarrage independantes: " + c.getCode());
		}
		
		for (ChaineDeProduction c : chaine_disputant_meme_entree) {
			System.out.println("Chaine dont les quantites demandées en entrée sont limitées (non achetable) et se dipsutant un element commun: " + c.getCode());
		}
		
		for (ChaineDeProduction c1 : chaines_entrees_limites) {
			if (!chaine_disputant_meme_entree.contains(c1)) {
				System.out.println("Chaine dont les quantites demandées en entrée sont limitées (non achetable): " + c1.getCode());
			}
		}
		

	}
