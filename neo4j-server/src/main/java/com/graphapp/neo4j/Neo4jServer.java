package com.graphapp.neo4j;

import org.neo4j.configuration.GraphDatabaseSettings;
import org.neo4j.configuration.connectors.BoltConnector;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Simple standalone Neo4j server that starts a Neo4j database and exposes a Bolt connector.
 * This can be run as a separate process to avoid resource contention with the main application.
 */
public class Neo4jServer {
    private static final Logger logger = LoggerFactory.getLogger(Neo4jServer.class);
    private static final String DEFAULT_DB_NAME = "neo4j";
    private static final int BOLT_PORT = 7687;
    private static DatabaseManagementService managementService;
    private static GraphDatabaseService graphDb;

    /**
     * Main method to start the Neo4j server.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        logger.info("Starting Neo4j Server...");
        
        try {
            // Create a temporary directory for the database
            Path dbPath = createTempDbDirectory();
            
            // Configure and start the database
            startDatabase(dbPath.toFile());
            
            // Add a shutdown hook to stop the database when the JVM exits
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Shutting down Neo4j Server...");
                stopDatabase();
            }));
            
            // Keep the server running
            logger.info("Neo4j Server is running on bolt://localhost:{}", BOLT_PORT);
            
            // Create some initial data for testing
            createInitialData();
            
            // Keep the server running until JVM exits
            Thread.currentThread().join();
            
        } catch (Exception e) {
            logger.error("Error starting Neo4j Server", e);
            System.exit(1);
        }
    }

    /**
     * Create a temporary directory for the database.
     *
     * @return The path to the temporary directory.
     * @throws IOException If an I/O error occurs.
     */
    private static Path createTempDbDirectory() throws IOException {
        Path path = Paths.get("neo4j-data");
        Files.createDirectories(path);
        logger.info("Created database directory: {}", path.toAbsolutePath());
        return path;
    }

    /**
     * Start the Neo4j database and expose it via Bolt.
     *
     * @param dbDir The directory where the database files will be stored.
     */
    private static void startDatabase(File dbDir) {
        managementService = new DatabaseManagementServiceBuilder(dbDir)
                .setConfig(GraphDatabaseSettings.default_database, DEFAULT_DB_NAME)
                .setConfig(BoltConnector.enabled, true)
                .setConfig(BoltConnector.listen_address, "0.0.0.0:" + BOLT_PORT)
                .build();
        
        graphDb = managementService.database(DEFAULT_DB_NAME);
        logger.info("Neo4j database started");
    }

    /**
     * Stop the Neo4j database.
     */
    private static void stopDatabase() {
        if (managementService != null) {
            managementService.shutdown();
            logger.info("Neo4j database stopped");
        }
    }
    
    /**
     * Create some initial data for testing.
     */
    private static void createInitialData() {
        try (Transaction tx = graphDb.beginTx()) {
            // Simple check to see if we've already initialized
            if (!tx.schema().getIndexes().iterator().hasNext()) {
                logger.info("Creating initial data...");
                
                // Create constraints and indexes
                tx.schema().constraintFor(Label.label("Person"))
                        .assertPropertyIsUnique("name")
                        .create();
                        
                tx.schema().constraintFor(Label.label("Technology"))
                        .assertPropertyIsUnique("name")
                        .create();
                        
                // Create some initial nodes and relationships
                Node alice = tx.createNode(Label.label("Person"));
                alice.setProperty("name", "Alice");
                alice.setProperty("age", 30);
                
                Node bob = tx.createNode(Label.label("Person"));
                bob.setProperty("name", "Bob");
                bob.setProperty("age", 35);
                
                Node java = tx.createNode(Label.label("Technology"));
                java.setProperty("name", "Java");
                java.setProperty("type", "Programming Language");
                
                Node neo4j = tx.createNode(Label.label("Technology"));
                neo4j.setProperty("name", "Neo4j");
                neo4j.setProperty("type", "Database");
                
                alice.createRelationshipTo(bob, RelationshipType.withName("KNOWS"));
                alice.createRelationshipTo(java, RelationshipType.withName("USES"));
                bob.createRelationshipTo(neo4j, RelationshipType.withName("USES"));
                java.createRelationshipTo(neo4j, RelationshipType.withName("CONNECTS_TO"));
                
                tx.commit();
                logger.info("Initial data created successfully");
            } else {
                logger.info("Database already initialized, skipping initial data creation");
            }
        }
    }
    
    /**
     * A label for nodes in the graph.
     */
    private enum Label implements org.neo4j.graphdb.Label {
        Person,
        Technology
    }
    
    /**
     * A relationship type in the graph.
     */
    private enum RelationshipType implements org.neo4j.graphdb.RelationshipType {
        KNOWS,
        USES,
        CONNECTS_TO
    }
    
    /**
     * A node in the graph.
     */
    private interface Node extends org.neo4j.graphdb.Node {
    }
}