package test;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import metier.Categorie;
import metier.MetierCatalogueImpl;
import metier.Produit;
import metier.SingletonConnection;
public class App {
	public static void main(String[] args) {
		Connection conn1 = SingletonConnection.getConnection();
		MetierCatalogueImpl metier = new MetierCatalogueImpl();
		Scanner x = new Scanner(System.in);
		System.out.println("Veuillez saisir le mot clé : ");
		String mc = x.nextLine();
		List<Produit> produits = metier.getProduitParMotCle(mc);
		for(Produit p:produits) {
			System.out.println(p.getNomProduit());
		}
	}

}
