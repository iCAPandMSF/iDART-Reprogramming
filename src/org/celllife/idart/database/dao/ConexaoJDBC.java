package org.celllife.idart.database.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.celllife.idart.commonobjects.iDartProperties;
import org.celllife.idart.database.hibernate.PrescriptionToPatient;
import org.celllife.idart.gui.alert.RiscoRoptura;

/**
 * Esta classe efectua conexao com a BD postgres e tem metodo para a manipulacao dos dados
 * @author EdiasJambaia
 *
 */

public class ConexaoJDBC {
	

Connection conn_db;  // Conexão com o servidor de banco de dados
Statement st;   // Declaração para executar os comandos
	
	public void conecta(String usr, String pwd) throws SQLException, ClassNotFoundException 
{



String url = "jdbc:postgresql://localhost/pharm?charSet=LATIN1";

// Carregar o driver
Class.forName("org.postgresql.Driver");

// Conectar com o servidor de banco de dados
System.out.println("Conectando ao banco de dados\nURL = " + url);
conn_db = DriverManager.getConnection(url, usr, pwd);

System.out.println("Conectado...Criando a declaração");
st = conn_db.createStatement();


}
	
	public Connection retornaConexao(String usr, String pwd) throws SQLException, ClassNotFoundException 
{



String url = "jdbc:postgresql://localhost/pharm?charSet=LATIN1";

// Carregar o driver
Class.forName("org.postgresql.Driver");

// Conectar com o servidor de banco de dados
System.out.println("Conectando ao banco de dados\nURL = " + url);
conn_db = DriverManager.getConnection(url, usr, pwd);

System.out.println("Conectado...Criando a declaração");
st = conn_db.createStatement();

return conn_db;

}
	
	
	
/**
 * Devolve a lista de PrescriptionToPatient, na verdade so devolve lista de tamanho 1
 * @param patientid
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public List<PrescriptionToPatient> listPtP(String patientid ) throws ClassNotFoundException, SQLException{
	
	conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	
	String query=""
			+ "SELECT "
			+ "p.id, "
			+ "p.current, "
			+ "p.duration, "
			+ "p.reasonforupdate, "
			+ "p.notes, "
			+ "pt.patientid, "
			+ "rt.regimeesquema, "
			+" date_part(\'YEAR\',now())-date_part(\'YEAR\',pt.dateofbirth) as idade,  "
			+" p.motivomudanca AS motivomudanca, "
			+" p.datainicionoutroservico as datainicionoutroservico, "
			+ "lt.linhanome "
			+" FROM "
			+ "  patient pt, "
			+ "regimeterapeutico rt,  "
			+ "linhat lt, "
			+ "prescription AS p "
			+ "WHERE ("
			+ "(p.current = \'T\'::bpchar) "
			+ "AND "
			+ "(pt.id = p.patient) "
			+ "AND "
			+ "(pt.patientid=\'"+patientid+"\') "
			+ "AND "
			+ "(rt.regimeid=p.regimeid) "
			+ "AND "
			+ "(lt.linhaid=p.linhaid)) ";
	
	
	//ResultSet rs = st.executeQuery("select id, current, duration, reasonforupdate, notes, patientid from PrescriptioToPatient where patientid=\'"+patientid+"\'");
	List <PrescriptionToPatient> ptp = new ArrayList();
	ResultSet rs=st.executeQuery(query);
	if (rs != null)
        {
           
            while (rs.next())
            {

            	ptp.add(new PrescriptionToPatient(rs.getInt("id"), rs.getString("current"), rs.getInt("duration"),
            			rs.getString("reasonforupdate"),rs.getString("notes"), rs.getString("patientid"), rs.getString("regimeesquema"), rs.getInt("idade"), rs.getString("motivomudanca"),rs.getDate("datainicionoutroservico"),rs.getString("linhanome")));



           
            }
            rs.close(); // é necessário fechar o resultado ao terminar
        }
        System.out.println("Fechando a conexão");
        st.close();
        conn_db.close();
        return ptp;
	}

/**
 * Converte uma data para o formato DD Mon YYYY
 * @param date
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public Date converteData(String date) throws ClassNotFoundException, SQLException{
	
	Date data=new Date();
	conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	
	String query="select to_date(\'"+date+"\', \'DD Mon YYYY\')";
	ResultSet rs=st.executeQuery(query);
	
	rs.next();
	data=rs.getDate("to_date");
	
	  st.close();
      conn_db.close();
      return data;
}



/**
 * devolve um vector de todos medicamentos com seus AMC, SALDO E QUANTIDADE DE REQUISICAO
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public Vector<RiscoRoptura> selectRiscoDeRopturaStock() throws ClassNotFoundException, SQLException{

	
		
		
	String query="SELECT drugname, consumo_max_ult_3meses, saldos "
			+ "FROM "
			+ "alimenta_risco_roptura";
	
	
    Vector<RiscoRoptura> riscos=new Vector<RiscoRoptura>();
 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
   
	ResultSet rs=st.executeQuery(query);
	if (rs != null)
        {
           
            while (rs.next())
            {

RiscoRoptura rr=new RiscoRoptura(rs.getString("drugname"), rs.getInt("consumo_max_ult_3meses"), rs.getInt("saldos"), rs.getInt("consumo_max_ult_3meses")*3 - rs.getInt("saldos"));

riscos.add(rr);
System.out.println(" \n");

            } 
            rs.close(); // é necessário fechar o resultado ao terminar
        }
	
	
        System.out.println("Fechando a conexão");
        st.close();
        conn_db.close();
return riscos;

	
}



}
