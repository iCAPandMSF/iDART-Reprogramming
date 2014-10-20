package org.celllife.idart.database.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




 
 
/**
 * Classe responsável por gerir conexões com a base de dados do MS-Access
 * @author EdiasJambaia
 *
 */
public class ConexaoODBC0 {
	
    private static final String USERNAME = "Admin";
    private static final String PASSWORD= "";
    private static final String DSN = "ACCESSJAVA"; 
    private static final String DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver"; 
    private static Connection conn = null;
  
             
    /**
     *  retorna uma conexão com a base de dados Access.
     *  
     */
    public static Connection getConnection() throws Exception {
        if(conn == null) {
        	System.out.println("++++++ A Estabelecer a conexao com MS ACESS.....");
            String url = "jdbc:odbc:" + DSN;
            try{
            	Class.forName(DRIVER);
            	 conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
                 System.out.println("++++++ A conexao com MS ACESS estabelecida com sucesso.....");
            }
            catch (SQLException ex) {
            	System.out.println("++++++ Problemas ao conectar a base de dados MS ACESS.....");
                ex.printStackTrace();
            } finally {
                conn = null;
            }
        }
        return conn;
    }
     
    /**
     * Fecha a conexão com o Banco de dados access
     * Chamar esse método ao sair da aplicação
     */
     public static void close() {
        if(conn != null) {
            try {
            	System.out.println("++++++ A Fechar a conexao com MS ACESS.....");
                conn.close();
                System.out.println("++++++ Conexao com MS ACESS fechada com sucesso.....");
            } catch (SQLException ex) {
            	System.out.println("++++++ Problemas ao fechar a Conexao com MS ACESS.....");
                ex.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }
     


    


public static void main( String[] args ){
	
	
	

	
}


}