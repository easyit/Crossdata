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

package com.stratio.meta2.core.validator.statements;


import com.stratio.meta.common.exceptions.IgnoreQueryException;
import com.stratio.meta.common.exceptions.ValidationException;
import com.stratio.meta2.common.data.CatalogName;
import com.stratio.meta2.core.query.BaseQuery;
import com.stratio.meta2.core.query.MetaDataParsedQuery;
import com.stratio.meta2.core.query.ParsedQuery;
import com.stratio.meta2.core.statements.CreateCatalogStatement;
import com.stratio.meta2.core.validator.BasicValidatorTest;
import com.stratio.meta2.core.validator.Validator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateCatalogStatementTest extends BasicValidatorTest {


  @Test
  public void createCatalogIfNotExists() {
      String query = "CREATE CATALOG IF NOT EXISTS new_catalog;";
      CreateCatalogStatement alterCatalogStatement=new CreateCatalogStatement("demo2",true,"");
      Validator validator=new Validator();

      BaseQuery baseQuery=new BaseQuery("createCatalogid",query, new CatalogName("demo2"));

      ParsedQuery parsedQuery=new MetaDataParsedQuery(baseQuery,alterCatalogStatement);
      try {
          validator.validate(parsedQuery);
          Assert.assertFalse(false);
      } catch (ValidationException e) {
          Assert.assertTrue(true,e.getMessage());
      } catch (IgnoreQueryException e) {
          Assert.assertTrue(true, e.getMessage());
      }

  }

  @Test
  public void createCatalogIfNotExistsWithExistingCatalog() {
      String query = "CREATE CATALOG IF NOT EXISTS demo;";
      CreateCatalogStatement createCatalogStatement=new CreateCatalogStatement("demo",true,"");
      Validator validator=new Validator();

      BaseQuery baseQuery=new BaseQuery("createCatalogid",query, new CatalogName("demo"));

      ParsedQuery parsedQuery=new MetaDataParsedQuery(baseQuery,createCatalogStatement);
      try {
          validator.validate(parsedQuery);
          Assert.assertFalse(false);
      } catch (ValidationException e) {
          Assert.fail(e.getMessage());
      } catch (IgnoreQueryException e) {
          Assert.assertTrue(true, e.getMessage());
      }

  }

  @Test
  public void createCatalogWithExistingCatalog() {
      String query = "CREATE CATALOG demo;";
      CreateCatalogStatement alterCatalogStatement=new CreateCatalogStatement("demo",false,"");
      Validator validator=new Validator();

      BaseQuery baseQuery=new BaseQuery("createCatalogid",query, new CatalogName("demo"));

      ParsedQuery parsedQuery=new MetaDataParsedQuery(baseQuery,alterCatalogStatement);
      try {
          validator.validate(parsedQuery);
          Assert.assertFalse(false);
      } catch (ValidationException e) {
          Assert.assertTrue(true,e.getMessage());
      } catch (IgnoreQueryException e) {
          Assert.assertTrue(true, e.getMessage());
      }

  }

  @Test
  public void createCatalogWithOptions() {
      String query = "CREATE CATALOG new_catalog WITH {\"comment\":\"This is a comment\"};";
      CreateCatalogStatement alterCatalogStatement=new CreateCatalogStatement("demo",true,"");
      Validator validator=new Validator();

      BaseQuery baseQuery=new BaseQuery("createCatalogid",query, new CatalogName("demo"));

      ParsedQuery parsedQuery=new MetaDataParsedQuery(baseQuery,alterCatalogStatement);
      try {
          validator.validate(parsedQuery);
          Assert.assertFalse(false);
      } catch (ValidationException e) {
          Assert.assertTrue(true, e.getMessage());
      } catch (IgnoreQueryException e) {
          Assert.assertTrue(true, e.getMessage());
      }

  }


}
