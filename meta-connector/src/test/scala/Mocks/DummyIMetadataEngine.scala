/*
 * Licensed to STRATIO (C) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  The STRATIO (C) licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package Mocks

import com.stratio.meta.common.connector._
import com.stratio.meta.common.result.QueryResult
import com.stratio.meta2.common.data.{CatalogName, ClusterName, TableName}
import com.stratio.meta2.common.metadata.{CatalogMetadata, IndexMetadata, TableMetadata}

/**
 * Created by carlos on 6/10/14.
 */
class DummyIMetadataEngine extends IMetadataEngine{
  override def createCatalog(targetCluster: ClusterName, catalogMetadata: CatalogMetadata): Unit = null

  override def createTable(targetCluster: ClusterName, tableMetadata: TableMetadata): Unit = {
      println("very slow function")
      for (i <- 1 to 5) {
        Thread.sleep(1000)
        println(i + " seconds gone by ----")
      }
      println("very Slow process (end)")
      QueryResult.createSuccessQueryResult()
  }

  override def createIndex(targetCluster: ClusterName, indexMetadata: IndexMetadata): Unit = null

  override def dropCatalog(targetCluster: ClusterName, name: CatalogName): Unit = null

  override def dropTable(targetCluster: ClusterName, name: TableName): Unit = null

  override def dropIndex(targetCluster: ClusterName, indexMetadata: IndexMetadata): Unit = null
}