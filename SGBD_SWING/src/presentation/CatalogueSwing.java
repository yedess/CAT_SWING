package presentation;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import metier.MetierCatalogueImpl;
import metier.Produit;
import metier.SingletonConnection;
public class CatalogueSwing extends JFrame{
	private JComboBox jcombo = new JComboBox();
	private JLabel jlbMC = new JLabel("Mot clé: ");
	private JTextField txtMC = new JTextField(12);
	private JTextField txtid = new JTextField(5);
	private JTextField JTextFieldidCat = new JTextField();
	private JButton btnOK = new JButton("OK");
	private JTable jTable;
	private ProduitModel produitModel;
	private MetierCatalogueImpl metier = new MetierCatalogueImpl();
	public void remplirCombo() {
		try {
			String query = "SELECT * FROM categorie";
			
			Connection conn = SingletonConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				jcombo.addItem(rs.getString("nomCategorie"));
				JTextFieldidCat.setText(String.valueOf(rs.getInt("idCategorie")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public CatalogueSwing() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		JPanel jPanelN = new JPanel();
		jPanelN.add(jlbMC);jPanelN.add(txtMC);jPanelN.add(btnOK);jPanelN.add(jcombo);jPanelN.add(txtid);
		this.add(jPanelN,BorderLayout.NORTH);
		produitModel = new ProduitModel();
		jTable=new JTable(produitModel);
		JScrollPane jscrollPane = new JScrollPane(jTable);
		this.add(jscrollPane,BorderLayout.CENTER);
		this.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setVisible(true);
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mc = txtMC.getText();
				List<Produit>produits = metier.getProduitParMotCle(mc);
				produitModel.loadData(produits);
			}
		});
		jcombo.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "SELECT * FROM categorie WHERE nomCategorie LIKE ?";
					Connection conn = SingletonConnection.getConnection();
					PreparedStatement ps = conn.prepareStatement(query);
					String nomCat = (String)jcombo.getSelectedItem();
					ps.setString(1, nomCat);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						int idCategorie = rs.getInt("idCategorie");
						List<Produit>produits=metier.getProduitParIDCategorie(idCategorie);
						produitModel.loadData(produits);
						txtid.setText(String.valueOf(rs.getInt("idCategorie")));
						txtid.setEditable(false);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
				});
		remplirCombo();
	}
	public static void main(String[] args) {
		new CatalogueSwing();
	}
}
