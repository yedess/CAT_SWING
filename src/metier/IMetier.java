package metier;
import java.util.List;

public interface IMetier {
	public void addCategorie(Categorie C);
	public void addProduit(Produit P, int idCategorie);
	public List<Produit> getProduitParMotCle(String mc);
	public List<Produit> getProduitParIDCategorie(int idCategorie);
	public List<Categorie> getAllCategorie();
	public Categorie getCategorie(int idCategorie);
}