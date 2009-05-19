package liquibase.change;

import liquibase.database.Database;
import liquibase.database.MockDatabase;
import liquibase.database.SQLiteDatabase;
import liquibase.statement.DropUniqueConstraintStatement;
import liquibase.statement.SqlStatement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class DropUniqueConstraintChangeTest  extends AbstractChangeTest {
    private DropUniqueConstraintChange change;

    @Before
    public void setUp() throws Exception {
        change = new DropUniqueConstraintChange();
        change.setSchemaName("SCHEMA_NAME");
        change.setTableName("TAB_NAME");
        change.setConstraintName("UQ_CONSTRAINT");
    }

    @Override
    @Test
    public void getRefactoringName() throws Exception {
        assertEquals("Drop Unique Constraint", change.getChangeMetaData().getDescription());
    }

    @Override
    @Test
    public void generateStatement() throws Exception {
        SqlStatement[] sqlStatements = change.generateStatements(new MockDatabase());
        assertEquals(1, sqlStatements.length);
        assertTrue(sqlStatements[0] instanceof DropUniqueConstraintStatement);
        assertEquals("SCHEMA_NAME", ((DropUniqueConstraintStatement) sqlStatements[0]).getSchemaName());
        assertEquals("TAB_NAME", ((DropUniqueConstraintStatement) sqlStatements[0]).getTableName());
        assertEquals("UQ_CONSTRAINT", ((DropUniqueConstraintStatement) sqlStatements[0]).getConstraintName());
    }

    @Override
    @Test
    public void getConfirmationMessage() throws Exception {
        assertEquals("Unique constraint UQ_CONSTRAINT dropped from TAB_NAME", change.getConfirmationMessage());
    }

    @Override
    protected boolean changeIsUnsupported(Database database) {
        return database instanceof SQLiteDatabase;
    }

}
