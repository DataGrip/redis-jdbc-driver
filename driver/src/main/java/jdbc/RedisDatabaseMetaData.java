package jdbc;

import java.sql.*;
import java.util.regex.Pattern;

public class RedisDatabaseMetaData implements DatabaseMetaData {

    private final RedisConnection connection;
    private final RedisDriver driver;

    RedisDatabaseMetaData(RedisConnection connection, RedisDriver driver) {
        this.connection = connection;
        this.driver = driver;
    }

    @Override
    public boolean allProceduresAreCallable() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean allTablesAreSelectable() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getURL() throws SQLException {
        return null;
    }

    @Override
    public String getUserName() throws SQLException {
        return null;
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getDatabaseProductName() throws SQLException {
        return "Redis";
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("INFO server");
        String serverInfo = result.next() ? result.getString(1) : "";
        return Pattern.compile("redis_version:(.+)").matcher(serverInfo).group(1);
    }

    @Override
    public String getDriverName() throws SQLException {
        return "Redis JDBC Driver";
    }

    @Override
    public String getDriverVersion() throws SQLException {
        return driver.getVersion();
    }

    @Override
    public int getDriverMajorVersion() {
        return driver.getMajorVersion();
    }

    @Override
    public int getDriverMinorVersion() {
        return driver.getMinorVersion();
    }

    @Override
    public boolean usesLocalFiles() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean usesLocalFilePerTable() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public String getIdentifierQuoteString() throws SQLException {
        return " ";
    }

    @Override
    public String getSQLKeywords() throws SQLException {
        return null;
    }

    @Override
    public String getNumericFunctions() throws SQLException {
        return null;
    }

    @Override
    public String getStringFunctions() throws SQLException {
        return null;
    }

    @Override
    public String getSystemFunctions() throws SQLException {
        return null;
    }

    @Override
    public String getTimeDateFunctions() throws SQLException {
        return null;
    }

    @Override
    public String getSearchStringEscape() throws SQLException {
        return null;
    }

    @Override
    public String getExtraNameCharacters() throws SQLException {
        return "";
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsConvert() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsGroupBy() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        return true;
    }

    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsOuterJoins() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getSchemaTerm() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getProcedureTerm() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        return "database";
    }

    @Override
    public boolean isCatalogAtStart() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        return null;
    }

    @Override
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsUnion() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsUnionAll() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxColumnNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxColumnsInTable() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxConnections() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxCursorNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxIndexLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxRowSize() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxStatementLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxStatements() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxTableNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxTablesInSelect() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxUserNameLength() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        // TODO (transactions) ?
        return Connection.TRANSACTION_NONE;
    }

    @Override
    public boolean supportsTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        // TODO (transactions) ?
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        // TODO (transactions) ?
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        // TODO (transactions) ?
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        // TODO (transactions) ?
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        // TODO (transactions) ?
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        return null;
    }

    @Override
    public ResultSet getCatalogs() throws SQLException {
        return null;
    }

    @Override
    public ResultSet getTableTypes() throws SQLException {
        return null;
    }

    @Override
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getTypeInfo() throws SQLException {
        return null;
    }

    @Override
    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
        return null;
    }

    @Override
    public boolean supportsResultSetType(int type) throws SQLException {
        return type == ResultSet.TYPE_FORWARD_ONLY;
    }

    @Override
    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean updatesAreDetected(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean deletesAreDetected(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean insertsAreDetected(int type) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public boolean supportsSavepoints() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsNamedParameters() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsGetGeneratedKeys() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
        return null;
    }

    @Override
    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        String[] parts = getDatabaseProductVersion().split("\\.");
        return Integer.parseInt(parts[0]);
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        String[] parts = getDatabaseProductVersion().split("\\.");
        if (parts.length <= 1) return 0;
        return Integer.parseInt(parts[1]);
    }

    @Override
    public int getJDBCMajorVersion() throws SQLException {
        return 4;
    }

    @Override
    public int getJDBCMinorVersion() throws SQLException {
        return 2;
    }

    @Override
    public int getSQLStateType() throws SQLException {
        return DatabaseMetaData.sqlStateXOpen;
    }

    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsStatementPooling() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        return null;
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        return null;
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
