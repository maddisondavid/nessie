/*
 * Copyright (C) 2020 Dremio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.projectnessie.versioned.persist.tx.postgres;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.projectnessie.versioned.persist.adapter.DatabaseAdapterFactory.Builder;
import org.projectnessie.versioned.persist.tests.AbstractTieredCommitsTest;
import org.projectnessie.versioned.persist.tx.local.ImmutableDefaultLocalDatabaseAdapterConfig;
import org.projectnessie.versioned.persist.tx.local.LocalDatabaseAdapterConfig;

@EnabledIfSystemProperty(named = "it.nessie.dbs", matches = ".*cockroach.*")
public class ITTieredCommitsCockroach
    extends AbstractTieredCommitsTest<LocalDatabaseAdapterConfig> {
  static ContainerFixture container =
      new ContainerFixture("cockroachdb/cockroach", "v21.1.6", "it.nessie.container.cockroach.tag");

  @BeforeAll
  static void startContainer() {
    container.setup();
  }

  @AfterAll
  static void stopContainer() {
    container.stop();
  }

  @Override
  protected String adapterName() {
    return "PostgreSQL";
  }

  @Override
  protected Builder<LocalDatabaseAdapterConfig> createAdapterBuilder() {
    return super.createAdapterBuilder()
        .withConfig(ImmutableDefaultLocalDatabaseAdapterConfig.builder().build());
  }

  @Override
  protected LocalDatabaseAdapterConfig configureDatabaseAdapter(LocalDatabaseAdapterConfig config) {
    return container.configureDatabaseAdapter(config);
  }
}