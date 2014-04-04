/*
 * Stratio Meta
 *
 * Copyright (c) 2014, Stratio, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package com.stratio.meta.server.cassandra;

import com.stratio.meta.core.parser.Parser;
import com.stratio.meta.server.MetaServer;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.InvalidQueryException;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertTrue;


public class BasicServerCassandraTest {

    private static final String DEFAULT_HOST = "127.0.0.1";

    /**
     * Class logger.
     */
    private static final Logger logger = Logger.getLogger(CassandraTest.class);
    protected final Parser parser = new Parser();
    protected static final MetaServer metaServer = new MetaServer();

    /**
     * Session to launch queries on C*.
     */
    protected static Session _session = null;

    /**
     * Establish the connection with Cassandra in order to be able to retrieve
     * metadata from the system columns.
     * @param host The target host.
     * @return Whether the connection has been established or not.
     */
    protected static boolean connect(String host){
        boolean result = false;
        Cluster c = Cluster.builder().addContactPoint(host).build();
        _session = c.connect();
        result = null == _session.getLoggedKeyspace();
        return result;
    }

    /**
     * Initialize the connection to Cassandra using the
     * host specified by {@code DEFAULT_HOST}.
     */
    public static void initCassandraConnection(){
        assertTrue(connect(DEFAULT_HOST), "Cannot connect to cassandra");
    }

    /**
     * Drop a keyspace if it exists in the database.
     * @param targetKeyspace The target keyspace.
     */
    public static void dropKeyspaceIfExists(String targetKeyspace){
        String query = "USE " + targetKeyspace;
        boolean ksExists = true;
        try{
            ResultSet result = _session.execute(query);
        }catch (InvalidQueryException iqe){
            ksExists = false;
        }

        if(ksExists){
            String q = "DROP KEYSPACE " + targetKeyspace;
            try{
                _session.execute(q);
            }catch (Exception e){
                logger.error("Cannot drop keyspace: " + targetKeyspace, e);
            }
        }
    }

    @BeforeClass
    public static void setUpBeforeClass(){
        try {
            Process p = Runtime.getRuntime().exec("./meta-core/src/test/resources/test.sh");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            logger.error("Cannot execute ccm start script");
        }
        initCassandraConnection();
        dropKeyspaceIfExists("testKS");
    }

    public static void closeCassandraConnection(){
        metaServer.close();
        /*try {
            Process p = Runtime.getRuntime().exec("./meta-core/src/test/resources/close.sh");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            logger.error("Cannot execute ccm close script");
        }*/

    }

    /**
     * Load a {@code keyspace} in Cassandra using the CQL sentences in the script
     * path. The script is executed if the keyspace does not exists in Cassandra.
     * @param keyspace The name of the keyspace.
     * @param path The path of the CQL script.
     */
    public static void loadTestData(String keyspace, String path){
        KeyspaceMetadata metadata = metaServer.getMetadata().getKeyspace(keyspace);
        if(metadata == null){
            logger.info("Creating keyspace " + keyspace + " using " + path);
            List<String> scriptLines = loadScript(path);
            logger.info("Executing " + scriptLines.size() + " lines");
            for(String cql : scriptLines){
               ResultSet result = _session.execute(cql);
                if(logger.isDebugEnabled()){
                	logger.debug("Executing: " + cql + " -> " + result.toString());
                }
            }
        }
        logger.info("Using existing keyspace " + keyspace);
    }

    /**
     * Load the lines of a CQL script containing one statement per line
     * into a list.
     * @param path The path of the CQL script.
     * @return The contents of the script.
     */
    public static List<String> loadScript(String path){
        List<String> result = new ArrayList<>();
        URL url = BasicServerCassandraTest.class.getResource(path);
        logger.info("Loading script from: " + url);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while((line = br.readLine()) != null){
                if(line.length() > 0 && !line.startsWith("#")){
                    result.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @AfterClass
    public static void tearDownAfterClass(){
        dropKeyspaceIfExists("testKs");
        closeCassandraConnection();
    }  

}
