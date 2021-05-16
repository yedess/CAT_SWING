package metier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MetierCatalogueImpl implements IMetier{
	@Override
	public void addCategorie(Categorie C) {
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("INSERT INTO categorie (nomCategorie) VALUES (?)");
			ps.setString(1, C.getNomCategorie());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void addProduit (Produit p, int idCategorie) {
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO produit VALUES (?,?,?,?,?)");
			ps.setString(1, p.getIdProduit());
			ps.setString(2, p.getNomProduit());
			ps.setDouble(3, p.getPrix());
			ps.setInt(4, p.getQuantite());
			ps.setInt(5, idCategorie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<Produit> getProduitParMotCle(String mc) {
		List<Produit> produits = new ArrayList<Produit>();
		Connection conn=SingletonConnection.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM produit WHERE nomProduit LIKE ?");
			ps.setString(1, "%"+mc+"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Produit p = new Produit();
				p.setIdProduit(rs.getString("idProduit"));
				p.setNomProduit(rs.getString("nomProduit"));
				p.setPrix(rs.getDouble("prix"));
				p.setQuantite(rs.getInt("quantite"));
				int idCategorie = rs.getInt("idCategorie");
				PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM categorie WHERE idCategorie=?");
				ps2.setInt(1, idCategorie);
				ResultSet rs2 = ps2.executeQuery();
				if(rs2.next()) {
					Categorie cat = new Categorie();
					cat.setIdCategorie(rs2.getInt("idCategorie"));
					cat.setNomCategorie(rs2.getString("nomCategorie"));
				}
				produits.add(p);
				ps2.close();
			}
			ps.close();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return produits;
	}
	@Override
	public List<Produit> getProduitParIDCategorie(int idCategorie){
		List<Produit> produits = new ArrayList<Produit>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM produit WHERE idCategorie = ?");
			ps.setInt(1, idCategorie);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Produit p = new Produit();
				p.setIdProduit(rs.getString("idProduit"));
				p.setNomProduit(rs.getString("nomProduit"));
				p.setPrix(rs.getDouble("prix"));
				p.setQuantite(rs.getInt("quantite"));
				idCategorie = rs.getInt("idCategorie");
				PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM categorie WHERE idCategorie=?");
				ps2.setInt(1, idCategorie);
				ResultSet rs2 = ps2.executeQuery();
				if(rs2.next()) {
					Categorie cat = new Categorie();
					cat.setIdCategorie(rs2.getInt(1));
					cat.setNomCategorie(rs2.getString(2));
					p.setCategorie(cat);
				}
				produits.add(p);
				ps2.close();
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produits;
	}
	public List<Categorie> getAllCategorie() {
		List<Categorie> categories = new ArrayList<Categorie>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM categorie");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Categorie c = new Categorie();
				c.setIdCategorie(rs.getInt("idCategorie"));
				c.setNomCategorie(rs.getString("nomCategorie"));
				categories.add(c);
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}
	
	@Override
	public Categorie getCategorie(int idCategorie) {
		Categorie categorie = null;
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM categorie WHERE idCategorie = ?");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				categorie = new Categorie();
				categorie.setIdCategorie(rs.getInt(1));
				categorie.setNomCategorie(rs.getString(2));
				List<Produit>produits=this.getProduitParIDCategorie(idCategorie);
				categorie.setProduits(produits);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorie;
	}
}
