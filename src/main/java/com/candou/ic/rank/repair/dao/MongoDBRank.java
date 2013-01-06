package com.candou.ic.rank.repair.dao;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDBRank {
	protected static Logger logger = Logger.getLogger(MongoDBRank.class);

	public static final String MONGO_HOST = "192.168.1.104";
	public static final int MONGO_PORT = 27017;
	private static Mongo mongo;

	private MongoDBRank() {
	}

	public static Mongo getMongo() {
		if (mongo == null) {
			try {
				mongo = new Mongo(MONGO_HOST, MONGO_PORT);
			} catch (UnknownHostException e) {
				logger.error(e);
			} catch (MongoException e) {
				logger.error(e);
			}
		}

		return mongo;
	}

	public static DB getDB(String dbname) {
		return getMongo().getDB(dbname);
	}

	public static DBCollection getCollection(String dbname, String collection) {
		return getDB(dbname).getCollection(collection);
	}
//    protected static Logger logger = Logger.getLogger(MongoDBRank.class);
//
//    private static List<ServerAddress> servers;
//    private static Mongo m;
//
//    // Make sure no one can instantiate our factory
//    private MongoDBRank() {
//    }
//
//    // Return an instance of Mongo
//    public static Mongo getMongo() {
//        logger.debug("Retrieving MongoDB");
//        if (m == null) {
//            servers = new ArrayList<ServerAddress>();
//            try {
//                servers.add(new ServerAddress("183.203.14.8", 27017));
//                servers.add(new ServerAddress("183.203.14.9", 27017));
//                servers.add(new ServerAddress("183.203.14.10", 27017));
//            }
//            catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
//
//            m = new Mongo(servers);
//            m.setWriteConcern(WriteConcern.REPLICAS_SAFE);
//        }
//
//        return m;
//    }
//
//    // Retrieve a db
//    public static DB getDB(String dbname) {
//        logger.debug("Retrieving db: " + dbname);
//        return getMongo().getDB(dbname);
//    }
//
//    // Retrieve a collection
//    public static DBCollection getCollection(String dbname, String collection) {
//        logger.debug("Retrieving collection: " + collection);
//        return getDB(dbname).getCollection(collection);
//    }
}
