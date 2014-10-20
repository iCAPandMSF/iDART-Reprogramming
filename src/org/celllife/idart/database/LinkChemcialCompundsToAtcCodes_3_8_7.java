package org.celllife.idart.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;

public class LinkChemcialCompundsToAtcCodes_3_8_7 implements CustomSqlChange {

	@Override
	public String getConfirmationMessage() {
		return "ChemicalCompounds mapped to ATC Codes";
	}

	@Override
	public void setFileOpener(ResourceAccessor fileOpener) {
	}

	@Override
	public void setUp() throws SetupException {
	}

	@Override
	public ValidationErrors validate(Database arg0) {
		return null;
	}

	@Override
	public liquibase.statement.SqlStatement[] generateStatements(Database arg0)
			throws CustomChangeException {
		JdbcConnection con = (JdbcConnection) arg0.getConnection();

		List<SqlStatement> statements = new ArrayList<SqlStatement>();

		try {
			insertChemicalCompoundIfNeeded(con, statements, "Abacavir", "ABC");
			getMappingStatement(statements, 31, "Abacavir");
			
			insertChemicalCompoundIfNeeded(con, statements, "Didanosine", "DDI");
			getMappingStatement(statements, 27, "Didanosine");
			
			insertChemicalCompoundIfNeeded(con, statements, "Efavirenz", "EFV");
			getMappingStatement(statements, 40, "Efavirenz");
			
			insertChemicalCompoundIfNeeded(con, statements, "Lamivudine", "3TC");
			getMappingStatement(statements, 30, "Lamivudine");
			
			insertChemicalCompoundIfNeeded(con, statements, "Nevirapine", "NVP");
			getMappingStatement(statements, 38, "Nevirapine");
			
			insertChemicalCompoundIfNeeded(con, statements, "Ritonavir", "RTV");
			getMappingStatement(statements, 18, "Ritonavir");
			
			insertChemicalCompoundIfNeeded(con, statements, "Stavudine", "D4T");
			getMappingStatement(statements, 29, "Stavudine");
			
			insertChemicalCompoundIfNeeded(con, statements, "Zidovudine", "AZT");
			getMappingStatement(statements, 26, "Zidovudine");
			
			insertChemicalCompoundIfNeeded(con, statements, "Lopinavir", "LPV");
			getMappingStatement(statements, 21, "Lopinavir");
			
			insertChemicalCompoundIfNeeded(con, statements, "Nelfinavir", "NLV");
			getMappingStatement(statements, 19, "Nelfinavir");
			
			insertChemicalCompoundIfNeeded(con, statements, "Tenofovir", "TDF");
			getMappingStatement(statements, 32, "Tenofovir");

			insertChemicalCompoundIfNeeded(con, statements, "Metisazone", null);
			getMappingStatement(statements, 0, "Metisazone");

			insertChemicalCompoundIfNeeded(con, statements, "Aciclovir", "ACV");
			getMappingStatement(statements, 1, "Aciclovir");

			insertChemicalCompoundIfNeeded(con, statements, "Idoxuridine","IDU");
			getMappingStatement(statements, 2, "Idoxuridine");

			insertChemicalCompoundIfNeeded(con, statements, "Vidarabine", null);
			getMappingStatement(statements, 3, "Vidarabine");

			insertChemicalCompoundIfNeeded(con, statements, "Ribavirin", "RBV");
			getMappingStatement(statements, 4, "Ribavirin");

			insertChemicalCompoundIfNeeded(con, statements, "Ganciclovir","GCV");
			getMappingStatement(statements, 5, "Ganciclovir");

			insertChemicalCompoundIfNeeded(con, statements, "Famciclovir","FCV");
			getMappingStatement(statements, 6, "Famciclovir");

			insertChemicalCompoundIfNeeded(con, statements, "Valaciclovir","VACV");
			getMappingStatement(statements, 7, "Valaciclovir");

			insertChemicalCompoundIfNeeded(con, statements, "Cidofovir", "CDV");
			getMappingStatement(statements, 8, "Cidofovir");

			insertChemicalCompoundIfNeeded(con, statements, "Penciclovir","PCV");
			getMappingStatement(statements, 9, "Penciclovir");

			insertChemicalCompoundIfNeeded(con, statements, "Valganciclov",null);
			getMappingStatement(statements, 10, "Valganciclov");

			insertChemicalCompoundIfNeeded(con, statements, "Brivudine", "BVDU");
			getMappingStatement(statements, 11, "MetisBrivudineazone");

			insertChemicalCompoundIfNeeded(con, statements, "Rimantadine", "RH");
			getMappingStatement(statements, 12, "Rimantadine");

			insertChemicalCompoundIfNeeded(con, statements, "Tromantadine",null);
			getMappingStatement(statements, 13, "Tromantadine");

			insertChemicalCompoundIfNeeded(con, statements, "Foscarnet", "FOS");
			getMappingStatement(statements, 14, "Foscarnet");

			insertChemicalCompoundIfNeeded(con, statements, "Fosfonet", null);
			getMappingStatement(statements, 15, "Fosfonet");

			insertChemicalCompoundIfNeeded(con, statements, "Saquinavir", "SAQ");
			getMappingStatement(statements, 16, "Saquinavir");

			insertChemicalCompoundIfNeeded(con, statements, "Indinavir", "IND");
			getMappingStatement(statements, 17, "Indinavir");

			insertChemicalCompoundIfNeeded(con, statements, "Amprenavir", "APV");
			getMappingStatement(statements, 20, "Amprenavir");

			insertChemicalCompoundIfNeeded(con, statements, "Fosamprenavir","FPV");
			getMappingStatement(statements, 22, "Fosamprenavir");

			insertChemicalCompoundIfNeeded(con, statements, "Atazanavir", "ATV");
			getMappingStatement(statements, 23, "Atazanavir");

			insertChemicalCompoundIfNeeded(con, statements, "Tipranavir", "TPV");
			getMappingStatement(statements, 24, "Tipranavir");

			insertChemicalCompoundIfNeeded(con, statements, "Darunavir", "DRV");
			getMappingStatement(statements, 25, "Darunavir");

			insertChemicalCompoundIfNeeded(con, statements, "Zalcitabine", null);
			getMappingStatement(statements, 28, "Zalcitabine");

			insertChemicalCompoundIfNeeded(con, statements, "Adefovir", "ADV");
			getMappingStatement(statements, 33, "Adefovir");

			insertChemicalCompoundIfNeeded(con, statements, "Emtricitabine",null);
			getMappingStatement(statements, 34, "Emtricitabine");

			insertChemicalCompoundIfNeeded(con, statements, "Entecavir", "ETV");
			getMappingStatement(statements, 35, "Entecavir");

			insertChemicalCompoundIfNeeded(con, statements, "Telbivudine", null);
			getMappingStatement(statements, 36, "Telbivudine");

			insertChemicalCompoundIfNeeded(con, statements, "Clevudine", null);
			getMappingStatement(statements, 37, "Clevudine");

			insertChemicalCompoundIfNeeded(con, statements, "Delavirdine","DLV");
			getMappingStatement(statements, 39, "Delavirdine");

			insertChemicalCompoundIfNeeded(con, statements, "Etravirine", null);
			getMappingStatement(statements, 41, "Etravirine");

			insertChemicalCompoundIfNeeded(con, statements, "Zanamivir", null);
			getMappingStatement(statements, 42, "Zanamivir");

			insertChemicalCompoundIfNeeded(con, statements, "Oseltamivir", null);
			getMappingStatement(statements, 43, "Oseltamivir");

			insertChemicalCompoundIfNeeded(con, statements, "Moroxydine", null);
			getMappingStatement(statements, 51, "Moroxydine");

			insertChemicalCompoundIfNeeded(con, statements, "Lysozyme", "LSZ");
			getMappingStatement(statements, 52, "Lysozyme");

			insertChemicalCompoundIfNeeded(con, statements, "Inosine pranobex","INPX");
			getMappingStatement(statements, 53, "Inosine pranobex");

			insertChemicalCompoundIfNeeded(con, statements, "Pleconaril", null);
			getMappingStatement(statements, 54, "Pleconaril");

			insertChemicalCompoundIfNeeded(con, statements, "Enfuvirtide","ENF");
			getMappingStatement(statements, 55, "Enfuvirtide");

			insertChemicalCompoundIfNeeded(con, statements, "Raltegravir", null);
			getMappingStatement(statements, 56, "Raltegravir");

			insertChemicalCompoundIfNeeded(con, statements, "Maraviroc", null);
			getMappingStatement(statements, 57, "Maraviroc");

			insertChemicalCompoundIfNeeded(con, statements, "Maribavir", "MBV");
			getMappingStatement(statements, 58, "Maribavir");

			/*
			 * combinations
			 */
			
			// Zidovudine and Lamivudine
			getMappingStatement(statements, 44, "Zidovudine");
			getMappingStatement(statements, 44, "Lamivudine");

			// Lamivudine and Abacavir
			getMappingStatement(statements, 45, "Lamivudine");
			getMappingStatement(statements, 45, "Abacavir");

			// Tenofovir and Emtricitabine
			getMappingStatement(statements, 46, "Tenofovir");
			getMappingStatement(statements, 46, "Emtricitabine");

			// Zidovudine, Lamivudine and Abacavir
			getMappingStatement(statements, 47, "Zidovudine");
			getMappingStatement(statements, 47, "Lamivudine");
			getMappingStatement(statements, 47, "Abacavir");

			// Zidovudine, Lamivudine and Nevirapine
			getMappingStatement(statements, 48, "Zidovudine");
			getMappingStatement(statements, 48, "Lamivudine");
			getMappingStatement(statements, 48, "Nevirapine");

			// Emtricitabine, Tenofovir and Efavirenz
			getMappingStatement(statements, 49, "Emtricitabine");
			getMappingStatement(statements, 49, "Tenofovir");
			getMappingStatement(statements, 49, "Efavirenz");

			// Stavudine, Lamivudine and Nevirapine
			getMappingStatement(statements, 50, "Stavudine");
			getMappingStatement(statements, 50, "Lamivudine");
			getMappingStatement(statements, 50, "Nevirapine");

		} catch (Exception e) {
			throw new CustomChangeException(e);
		}

		return statements.toArray(new SqlStatement[statements.size()]);
	}

	private void insertChemicalCompoundIfNeeded(JdbcConnection con,
		List<SqlStatement> statements, String compoundName, String acronym) throws DatabaseException, SQLException {
		String sql = "select count(*) as c from chemicalcompound where name = ?";
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setString(1, compoundName);
		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
			int count = rs.getInt("c");
			if (count == 0) {
				acronym = acronym == null || acronym.isEmpty() ? "NULL"
						: "'" + acronym + "'";
				statements
						.add(new RawSqlStatement(
								"insert into chemicalcompound (id, acronym, name) values(nextval('hibernate_sequence'), "
										+ acronym
										+ ", '"
										+ compoundName
										+ "')"));
			}
		}
	}

	private void getMappingStatement(List<SqlStatement> statements,
			int atcCodeId, String compoundName) {
		statements
				.add(new RawSqlStatement(
						"insert into atccode_chemicalcompound (chemicalcompound_id, atccode_id) select id, "
								+ atcCodeId
								+ " from chemicalcompound where name = '"
								+ compoundName + "'"));
	}
}
