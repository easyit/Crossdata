/*
 * Licensed to STRATIO (C) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  The STRATIO (C) licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.03 at 12:15:47 AM CEST 
//

package com.stratio.meta2.common.api.generated.datastore;

import java.io.Serializable;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the generated package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class DataStoreFactory implements Serializable {

    private final static QName _DataStore_QNAME = new QName("", "DATASTORE");
    private static final long serialVersionUID = 5488190701482501301L;

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     */
    public DataStoreFactory() {
    }

    /**
     * Create an instance of {@link DataStoreType }
     */
    public DataStoreType createDataStoreType() {
        return new DataStoreType();
    }

    /**
     * Create an instance of {@link HostsType }
     */
    public HostsType createHostsType() {
        return new HostsType();
    }

    /**
     * Create an instance of {@link ClusterType }
     */
    public ClusterType createClusterType() {
        return new ClusterType();
    }

    /**
     * Create an instance of {@link OptionalPropertiesType }
     */
    public OptionalPropertiesType createOptionalPropertiesType() {
        return new OptionalPropertiesType();
    }

    /**
     * Create an instance of {@link PropertyType }
     */
    public PropertyType createPropertyType() {
        return new PropertyType();
    }

    /**
     * Create an instance of {@link RequiredPropertiesType }
     */
    public RequiredPropertiesType createRequiredPropertiesType() {
        return new RequiredPropertiesType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataStoreType }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "DATASTORE")
    public JAXBElement<DataStoreType> createDataStore(DataStoreType value) {
        return new JAXBElement<DataStoreType>(_DataStore_QNAME, DataStoreType.class, null, value);
    }

}
