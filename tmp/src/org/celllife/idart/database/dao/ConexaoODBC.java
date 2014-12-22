package org.celllife.idart.database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
 
 
/**
 * Classe respons�vel por gerir conex�es com a base de dados do MS-Access
 * @author EdiasJambaia
 *
 */
public class ConexaoODBC {
	
    private static final String username = "Admin";
    private static final String password = "";
    private static final String DSN = "ACCESSJAVA"; // nome da fonte de dados
    private static final String driver = "sun.jdbc.odbc.JdbcOdbcDriver"; // driver usado
    private static Connection conn = null;
             
    /**
     *  retorna uma conex�o com a base de dados Access.
     *  
     */
    public static Connection getConnection() throws Exception {
        if(conn == null) {
        	System.out.println("++++++ A Estabelecer a conexao com MS ACESS.....");
            String url = "jdbc:odbc:" + DSN;
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("++++++ A conexao com MS ACESS estabelecida com sucesso.....");
        }
        return conn;
    }
     
    /**
     * Fecha a conex�o com o Banco de dados access
     * Chamar esse m�todo ao sair da aplica��o
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
     


    
    
    // Insere uma dispensa em t_tarv MS ACCESS
   
    public static void insereT_tarv(Vector medicamentos, String nid, String datatarv, int qtdComp,  String regime, int dias, 
    		String tipotarv, String dataproxima, int idade, String linha) throws Exception
    {
    	try {
            // inicilizando a conex�o
            Connection conn = ConexaoODBC.getConnection();
 
            Statement st = conn.createStatement();
            System.out.println("++++++ A Preparar a Insercao de dados na tabela T_TARV do MS ACESS.....");
            //insere dados  
            
            switch(medicamentos.size())
            {
            case 1: //Se tiver apenas 1 medicamento na prescricao
            	   st.executeUpdate(""
            	    	+ "insert into t_tarv"
            	     	+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
            	   	    + "QtdComp,"
                  		+ "idade,"
                  		+ "compromido, "
                  		+ "linhaTerapeutica"
            	   		+ ") "
            	      + "values ("            	   
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+","+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 2: //Se tiver apenas 2 medicamento na prescricao
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+","+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 3: //Se tiver apenas 3 medicamento na prescricao
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1,"
                  		+ "compromido2, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+","+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  default:   
                	  st.executeUpdate(""
                    		+ "insert into t_tarv"
                      		+ "("
                      		+ "nid,"
                      		+ "datatarv,"
                      		+ "codregime,"
                      		+ "dias,"
                      		+ "tipotarv,"
                      		+ "dataproxima,"
                      		+ "QtdComp,"
                      		+ "idade,"
                      		+ "compromido,"
                      		+ "compromido1,"
                      		+ "compromido2, "
                  		+ "linhaTerapeutica"
                      		+ ") "
                    + "values ("
                    + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\')");
                      System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                      
                      break;
                  
            }
            	
          
            // fechando a conex�o
            ConexaoODBC.close();
        }


catch (Exception e)

{
	 System.out.println("++++++ Houve erro na Insercao de dados na tabela T_TARV do MS ACESS.....");
     
   e.printStackTrace();
 
}
    	
    	
    }

	//insere t_tarv com motivo de mudanca de ARV�S
	public static void insereT_tarvMotivo(Vector medicamentos, String nid, String datatarv, int qtdComp,  String regime, int dias, 
    		String tipotarv, String dataproxima, int idade, String codmudanca, String linha) throws Exception
    {
    	try {
            // inicilizando a conex�o
            Connection conn = ConexaoODBC.getConnection();
 
            Statement st = conn.createStatement();
            System.out.println("++++++ A Preparar a Insercao de dados na tabela T_TARV do MS ACESS.....");
            //insere dados  
            
            switch(medicamentos.size())
            {
            case 1: //para 1 medicamento
            	   st.executeUpdate(""
            	    	+ "insert into t_tarv"
            	     	+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
            	   	    + "QtdComp,"
                  		+ "codmudanca, "
            	   	   	+ "idade, "
                  		+ "compromido, "
                  		+ "linhaTerapeutica"
            	   		+ ") "
            	      + "values ("            	   
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+codmudanca+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 2: //para 2 medicamento
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "codmudanca, "
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+codmudanca+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 3://para 3 medicamento
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "codmudanca, "
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1,"
                  		+ "compromido2, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+codmudanca+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\'))");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  default:   //para 3 medicamento
                	  st.executeUpdate(""
                    		+ "insert into t_tarv"
                      		+ "("
                      		+ "nid,"
                      		+ "datatarv,"
                      		+ "codregime,"
                      		+ "dias,"
                      		+ "tipotarv,"
                      		+ "dataproxima,"
                      		+ "QtdComp,"
                      		+ "codmudanca, "
                      		+ "idade,"
                      		+ "compromido,"
                      		+ "compromido1,"
                      		+ "compromido2, "
                  		+ "linhaTerapeutica"
                      		+ ") "
                    + "values ("
                    + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+codmudanca+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\'))");
                      System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                      
                      break;
                  
            }
            	
          
            // fechando a conex�o
            ConexaoODBC.close();
        }


catch (Exception e)

{
	 System.out.println("++++++ Houve erro na Insercao de dados na tabela T_TARV do MS ACESS.....");
     
   e.printStackTrace();
 
}
    	
    	
    }

	//insere ttarv transferido de 
	public void insereT_tarvTransferidoDE(Vector medicamentos, String nid, String datatarv, int qtdComp,  String regime, int dias, 
    		String tipotarv, String dataproxima, int idade, String dataoutroservico, String linha) {

		try {
            // inicilizando a conex�o
            Connection conn = ConexaoODBC.getConnection();
 
            Statement st = conn.createStatement();
            System.out.println("++++++ A Preparar a Insercao de dados na tabela T_TARV do MS ACESS.....");
            //insere dados  
            
            switch(medicamentos.size())
            {
            case 1:
            	   st.executeUpdate(""
            	    	+ "insert into t_tarv"
            	     	+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
            	   	    + "QtdComp,"
                  		+ "dataoutroservico, "
            	   	   	+ "idade,"
                  		+ "compromido, "
                  		+ "linhaTerapeutica"
            	   		+ ") "
            	      + "values ("            	   
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+dataoutroservico+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 2: 
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataoutroservico,"
                  		+ "QtdComp,"
                  		+ "codmudanca, "
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+dataoutroservico+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 3:
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "dataoutroservico, "
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1,"
                  		+ "compromido2, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+dataoutroservico+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  default:   
                	  st.executeUpdate(""
                    		+ "insert into t_tarv"
                      		+ "("
                      		+ "nid,"
                      		+ "datatarv,"
                      		+ "codregime,"
                      		+ "dias,"
                      		+ "tipotarv,"
                      		+ "dataproxima,"
                      		+ "QtdComp,"
                      		+ "dataoutroservico, "
                      		+ "idade,"
                      		+ "compromido,"
                      		+ "compromido1,"
                      		+ "compromido2, "
                  		+ "linhaTerapeutica"
                      		+ ") "
                    + "values ("
                    + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+dataoutroservico+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\')");
                      System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                      
                      break;
                  
            }
            	
          
            // fechando a conex�o
            ConexaoODBC.close();
        }


catch (Exception e)

{
	 System.out.println("++++++ Houve erro na Insercao de dados na tabela T_TARV do MS ACESS.....");
     
   e.printStackTrace();
 
}
	}


	
	public static void selectAcess( String nid){
		

		
		String sqlQueryPaciente = "SELECT " 
	            + "sexo, " 
	            + "datanasc, " 
	            + "nome, " 
	            + "apelido, " 
	            + "avenida "
	        
	 + "FROM  t_paciente where nid=\'"+nid+"\'";
	    
	   

		
		ResultSet rs = null;
		try {
			Connection conn = ConexaoODBC.getConnection();
			 
		    Statement st=conn.createStatement();
			rs = st.executeQuery(sqlQueryPaciente);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (rs != null)
	    {
	       
	        try {
				while (rs.next())
				{
	  
	System.out.println("Nome: "+ rs.getString("nome")+ " Sexo: "+rs.getString("sexo"));
	      
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // � necess�rio fechar o resultado ao terminar
	    }
	}
	
public static void main(String [] ars){
	selectAcess( "11050801/14/1062");
}

}